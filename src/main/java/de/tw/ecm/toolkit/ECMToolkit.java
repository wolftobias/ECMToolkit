/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;
import main.java.de.tw.ecm.toolkit.util.Context;
import main.java.de.tw.ecm.toolkit.util.ViewContext;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author twolf10
 */
public class ECMToolkit extends Application {

	Logger log = LogManager.getLogger(ECMToolkit.class);

	private Context context;
	
	private ViewContext viewContext;
	
	private Stage primaryStage;

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) {
		log.entry();
		this.primaryStage = primaryStage;
		try {
			this.context = Context.context();
			this.viewContext = ViewContext.context();
			this.context.put(this);
			this.viewContext.setPrimaryStage(primaryStage);
			this.parseCmd(this.getParameters());
			primaryStage.setTitle("ECM Toolkit");
			this.viewContext.showLoginView();
			primaryStage.show();
		} catch (Exception ex) {
			log.catching(ex);
		}
		log.exit();
	}

	@Override
	public void stop() throws Exception {
		log.entry();
		super.stop();
		DataSource dataSource = context.getDataSource();
		if (dataSource != null) {
			dataSource.destroy();
			log.debug("datasource destroyed");
		}
		log.exit();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	private void parseCmd(Parameters parameters) throws ParseException {
		Options options = new Options();
		options.addOption("u", "user", true, "user login name");
		options.addOption("p", "password", true, "userid password");

		List<String> rawList = parameters.getRaw();

		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parser.parse(options,
				(String[]) rawList.toArray(new String[rawList.size()]));
		this.context.put(commandLine);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
