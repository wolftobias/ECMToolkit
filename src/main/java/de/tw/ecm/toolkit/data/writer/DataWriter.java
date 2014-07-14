package main.java.de.tw.ecm.toolkit.data.writer;

import main.java.de.tw.ecm.toolkit.data.DataRow;

public interface DataWriter {

	public void open(String sql) throws WriterException;

	public void close() throws WriterException;

	public void createRow(DataRow items) throws WriterException;
	
	public void updateRow(DataRow items) throws WriterException;
	
	public void deleteRow(DataRow items) throws WriterException;
}
