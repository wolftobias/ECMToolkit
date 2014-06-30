package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;

public class DataList implements Iterable<DataRow> {

	private Entity entity;

	private String entityId;

	private DataHeader header;

	private List<DataRow> data = new ArrayList<>();

	public DataList(String entityId, DataHeader header) {
		this.entityId = entityId;
		this.header = header;
	}

	public DataList(Entity entity, DataHeader header) {
		this.entity = entity;
		this.entityId = entity.getId();
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

	public List<String> getHeaderNames() {
		return this.header.getNames();
	}

	public List<String> getHeaderCaptions() {
		return this.header.getCaptions();
	}

	public List<DataRow> getData() {
		return data;
	}

	public void setData(List<DataRow> data) {
		// if (this.entity != null) {
		// this.data.clear();
		//
		// for (int i = 0; i < data.size(); i++) {
		// DataRow oldRow = data.get(i);
		// DataRow newRow = this.convertToJavaTypes(oldRow);
		// this.data.add(newRow);
		// }
		// } else
		this.data = data;
	}

	public ObservableList<DataRow> toObservableList() {
		return FXCollections.observableList(this.data);
	}

	@Override
	public Iterator<DataRow> iterator() {
		return this.data.iterator();
	}
}
