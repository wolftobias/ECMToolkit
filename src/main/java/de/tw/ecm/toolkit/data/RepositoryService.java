package main.java.de.tw.ecm.toolkit.data;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RepositoryService extends Service<Repositories> {

	private static RepositoryService service;
	
	private Repositories repositories;
	
	public static synchronized RepositoryService getService() {
		if (service == null)
			service = new RepositoryService();
		return service;
	}

	private RepositoryService() {
		service.start();
	}

	@Override
	protected Task<Repositories> createTask() {
		return new Task<Repositories>() {
			this.repositories = 
			@Override
			protected Repositories call() throws Exception {
				return new Repositories().build();
			}
		};
	}
}
