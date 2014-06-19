package main.java.de.tw.ecm.toolkit.view.system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.de.tw.ecm.toolkit.ECMToolkit;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
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
	ComboBox cmbRepository;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			final Repositories repositories = this.context.getSystemPrefs().getRepositories();
			ObservableList<String> options = FXCollections
					.observableArrayList(repositories.getRepositoryNames());
			this.cmbRepository.setItems(options);
			this.cmbRepository.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String selected = cmbRepository.getValue().toString();
					repositories.setSelectedRepository(selected);
				}
			});

			// show the default repo as default in the combobox
			this.cmbRepository.setValue(repositories.getDefaultRepository()
					.getCaption());
			this.selectedRepository = repositories.getSelectedRepository();
			this.selectedRepository.initialize();
			
			if(this.context.getCommandLine().hasOption("user"))
				this.setUsername(this.context.getCommandLine().getOptionValue("user"));
			if(this.context.getCommandLine().hasOption("password"))
				this.setPassword(this.context.getCommandLine().getOptionValue("password"));
			
		} catch (Exception e) {
			Dialogs.create().showException(e);
		}
	}
	
	public void onLogin(ActionEvent event) {
		try {
			boolean login = this.selectedRepository.login(this.txtUsername.getText(),
					this.pwdPassword.getText());

			if (login) {
				this.context.setSelectedRepository(selectedRepository);
				this.context.getViewContext().showMainView();
			}
			else
				this.lblErrorMessage.setText("Login fehlgeschlagen!");
		} catch (RepositoryException e) {
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
