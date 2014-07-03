package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;

public class RepositoryService extends Service<Repositories> {

	private static RepositoryService service;

	private Repository selected;

	private Repositories repositories;

	public static synchronized RepositoryService getService() {
		if (service == null)
			service = new RepositoryService();
		return service;
	}

	private RepositoryService() {
		start();
		setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				repositories = service.getValue();
			}
		});
	}

	@Override
	protected Task<Repositories> createTask() {
		return new Task<Repositories>() {
			@Override
			protected Repositories call() throws Exception {
				return new Repositories().build();
			}
		};
	}

	public Repositories getRepositories() {
		try {
			return (Repositories) repositories.clone();
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
