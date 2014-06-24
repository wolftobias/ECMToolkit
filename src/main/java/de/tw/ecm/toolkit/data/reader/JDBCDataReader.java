package main.java.de.tw.ecm.toolkit.data.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;

public class JDBCDataReader extends AbstractDataReader {

	private Connection connection;

	private ResultSet rs;

	public JDBCDataReader(Connection connection) {
		this.connection = connection;
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
}
