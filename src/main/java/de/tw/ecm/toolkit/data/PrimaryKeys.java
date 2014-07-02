package main.java.de.tw.ecm.toolkit.data;

import java.util.List;

public class PrimaryKeys extends Values<String> {

	public PrimaryKeys() {

	}

	public PrimaryKeys(List<String> primaryKeys) {
		this.values = primaryKeys;
	}

	public String getByName(String name) {
		for (String key : values) {
			if (key.equals(name))
				return key;
		}

		return null;
	}
}
