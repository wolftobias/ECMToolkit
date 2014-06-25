package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRow {
	
	private List<Object> values = new ArrayList<>();

	public DataRow() {
	}

	public DataRow(List<Object> values) {
		this.values = values;
	}
	
	public DataRow(Object[] values) {
		this.values = Arrays.asList(values);
	}
	
	public void add(Object value) {
		this.values.add(value);
	}

	public int size() {
		return this.values.size();
	}

	public Object get(int i) {
		return this.values.get(i);
	}
}