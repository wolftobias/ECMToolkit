package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.ECMProperties.ECMProperty;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;

import org.apache.commons.lang3.builder.Builder;

@XmlType(propOrder = { "id", "caption", "properties", "entities" })
public class Repository implements Builder<Repository> {

	private Class dataSourceClass;

	private String caption;

	private String id;

	private ECMProperties properties = new ECMProperties();

	private String dataSourceClassname;

	private Entities entities;

	public Repository() {
	}

	@XmlTransient
	public Class<DataSource> getDataSourceClass() {
		return dataSourceClass;
	}

	public void setDataSourceClass(Class dataSourceClass) {
		this.dataSourceClass = dataSourceClass;
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

	@XmlElement(name = "property")
	public List<ECMProperty> getProperties() {
		return properties.getProperties();
	}
	
	@XmlTransient
	public ECMProperties getECMProperties() {
		return properties;
	}

	public void setECMProperties(ECMProperties properties) {
		this.properties = properties;
	}

	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	@XmlAttribute(name = "class")
	public String getDataSourceClassname() {
		return dataSourceClassname;
	}

	public void setDataSourceClassname(String dataSourceClassname) {
		this.dataSourceClassname = dataSourceClassname;
	}

	public void setEntities(Entities entities) {
		this.entities = entities;
	}

	@XmlElement
	public Entities getEntities() {
		return entities;
	}

	@Override
	public Repository build() {
		try {
			this.dataSourceClass = Class.forName(this.getDataSourceClassname());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(obj);
	}

	@Override
	public String toString() {
		return id.toString() + this.caption;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
