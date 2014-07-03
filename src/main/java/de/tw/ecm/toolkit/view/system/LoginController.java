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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
import main.java.de.tw.ecm.toolkit.service.BootstrapService;
import main.java.de.tw.ecm.toolkit.service.DataSourceLogin;
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
	
	@FXML
	ProgressBar progressBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			final BootstrapService service = BootstrapService.getService();
			this.cmbRepository.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String selected = cmbRepository.getValue().toString();
					service.setSelectedRepository(selected);
					selectedRepository = service.getSelectedRepository();
					try {
						selectedRepository.initialize();
						currentDataSource = selectedRepository.getDataSource();
					} catch (RepositoryException e1) {
						handleException(e1);
					}
					context.setSelectedRepository(selectedRepository);
				}
			});

			service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				public void handle(WorkerStateEvent event) {
					ObservableList<String> options = FXCollections
							.observableArrayList(service.getRepositories()
									.getRepositoryCaptions());
					cmbRepository.setItems(options);
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
		DataSourceLogin loginService = new DataSourceLogin(currentDataSource,
				this.txtUsername.getText(), this.pwdPassword.getText());
		loginService.start();
		loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (loginService.getValue()) {
					context.getViewContext().showMainView();
				} else
					lblErrorMessage.setText("Login fehlgeschlagen!");
			}
		});
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
