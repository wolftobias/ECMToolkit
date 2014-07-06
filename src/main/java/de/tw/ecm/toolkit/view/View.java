package main.java.de.tw.ecm.toolkit.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "user", "group", "default", "views" })
public class View extends BaseViews<NavigationViews> {

	private String id;

	private String user;

	private String group;

	private String defaultView;

	public View() {
	}

	@XmlElement(name = "navigationviews")
	public List<NavigationViews> getViews() {
		return this.values;
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
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
	public View build() {
		for (NavigationViews views : values) {
			views.build();
		}

		return this;
	}
}
