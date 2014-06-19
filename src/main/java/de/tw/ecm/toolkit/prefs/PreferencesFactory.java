/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.prefs;

/**
 *
 * @author twolf10
 */
public class PreferencesFactory {

	private static SystemPreferences systemPreferences;

	private static UserPreferences userPreferences;

	public synchronized static SystemPreferences systemPrefs() {
		if (systemPreferences == null)
			systemPreferences = new SystemPreferences();
		return systemPreferences;
	}

	public synchronized static UserPreferences userPrefs() {
		if (userPreferences == null)
			userPreferences = new UserPreferences();
		return userPreferences;
	}
}
