package main.java.de.tw.ecm.toolkit.data.reader;

import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataRow;

public interface DataReader {

	public void open(String url) throws ReaderException;

	public void close() throws ReaderException;

	public DataRow readRow() throws ReaderException;

	public boolean next() throws ReaderException;

	public DataHeader readHeaders() throws ReaderException;

	public String getEntity() throws ReaderException;

}
