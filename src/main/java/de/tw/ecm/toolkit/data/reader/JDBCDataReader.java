package main.java.de.tw.ecm.toolkit.data.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;

public class JDBCDataReader extends AbstractDataReader {

	private Connection connection;

	private ResultSet rs;

	public JDBCDataReader(Connection connection, Entity entity) {
		this.connection = connection;
		this.entity = entity;
	}

	@Override
	public void open(String query) throws ReaderException {
		try {
			PreparedStatement prepareStatement = this.connection
					.prepareStatement(query);
			this.rs = prepareStatement.executeQuery();
		} catch (SQLException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public void close() throws ReaderException {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public DataRow readRow() throws ReaderException {
		try {
			int columnCount = rs.getMetaData().getColumnCount();
			Object[] result = new Object[columnCount];

			for (int i = 1; i <= columnCount; i++) {
				Object object = rs.getObject(i);
				result[i - 1] = object;
			}

			return new DataRow(result);
		} catch (SQLException e) {
			throw new ReaderException(e);
		}
	}

	public boolean next() throws ReaderException {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public DataHeader readHeaders() throws ReaderException {
		DataHeader header = new DataHeader();
		try {
			PreparedStatement prepareStatement = this.connection
					.prepareStatement("SELECT * FROM " + this.getEntity().getId());
			ResultSet rs = prepareStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);

				header.add(columnName, this.entity.getAttributes().getCaptionByName(columnName));
			}
		} catch (Exception e) {
			throw new ReaderException(e);
		}

		return header;
	}

	@Override
	public Entity getEntity() throws ReaderException {
		return this.entity;
	}
}
