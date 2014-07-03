package main.java.de.tw.ecm.toolkit.view.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
import main.java.de.tw.ecm.toolkit.data.sources.DataSourceException;
import main.java.de.tw.ecm.toolkit.service.RepositoryService;
import main.java.de.tw.ecm.toolkit.view.AbstractController;

import org.controlsfx.dialog.Dialogs;

/**
 * Login Controller.
 */
public class LoginController extends AbstractController {

	@FXML
	TextField txtUsername;
	@FXML
	PasswordField pwdPassword;
	@FXML
	Label lblErrorMessage;
	@FXML
	ComboBox<String> cmbRepository;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			final RepositoryService service = RepositoryService.getService();
			// final Repositories repositories = new Repositories().build();
			ObservableList<String> options = FXCollections
					.observableArrayList(service.getRepositories().getRepositoryCaptions());
			this.cmbRepository.setItems(options);
			this.cmbRepository.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String selected = cmbRepository.getValue().toString();
					service.setSelectedRepository(selected);
					selectedRepository = service.getSelectedRepository();
					try {
						selectedRepository.initialize();
					} catch (RepositoryException e1) {
						handleException(e1);
					}
					context.setSelectedRepository(selectedRepository);
				}
			});

			service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				public void handle(WorkerStateEvent event) {
					// show the default repo as default in the combobox
					cmbRepository.setValue(service.getDefaultRepository()
							.getCaption());

					if (context.getCommandLine().hasOption("user"))
						setUsername(context.getCommandLine().getOptionValue(
								"user"));
					if (context.getCommandLine().hasOption("password"))
						setPassword(context.getCommandLine().getOptionValue(
								"password"));
				};
			});
		} catch (Exception e) {
			Dialogs.create().showException(e);
		}
	}

	public void onLogin(ActionEvent event) {
		try {
			boolean login = this.selectedRepository.getDataSource().login(
					this.txtUsername.getText(), this.pwdPassword.getText());

			if (login) {
				this.context.getViewContext().showMainView();
			} else
				this.lblErrorMessage.setText("Login fehlgeschlagen!");
		} catch (DataSourceException e) {
			Dialogs.create().showException(e);
		}
	}

	public void onCancel(ActionEvent event) {
		System.exit(0);
	}

	public void setUsername(String username) {
		this.txtUsername.setText(username);
	}

	public void setPassword(String password) {
		this.pwdPassword.setText(password);
	}

}
