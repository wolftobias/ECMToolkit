package test.main.java.de.tw.ecm.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.xml.bind.JAXB;

import main.java.de.tw.ecm.toolkit.data.Attribute;
import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;
import main.java.de.tw.ecm.toolkit.data.Attributes;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;

import org.junit.Test;

public class RepositoryTest {

	@Test
	public void testToXml() {

		Repositories repositories = this.createRepositories();
		JAXB.marshal(repositories, System.out);
	}

	@Test
	public void testFromXml() {
		try {
			File file = new File("./test_repositories.xml");
			file.createNewFile();
			file.deleteOnExit();

			InputStream fileIn = new FileInputStream(file);
			OutputStream fileOut = new FileOutputStream(file);

			Repositories repositories = this.createRepositories();
			JAXB.marshal(repositories, fileOut);

			Repositories unmarshal = JAXB.unmarshal(fileIn, Repositories.class);
			System.out.println(unmarshal.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Repositories createRepositories() {
		ECMProperties properties = new ECMProperties();
		properties.setProperty("driver", "com.mysql.jdbc.Driver");
		properties.setProperty("url", "jdbc:mysql://localhost:3306/ECMToolkit");

		Repository repository = new Repository();
		repository.setCaption("MySQL ECMToolkit");
		repository.setId("MySQL_ECMToolkit1");
		repository
				.setDataSourceClassname("main.java.de.tw.ecm.toolkit.data.sources.JDBCDataSource");
		repository.setECMProperties(properties);
		repository.setEntities(this.createEntities());
		
		Repositories repositories = new Repositories();
		repositories.setDefault("MySQL_ECMToolkit1");
		repositories.add(repository);

		return repositories;
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
