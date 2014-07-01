package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRow extends Values<Object> {

	public DataRow() {
	}

	public DataRow(List<Object> values) {
		this.values = new ArrayList<Object>(values);
	}

	public DataRow(Object[] values) {
		this(Arrays.asList(values));
	}

	public boolean replace(Object oldValue, Object newValue) {
		int index;
		if ((index = this.values.indexOf(oldValue)) != -1) {
			this.set(index, newValue);
			return true;
		}

		return false;
	}
	
	public Object set(int index, Object element) {
		return this.values.set(index, element);
	}
}