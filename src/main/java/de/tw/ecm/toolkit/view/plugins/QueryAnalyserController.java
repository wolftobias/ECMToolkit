/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.plugins;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import main.java.de.tw.ecm.toolkit.data.Attribute;
import main.java.de.tw.ecm.toolkit.data.Attributes;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.service.CRUDService;
import main.java.de.tw.ecm.toolkit.service.CRUDService.CreateService;
import main.java.de.tw.ecm.toolkit.service.CRUDService.DeleteService;
import main.java.de.tw.ecm.toolkit.service.CRUDService.UpdateService;
import main.java.de.tw.ecm.toolkit.service.FileService;
import main.java.de.tw.ecm.toolkit.service.FileService.WriteService;

/**
 * FXML Controller class
 *
 * @author twolf10
 */
public class QueryAnalyserController extends AbstractUserController {

	@FXML
	private Button playButton;

	@FXML
	private Button importButton;

	@FXML
	private Button exportButton;

	@FXML
	private ToolBar toolBar;

	@FXML
	private ChoiceBox<String> queryChoiceBox;

	@FXML
	private TextArea queryTextArea;

	@FXML
	private TableView<DataRow> tableView;

	private Entity selectedEntity;

	private File selectedFile = new File(System.getProperty("user.home"));

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		TreeView treeView = (TreeView) context.get(TreeView.class);
		treeView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue observable,
							Object oldValue, Object newValue) {
						TreeItem<String> selectedItem = (TreeItem<String>) newValue;
						if (selectedItem.isLeaf()) {
							String query = context.getDataSource()
									.defaultSelectQuery(
											selectedItem.getValue(), null);
							queryTextArea.setText(query);
							toolBar.setDisable(false);
							selectedEntity = selectedRepository.getEntities()
									.getById(selectedItem.getValue());
						} else
							toolBar.setDisable(true);
					}
				});
	}

	public void onPlay(ActionEvent event) {
		String query = queryTextArea.getText();
		final CRUDService.ReadService readListService = new CRUDService.ReadService(
				this.selectedEntity, query);
		readListService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				DataList dataList = readListService.getValue();
				initDataTable(dataList.toObservableList());
			}
		});
	}

	private void initDataTable(ObservableList<DataRow> data) {
		// first clear all items
		this.tableView.getColumns().clear();
		this.tableView.getSelectionModel().setSelectionMode(
				SelectionMode.MULTIPLE);

		TableColumn column;
		Attributes attributes = this.selectedEntity.getAttributes();

		for (int i = 0; i < attributes.size(); i++) {
			final Attribute attribute = attributes.get(i);
			final int counter = i;
			column = new TableColumn(attribute.getCaption().getText());
			column.setId(attribute.getName());
			column.setCellFactory(TextFieldTableCell.forTableColumn());
			column.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<ObservableList, String> param) {
					DataRow row = (DataRow) param.getValue();
					Object object = row.get(counter);
					if (object != null)
						return new SimpleStringProperty(object.toString());
					else
						return new SimpleStringProperty("");
				}
			});
			column.setOnEditCommit(new EventHandler<CellEditEvent<ObservableList, String>>() {
				@Override
				public void handle(CellEditEvent<ObservableList, String> table) {
					DataRow row = (DataRow) table.getTableView().getItems()
							.get(table.getTablePosition().getRow());
					String oldValue = table.getOldValue();
					String newValue = table.getNewValue();
					String columnId = table.getTableColumn().getId();

					DataList newList = selectedEntity.newList();
					// check for primary key column
					if (!newList.getEntity().getPrimaryKeys()
							.contains(columnId)) {
						int position = newList.getHeader()
								.getPosition(columnId);
						row.set(position, newValue);
						newList.addNew(row);

						UpdateService updateService = new UpdateService(
								selectedEntity, newList);
					}
				}
			});

			this.tableView.getColumns().add(column);
		}

		this.tableView.setItems(data);
	}

	public void onImport(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(this.resources
				.getString("import.fileChooser.title"));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV File", "*.csv"));
		fileChooser.setInitialDirectory(selectedFile);
		this.selectedFile = fileChooser.showOpenDialog(this.context
				.getRootWindow());

		final FileService.ReadService readService = new FileService.ReadService(
				selectedFile, selectedEntity);
		this.selectedFile = this.selectedFile.getParentFile();

		if (this.selectedFile != null) {
			readService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					DataList items = readService.getValue();
					final DataList newItems = selectedEntity.newList();
					newItems.setValues(items.toList());
					CreateService createService = new CreateService(
							selectedEntity, newItems);
					createService
							.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
								@Override
								public void handle(WorkerStateEvent event) {
									tableView.getItems().addAll(
											newItems.toObservableList());
								}
							});
				}
			});
		}
	}

	public void onExport(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(this.resources
				.getString("export.fileChooser.title"));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV File", "*.csv"));
		fileChooser.setInitialFileName(this.selectedEntity.getCaption()
				.getText());
		fileChooser.setInitialDirectory(selectedFile);
		this.selectedFile = fileChooser.showSaveDialog(this.context
				.getRootWindow());

		if (this.selectedFile != null) {
			ObservableList<DataRow> items = this.tableView.getItems();
			DataList list = selectedEntity.newList();
			list.setValues(items);

			WriteService writeFile = new WriteService(selectedFile,
					selectedEntity, list);

			this.selectedFile = this.selectedFile.getParentFile();
		}
	}

	public void onQueryChoice(ActionEvent event) {

	}

	public void onDeleteRow() {
		List<DataRow> selectedItems = this.tableView.getSelectionModel()
				.getSelectedItems();
		final DataList deleteItems = selectedEntity.newList();
		deleteItems.setValues(selectedItems);
		Service deleteService = new DeleteService(this.selectedEntity,
				deleteItems);
		deleteService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
				ObservableList<DataRow> items = tableView.getItems();
				items.removeAll(deleteItems.toList());
				tableView.getSelectionModel().clearSelection();
			};
		});
	}
}
