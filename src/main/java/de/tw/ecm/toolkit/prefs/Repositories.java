package main.java.de.tw.ecm.toolkit.prefs;

import java.util.ArrayList;

public class Repositories {

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

	public Repository[] getRepositories() {
		return (Repository[]) this.cache.toArray(new Repository[cache.size()]);
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

	public Repository getSelectedRepository() {
		return selected;
	}

	public void setSelectedRepository(String selected) {
		this.selected = this.getByCaption(selected);
	}

}
