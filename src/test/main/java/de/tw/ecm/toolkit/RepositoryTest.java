package test.main.java.de.tw.ecm.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

import main.java.de.tw.ecm.toolkit.data.ECMProperties;
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
		repository.setProperties(properties);

		Repositories repositories = new Repositories();
		repositories.setDefaultRepository("MySQL_ECMToolkit1");
		repositories.add(repository);

		return repositories;
	}
}
