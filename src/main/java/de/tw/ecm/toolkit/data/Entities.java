package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

public class Entities {

	private static final String LOCAL_STRATEGY = "LOCAL_STRATEGY";
	private static final String REPOSITORY_STRATEGY = "REPOSITORY_STRATEGY";
	private static final String MIXED_STRATEGY = "MIXED_STRATEGY";
	
	private String strategy;
	
	private List<Entity> cache = new ArrayList<>();

	public Entities() {
	}

	public Entities(List<Entity> entities) {
		this.cache = entities;
	}

	public Entity getByCaption(String caption) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getCaption().equals(caption))
				return cache.get(i);
		}

		return null;
	}

	public Entity getById(String id) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getId().equals(id))
				return cache.get(i);
		}

		return null;
	}

	public List<Entity> getEntities() {
		return this.cache;
	}

	public void add(Entity entity) {
		this.cache.add(entity);
	}

	public int size() {
		return this.cache.size();
	}

	public Entity get(int i) {
		return this.cache.get(i);
	}
}
