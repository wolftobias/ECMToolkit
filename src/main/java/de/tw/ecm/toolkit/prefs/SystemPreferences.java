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

import main.java.de.tw.ecm.toolkit.data.Repositories;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.RepositoryException;

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

	protected SystemPreferences() {
		try {
			InputStream is = SystemPreferences.class
					.getResourceAsStream("/systemPrefs.xml");
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(is);
			
			this.parseRepositories();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public Repositories getRepositories() throws RepositoryException {
		return repositories;
	}

	private void parseRepositories() throws RepositoryException {
		Repository temp;

		Node reposNode = document.getElementsByTagName("Repositories").item(0);
		NodeList reposNodes = reposNode.getChildNodes();

		String defaultRepo = reposNode.getAttributes().getNamedItem("default")
				.getTextContent();
		this.repositories.setDefaultRepository(defaultRepo);

		for (int i = 0; i < reposNodes.getLength(); i++) {

			Node repoNode = reposNodes.item(i);
			if (repoNode.getNodeType() == Node.ELEMENT_NODE) {

				Element repoElement = (Element) repoNode;
				temp = new Repository();
				temp.setCaption(repoElement.getAttribute("caption"));
				temp.setId(repoElement.getAttribute("id"));
				try {
					temp.setImplementationClass(Class.forName(repoElement
							.getAttribute("class")));
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				}

				NodeList repoPropsNodes = repoNode.getChildNodes();
				for (int j = 0; j < repoPropsNodes.getLength(); j++) {
					Node repoPropNode = repoPropsNodes.item(j);
					if (repoPropNode.getNodeType() == Node.ELEMENT_NODE) {
						temp.setProperty(repoPropNode.getNodeName(),
								repoPropNode.getTextContent());
					}
				}

				this.repositories.add(temp);
			}
		}
	}
}
