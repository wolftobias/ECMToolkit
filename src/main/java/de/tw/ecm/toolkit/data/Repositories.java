package main.java.de.tw.ecm.toolkit.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.Builder;

@XmlRootElement(name = "repositories")
public class Repositories implements Builder<Repositories>,
		Iterable<Repository> {

	private ArrayList<Repository> cache = new ArrayList<>();

	private String defaultRepo;

	private Repository selected;

	public Repositories() {
	}

	public Repositories(ArrayList<Repository> repositories) {
		this.cache = repositories;
	}

	public Repository getByCaption(String caption) {
		for (Repository repository : this.cache) {
			if (repository.getCaption().equals(caption))
				return repository;
		}

		return null;
	}

	public Repository getById(String id) {
		for (Repository repository : this.cache) {
			if (repository.getId().equalsIgnoreCase(id))
				return repository;
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

	@XmlAttribute(name = "default")
	public String getDefault() {
		return this.defaultRepo;
	}

	public void setDefault(String defaultRepo) {
		this.defaultRepo = defaultRepo;
	}

	public void setDefaultRepository(String defaultRepo) {
		this.defaultRepo = defaultRepo;
	}

	public List<String> getRepositoryIds() {
		List<String> ids = new ArrayList<>();
		for (Repository repository : this.cache) {
			ids.add(repository.getId());
		}

		return ids;
	}

	public List<String> getRepositoryCaptions() {
		List<String> captions = new ArrayList<>();
		for (Repository repository : this.cache) {
			captions.add(repository.getCaption());
		}

		return captions;
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
	public Iterator<Repository> iterator() {
		return this.cache.iterator();
	}

	@Override
	public Repositories build() {
		Repositories repositories = JAXB.unmarshal(new File(
				"./systemPrefs.repositories.xml"), Repositories.class);

		for (Repository repository : repositories) {
			repository.build();
		}

		return repositories;
	}
}
