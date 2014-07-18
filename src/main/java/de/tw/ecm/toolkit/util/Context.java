package main.java.de.tw.ecm.toolkit.util;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.de.tw.ecm.toolkit.ECMToolkit;
import main.java.de.tw.ecm.toolkit.auth.User;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.sources.DataSource;
import main.java.de.tw.ecm.toolkit.prefs.PreferencesFactory;
import main.java.de.tw.ecm.toolkit.prefs.SystemPreferences;
import main.java.de.tw.ecm.toolkit.prefs.UserPreferences;
import main.java.de.tw.ecm.toolkit.service.AbstractService;
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

	private Repository selectedRepository;

	private DataSource dataSource;

	private User user;

	private ListProperty<AbstractService<?>> runningServices = new SimpleListProperty<AbstractService<?>>(
			FXCollections.<AbstractService<?>> observableArrayList());

	private Context() {
		this.systemPrefs = PreferencesFactory.systemPrefs();
		this.userPreferences = PreferencesFactory.userPrefs();
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ListProperty<AbstractService<?>> runningServices() {
		return this.runningServices;
	}
}