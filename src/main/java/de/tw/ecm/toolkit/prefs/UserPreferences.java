/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.prefs;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.java.de.tw.ecm.toolkit.view.View;
import main.java.de.tw.ecm.toolkit.view.View.NavigationView;
import main.java.de.tw.ecm.toolkit.view.View.NavigationView.ContentView;
import main.java.de.tw.ecm.toolkit.view.Views;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author twolf10
 */
public class UserPreferences {

	private static Logger log = Logger.getLogger(SystemPreferences.class
			.getPackage().getName());

	private Document document;

	protected UserPreferences() {
		try {
			InputStream is = SystemPreferences.class
					.getResourceAsStream("/userPrefs.xml");
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(is);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
