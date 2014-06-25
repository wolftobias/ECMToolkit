/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.prefs;

import java.util.logging.Logger;

import org.w3c.dom.Document;

/**
 *
 * @author twolf10
 */
public class UserPreferences {

	private static Logger log = Logger.getLogger(SystemPreferences.class
			.getPackage().getName());

	private Document document;

	protected UserPreferences() {
		// try {
		// InputStream is = SystemPreferences.class
		// .getResourceAsStream("/userPrefs.xml");
		// DocumentBuilder documentBuilder = DocumentBuilderFactory
		// .newInstance().newDocumentBuilder();
		// document = documentBuilder.parse(is);
		//
		// } catch (Exception e) {
		// log.log(Level.SEVERE, e.getMessage(), e);
		// }
	}
}
