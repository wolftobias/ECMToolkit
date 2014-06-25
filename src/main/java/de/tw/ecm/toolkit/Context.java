package main.java.de.tw.ecm.toolkit;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.prefs.PreferencesFactory;
import main.java.de.tw.ecm.toolkit.prefs.SystemPreferences;
import main.java.de.tw.ecm.toolkit.prefs.UserPreferences;
import main.java.de.tw.ecm.toolkit.view.View;
import main.java.de.tw.ecm.toolkit.view.View.NavigationView;
import main.java.de.tw.ecm.toolkit.view.View.NavigationView.ContentView;
import main.java.de.tw.ecm.toolkit.view.Views;
import main.java.de.tw.ecm.toolkit.view.system.LoginController;
import main.java.de.tw.ecm.toolkit.view.system.MainController;
import main.java.de.tw.ecm.toolkit.view.user.AbstractUserController;

import org.apache.commons.cli.CommandLine;

public class Context {

	private static Context context;

	public synchronized static Context context() {
		if (context == null)
			context = new Context();
		return context;
	}

	private Hashtable<String, Object> cache = new Hashtable<>();

	private SystemPreferences systemPrefs;

	private UserPreferences userPreferences;

	private ViewContext viewContext;

	private Repository selectedRepository;

	private Context() {
		this.systemPrefs = PreferencesFactory.systemPrefs();
		this.userPreferences = PreferencesFactory.userPrefs();
		this.viewContext = new ViewContext();
	}

	public void put(Object value) {
		if (value == null)
			throw new IllegalArgumentException("value can not be null!");
		this.put(value.getClass().getName(), value);
	}

	public void put(String key, Object value) {
		this.cache.put(key, value);
	}

	public void remove(Object value) {
		this.cache.remove(value.getClass().getName());
	}

	public Object get(Object value) {
		Object returnObj;
		if (value instanceof String)
			returnObj = this.cache.get(value);
		else if (value instanceof Class)
			returnObj = this.cache.get(((Class) value).getName());
		else
			returnObj = this.cache.get(value.getClass().getName());

		return returnObj;
	}

	public SystemPreferences getSystemPrefs() {
		return systemPrefs;
	}

	public UserPreferences getUserPrefs() {
		return userPreferences;
	}

	public ViewContext getViewContext() {
		return viewContext;
	}

	public ECMToolkit getECMToolkit() {
		return (ECMToolkit) this.get(ECMToolkit.class);
	}

	public Stage getRootWindow() {
		ECMToolkit ecmToolkit = (ECMToolkit) this.get(ECMToolkit.class);
		return ecmToolkit.getPrimaryStage();
	}

	public CommandLine getCommandLine() {
		return (CommandLine) this.get(CommandLine.class);
	}

	public Repository getSelectedRepository() {
		return selectedRepository;
	}

	public void setSelectedRepository(Repository selectedRepository) {
		this.selectedRepository = selectedRepository;
	}

	public class ViewContext {

		private Stage primaryStage;

		protected ViewContext() {

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

				Views views = systemPrefs.getViews();

				for (int i = 0; i < views.size(); i++) {
					View view = views.get(i);
					for (int j = 0; j < view.size(); j++) {
						NavigationView navigationView = view.get(j);
						Pane navigationPane = replaceSceneContent(
								navigationView.getController(),
								navigationView.getResources(),
								navigationView.getFxml());
						String navigationViewCaption = ((AbstractUserController) get(navigationView
								.getController())).getCaption();
						main.addNavigationTab(navigationViewCaption,
								navigationPane);

						for (int k = 0; k < navigationView.size(); k++) {
							ContentView contentView = navigationView.get(k);
							Pane contentPane = replaceSceneContent(
									contentView.getController(),
									contentView.getResources(),
									contentView.getFxml());
							String contentPaneCaption = ((AbstractUserController) get(contentView
									.getController())).getCaption();
							main.addContentTab(contentPaneCaption, contentPane);
						}
					}
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

		private Pane replaceSceneContent(Class controller,
				String resourcesFile, String fxmlFile) throws Exception {
			String path = controller.getPackage().getName();
			FXMLLoader loader = new FXMLLoader();

			InputStream in = controller.getResourceAsStream(fxmlFile);
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(controller.getResource("/"));
			loader.setResources(new PropertyResourceBundle(controller
					.getResourceAsStream(resourcesFile)));

			Pane pane;
			try {
				pane = loader.load(in);
			} finally {
				in.close();
			}

			put(loader.getController());

			return pane;
		}

	}
}