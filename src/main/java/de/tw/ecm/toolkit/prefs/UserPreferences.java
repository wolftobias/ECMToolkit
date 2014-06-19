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
public class UserPreferences {

	private static Logger log = Logger.getLogger(SystemPreferences.class
			.getPackage().getName());

	private Document document;

	private Views views = new Views();

	protected UserPreferences() {
		try {
			InputStream is = SystemPreferences.class
					.getResourceAsStream("/userPrefs.xml");
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(is);

			this.parseViews();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public Views getViews() throws RepositoryException {
		return views;
	}

	private void parseViews() throws RepositoryException {
		View view;

		Node viewsNode = document.getElementsByTagName("Views").item(0);
		NodeList viewsNodes = viewsNode.getChildNodes();

		String defaultView = viewsNode.getAttributes().getNamedItem("default")
				.getTextContent();
		this.views.setDefaultView(defaultView);

		for (int i = 0; i < viewsNodes.getLength(); i++) {

			Node viewNode = viewsNodes.item(i);
			if (viewNode.getNodeType() == Node.ELEMENT_NODE) {

				Element viewElement = (Element) viewNode;
				view = new View();
				view.setId(viewElement.getAttribute("id"));
				view.setUser(viewElement.getAttribute("user"));
				view.setGroup(viewElement.getAttribute("group"));
				view.setDefaultNavigationView(viewElement.getAttribute("default"));

				NodeList navViewNodes = viewNode.getChildNodes();
				this.parseNavigationViews(view, navViewNodes);
				this.views.add(view);
			}
		}
	}

	private void parseNavigationViews(View view, NodeList viewNodes)
			throws RepositoryException {
		NavigationView navigationView;

		for (int i = 0; i < viewNodes.getLength(); i++) {

			Node viewNode = viewNodes.item(i);
			if (viewNode.getNodeType() == Node.ELEMENT_NODE) {

				Element viewElement = (Element) viewNode;
				navigationView = view.new NavigationView();
				navigationView.setId(viewElement.getAttribute("id"));
				navigationView.setDefaultContentView(viewElement.getAttribute("default"));
				try {
					navigationView.setController(Class.forName(viewElement.getAttribute("controller")));
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				}
				navigationView.setResources(viewElement.getAttribute("resources"));
				navigationView.setFxml(viewElement.getAttribute("fxml"));
				
				NodeList navViewNodes = viewNode.getChildNodes();
				this.parseContentViews(navigationView, navViewNodes);
				view.add(navigationView);
			}
		}
	}

	private void parseContentViews(NavigationView view, NodeList viewNodes)
			throws RepositoryException {
		ContentView contentView;

		for (int i = 0; i < viewNodes.getLength(); i++) {

			Node viewNode = viewNodes.item(i);
			if (viewNode.getNodeType() == Node.ELEMENT_NODE) {

				Element viewElement = (Element) viewNode;
				contentView = view.new ContentView();
				contentView.setId(viewElement.getAttribute("id"));
				try {
					contentView.setController(Class.forName(viewElement.getAttribute("controller")));
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				}
				contentView.setResources(viewElement.getAttribute("resources"));
				contentView.setFxml(viewElement.getAttribute("fxml"));

				NodeList navViewNodes = viewNode.getChildNodes();
				view.add(contentView);
			}
		}
	}

}
