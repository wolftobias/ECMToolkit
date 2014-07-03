package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;

public class BootstrapService extends Service<Void> {

	private static BootstrapService service;

	private Repository selected;

	private Repositories repositories;

	public static synchronized BootstrapService getService() {
		if (service == null)
			service = new BootstrapService();
		return service;
	}

	private BootstrapService() {
		start();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				repositories = new Repositories().build();
				return null;
			}
		};
	}

	public Repositories getRepositories() {
		try {
			if(repositories != null)
				return (Repositories) repositories.clone();
			else
				return null;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public Repository getSelectedRepository() {
		return selected;
	}

	public void setSelectedRepository(String selected) {
		this.selected = this.repositories.getByCaption(selected);
	}

	public Repository getDefaultRepository() {
		String _default = this.repositories.getDefault();
		return this.repositories.getById(_default);
	}

}
