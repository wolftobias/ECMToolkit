package main.java.de.tw.ecm.toolkit.data.reader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class AbstractDataReader implements DataReader {

	@Override
	public ObservableList<Object> readAsList() throws ReaderException {
		ObservableList<Object> row = FXCollections.observableArrayList();
		row.addAll(this.readRow());
		return row;
	}
}
