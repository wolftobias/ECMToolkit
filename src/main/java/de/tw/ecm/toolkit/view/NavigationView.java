package main.java.de.tw.ecm.toolkit.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "default", "controller", "resources", "fxml",
		"views" })
public class NavigationView extends BaseViews<ContentView> {

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

	@Override
	public NavigationView build() {
		super.build();
		for (ContentView contentView : this.values) {
			contentView.build();
		}

		return this;
	}
}