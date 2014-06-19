package main.java.de.tw.ecm.toolkit.data;

import java.util.Properties;

import javafx.collections.ObservableList;

public interface DataSource {

	public void initialize(Repository repository, Properties properties)
			throws DataSourceException;
	
	public void destroy() throws DataSourceException;
	
	public boolean login(String username, String password)
			throws DataSourceException;

	public Entities getEntities() throws DataSourceException;

	public DataCursor select(String query) throws DataSourceException;

	public ObservableList selectAsList(String query) throws DataSourceException;

	public String defaultSelectQuery(String table, String... attributes);

}
