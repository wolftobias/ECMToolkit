/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.tw.ecm.toolkit.prefs;

import java.io.InputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private Document document;

	private Repositories repositories = new Repositories();

	private Views views = new Views();

	protected SystemPreferences() {
		try {
			InputStream is = SystemPreferences.class
					.getResourceAsStream("/systemPrefs.xml");
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(is);

			this.parseRepositories();
			this.parseViews();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public Repositories getRepositories() throws RepositoryException {
		return repositories;
	}

	public Views getViews() throws RepositoryException {
		return views;
	}

	private void parseRepositories() throws RepositoryException {
		Repository repository;

		Node reposNode = document.getElementsByTagName("Repositories").item(0);
		NodeList reposNodes = reposNode.getChildNodes();

		String defaultRepo = reposNode.getAttributes().getNamedItem("default")
				.getTextContent();
		this.repositories.setDefaultRepository(defaultRepo);

		for (int i = 0; i < reposNodes.getLength(); i++) {

			Node repoNode = reposNodes.item(i);
			if (repoNode.getNodeType() == Node.ELEMENT_NODE) {

				Element repoElement = (Element) repoNode;
				repository = new Repository();
				repository.setCaption(repoElement.getAttribute("caption"));
				repository.setId(repoElement.getAttribute("id"));
				try {
					Class dataSource = Class.forName(repoElement
							.getAttribute("class"));
					repository.setImplementationClass(dataSource);
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				}

				this.parseRepository(repository, repoNode.getChildNodes());
				this.repositories.add(repository);
			}
		}
	}

	private void parseRepository(Repository repository, NodeList repoPropsNodes)
			throws RepositoryException {
		for (int i = 0; i < repoPropsNodes.getLength(); i++) {
			Node repoPropNode = repoPropsNodes.item(i);
			if (repoPropNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = repoPropNode.getNodeName();
				if (nodeName.equalsIgnoreCase("Entities"))
					this.parseEntities(repository);
				else
					repository.setProperty(repoPropNode.getNodeName(),
							repoPropNode.getTextContent());
			}
		}
	}

	private void parseEntities(Repository repository)
			throws RepositoryException {
		Entities entities = new Entities();
		Entity entity;

		Node entitiesNode = document.getElementsByTagName("Entities").item(0);
		NodeList entityNodes = entitiesNode.getChildNodes();

		for (int i = 0; i < entityNodes.getLength(); i++) {

			Node entityNode = entityNodes.item(i);
			if (entityNode.getNodeType() == Node.ELEMENT_NODE) {
				Element entityElement = (Element) entityNode;
				String id = entityElement.getAttribute("id");
				entity = new Entity(repository, id);
				this.parseEntity(entity, entityNode.getChildNodes());
				entities.add(entity);
			}
		}

		repository.setEntities(entities);
	}

	private void parseEntity(Entity entity, NodeList entityNodes)
			throws RepositoryException {
		Attributes attributes = entity.new Attributes();

		for (int i = 0; i < entityNodes.getLength(); i++) {
			Node entityNode = entityNodes.item(i);
			if (entityNode.getNodeType() == Node.ELEMENT_NODE) {
				Element entityElement = (Element) entityNode;
				String nodeName = entityElement.getNodeName();
				if (nodeName.equalsIgnoreCase("Attributes")) {
					this.parseAttributes(attributes,
							entityElement.getChildNodes());
				} else
					entity.setCaption(entityElement.getNodeValue());
			}
			entity.setAttributes(attributes);
		}
	}

	private void parseAttributes(Attributes attributes, NodeList attributesNodes)
			throws RepositoryException {
		Attribute attribute;

		for (int i = 0; i < attributesNodes.getLength(); i++) {
			NodeList attributeNodes = attributesNodes.item(i).getChildNodes();
			attribute = attributes.new Attribute();
			
			if(attributeNodes.getLength() > 0) {
				attributes.add(attribute);
			}
			
			for (int j = 0; j < attributeNodes.getLength(); j++) {
				Node attributeNode = attributeNodes.item(j);
				if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
					Element attributeElement = (Element) attributeNode;
					String nodeName = attributeElement.getNodeName();
					if (nodeName.equalsIgnoreCase("name")) {
						attribute.setName(attributeElement.getTextContent());
					} else if (nodeName.equalsIgnoreCase("caption")) {
						Caption caption = attribute.new Caption();
						Locale locale = new Locale(
								attributeElement.getAttribute("locale"));
						caption.setLocale(locale);
						caption.setText(attributeElement.getTextContent());
						attribute.setCaption(caption);
					}
				}
			}
		}
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
				view.setDefaultNavigationView(viewElement
						.getAttribute("default"));

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
				navigationView.setDefaultContentView(viewElement
						.getAttribute("default"));
				try {
					navigationView.setController(Class.forName(viewElement
							.getAttribute("controller")));
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				}
				navigationView.setResources(viewElement
						.getAttribute("resources"));
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
					contentView.setController(Class.forName(viewElement
							.getAttribute("controller")));
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
