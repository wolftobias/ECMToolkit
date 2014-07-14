package main.java.de.tw.ecm.toolkit.service;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import main.java.de.tw.ecm.toolkit.Context;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

import org.controlsfx.dialog.Dialogs;

public abstract class AbstractService<V> extends Service<V> {

	protected static Logger log;

	protected Context context = Context.context();

	protected DataSource dataSource;

	protected Repository selectedRepository;

	public AbstractService() {
		log = Logger.getLogger(this.getClass().getName());
		this.selectedRepository = context.getSelectedRepository();
		this.dataSource = context.getDataSource();
		this.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleException(this, "setOnFailed", getException());
			}
		});
		this.setOnRunning(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {

			}
		});

		this.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(
					ObservableValue<? extends State> observableValue,
					State oldSate, State newState) {
				if (newState != null
						&& (newState.equals(State.CANCELLED)
								|| newState.equals(State.SUCCEEDED) || newState
									.equals(State.FAILED))) {
					context.runningServices().remove(AbstractService.this);
				}
				if (newState != null && (newState.equals(State.RUNNING))) {
					context.runningServices().add(AbstractService.this);
				}
			}
		});
	}

	protected void handleException(Object source, String sourceMethod,
			Throwable thrown) {
		notNull(source);
		notNull(thrown);

		this.handleException(source.getClass().getName(), sourceMethod, thrown);
	}

	protected void handleException(String sourceClass, String sourceMethod,
			Throwable thrown) {
		log.throwing(sourceClass, sourceMethod, thrown);
		Dialogs.create().showException(thrown);
	}
}
