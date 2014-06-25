package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute.Caption;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.sources.DataSourceException;

@XmlType(propOrder = { "id", "caption", "attributes" })
public class Entity {

	private Repository repository;

	private Caption caption;

	private String id;

	private Attributes attributes = new Attributes();

	public Entity() {
	}

	public Entity(Repository repository, String id) {
		this.repository = repository;
		this.id = id;
	}

	@XmlTransient
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	@XmlElement(name = "caption")
	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "attributes")
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	public String getSelectQuery(String... attributes) {
		return this.repository.getDataSource().defaultSelectQuery(this.id,
				attributes);
	}

	public DataList readList(String query) throws DataSourceException {
		return this.repository.getDataSource().readList(query);
	}

	public DataReader select(String query) throws DataSourceException {
		return this.repository.getDataSource().read(query);
	}

	@XmlType
	public static class Attributes {
		private List<Attribute> cache = new ArrayList<>();

		public Attributes() {
		}

		public Attributes(List<Attribute> attributes) {
			this.cache = attributes;
		}

		public Attribute getByName(String caption) {
			for (int i = 0; i < cache.size(); i++) {
				if (cache.get(i).getName().equals(caption))
					return cache.get(i);
			}

			return null;
		}

		@XmlElement(name = "attribute")
		public List<Attribute> getAttributes() {
			return this.cache;
		}

		public void add(Attribute attribute) {
			this.cache.add(attribute);
		}

		public int size() {
			return this.cache.size();
		}

		public Attribute get(int i) {
			return this.cache.get(i);
		}

		public String[] getCaptions() {
			String[] captions = new String[this.size()];
			for (int i = 0; i < captions.length; i++) {
				captions[i] = this.get(i).getCaption().getText();
			}

			return captions;
		}

		@XmlType(propOrder = { "name", "caption" })
		public static class Attribute {

			private String name;

			private Caption caption;

			private String type;

			private int size;

			private String nativeType;

			public Attribute() {
			}

			public Attribute(String name) {
				this.name = name;
			}

			@XmlElement
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Caption getCaption() {
				return caption;
			}

			public void setCaption(Caption caption) {
				this.caption = caption;
			}
			
			@XmlTransient
			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
			
			@XmlTransient
			public Class getTypeClass() throws ClassNotFoundException {
				return Class.forName(this.getType());
			}
			
			@XmlTransient
			public int getSize() {
				return size;
			}

			public void setSize(int size) {
				this.size = size;
			}
			
			@XmlTransient
			public String getNativeType() {
				return nativeType;
			}

			public void setNativeType(String nativeType) {
				this.nativeType = nativeType;
			}

			@XmlType(propOrder = { "text", "localeStr" })
			public static class Caption {

				private String text;

				private Locale locale;

				public Caption() {
					this.locale = Locale.getDefault();
				}

				public Caption(String text) {
					this.text = text;
					this.locale = Locale.getDefault();					
				}

				public Caption(String text, Locale locale) {
					this.text = text;
					this.locale = locale;
				}

				public Caption(String text, String locale) {
					this.text = text;
					this.locale = new Locale(locale);
				}

				@XmlValue
				public String getText() {
					return text;
				}

				public void setText(String text) {
					this.text = text;
				}

				@XmlAttribute(name = "locale")
				public String getLocaleStr() {
					return locale.toString();
				}

				public void setLocale(String locale) {
					this.locale = new Locale(locale);
				}

				@XmlTransient
				public Locale getLocale() {
					return locale;
				}

				public void setLocale(Locale locale) {
					this.locale = locale;
				}
			}
		}
	}
}
