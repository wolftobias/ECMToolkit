package main.java.de.tw.ecm.toolkit.data;

public interface DataCursor {

	public boolean next() throws DataSourceException;

	public void close() throws DataSourceException;

	public Object get(int i) throws DataSourceException;
	
	public Object get(String columnLabel) throws DataSourceException;	
}
