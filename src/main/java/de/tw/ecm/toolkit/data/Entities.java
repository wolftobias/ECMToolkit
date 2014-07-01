package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.Builder;

@XmlType(name = "entities")
public class Entities extends Values<Entity> implements Builder<Entities> {

	public Entities() {
	}

	public Entities(List<Entity> entities) {
		this.values = entities;
	}

	public Entity getByCaption(String caption) {
		for (Entity entity : this.values) {
			if (entity.getCaption().getText().equalsIgnoreCase(caption))
				return entity;
		}

		return null;
	}

	public Entity getById(String id) {
		for (Entity entity : this.values) {
			if (entity.getId().equalsIgnoreCase(id))
				return entity;
		}

		return null;
	}

	@XmlElement(name = "entity")
	public List<Entity> getEntities() {
		return this.values;
	}

	public String toXml() {
		String xml = "";
		JAXB.marshal(this, xml);
		return xml;
	}

	@Override
	public Entities build() {
		return this;
	}
}
