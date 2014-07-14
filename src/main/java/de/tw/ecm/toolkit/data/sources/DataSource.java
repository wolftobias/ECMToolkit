package main.java.de.tw.ecm.toolkit.data.sources;

import java.util.List;

import main.java.de.tw.ecm.toolkit.auth.User;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.writer.DataWriter;

public interface DataSource {

	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException;

	public void destroy() throws DataSourceException;

	public void login(User user) throws DataSourceException;

	public Entities getEntities() throws DataSourceException;

	public DataReader read(Entity entity, String query)
			throws DataSourceException;

	public DataWriter create(Entity entity, DataList dataList)
			throws DataSourceException;

	public DataWriter update(Entity entity, DataList dataList)
			throws DataSourceException;

	public DataWriter delete(Entity entity, DataList dataList)
			throws DataSourceException;

	public void commit() throws DataSourceException;

	public void rollback() throws DataSourceException;

	public String defaultSelectQuery(String table, List<String> headers);
}
