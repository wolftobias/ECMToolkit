package main.java.de.tw.ecm.toolkit.data.sources;

import java.util.List;

import main.java.de.tw.ecm.toolkit.auth.User;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.writer.DataWriter;

public class CM8DataSource extends AbstractDataSource {

	@Override
	public void destroy() throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(User user) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entities getEntities() throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataReader read(Entity entity, String query)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWriter create(Entity entity, DataList dataList)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWriter update(Entity entity, DataList dataList)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWriter delete(Entity entity, DataList dataList)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit() throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String defaultSelectQuery(String table, List<String> headers) {
		// TODO Auto-generated method stub
		return null;
	}


}
