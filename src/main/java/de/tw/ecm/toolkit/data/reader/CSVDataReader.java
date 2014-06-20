package main.java.de.tw.ecm.toolkit.data.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.supercsv.io.AbstractCsvReader;
import org.supercsv.prefs.CsvPreference;

public class CSVDataReader extends AbstractDataReader {

	private File file;

	private CSVReader csvReader;

	public CSVDataReader() {
	}

	@Override
	public void open(String url) throws ReaderException {
		this.file = new File(url);

		if (!this.file.exists())
			throw new ReaderException("File '" + url + "' does not exist!");

		try {
			this.csvReader = new CSVReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public void close() throws ReaderException {
		try {
			this.csvReader.close();
		} catch (IOException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public Object[] read() throws ReaderException {
		int length = this.csvReader.length();
		Object[] result = new Object[length];

		for (int i = 0; i < length; i++) {
			Object object = this.csvReader.get(i);
			result[i] = object;
		}

		return result;
	}

	@Override
	public boolean next() throws ReaderException {
		// TODO Auto-generated method stub
		return false;
	}

	private class CSVReader extends AbstractCsvReader {
		CSVReader(final Reader reader) {
			super(reader, CsvPreference.STANDARD_PREFERENCE);
		}
	}
}
