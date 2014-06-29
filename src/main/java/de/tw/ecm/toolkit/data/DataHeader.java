package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHeader {
	
	private List<String> values = new ArrayList<>();

	public DataHeader() {
	}

	public DataHeader(List<String> values) {
		this.values = values;
	}
	
	public DataHeader(String[] values) {
		this.values = Arrays.asList(values);
	}
	
	public void add(String value) {
		this.values.add(value);
	}

	public int size() {
		return this.values.size();
	}

	public String get(int i) {
		return this.values.get(i);
	}
	
	public String[] toArray() {
		return this.values.toArray(new String[this.size()]);
	}
	
	public List<String> getValues() {
		return this.values;
	}
}
