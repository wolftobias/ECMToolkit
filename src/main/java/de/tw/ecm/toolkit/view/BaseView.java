package main.java.de.tw.ecm.toolkit.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.Builder;

@XmlTransient
public class BaseView implements Builder<BaseView> {

	protected String id;

	protected String controller;

	protected Class controllerClass;

	protected String resources;

	protected String fxml;

	public BaseView() {
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
	public BaseView build() {
		try {
			this.controllerClass = Class.forName(this.controller);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
}