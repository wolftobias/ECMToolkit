package main.java.de.tw.ecm.toolkit.data.reader;

import javafx.collections.ObservableList;

public interface DataReader {

	public void open(String url) throws ReaderException;

	public void close() throws ReaderException;

	public Object[] readRow() throws ReaderException;

	public ObservableList<Object> readAsList() throws ReaderException;

	public boolean next() throws ReaderException;
}
