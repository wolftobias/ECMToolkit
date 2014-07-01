package main.java.de.tw.ecm.toolkit.data.sources;

import java.util.List;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;

public interface DataSource {

	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException;

	public void destroy() throws DataSourceException;

	public boolean login(String username, String password)
			throws DataSourceException;

	public Entities getEntities() throws DataSourceException;

	public void create(Entity entity, DataList list) throws DataSourceException;

	public DataReader read(Entity entity, String query)
			throws DataSourceException;

	public DataList readList(Entity entity, String query)
			throws DataSourceException;

	public void update(Entity entity, DataList list) throws DataSourceException;

	public void delete(Entity entity, DataList list) throws DataSourceException;

	public void delete(Entity entity, String sql) throws DataSourceException;

	public String defaultSelectQuery(String table, List<String> headers);

}
