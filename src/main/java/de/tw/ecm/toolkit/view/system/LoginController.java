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
import javafx.scene.layout.Pane;
import main.java.de.tw.ecm.toolkit.auth.User;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
import main.java.de.tw.ecm.toolkit.service.BootstrapService;
import main.java.de.tw.ecm.toolkit.service.LoginService;
import main.java.de.tw.ecm.toolkit.view.AbstractController;

/**
 * Login Controller.
 */
public class LoginController extends AbstractController {

	@FXML
	TextField txtUsername;
	@FXML
	PasswordField pwdPassword;
	@FXML
	Pane loginPane;
	@FXML
	ComboBox<String> cmbRepository;
	@FXML
	ProgressBar progressBar;
	@FXML
	Label lblStatus;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			final BootstrapService bootstrapService = new BootstrapService();
			this.cmbRepository.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						String selected = cmbRepository.getValue().toString();
						bootstrapService.selectedRepositoryByCaption(selected);
						selectedRepository = bootstrapService
								.getSelectedRepository();
						context.setSelectedRepository(selectedRepository);
					} catch (RepositoryException e1) {
						handleException(this, "initialize", e1);
					}
				}
			});

			bootstrapService
					.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent event) {
							lblStatus.setVisible(false);
							progressBar.setVisible(false);
							ObservableList<String> options = FXCollections
									.observableArrayList(bootstrapService
											.getRepositories()
											.getRepositoryCaptions());
							cmbRepository.setItems(options);
							// show the default repo as default in the combobox
							cmbRepository.setValue(bootstrapService
									.getDefaultRepository().getCaption());

							if (context.getCommandLine().hasOption("user"))
								setUsername(context.getCommandLine()
										.getOptionValue("user"));
							if (context.getCommandLine().hasOption("password"))
								setPassword(context.getCommandLine()
										.getOptionValue("password"));
						};
					});

			bootstrapService.setOnRunning(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					lblStatus.setVisible(true);
					lblStatus.textProperty().bind(
							bootstrapService.messageProperty());
					progressBar.progressProperty().bind(
							bootstrapService.progressProperty());
				}
			});

			bootstrapService.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent arg0) {
					lblStatus.setVisible(false);
					progressBar.setVisible(false);
					handleException(bootstrapService, "setOnFailed",
							bootstrapService.getException());
				}
			});
		} catch (Exception e) {
			this.handleException(this, "initialize", e);
		}
	}

	public void onLogin(ActionEvent event) {
		this.loginPane.setDisable(true);
		final User user = new User(txtUsername.getText(), pwdPassword.getText());
		final LoginService loginService = new LoginService(user);
		loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				context.setUser(user);
				viewContext.showMainView();
			}
		});

		loginService.setOnRunning(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				lblStatus.setVisible(true);
				progressBar.setVisible(true);
				lblStatus.textProperty().bind(loginService.messageProperty());
				progressBar.progressProperty().bind(
						loginService.progressProperty());
			}
		});

		loginService.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				loginPane.setDisable(false);
				lblStatus.setVisible(false);
				progressBar.setVisible(false);
				handleException(LoginController.class, "initialize", loginService.getException());
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
