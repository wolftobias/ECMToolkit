/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author twolf10
 */
public class ECMToolkit extends Application {

	private Context context;
	
	private Stage primaryStage;
	
	@Override
	public void init() throws Exception {
		super.init();
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			this.context = Context.context();
			this.context.put(this);
			this.context.getViewContext().setPrimaryStage(primaryStage);
			this.parseCmd(this.getParameters());
			primaryStage.setTitle("ECM Toolkit");
			this.context.getViewContext().showLoginView();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(ECMToolkit.class.getName()).log(Level.SEVERE,
					ex.getMessage(), ex);
		}
	}

	private void parseCmd(Parameters parameters) throws ParseException {
		Options options = new Options();
		options.addOption("u", "user", true, "user login name");
		options.addOption("p", "password", true, "user password");

		List<String> rawList = parameters.getRaw();

		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parser.parse(options,
				(String[]) rawList.toArray(new String[rawList.size()]));
		this.context.put(commandLine);
	}
	
	@Override
	public void stop() throws Exception {
		this.context.getSelectedRepository().getDataSource().destroy();
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
