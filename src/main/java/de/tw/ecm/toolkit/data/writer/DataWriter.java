package main.java.de.tw.ecm.toolkit.data.writer;

public interface DataWriter {

	public void open(String url) throws WriterException;

	public void close() throws WriterException;

	public void create(Object[] items) throws WriterException;

	public void update() throws WriterException;

	public void delete() throws WriterException;
}
