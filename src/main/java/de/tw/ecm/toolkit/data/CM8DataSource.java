package main.java.de.tw.ecm.toolkit.data;

import java.util.Properties;

import javafx.collections.ObservableList;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.prefs.Repository;

public class CM8DataSource extends AbstractDataSource {

	public CM8DataSource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(Repository repository, Properties properties)
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
	public Entities getEntities() throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
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