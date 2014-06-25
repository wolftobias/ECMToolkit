package main.java.de.tw.ecm.toolkit.view;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "user", "group", "default" })
public class View {

	private String id;

	private String user;

	private String group;

	private ArrayList<NavigationView> cache = new ArrayList<>();

	private String defaultView;

	public View() {
	}

	public void add(NavigationView view) {
		this.cache.add(view);
	}

	public int size() {
		return this.cache.size();
	}

	public NavigationView get(int i) {
		return this.cache.get(i);
	}
	
	public NavigationView getDefaultNavigationView() {
		return this.getById(this.defaultView);
	}

	public NavigationView getById(String id) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getId().equals(id))
				return cache.get(i);
		}

		return null;
	}
	
	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@XmlAttribute
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	@XmlAttribute
	public String getDefault() {
		return defaultView;
	}

	public void setDefault(String defaultView) {
		this.defaultView = defaultView;
	}
	
	@XmlType(propOrder = { "id", "controller", "resources", "fxml", "default" })
	public static class NavigationView {

		private String id;

		private Class controller;

		private String resources;

		private String fxml;

		private String defaultView;

		private ArrayList<ContentView> cache = new ArrayList<>();

		public NavigationView() {
		}

		public void add(ContentView View) {
			this.cache.add(View);
		}

		public int size() {
			return this.cache.size();
		}

		public ContentView get(int i) {
			return this.cache.get(i);
		}

		public ContentView getById(String id) {
			for (int i = 0; i < cache.size(); i++) {
				if (cache.get(i).getId().equals(id))
					return cache.get(i);
			}

			return null;
		}
		
		public ContentView getDefaultContentView() {
			return this.getById(this.defaultView);
		}
		
		@XmlAttribute(name="default")		
		public String getDefault() {
			return this.defaultView;
		}
		
		public void setDefault(String defaultView) {
			this.defaultView = defaultView;
		}

		public Class getController() {
			return controller;
		}

		public void setController(Class controller) {
			this.controller = controller;
		}

		public String getResources() {
			return resources;
		}

		public void setResources(String resources) {
			this.resources = resources;
		}

		public String getFxml() {
			return fxml;
		}

		public void setFxml(String fxml) {
			this.fxml = fxml;
		}
		
		@XmlAttribute
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public static class ContentView {

			private String id;

			private String defaultView;

			private Class controller;

			private String resources;

			private String fxml;

			public ContentView() {
			}

			public Class getController() {
				return controller;
			}

			public void setController(Class controller) {
				this.controller = controller;
			}

			public String getResources() {
				return resources;
			}

			public void setResources(String resources) {
				this.resources = resources;
			}

			public String getFxml() {
				return fxml;
			}

			public void setFxml(String fxml) {
				this.fxml = fxml;
			}

			public String getDefaultView() {
				return defaultView;
			}

			public void setDefaultView(String defaultView) {
				this.defaultView = defaultView;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}
		}
	}
}
