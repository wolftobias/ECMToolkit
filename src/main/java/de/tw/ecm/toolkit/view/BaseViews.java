package main.java.de.tw.ecm.toolkit.view;

import javax.xml.bind.annotation.XmlTransient;

import main.java.de.tw.ecm.toolkit.data.Values;

import org.apache.commons.lang3.builder.Builder;

public class BaseViews<T> extends Values<T> implements Builder<BaseViews> {

	protected String id;

	protected String defaultView;

	protected String controller;

	protected Class controllerClass;

	protected String resources;

	protected String fxml;

	public BaseViews() {
	}

	@XmlTransient
	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	@XmlTransient
	public Class getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class controllerClass) {
		this.controllerClass = controllerClass;
	}

	@XmlTransient
	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	@XmlTransient
	public String getFxml() {
		return fxml;
	}

	public void setFxml(String fxml) {
		this.fxml = fxml;
	}

	@XmlTransient
	public String getDefault() {
		return defaultView;
	}

	public void setDefault(String defaultView) {
		this.defaultView = defaultView;
	}

	@XmlTransient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public BaseViews build() {
		try {
			this.controllerClass = Class.forName(this.controller);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return this;
	}
}