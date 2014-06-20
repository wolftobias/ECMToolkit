package main.java.de.tw.ecm.toolkit.data;

import java.util.Properties;

import javafx.collections.ObservableList;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.prefs.Repository;

public interface DataSource {

	public void initialize(Repository repository, Properties properties)
			throws DataSourceException;

	public void destroy() throws DataSourceException;

	public boolean login(String username, String password)
			throws DataSourceException;

	public Entities getEntities() throws DataSourceException;

	public void create() throws DataSourceException;

	public DataReader read(String query) throws DataSourceException;

	public ObservableList readAsList(String query) throws DataSourceException;

	public void update(Object[] items) throws DataSourceException;

	public void delete(Object[] items) throws DataSourceException;

	public String defaultSelectQuery(String table, String... attributes);

}
