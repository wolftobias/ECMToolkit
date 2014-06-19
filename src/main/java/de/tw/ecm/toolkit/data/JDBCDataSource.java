package main.java.de.tw.ecm.toolkit.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.de.tw.ecm.toolkit.data.Entity.Attribute;

import com.sun.istack.internal.logging.Logger;

public class JDBCDataSource implements DataSource {

	Logger log = Logger.getLogger(JDBCDataSource.class);

	public static final String DRIVER = "driver";
	public static final String URL = "url";

	private Repository repository;
	private Connection connection;
	private String driver;
	private String url;
	private Entities entities;

	public JDBCDataSource() {
	}

	@Override
	public void initialize(Repository repository, Properties properties)
			throws DataSourceException {
		try {
			this.repository = repository;
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
	public DataCursor select(String query) throws DataSourceException {
		ResultSet rs = null;
		DataCursor cursor;
		try {
			PreparedStatement prepareStatement = this.connection
					.prepareStatement(query);
			rs = prepareStatement.executeQuery();
			cursor = new JDBCDataCursor(rs);
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}

		return cursor;
	}

	@Override
	public ObservableList selectAsList(String query) throws DataSourceException {
		ResultSet rs = null;
		ObservableList data = FXCollections.observableArrayList();

		try {
			PreparedStatement prepareStatement = this.connection
					.prepareStatement(query);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				ObservableList row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getObject(i));
				}
				data.add(row);
			}
		} catch (SQLException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(rs);
		}

		return data;
	}

	@Override
	public String defaultSelectQuery(String table, String... attributes) {
		return JdbcDataSourceUtil.createSelectStatement(table, attributes);
	}

	@Override
	public void destroy() throws DataSourceException {
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new DataSourceException(e);
		}
	}

	private void closeQuietly(ResultSet rs) throws DataSourceException {
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
			DatabaseMetaData meta = this.connection.getMetaData();
			rs = meta.getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next()) {
				tablename = rs.getString("TABLE_NAME");
				entity = new Entity(this.repository, this, tablename);
				this.readColumnInfo(entity);
				entities.add(entity);
			}

		} catch (SQLException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(rs);
		}
	}

	private void readColumnInfo(Entity entity) throws SQLException {
		Attribute attr;
		PreparedStatement prepareStatement = this.connection
				.prepareStatement("SELECT * FROM " + entity.getId());
		ResultSet rs = prepareStatement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				attr = entity.new Attribute();
				String columnLabel = rsmd.getColumnLabel(i);
				attr.setCaption(columnLabel);
				String columnName = rsmd.getColumnName(i);
				attr.setName(columnName);
				String columnClassName = rsmd.getColumnClassName(i);
				attr.setType(columnClassName);
				String columnNativeType = rsmd.getColumnTypeName(i);
				attr.setNativeType(columnNativeType);
				int columnDisplaySize = rsmd.getColumnDisplaySize(i);
				attr.setSize(columnDisplaySize);

				entity.addAttribute(attr);
			}
		} finally {
			rs.close();
		}
	}
}