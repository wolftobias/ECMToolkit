package main.java.de.tw.ecm.toolkit.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "controller", "resources", "fxml", "views" })
public class NavigationView extends BaseViews<ContentView> {

	protected String id;

	protected String controller;

	protected Class controllerClass;

	protected String resources;

	protected String fxml;

	public NavigationView() {
	}

	public ContentView getById(String id) {
		for (ContentView view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElementWrapper(name = "contentviews")
	@XmlElement(name = "contentview")
	public List<ContentView> getViews() {
		return this.values;
	}

	public ContentView getDefaultContentView() {
		return this.getById(this.defaultView);
	}

	@XmlTransient
	public Class getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class controllerClass) {
		this.controllerClass = controllerClass;
	}

	@XmlAttribute
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getFxml() {
		return this.fxml;
	}

	public void setFxml(String fxml) {
		this.fxml = fxml;
	}

	@XmlElement
	public String getResources() {
		return this.resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	@XmlElement
	public String getController() {
		return this.controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	@Override
	public NavigationView build() {
		try {
			this.controllerClass = Class.forName(this.controller);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		for (ContentView contentView : this.values) {
			contentView.build();
		}

		return this;
	}
}