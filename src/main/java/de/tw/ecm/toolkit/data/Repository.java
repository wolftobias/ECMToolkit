package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

@XmlType(propOrder = { "id", "caption", "properties" })
public class Repository {

	private Class implementationClass;

	private String caption;

	private String id;
	
	private ECMProperties properties = new ECMProperties();

	private DataSource dataSource;

	private String dataSourceClassname;

	public Repository() {
	}

	@XmlTransient
	public Class<DataSource> getDataSourceClass() {
		return implementationClass;
	}

	public void setDataSourceClass(Class<DataSource> implementationClass) {
		this.implementationClass = implementationClass;
	}

	@XmlAttribute
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="property")
	public List<ECMProperty> getProperties() {
		return properties.getProperties();
	}

	public void setProperties(ECMProperties properties) {
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

	@XmlAttribute(name = "class")
	public String getDataSourceClassname() {
		return dataSourceClassname;
	}

	public void setDataSourceClassname(String dataSourceClassname) {
		this.dataSourceClassname = dataSourceClassname;
	}

	public void initialize() throws RepositoryException {
		try {
			this.implementationClass = Class.forName(dataSourceClassname);
			this.dataSource = (DataSource) implementationClass.newInstance();
			this.dataSource.initialize(this, this.properties);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
}
