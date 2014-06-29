package main.java.de.tw.ecm.toolkit.data.sources;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;

public interface DataSource {

	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException;

	public void destroy() throws DataSourceException;

	public boolean login(String username, String password)
			throws DataSourceException;

	public Entities getEntities() throws DataSourceException;

	public void create(DataList list) throws DataSourceException;

	public DataReader read(String query) throws DataSourceException;

	public DataList readList(String query) throws DataSourceException;

	public void update(DataList list) throws DataSourceException;

	public void delete(DataList list) throws DataSourceException;

	public void delete(String sql) throws DataSourceException;

	public String defaultSelectQuery(String table, String... attributes);

}
