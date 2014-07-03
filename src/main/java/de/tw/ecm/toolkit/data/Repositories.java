package main.java.de.tw.ecm.toolkit.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.Builder;

@XmlRootElement(name = "repositories")
public class Repositories extends Values<Repository> implements
		Builder<Repositories>, Iterable<Repository> {

	private String defaultRepo;

	public Repositories() {
	}

	public Repositories(List<Repository> repositories) {
		this.values = repositories;
	}

	public Repository getByCaption(String caption) {
		for (Repository repository : this.values) {
			if (repository.getCaption().equals(caption))
				return repository;
		}

		return null;
	}

	public Repository getById(String id) {
		for (Repository repository : this.values) {
			if (repository.getId().equalsIgnoreCase(id))
				return repository;
		}

		return null;
	}

	@XmlElement(name = "repository")
	public List<Repository> getRepositories() {
		return this.values;
	}

	@XmlAttribute(name = "default")
	public String getDefault() {
		return this.defaultRepo;
	}

	public void setDefault(String defaultRepo) {
		this.defaultRepo = defaultRepo;
	}

	public List<String> getRepositoryIds() {
		List<String> ids = new ArrayList<>();
		for (Repository repository : this.values) {
			ids.add(repository.getId());
		}

		return ids;
	}

	public List<String> getRepositoryCaptions() {
		List<String> captions = new ArrayList<>();
		for (Repository repository : this.values) {
			captions.add(repository.getCaption());
		}

		return captions;
	}

	public String toXml() {
		String xml = "";
		JAXB.marshal(this, xml);
		return xml;
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
