package main.java.de.tw.ecm.toolkit.data;

import java.util.Properties;

public class Repository {

	private Class implementationClass;

	private String caption;

	private String id;

	private Properties properties = new Properties();

	private DataSource dataSource;

	public Repository() {
		// TODO Auto-generated constructor stub
	}

	public Class getImplementationClass() {
		return implementationClass;
	}

	public void setImplementationClass(Class implementationClass) {
		this.implementationClass = implementationClass;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	public void initialize() throws RepositoryException {
		try {
			this.dataSource = (DataSource) implementationClass.newInstance();
			this.dataSource.initialize(this, this.properties);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	public void destroy() throws RepositoryException {
		try {
			this.dataSource.destroy();
		} catch (DataSourceException e) {
			throw new RepositoryException(e);
		}
	}
	
	public boolean login(String username, String password)
			throws RepositoryException {
		try {
			return this.dataSource.login(username, password);
		} catch (DataSourceException e) {
			throw new RepositoryException(e);
		}
	}

	public Entities getEntities() throws RepositoryException {
		try {
			return this.dataSource.getEntities();
		} catch (DataSourceException e) {
			throw new RepositoryException(e);
		}
	}
}
