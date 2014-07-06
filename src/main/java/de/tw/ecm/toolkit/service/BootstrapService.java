package main.java.de.tw.ecm.toolkit.service;

import javafx.concurrent.Task;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

public class BootstrapService extends AbstractService<Void> {

	private Repository selectedRepository;

	private Repositories repositories;

	public BootstrapService() {
		this.start();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateMessage("bootstraping...");
				repositories = new Repositories().build();
				String repositoryId = repositories.getDefault();
				selectedRepositoryById(repositoryId);
				
				return null;
			}
		};
	}

	public Repositories getRepositories() {
		try {
			if (repositories != null)
				return (Repositories) repositories.clone();
			else
				return null;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public Repository getSelectedRepository() {
		return selectedRepository;
	}

	public void selectedRepositoryByCaption(String caption) throws RepositoryException {
		this.selectedRepository = this.repositories.getByCaption(caption);
		this.initializeDataSource(this.selectedRepository);
		this.context.setSelectedRepository(selectedRepository);
	}

	public void selectedRepositoryById(String id) throws RepositoryException {
		this.selectedRepository = this.repositories.getById(id);
		this.initializeDataSource(this.selectedRepository);
		this.context.setSelectedRepository(selectedRepository);
	}

	public Repository getDefaultRepository() {
		String _default = this.repositories.getDefault();
		return this.repositories.getById(_default);
	}

	private void initializeDataSource(Repository repository) throws RepositoryException {
		try {
			Class<DataSource> dataSourceClass = repository.getDataSourceClass();
			this.dataSource = (DataSource) dataSourceClass.newInstance();
			this.dataSource.initialize(repository, repository.getECMProperties());
			this.context.setDataSource(dataSource);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
}
