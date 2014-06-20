package main.java.de.tw.ecm.toolkit.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.de.tw.ecm.toolkit.data.reader.DataReader;
import main.java.de.tw.ecm.toolkit.data.reader.ReaderException;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataSource implements DataSource {

	Logger log = Logger.getLogger(AbstractDataSource.class);

	@Override
	public ObservableList readAsList(String query) throws DataSourceException {
		DataReader reader = this.read(query);
		ObservableList data = FXCollections.observableArrayList();

		try {
			while (reader.next()) {
				data.add(reader.readAsList());
			}
		} catch (ReaderException e) {
			throw new DataSourceException(e);
		} finally {
			closeQuietly(reader);
		}

		return data;
	}

	private void closeQuietly(DataReader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (ReaderException e) {
			log.logSevereException(e);
		}
	}

}
