package main.java.de.tw.ecm.toolkit.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.Builder;

@XmlType(propOrder = { "id", "controller", "resources", "fxml", "default" })
public class BaseView implements Builder<BaseView> {

	protected String id;

	protected String defaultView;

	protected String controller;

	protected Class controllerClass;

	protected String resources;

	protected String fxml;

	public BaseView() {
	}

	@XmlElement
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

	@XmlElement
	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	@XmlElement
	public String getFxml() {
		return fxml;
	}

	public void setFxml(String fxml) {
		this.fxml = fxml;
	}

	@XmlAttribute
	public String getDefault() {
		return defaultView;
	}

	public void setDefault(String defaultView) {
		this.defaultView = defaultView;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public BaseView build() {
		try {
			this.controllerClass = Class.forName(this.controller);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return this;
	}
}