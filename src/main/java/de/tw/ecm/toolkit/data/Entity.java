package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;

import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.prefs.Repository;
import javafx.collections.ObservableList;

public class Entity {
	
	private Repository repository;
	
	private DataSource dataSource;
	
	private String caption;

	private String id;
	
	private Attributes attributes = new Attributes();
	
	public Entity() {
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Entity(Repository repository, DataSource dataSource,  String id) {
		this.repository = repository;
		this.dataSource = dataSource;
		this.id = id;
		this.caption = id;
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
		
	public String getSelectQuery(String...attributes) {
		return this.dataSource.defaultSelectQuery(this.id, attributes);
	}
	
	public ObservableList readAsList(String query) throws DataSourceException {
		return this.dataSource.readAsList(query);
	}
	
	public DataReader select(String query) throws DataSourceException {
		return this.dataSource.read(query);
	}
	
	public class Attributes {
		private ArrayList<Attribute> cache = new ArrayList<>();

		public Attributes() {
		}

		public Attributes(ArrayList<Attribute> attributes) {
			this.cache = attributes;
		}

		public Attribute getByName(String caption) {
			for (int i = 0; i < cache.size(); i++) {
				if (cache.get(i).getName().equals(caption))
					return cache.get(i);
			}

			return null;
		}

		public Attribute[] getAttributes() {
			return (Attribute[]) this.cache.toArray(new Attribute[cache.size()]);
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
	}
	
	public class Attribute {
		
		private String name;
		
		private String caption;
		
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

		public String getCaption() {
			return caption;
		}

		public void setCaption(String caption) {
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
		
		
	}
}
