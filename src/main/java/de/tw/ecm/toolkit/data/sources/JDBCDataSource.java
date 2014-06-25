package main.java.de.tw.ecm.toolkit.data.sources;

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
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.JDBCDataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;

import com.sun.istack.internal.logging.Logger;

public class JDBCDataSource extends AbstractDataSource {

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
			this.entities = repository.getEntities();
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
	public DataReader read(String query) throws DataSourceException {
		try {
			DataReader reader = new JDBCDataReader(this.connection);
			reader.open(query);
			return reader;
		} catch (ReaderException e) {
			throw new DataSourceException(e);
		}
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
			DatabaseMetaData meta = this.connection.getMetaData();
			rs = meta.getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next()) {
				tablename = rs.getString("TABLE_NAME");
				entity = new Entity(this.repository, tablename);
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
		Attribute attribute;
		Attributes attributes = entity.new Attributes();
		PreparedStatement prepareStatement = this.connection
				.prepareStatement("SELECT * FROM " + entity.getId());
		ResultSet rs = prepareStatement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				attribute = attributes.new Attribute();
				String columnLabel = rsmd.getColumnLabel(i);
				attribute.setCaption(attribute.new Caption(columnLabel));
				String columnName = rsmd.getColumnName(i);
				attribute.setName(columnName);
				String columnClassName = rsmd.getColumnClassName(i);
				attribute.setType(columnClassName);
				String columnNativeType = rsmd.getColumnTypeName(i);
				attribute.setNativeType(columnNativeType);
				int columnDisplaySize = rsmd.getColumnDisplaySize(i);
				attribute.setSize(columnDisplaySize);

				attributes.add(attribute);
			}
			
			entity.setAttributes(attributes);
		} finally {
			rs.close();
		}
	}

	@Override
	public void create() throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object[] items) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object[] items) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}
}
