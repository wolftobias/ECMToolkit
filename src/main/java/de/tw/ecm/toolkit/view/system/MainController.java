/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.system;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Duration;
import main.java.de.tw.ecm.toolkit.service.EntityService;
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

	@FXML
	private TabPane monitoringTabPane;

	@FXML
	private Label lblUser;

	@FXML
	private Label lblDateTime;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		new EntityService();

		String userid = this.context.getUser().getUserId();
		String repository = this.context.getSelectedRepository().getCaption();
		this.lblUser.setText(userid + "@" + repository);

		DateTimeService dateTimeService = new DateTimeService();
		lblDateTime.textProperty().bind(dateTimeService.messageProperty());
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

	public void addNavigationTab(String caption, Node navigationPane) {
		Tab tab = new Tab(caption);
		tab.setContent(navigationPane);
		this.navigationTabPane.getTabs().add(tab);
	}

	public void addContentTab(String caption, Node contentPane) {
		contentPane.autosize();
		Tab tab = new Tab(caption);
		tab.setContent(contentPane);
		this.contentTabPane.getTabs().add(tab);
	}

	public void addMonitoringTab(String caption, Node contentPane) {
		monitoringTabPane.autosize();
		Tab tab = new Tab(caption);
		tab.setContent(contentPane);
		this.monitoringTabPane.getTabs().add(tab);
	}

	private class DateTimeService extends ScheduledService<Void> {

		public DateTimeService() {
			this.setDelay(Duration.seconds(1));
			this.start();
		}

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (true) {
						String currentDate = SimpleDateFormat
								.getDateTimeInstance().format(new Date());
						updateMessage(currentDate);

						Thread.sleep(1000);
					}
				}
			};
		}
	}
}
