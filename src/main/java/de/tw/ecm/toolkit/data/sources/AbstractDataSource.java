package main.java.de.tw.ecm.toolkit.data.sources;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataSource implements DataSource {

	Logger log = Logger.getLogger(AbstractDataSource.class);

	@Override
	public DataList readList(String query) throws DataSourceException {
		DataReader reader = this.read(query);
		DataList dataList = new DataList();

		try {
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
	
	
	
	protected void closeQuietly(DataReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (ReaderException e) {
			log.logSevereException(e);
		}
	}

}
