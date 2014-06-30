package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;

public class DataHeader {

	private ArrayList<String> names = new ArrayList<>();

	private ArrayList<Caption> captions = new ArrayList<>();

	public DataHeader() {
	}

	public DataHeader(List<String> names, List<Caption> captions) {
		this.names = new ArrayList<>(names);
		this.captions = new ArrayList<>(captions);
	}

	public void add(String name, Caption caption) {
		this.names.add(name);
		this.captions.add(caption);
	}

	public String getName(int i) {
		return this.names.get(i);
	}

	public Caption getCaption(int i) {
		return this.captions.get(i);
	}

	public int size() {
		return this.names.size();
	}
	
	public List<String> getNames() {
		return this.names;
	}
	
	public int getPosition(String column) {
		int counter = 0;
		for (String name : names) {
			if(name.equals(column))
				return counter++;
		}
		return counter;
	}
	
	public List<String> getCaptions() {
		List<String> labels = new ArrayList<>();
		for (Caption caption : this.captions) {
			labels.add(caption.getText());
		}
		return labels;
	}
	
}
