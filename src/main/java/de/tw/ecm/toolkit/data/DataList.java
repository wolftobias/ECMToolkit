package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

public class DataList extends Values<DataRow> {

	private Entity entity;

	private DataHeader header;

	public DataList(Entity entity, DataHeader header) {
		this.entity = entity;
		this.header = header;
	}

	public DataList(Entity entity) {
		this.entity = entity;
	}

	public DataRow addNew(DataRow row) {
		try {
			DataRow newRow = (DataRow)row.clone();
			this.add(newRow);
			return newRow;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
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
	
	public int primaryKeyPos() {
		List<String> names = this.header.getNames();
		for (int i = 0; i < names.size(); i++) {
			PrimaryKeys primaryKeys = this.entity.getPrimaryKeys();
			for (int j = 0; j < primaryKeys.size(); j++) {
				if(names.get(i).equals(primaryKeys.get(j)))
					return i;
			}
		}
		
		return -1;
	}
}
