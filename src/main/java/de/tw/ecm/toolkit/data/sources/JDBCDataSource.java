package main.java.de.tw.ecm.toolkit.data.sources;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.de.tw.ecm.toolkit.data.Attribute;
import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;
import main.java.de.tw.ecm.toolkit.data.Attributes;
import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.PrimaryKeys;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.JDBCDataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;

import com.sun.istack.internal.logging.Logger;

public class JDBCDataSource extends AbstractDataSource {

	Logger log = Logger.getLogger(JDBCDataSource.class);

	public static final String DRIVER = "driver";
	public static final String URL = "url";

	private Connection connection;
	private String driver;
	private String url;
	private Entities entities;

	public JDBCDataSource() {
	}

	@Override
	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException {
		super.initialize(repository, properties);
		try {
			this.driver = properties.getProperty(DRIVER);
			this.url = properties.getProperty(URL);
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public boolean login(String username, String password)
			throws DataSourceException {
		try {
			connection = DriverManager.getConnection(url + "?user=" + username
					+ "&password=" + password);
			return true;
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public Entities getEntities() throws DataSourceException {
		if (this.entities == null)
			this.createEntities();

		return this.entities;
	}

	@Override
	public DataReader read(Entity entity, String query)
			throws DataSourceException {
		try {
			DataReader reader = new JDBCDataReader(this.connection, entity);
			reader.open(query);
			return reader;
		} catch (ReaderException e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public String defaultSelectQuery(String table, List<String> headers) {
		String queryString = "SELECT ";

		if (headers != null && headers.size() > 0) {
			for (int i = 0; i < headers.size(); i++) {
				queryString += headers.get(i);
				queryString += ", ";
			}
		} else
			queryString += "*";

		queryString += " FROM " + table;
		return queryString.toUpperCase();
	}

	private String insertQuery(String table, List<String> headers) {
		String sql = "INSERT INTO " + table + " (%s) VALUES (%s)";
		String columns = "";
		String values = "";

		for (int i = 0; i < headers.size(); i++) {
			columns += headers.get(i);
			values += "?";

			if (i < headers.size() - 1) {
				columns += ", ";
				values += ", ";
			}
		}

		return String.format(sql, columns, values);
	}

	private String updateQuery(Entity entity, List<String> headers) {
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();
		String sql = "UPDATE " + entity.getId() + " SET ";
		String columns = "";

		for (int i = 0; i < headers.size(); i++) {
			String header = headers.get(i);
			
			if(!primaryKeys.contains(header)) {
				columns += header + " = ?";
	
				if (i < headers.size() - 1) {
					columns += ", ";
				}
			}
		}
		
		sql += columns;
		sql += " WHERE ";
		
		for (int j = 0; j < primaryKeys.size(); j++) {
			sql += primaryKeys.get(j) + " = ?";

			if (j < primaryKeys.size() - 1) {
				sql += " AND ";
			}
		}
		
		return sql;
	}

	private String deleteQuery(Entity entity) {
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();
		String sql = "DELETE FROM " + entity.getId() + " WHERE ";

		for (int j = 0; j < primaryKeys.size(); j++) {
			sql += primaryKeys.get(j) + " = ?";

			if (j < primaryKeys.size() - 1) {
				sql += " AND ";
			}
		}

		return sql;
	}

	@Override
	public void destroy() throws DataSourceException {
		try {
			if (this.connection != null)
				this.connection.close();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	private void closeQuietly(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			log.logSevereException(e);
		}
	}

	private void createEntities() throws DataSourceException {
		ResultSet rs = null;
		String tablename;
		Entity entity;

		try {
			this.entities = new Entities();
			DatabaseMetaData metaData = this.connection.getMetaData();
			rs = metaData.getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next()) {
				tablename = rs.getString("TABLE_NAME");
				entity = new Entity(tablename);
				PrimaryKeys primaryKeys = this.readPrimaryKeys(metaData,
						tablename);
				entity.setPrimaryKeys(primaryKeys);
				this.readColumnInfo(entity, primaryKeys);
				entities.add(entity);
			}

		} catch (SQLException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(rs);
		}
	}

	private void readColumnInfo(Entity entity, PrimaryKeys primaryKeys)
			throws SQLException {
		Attribute attribute;
		Attributes attributes = new Attributes();
		PreparedStatement prepareStatement = this.connection
				.prepareStatement("SELECT * FROM " + entity.getId());
		ResultSet rs = prepareStatement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				attribute = new Attribute();
				String columnLabel = rsmd.getColumnLabel(i);
				attribute.setCaption(new Caption(columnLabel));
				String columnName = rsmd.getColumnName(i);
				attribute.setName(columnName);
				String columnClassName = rsmd.getColumnClassName(i);
				attribute.setType(columnClassName);
				String columnNativeType = rsmd.getColumnTypeName(i);
				attribute.setNativeType(columnNativeType);
				int columnDisplaySize = rsmd.getColumnDisplaySize(i);
				attribute.setSize(columnDisplaySize);

				for (String primaryKey : primaryKeys) {
					if (attribute.getName().equals(primaryKey))
						attribute.setPrimaryKey(true);
				}

				attributes.add(attribute);
			}

			entity.setAttributes(attributes);
		} finally {
			closeQuietly(rs);
		}
	}

	private PrimaryKeys readPrimaryKeys(DatabaseMetaData metaData, String table)
			throws SQLException {
		PrimaryKeys primaryKeys = new PrimaryKeys();
		ResultSet rs = metaData.getPrimaryKeys(null, null, table);
		try {
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				primaryKeys.add(columnName);
			}
		} finally {
			closeQuietly(rs);
		}
		return primaryKeys;
	}

	@Override
	public void create(Entity entity, DataList dataList)
			throws DataSourceException {
		List<String> headers = dataList.getHeaderNames();
		String sql = this.insertQuery(dataList.getEntity().getId(), headers);

		try {
			this.connection.setAutoCommit(false);
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			for (int i = 0; i < dataList.size(); i++) {
				DataRow dataRow = dataList.get(i);
				for (int j = 0; j < dataRow.size(); j++) {
					pstmt.setObject(j + 1, dataRow.get(j));
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public void update(Entity entity, DataList dataList)
			throws DataSourceException {
		List<String> headers = dataList.getHeaderNames();
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();
		String sql = this.updateQuery(dataList.getEntity(), headers);

		try {
			this.connection.setAutoCommit(false);
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			for (int i = 0; i < dataList.size(); i++) {
				DataRow dataRow = dataList.get(i);
				int parameterIndex = 1;
				for (int j = 0; j < dataRow.size(); j++) {
					int primaryKeyPos = dataList.primaryKeyPos();
					if(j != primaryKeyPos)
						pstmt.setObject(parameterIndex++, dataRow.get(j));
				}
				
				for (int j = 0; j < primaryKeys.size(); j++) {
					int position = dataList.getHeader().getPosition(primaryKeys.get(j));
					Object object = dataRow.get(position);
					pstmt.setObject(parameterIndex++, object);
				}				
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public void delete(Entity entity, DataList dataList)
			throws DataSourceException {
		String sql = this.deleteQuery(entity);
		DataHeader header = dataList.getHeader();
		PrimaryKeys primaryKeys = entity.getPrimaryKeys();

		try {
			this.connection.setAutoCommit(false);
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			for (int i = 0; i < dataList.size(); i++) {
				DataRow dataRow = dataList.get(i);
				for (int j = 0; j < primaryKeys.size(); j++) {
					int position = header.getPosition(primaryKeys.get(j));
					pstmt.setObject(j + 1, dataRow.get(position));
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public void delete(Entity entity, String sql) throws DataSourceException {
		// TODO Auto-generated method stub

	}
}
