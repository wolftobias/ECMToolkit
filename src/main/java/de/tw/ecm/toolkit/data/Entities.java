package main.java.de.tw.ecm.toolkit.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.Builder;

@XmlType(name = "entities")
public class Entities implements Builder<Entities> {

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

	@XmlElement(name = "entity")
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

	public String toXml() {
		String xml = "";
		JAXB.marshal(this, xml);
		return xml;
	}

	@Override
	public Entities build() {
		return JAXB.unmarshal(new File("./systemPrefs.entities.xml"),
				Entities.class);
	}
}
