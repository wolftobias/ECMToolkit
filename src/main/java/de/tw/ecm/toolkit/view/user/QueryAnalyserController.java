/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.user;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;
import main.java.de.tw.ecm.toolkit.data.DataSourceException;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Entity.Attribute;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;

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
	private TableView tableView;

	private Entity selectedEntity;

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
							try {
								selectedEntity = selectedRepository
										.getEntities().getById(
												selectedItem.getValue());
								String query = selectedEntity
										.getSelectQuery(null);
								queryTextArea.setText(query);
								toolBar.setDisable(false);
							} catch (RepositoryException e) {
								handleException(e);
							}
						} else
							toolBar.setDisable(true);
					}

				});
		
	}

	public void onPlay(ActionEvent event) {
		try {
			String query = queryTextArea.getText();
			ObservableList data = this.selectedEntity.selectAsList(query);
			this.initDataTable(data);
		} catch (DataSourceException e) {
			this.handleException(e);
		}
	}

	private void initDataTable(ObservableList data) {
		TableColumn column;
		Attributes attributes = this.selectedEntity.getAttributes();
		
		for (int i = 0; i < attributes.size(); i++) {
			final Attribute attribute = attributes.get(i);
			final int counter = i;
			column = new TableColumn(attribute.getCaption());
			column.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty(param.getValue().get(
							counter).toString());
				}
			});

			this.tableView.getColumns().add(column);
		}
		
		this.tableView.setItems(data);
	}

	public void onImport(ActionEvent event) {

	}

	public void onExport(ActionEvent event) {

	}

	public void onQueryChoice(ActionEvent event) {

	}
}