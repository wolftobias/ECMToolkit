package main.java.de.tw.ecm.toolkit.data.sources;

import java.io.File;
import java.util.List;

import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;
import main.java.de.tw.ecm.toolkit.data.reader.CSVDataReader;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;
import main.java.de.tw.ecm.toolkit.data.writer.CSVDataWriter;
import main.java.de.tw.ecm.toolkit.data.writer.DataWriter;
import main.java.de.tw.ecm.toolkit.data.writer.WriterException;

import com.sun.istack.internal.logging.Logger;

public class FileDataSource {

	Logger log = Logger.getLogger(AbstractDataSource.class);

	protected File file;

	protected Entity entity;
	
	public FileDataSource(File file, Entity entity) {
		this.file = file;
		this.entity = entity;
	}

	public DataList readList() throws DataSourceException {
		DataList dataList = null;
		DataReader reader = null;

		try {
			reader = new CSVDataReader(file, this.entity);
			dataList = new DataList(this.entity);
			while (reader.next()) {
				dataList.add(reader.readRow());
			}
		} catch (ReaderException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(reader);
		}

		return dataList;
	}

	public void writeList(DataList list) throws DataSourceException {
		CSVDataWriter writer = null;
		try {
			List<String> headers = list.getHeaderCaptions();
			
			if(headers.isEmpty())
				headers = list.getHeaderNames();
			
			writer = new CSVDataWriter(file);
			writer.writeHeader(headers);

			for (int i = 0; i < list.size(); i++) {
				DataRow dataRow = list.get(i);
				writer.writeRow(dataRow);
			}

		} catch (WriterException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(writer);
		}
	}

	protected void closeQuietly(DataReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (ReaderException e) {
			log.logSevereException(e);
		}
	}

	protected void closeQuietly(DataWriter writer) {
		try {
			if (writer != null)
				writer.close();
		} catch (WriterException e) {
			log.logSevereException(e);
		}
	}

}
