/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.plugins;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import main.java.de.tw.ecm.toolkit.data.Entities;

/**
 * FXML Controller class
 *
 * @author twolf10
 */
public class DataSourceExplorerController extends AbstractUserController {

	@FXML
	private TreeView<String> treeView;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		this.setupTree();
	}

	private void setupTree() {
		Entities entities = this.selectedRepository.getEntities();
		TreeItem<String> rootItem = new TreeItem<String>(
				this.selectedRepository.getCaption());
		rootItem.setExpanded(true);
		for (int i = 0; i < entities.size(); i++) {
			TreeItem<String> item = new TreeItem<String>(entities.get(i)
					.getId());
			rootItem.getChildren().add(item);
		}

		this.treeView.setRoot(rootItem);
		this.context.put(this.treeView);
	}
}
