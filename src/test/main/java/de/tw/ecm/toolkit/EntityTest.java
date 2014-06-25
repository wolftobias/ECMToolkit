package test.main.java.de.tw.ecm.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.xml.bind.JAXB;

import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute.Caption;

import org.junit.Test;

public class EntityTest {

	@Test
	public void testToXml() {

		Entities entities = this.createEntities();
		JAXB.marshal(entities, System.out);
	}

	@Test
	public void testFromXml() {
		try {
			File file = new File("./test_entities.xml");
			file.createNewFile();

			InputStream fileIn = new FileInputStream(file);
			OutputStream fileOut = new FileOutputStream(file);

			Entities entities = this.createEntities();
			JAXB.marshal(entities, fileOut);

			Entities unmarshal = JAXB.unmarshal(fileIn, Entities.class);
			System.out.println(unmarshal.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Entities createEntities() {
		Attributes attributes = new Attributes();

		Attribute attribute = new Attribute();
		attribute.setCaption(new Caption("Id", new Locale("de")));
		attribute.setName("ID");
		attributes.add(attribute);

		attribute = new Attribute();
		attribute.setCaption(new Caption("String Spalte", new Locale("de")));
		attribute.setName("StringCol");
		attributes.add(attribute);

		attribute = new Attribute();
		attribute.setCaption(new Caption("TimeStamp Spalte", new Locale("de")));
		attribute.setName("TimeStampCol");
		attributes.add(attribute);

		attribute = new Attribute();
		attribute.setCaption(new Caption("DateTime Spalte", new Locale("de")));
		attribute.setName("DateTimeCol");
		attributes.add(attribute);

		attribute = new Attribute();
		attribute.setCaption(new Caption("Date Spalte", new Locale("de")));
		attribute.setName("DateCol");
		attributes.add(attribute);

		attribute = new Attribute();
		attribute.setCaption(new Caption("Time Spalte", new Locale("de")));
		attribute.setName("TimeCol");
		attributes.add(attribute);

		Entity entity = new Entity();
		entity.setId("MyTable");
		entity.setCaption(new Caption("Meine Tabelle", Locale.GERMAN));
		entity.setAttributes(attributes);

		Entities entities = new Entities();
		entities.add(entity);

		return entities;
	}
}
