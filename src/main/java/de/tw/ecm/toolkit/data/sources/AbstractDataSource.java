package main.java.de.tw.ecm.toolkit.data.sources;

import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.ECMProperties;
import main.java.de.tw.ecm.toolkit.data.Repository;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataSource implements DataSource {

	Logger log = Logger.getLogger(AbstractDataSource.class);

	protected Repository repository;
	
	protected ECMProperties properties;

	@Override
	public void initialize(Repository repository, ECMProperties properties)
			throws DataSourceException {
		this.repository = repository;
		this.properties = properties;
	}

	@Override
	public DataList readList(String query) throws DataSourceException {
		DataList dataList = null;
		DataReader reader = this.read(query);

		try {
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

	protected void closeQuietly(DataReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (ReaderException e) {
			log.logSevereException(e);
		}
	}

}
