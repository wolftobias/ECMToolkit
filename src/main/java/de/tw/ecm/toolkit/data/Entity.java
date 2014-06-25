package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;
import main.java.de.tw.ecm.toolkit.data.sources.DataSourceException;

public class Entity {

	private Repository repository;

	private String caption;

	private String id;

	private Attributes attributes = new Attributes();

	public Entity() {
	}

	public Entity(Repository repository, String id) {
		this.repository = repository;
		this.id = id;
		this.caption = id;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
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
		return this.repository.getDataSource().defaultSelectQuery(this.id, attributes);
	}

	public DataList readList(String query) throws DataSourceException {
		return this.repository.getDataSource().readList(query);
	}

	public DataReader select(String query) throws DataSourceException {
		return this.repository.getDataSource().read(query);
	}

	public class Attributes {
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

		public class Attribute {

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

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public Class getTypeClass() throws ClassNotFoundException {
				return Class.forName(this.getType());
			}

			public int getSize() {
				return size;
			}

			public void setSize(int size) {
				this.size = size;
			}

			public String getNativeType() {
				return nativeType;
			}

			public void setNativeType(String nativeType) {
				this.nativeType = nativeType;
			}
			
			public class Caption {
				
				private String text;
				
				private Locale locale;
				
				public Caption() {
				}
				
				public Caption(String text) {
					this.text = text;
				}
				
				public Caption(String text, Locale locale) {
					this.text = text;
					this.locale = locale;
				}

				public String getText() {
					return text;
				}

				public void setText(String text) {
					this.text = text;
				}

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
