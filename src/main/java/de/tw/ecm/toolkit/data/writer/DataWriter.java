package main.java.de.tw.ecm.toolkit.data.writer;

import main.java.de.tw.ecm.toolkit.data.DataRow;

public interface DataWriter {

	public void open(String url) throws WriterException;

	public void close() throws WriterException;

	public void writeRow(DataRow items) throws WriterException;
}
