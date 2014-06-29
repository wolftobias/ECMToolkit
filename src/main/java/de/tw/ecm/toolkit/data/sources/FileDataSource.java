package main.java.de.tw.ecm.toolkit.data.sources;

import java.io.File;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;
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

	public FileDataSource(File file) {
		this.file = file;
	}

	public DataList readList() throws DataSourceException {
		DataList dataList = null;
		DataReader reader = null;

		try {
			reader = new CSVDataReader(file);
			dataList = new DataList(reader.getEntity(), reader.readHeaders());
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
			writer = new CSVDataWriter(file);
			writer.writeHeader(list.getHeaders());

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
