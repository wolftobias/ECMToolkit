package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.ECMProperties.ECMProperty;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;
import main.java.de.tw.ecm.toolkit.data.sources.DataSourceException;

import org.apache.commons.lang3.builder.Builder;

@XmlType(propOrder = { "id", "caption", "properties", "entities" })
public class Repository implements Builder<Repository> {

	private Class dataSourceClass;

	private String caption;

	private String id;

	private ECMProperties properties = new ECMProperties();

	private DataSource dataSource;

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

	@XmlElement
	public Entities getEntities() throws RepositoryException {
		try {
			Entities nativeEntities = this.getDataSource().getEntities();

			for (Entity nativeEntity : nativeEntities) {
				String nativeId = nativeEntity.getId();
				Entity entity = this.entities.getById(nativeId);
				if (entity != null) {
					nativeEntity.setCaption(entity.getCaption());
					mergeAttributes(entity.getAttributes(),
							nativeEntity.getAttributes());
				}
			}

			this.entities = nativeEntities;
		} catch (DataSourceException e) {
			throw new RepositoryException(e);
		}
		
		return this.entities;
	}

	private void mergeAttributes(Attributes attributes,
			Attributes nativeAttributes) {
		for (Attribute nativeAttribute : nativeAttributes) {
			String nativeName = nativeAttribute.getName();
			Attribute attribute = attributes.getByName(nativeName);
			
			if(attribute != null)
				this.mergeAttribute(attribute, nativeAttribute);
		}
	}

	private void mergeAttribute(Attribute attribute, Attribute nativeAttribute) {
		nativeAttribute.setCaption(attribute.getCaption());
	}

	public void setEntities(Entities entities) {
		this.entities = entities;
	}

	public void initialize() throws RepositoryException {
		try {
			this.dataSource = (DataSource) dataSourceClass.newInstance();
			this.dataSource.initialize(this, this.properties);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
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
}
