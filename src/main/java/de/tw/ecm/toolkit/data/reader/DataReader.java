package main.java.de.tw.ecm.toolkit.data.reader;

import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;

public interface DataReader {

	public void open(String query) throws ReaderException;

	public void close() throws ReaderException;

	public DataRow readRow() throws ReaderException;

	public boolean next() throws ReaderException;

	public DataHeader readHeaders() throws ReaderException;

	public Entity getEntity() throws ReaderException;
	
	public int getRowCount(String query) throws ReaderException;

}
