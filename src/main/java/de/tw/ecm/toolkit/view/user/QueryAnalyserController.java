/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.user;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes;
import main.java.de.tw.ecm.toolkit.data.reader.CSVDataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;
import main.java.de.tw.ecm.toolkit.data.sources.DataSourceException;
import main.java.de.tw.ecm.toolkit.data.writer.CSVDataWriter;
import main.java.de.tw.ecm.toolkit.data.writer.WriterException;

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
//							selectedEntity = selectedRepository.getEntities().getById(selectedItem.getValue());
							String query = selectedEntity.getSelectQuery(null);
							queryTextArea.setText(query);
							toolBar.setDisable(false);
						} else
							toolBar.setDisable(true);
					}
				});
	}

	public void onPlay(ActionEvent event) {
		try {
			String query = queryTextArea.getText();
			DataList dataList = this.selectedEntity.readList(query);
			this.initDataTable(dataList);
		} catch (DataSourceException e) {
			this.handleException(e);
		}
	}

	private void initDataTable(DataList data) {
		TableColumn column;
		Attributes attributes = this.selectedEntity.getAttributes();

		for (int i = 0; i < attributes.size(); i++) {
			final Attributes.Attribute attribute = attributes.get(i);
			final int counter = i;
			column = new TableColumn(attribute.getCaption().getText());
			column.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<ObservableList, String> param) {
					DataRow row = (DataRow) param.getValue();
					return new SimpleStringProperty(row.get(counter).toString());
				}
			});

			this.tableView.getColumns().add(column);
		}

		this.tableView.setItems(data.toObservableList());
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

		CSVDataReader csvDataReader = new CSVDataReader();
		try {
			csvDataReader.open(this.selectedFile);
			this.selectedFile = this.selectedFile.getParentFile();

			String[] headers = csvDataReader.getHeaders();
			DataRow row = csvDataReader.readRow();
		} catch (ReaderException e) {
			this.handleException(e);
		}
	}

	public void onExport(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(this.resources
				.getString("export.fileChooser.title"));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV File", "*.csv"));
		fileChooser.setInitialFileName(this.selectedEntity.getCaption().getText());
		fileChooser.setInitialDirectory(selectedFile);
		this.selectedFile = fileChooser.showSaveDialog(this.context
				.getRootWindow());

		CSVDataWriter csvDataWriter = new CSVDataWriter();
		if (this.selectedFile != null) {
			try {
				ObservableList items = this.tableView.getItems();
				csvDataWriter.open(this.selectedFile);
				this.selectedFile = this.selectedFile.getParentFile();

				String[] captions = this.selectedEntity.getAttributes()
						.getCaptions();
				csvDataWriter.writeHeader(captions);

				for (int i = 0; i < items.size(); i++) {
					ObservableList object = (ObservableList) items.get(i);
					csvDataWriter.writeRow(object.toArray());
				}
			} catch (WriterException e) {
				this.handleException(e);
			} finally {
				try {
					csvDataWriter.close();
				} catch (WriterException e) {
				}
			}
		}
	}

	public void onQueryChoice(ActionEvent event) {

	}
}
