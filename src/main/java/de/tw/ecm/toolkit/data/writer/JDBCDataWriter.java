package main.java.de.tw.ecm.toolkit.data.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.PrimaryKeys;

public class JDBCDataWriter implements DataWriter {

	private Entity entity;

	private DataList dataList;

	private Connection connection;

	private PreparedStatement pstmt;

	public JDBCDataWriter(Entity entity, DataList dataList,
			Connection connection) {
		this.entity = entity;
		this.dataList = dataList;
		this.connection = connection;
	}

	@Override
	public void open(String sql) throws WriterException {
		try {
			this.connection.setAutoCommit(false);
			this.pstmt = this.connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void close() throws WriterException {
		try {
			this.pstmt.executeBatch();
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void createRow(DataRow dataRow) throws WriterException {
		try {
			for (int j = 0; j < dataRow.size(); j++) {
				pstmt.setObject(j + 1, dataRow.get(j));
			}
			pstmt.addBatch();
		} catch (SQLException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void updateRow(DataRow dataRow) throws WriterException {
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();
		int parameterIndex = 1;
		int len = dataRow.size() - entity.getPrimaryKeys().size();

		try {
			for (int j = 0; j <= len; j++) {
				int primaryKeyPos = dataList.primaryKeyPos();
				if (j != primaryKeyPos)
					pstmt.setObject(parameterIndex++, dataRow.get(j));
			}

			for (int k = 0; k < primaryKeys.size(); k++) {
				int position = dataList.getHeader().getPosition(
						primaryKeys.get(k));
				Object object = dataRow.get(position);
				pstmt.setObject(parameterIndex++, object);
			}
			pstmt.addBatch();
		} catch (SQLException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void deleteRow(DataRow dataRow) throws WriterException {
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();

		try {
			for (int j = 0; j < primaryKeys.size(); j++) {
				int position = dataList.getHeader().getPosition(primaryKeys.get(j));
				pstmt.setObject(j + 1, dataRow.get(position));
			}
			pstmt.addBatch();
		} catch (SQLException e) {
			throw new WriterException(e);
		}
	}
}
