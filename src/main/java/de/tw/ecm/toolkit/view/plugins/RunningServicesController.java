/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.view.plugins;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import main.java.de.tw.ecm.toolkit.service.AbstractService;

/**
 * FXML Controller class
 *
 * @author twolf10
 */
public class RunningServicesController extends AbstractUserController {

	@FXML
	private ListView<AbstractService<?>> taskList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		taskList.setItems(context.runningServices());
		taskList.setCellFactory(new Callback<ListView<AbstractService<?>>, ListCell<AbstractService<?>>>() {
			@Override
			public ListCell<AbstractService<?>> call(
					ListView<AbstractService<?>> param) {
				return new TaskListCell();
			}
		});

	}
	
	
	private class TaskListCell extends ListCell<AbstractService<?>> {
		HBox hbox = new HBox();
		Label title = new Label();
		ProgressBar progressBar = new ProgressBar();
		Button button = new Button("x");

		public TaskListCell() {
			super();
			progressBar.setMaxWidth(Double.MAX_VALUE);
			hbox.setSpacing(5);
			hbox.getChildren().addAll(title, progressBar, button);
			HBox.setHgrow(progressBar, Priority.ALWAYS);
		}

		@Override
		protected void updateItem(final AbstractService<?> service, boolean empty) {
			super.updateItem(service, empty);
			if (!empty) {
				this.title.textProperty().bind(service.messageProperty());
				this.progressBar.progressProperty()
						.bind(service.progressProperty());
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						service.cancel();
					}
				});
				setGraphic(hbox);
			} else {
				setGraphic(null);
			}
		}
	}	
}
