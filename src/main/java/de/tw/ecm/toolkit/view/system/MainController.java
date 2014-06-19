/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import main.java.de.tw.ecm.toolkit.view.AbstractController;

/**
 * FXML Controller class
 *
 * @author twolf10
 */
public class MainController extends AbstractController {

	@FXML
	private CheckMenuItem mnFullScreen;

	@FXML
	private TabPane navigationTabPane;

	@FXML
	private TabPane contentTabPane;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@FXML
	void onQuit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void onFullScreen(ActionEvent event) {
		this.context.getViewContext().setFullScreen(
				this.mnFullScreen.isSelected());
	}

	public void addNavigationTab(String caption, Pane navigationPane) {
		Tab tab = new Tab(caption);
		tab.setContent(navigationPane);
		this.navigationTabPane.getTabs().add(tab);
	}
	
	public void addContentTab(String caption, Pane contentPane) {
		contentPane.autosize();
		Tab tab = new Tab(caption);
		tab.setContent(contentPane);
		this.contentTabPane.getTabs().add(tab);
	}
}
