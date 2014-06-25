package main.java.de.tw.ecm.toolkit.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.Builder;

@XmlType(propOrder = { "id", "user", "group", "default", "views" })
public class View implements Builder<View>, Iterable<NavigationView> {

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
		for (NavigationView view : this.cache) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElementWrapper(name = "navigationviews")
	@XmlElement(name = "navigationview")
	public List<NavigationView> getViews() {
		return this.cache;
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

	@Override
	public Iterator<NavigationView> iterator() {
		return this.cache.iterator();
	}

	@Override
	public View build() {
		for (NavigationView navigationView : this.cache) {
			navigationView.build();
		}

		return this;
	}
}
