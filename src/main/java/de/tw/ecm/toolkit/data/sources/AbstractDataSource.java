package main.java.de.tw.ecm.toolkit.data.sources;

import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Repository;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataSource implements DataSource {

	Logger log = Logger.getLogger(AbstractDataSource.class);

	protected Repository repository;

	protected ECMProperties properties;

	@Override
	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException {
		this.repository = repository;
		this.properties = properties;
	}
}
