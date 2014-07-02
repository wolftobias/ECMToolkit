package main.java.de.tw.ecm.toolkit.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "default", "controller", "resources", "fxml" })
public class ContentView extends BaseView {

	public ContentView() {
	}

	@Override
	@XmlAttribute
	public String getId() {
		return super.getId();
	}

	@Override
	public void setId(String id) {
		super.setId(id);
	}

	@Override
	@XmlAttribute
	public String getDefault() {
		return super.getDefault();
	}

	@Override
	public void setDefault(String defaultView) {
		super.setDefault(defaultView);
	}

	@Override
	@XmlElement
	public String getFxml() {
		return super.getFxml();
	}

	@Override
	public void setFxml(String fxml) {
		super.setFxml(fxml);
	}

	@Override
	@XmlElement
	public String getResources() {
		return super.getResources();
	}

	@Override
	public void setResources(String resources) {
		super.setResources(resources);
	}

	@Override
	@XmlElement
	public String getController() {
		return super.getController();
	}

	@Override
	public void setController(String controller) {
		super.setController(controller);
	}
}