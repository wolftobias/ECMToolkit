package main.java.de.tw.ecm.toolkit.data.sources;

import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;

public class CM8DataSource extends AbstractDataSource {

	public CM8DataSource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws DataSourceException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login(String username, String password)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create() throws DataSourceException {
		// TODO Auto-generated method stub

	}

	@Override
	public DataReader read(String query) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object[] items) throws DataSourceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object[] items) throws DataSourceException {
		// TODO Auto-generated method stub

	}

	@Override
	public String defaultSelectQuery(String table, String... attributes) {
		// TODO Auto-generated method stub
		return null;
	}
}
