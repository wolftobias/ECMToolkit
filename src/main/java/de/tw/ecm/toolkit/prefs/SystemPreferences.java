/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.prefs;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.java.de.tw.ecm.toolkit.data.Entities;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute;
import main.java.de.tw.ecm.toolkit.data.Entity.Attributes.Attribute.Caption;
import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;
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
public class SystemPreferences {

	private static Logger log = Logger.getLogger(SystemPreferences.class
			.getPackage().getName());
}
