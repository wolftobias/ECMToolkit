package main.java.de.tw.ecm.toolkit.view;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "monitoringviews")
public class MonitoringViews extends BaseViews<MonitoringView> {

	public MonitoringViews() {
	}

	public MonitoringViews(List<MonitoringView> view) {
		this.setValues(values);
	}

	public MonitoringView getById(String id) {
		for (MonitoringView view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlAttribute
	@Override
	public String getDefault() {
		return super.getDefault();
	}

	@XmlElement(name = "monitoringView")
	public List<MonitoringView> getViews() {
		return super.values;
	}

	public MonitoringView getDefaultView() {
		return this.getById(this.defaultView);
	}

	@Override
	public MonitoringViews build() {
		MonitoringViews views = JAXB.unmarshal(new File("./systemPrefs.monitoringviews.xml"),
				MonitoringViews.class);

		for (MonitoringView view : views) {
			view.build();
		}

		return views;
	}
}
