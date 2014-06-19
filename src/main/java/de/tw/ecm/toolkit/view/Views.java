package main.java.de.tw.ecm.toolkit.view;

import java.util.ArrayList;

public class Views {

	private ArrayList<View> cache = new ArrayList<>();

	private String defaultView;

	public Views() {
	}

	public Views(ArrayList<View> view) {
		this.cache = view;
	}

	public View getById(String id) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getId().equals(id))
				return cache.get(i);
		}

		return null;
	}

	public View[] getViews() {
		return (View[]) this.cache.toArray(new View[cache.size()]);
	}

	public void add(View View) {
		this.cache.add(View);
	}

	public int size() {
		return this.cache.size();
	}

	public View get(int i) {
		return this.cache.get(i);
	}

	public View getDefaultView() {
		return this.getById(this.defaultView);
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
}
