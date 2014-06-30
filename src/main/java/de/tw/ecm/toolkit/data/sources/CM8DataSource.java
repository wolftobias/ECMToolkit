package main.java.de.tw.ecm.toolkit.data.sources;

import java.util.List;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;

public class CM8DataSource extends AbstractDataSource {

	public CM8DataSource() {
		// TODO Auto-generated constructor stub
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
	public void create(Entity entity, DataList list) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataReader read(Entity entity, String query)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataList readList(Entity entity, String query)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Entity entity, DataList list) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Entity entity, DataList list) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Entity entity, String sql) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String defaultSelectQuery(String table, List<String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Entity entity, DataRow row) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Entity entity, DataRow row) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}




}
