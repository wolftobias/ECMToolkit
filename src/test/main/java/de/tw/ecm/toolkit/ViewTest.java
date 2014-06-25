package test.main.java.de.tw.ecm.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

import main.java.de.tw.ecm.toolkit.view.ContentView;
import main.java.de.tw.ecm.toolkit.view.NavigationView;
import main.java.de.tw.ecm.toolkit.view.View;
import main.java.de.tw.ecm.toolkit.view.Views;

import org.junit.Test;

public class ViewTest {

	@Test
	public void testToXml() {

		Views views = this.createViews();
		JAXB.marshal(views, System.out);
	}

	@Test
	public void testFromXml() {
		try {
			File file = new File("./test_views.xml");
			file.createNewFile();
			file.deleteOnExit();

			InputStream fileIn = new FileInputStream(file);
			OutputStream fileOut = new FileOutputStream(file);

			Views views = this.createViews();
			JAXB.marshal(views, fileOut);

			Views unmarshal = JAXB.unmarshal(fileIn, Views.class);
			System.out.println(unmarshal.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Views createViews() {
		View view = new View();
		view.setId("QueryAnalyser");
		view.setUser("*");
		view.setGroup("*");
		view.setDefault("DataSourceTree");

		NavigationView navigationView = new NavigationView();
		navigationView
				.setController("main.java.de.tw.ecm.toolkit.view.user.DataSourceExplorerController");
		navigationView.setDefault("QueryAnalyser");
		navigationView.setFxml("datasourceexplorer.fxml");
		navigationView.setId("DataSourceTree");
		navigationView.setResources("datasourceexplorer.properties");

		ContentView contentView = new ContentView();
		contentView
				.setController("main.java.de.tw.ecm.toolkit.view.user.QueryAnalyserController");
		contentView.setDefault("QueryAnalyser");
		contentView.setFxml("queryanalyser.fxml");
		contentView.setId("QueryAnalyser");
		contentView.setResources("queryanalyser.properties");

		navigationView.add(contentView);
		view.add(navigationView);

		Views views = new Views();
		views.add(view);

		return views;
	}
}
