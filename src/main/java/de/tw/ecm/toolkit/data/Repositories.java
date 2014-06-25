package main.java.de.tw.ecm.toolkit.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.Builder;

@XmlRootElement(name = "repositories")
public class Repositories implements Builder<Repositories> {

	private ArrayList<Repository> cache = new ArrayList<>();
	
	private String defaultRepo;
	
	private Repository selected;

	public Repositories() {
	}

	public Repositories(ArrayList<Repository> repositories) {
		this.cache = repositories;
	}

	public Repository getByCaption(String caption) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getCaption().equals(caption))
				return cache.get(i);
		}

		return null;
	}

	public Repository getById(String id) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getId().equals(id))
				return cache.get(i);
		}

		return null;
	}
	
	@XmlElement(name = "repository")
	public List<Repository> getRepositories() {
		return this.cache;
	}

	public void add(Repository repository) {
		this.cache.add(repository);
	}

	public int size() {
		return this.cache.size();
	}

	public Repository get(int i) {
		return this.cache.get(i);
	}
	
	public Repository getDefaultRepository() {
		return this.getById(defaultRepo);
	}
	
	@XmlAttribute(name="default")
	public String getDefault() {
		return this.defaultRepo;
	}
	
	public void setDefault(String defaultRepo) {
		this.defaultRepo = defaultRepo;
	}
	
	public void setDefaultRepository(String defaultRepo) {
		this.defaultRepo = defaultRepo;
	}

	public String[] getRepositoryNames() {
		String[] srepos = new String[this.size()];
		for (int i = 0; i < size(); i++) {
			srepos[i] = this.cache.get(i).getCaption();
		}

		return srepos;
	}
	
	@XmlTransient
	public Repository getSelectedRepository() {
		return selected;
	}

	public void setSelectedRepository(String selected) {
		this.selected = this.getByCaption(selected);
	}

	public String toXml() {
		String xml = "";
		JAXB.marshal(this, xml);
		return xml;
	}

	@Override
	public Repositories build() {
		return JAXB.unmarshal(new File("./systemPrefs.repositories.xml"),
				Repositories.class);
	}

}
