package main.java.de.tw.ecm.toolkit.data.sources;

import java.util.Properties;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;

public interface DataSource {

	public void initialize(Repository repository, Properties properties)
			throws DataSourceException;

	public void destroy() throws DataSourceException;

	public boolean login(String username, String password)
			throws DataSourceException;

	public void create() throws DataSourceException;

	public DataReader read(String query) throws DataSourceException;

	public DataList readList(String query) throws DataSourceException;

	public void update(Object[] items) throws DataSourceException;

	public void delete(Object[] items) throws DataSourceException;

	public String defaultSelectQuery(String table, String... attributes);

}
