package main.java.de.tw.ecm.toolkit.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "user", "group", "default", "views" })
public class View extends BaseViews<NavigationView> {

	private String user;

	private String group;

	private String defaultView;

	public View() {
	}

	public NavigationView getDefaultNavigationView() {
		return this.getById(this.defaultView);
	}

	public NavigationView getById(String id) {
		for (NavigationView view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElementWrapper(name = "navigationviews")
	@XmlElement(name = "navigationview")
	public List<NavigationView> getViews() {
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
	public View build() {
		for (NavigationView navigationView : this.values) {
			navigationView.build();
		}

		return this;
	}
}
