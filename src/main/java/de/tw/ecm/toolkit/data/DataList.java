package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataList {
	
	private Entity entity;
	
	private String entityId;

	private DataHeader header;

	private List<DataRow> data = new ArrayList<>();

	public DataList(String entityId, DataHeader header) {
		this.entityId = entityId;
		this.header = header;
	}
	
	public DataList(Entity entity) {
		this.entity = entity;
	}

	public void add(DataRow row) {
		this.data.add(row);
	}

	public int size() {
		return this.data.size();
	}

	public DataRow get(int i) {
		return this.data.get(i);
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public DataHeader getHeader() {
		return header;
	}

	public void setHeader(DataHeader header) {
		this.header = header;
	}
	
	public String[] getHeaders() {
		return this.header.toArray();
	}
	
	public List<DataRow> getData() {
		return data;
	}

	public void setData(List<DataRow> data) {
		this.data = data;
	}

	public ObservableList<DataRow> toObservableList() {
		return FXCollections.observableList(this.data);
	}
}
