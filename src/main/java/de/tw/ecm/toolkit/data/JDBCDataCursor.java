package main.java.de.tw.ecm.toolkit.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDataCursor implements DataCursor {

	private ResultSet rs;

	protected JDBCDataCursor(ResultSet rs) {
		this.rs = rs;
	}

	@Override
	public boolean next() throws DataSourceException {
		try {
			return rs.next();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

	}

	@Override
	public void close() throws DataSourceException {
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

	}

	public Object get(int i) throws DataSourceException {
		try {
			return this.rs.getObject(i);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}
	
	public Object get(String columnLabel) throws DataSourceException {
		try {
			return this.rs.getObject(columnLabel);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}
}
