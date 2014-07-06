package main.java.de.tw.ecm.toolkit.view;

import static org.apache.commons.lang3.Validate.notNull;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.Initializable;
import main.java.de.tw.ecm.toolkit.Context;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

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
			this.selectedRepository = this.context.getSelectedRepository();
		} catch (Exception e) {
			this.handleException(this, "<init>", e);
		}
	}

	protected void handleException(Object source, String sourceMethod,
			Throwable thrown) {
		notNull(source);
		notNull(thrown);

		this.handleException(source.getClass().getName(), sourceMethod, thrown);
	}

	protected void handleException(Class sourceClass, String sourceMethod,
			Throwable thrown) {
		notNull(sourceClass);
		notNull(thrown);

		this.handleException(sourceClass.getName(), sourceMethod, thrown);
	}

	protected void handleException(String sourceClass, String sourceMethod,
			Throwable thrown) {
		log.throwing(sourceClass, sourceMethod, thrown);
		Dialogs.create().showException(thrown);
	}

}
