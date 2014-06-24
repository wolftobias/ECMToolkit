package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataList {

	private List<DataRow> rows = new ArrayList<>();

	public DataList() {
	}

	public DataList(List<DataRow> rows) {
		this.rows = rows;
	}

	public void add(DataRow row) {
		this.rows.add(row);
	}

	public int size() {
		return this.rows.size();
	}

	public DataRow get(int i) {
		return this.rows.get(i);
	}
	
	public ObservableList<DataRow> toObservableList() {
		return FXCollections.observableList(this.rows);
	}
}
