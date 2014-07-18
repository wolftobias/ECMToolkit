package main.java.de.tw.ecm.toolkit.util;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.de.tw.ecm.toolkit.ECMToolkit;
import main.java.de.tw.ecm.toolkit.view.ContentView;
import main.java.de.tw.ecm.toolkit.view.MonitoringView;
import main.java.de.tw.ecm.toolkit.view.MonitoringViews;
import main.java.de.tw.ecm.toolkit.view.NavigationView;
import main.java.de.tw.ecm.toolkit.view.NavigationViews;
import main.java.de.tw.ecm.toolkit.view.View;
import main.java.de.tw.ecm.toolkit.view.Views;
import main.java.de.tw.ecm.toolkit.view.plugins.AbstractUserController;
import main.java.de.tw.ecm.toolkit.view.system.LoginController;
import main.java.de.tw.ecm.toolkit.view.system.MainController;

public class ViewContext {

	private static ViewContext context;

	public synchronized static ViewContext context() {
		if (context == null)
			context = new ViewContext();
		return context;
	}	

	private Stage primaryStage;

	private Views views;

	private MonitoringViews monitoringViews;
	
	private ViewContext() {
		this.views = new Views().build();
		this.monitoringViews = new MonitoringViews().build();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void showLoginView() {
		try {
			LoginController login = (LoginController) replaceSceneContent(
					LoginController.class, "login");
			put(login);
		} catch (Exception ex) {
			Logger.getLogger(ECMToolkit.class.getName()).log(Level.SEVERE,
					ex.getMessage(), ex);
		}
	}

	public void showMainView() {
		try {
			MainController main = (MainController) replaceSceneContent(
					MainController.class, "main");
			put(main);

			for (View view : views) {
				for (NavigationViews navigationViews : view) {
					for (NavigationView navigationView : navigationViews) {
						Node navigation = replaceSceneContent(
								navigationView.getControllerClass(),
								navigationView.getResources(),
								navigationView.getFxml());
						String navigationViewCaption = ((AbstractUserController) get(navigationView.getController()))
								.getCaption();
						main.addNavigationTab(navigationViewCaption, navigation);
						for (ContentView contentView : navigationView) {
							Node content = replaceSceneContent(
									contentView.getControllerClass(),
									contentView.getResources(),
									contentView.getFxml());
							String contentViewCaption = ((AbstractUserController) get(contentView.getController()))
									.getCaption();
							main.addContentTab(contentViewCaption, content);
						}
					}
				}
			}

			for (MonitoringView monitoringView : monitoringViews) {
				Node monitoring = replaceSceneContent(
						monitoringView.getControllerClass(),
						monitoringView.getResources(), monitoringView.getFxml());
				String contentViewCaption = ((AbstractUserController) get(monitoringView.getController())).getCaption();
				main.addMonitoringTab(contentViewCaption, monitoring);
			}
		} catch (Exception ex) {
			Logger.getLogger(ECMToolkit.class.getName()).log(Level.SEVERE,
					ex.getMessage(), ex);
		}
	}

	public void setFullScreen(boolean fullscreen) {
		this.primaryStage.setFullScreen(fullscreen);
		this.primaryStage.setFullScreenExitKeyCombination(KeyCombination
				.keyCombination("F11"));
	}

	private Initializable replaceSceneContent(Class controller, String name)
			throws Exception {
		String path = controller.getPackage().getName() + ".";
		String fxml = name + ".fxml";
		String bundle = name + ".properties";
		FXMLLoader loader = new FXMLLoader();

		InputStream in = controller.getResourceAsStream(fxml);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(controller.getResource(path));
		loader.setResources(new PropertyResourceBundle(controller
				.getResourceAsStream(bundle)));

		Pane pane;
		try {
			pane = loader.load(in);
		} finally {
			in.close();
		}
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		// primaryStage.sizeToScene();
		return (Initializable) loader.getController();
	}

	private Node replaceSceneContent(Class controller, String resourcesFile,
			String fxmlFile) throws Exception {
		String path = controller.getPackage().getName();
		FXMLLoader loader = new FXMLLoader();

		InputStream in = controller.getResourceAsStream(fxmlFile);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(controller.getResource("/"));
		loader.setResources(new PropertyResourceBundle(controller
				.getResourceAsStream(resourcesFile)));

		Node node;
		try {
			node = loader.load(in);
		} finally {
			in.close();
		}

		Object controllerObj = loader.getController();
		if (controllerObj == null)
			throw new IllegalArgumentException(
					"Controller class '"
							+ controller.getName()
							+ "' is null! Please check if you`ve set the controller in the fxm file!");

		put(controllerObj);
		return node;
	}
	
	private void put(Object object) {
		Context.context().put(object);
	}

	private Object get(Object object) {
		return Context.context().get(object);
	}
	
}
