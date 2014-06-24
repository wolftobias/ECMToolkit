package main.java.de.tw.ecm.toolkit.data;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

@XmlRootElement(name = "Repository")
public class Repository {

	private Class<DataSource> implementationClass;

	private String caption;

	private String id;

	private Properties properties = new Properties();

	private DataSource dataSource;

	public Repository() {
	}

	public Class<DataSource> getImplementationClass() {
		return implementationClass;
	}

	public void setImplementationClass(Class<DataSource> implementationClass) {
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void initialize() throws RepositoryException {
		try {
			this.dataSource = (DataSource) implementationClass.newInstance();
			this.dataSource.initialize(this, this.properties);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
}
