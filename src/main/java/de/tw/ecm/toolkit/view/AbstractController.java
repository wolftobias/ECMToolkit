package main.java.de.tw.ecm.toolkit.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;
import main.java.de.tw.ecm.toolkit.Context;
import main.java.de.tw.ecm.toolkit.data.Repository;

import org.controlsfx.dialog.Dialogs;

/**
 * Login Controller.
 */
public abstract class AbstractController implements Initializable {

	protected static Logger log;

	protected Repository selectedRepository;

	protected Context context;

	protected URL location;

	protected ResourceBundle resources;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log = Logger.getLogger(this.getClass().getName());
		this.location = location;
		this.resources = resources;
		this.context = Context.context();
		this.context.put(this);
		
		try {
			this.selectedRepository = this.context.getSystemPrefs()
					.getRepositories().getSelectedRepository();
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	protected void handleException(Exception e) {
		log.log(Level.SEVERE, "An error was thrown!", e);
		Dialogs.create().showException(e);
	}
}