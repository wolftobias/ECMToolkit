package test.main.java.de.tw.ecm.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

import main.java.de.tw.ecm.toolkit.view.MonitoringView;
import main.java.de.tw.ecm.toolkit.view.MonitoringViews;

import org.junit.Test;

public class MonitoringViewTest {

	@Test
	public void testToXml() {

		MonitoringViews views = this.createViews();
		JAXB.marshal(views, System.out);
	}

	@Test
	public void testFromXml() {
		try {
			File file = new File("./test_monitoringviews.xml");
			file.createNewFile();
			file.deleteOnExit();

			InputStream fileIn = new FileInputStream(file);
			OutputStream fileOut = new FileOutputStream(file);

			MonitoringViews views = this.createViews();
			JAXB.marshal(views, fileOut);

			MonitoringViews unmarshal = JAXB.unmarshal(fileIn,
					MonitoringViews.class);
			System.out.println(unmarshal.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MonitoringViews createViews() {
		MonitoringView monitoringView = new MonitoringView();
		monitoringView
				.setController("main.java.de.tw.ecm.toolkit.view.plugins.QueryAnalyserController");
		monitoringView.setFxml("queryanalyser.fxml");
		monitoringView.setId("QueryAnalyser");
		monitoringView.setResources("queryanalyser.properties");

		MonitoringViews views = new MonitoringViews();
		views.setDefault("QueryAnalyser");
		views.add(monitoringView);

		return views;
	}
}
