package main.java.de.tw.ecm.toolkit.data.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataList;
import main.java.de.tw.ecm.toolkit.data.DataRow;

import org.supercsv.io.AbstractCsvReader;
import org.supercsv.prefs.CsvPreference;

public class CSVDataReader extends AbstractDataReader {

	private CSVReader csvReader;
	
	public CSVDataReader() {
	}

	public CSVDataReader(String file) throws ReaderException {
		this.open(file);
	}

	public CSVDataReader(File file) throws ReaderException {
		this.open(file);
	}

	public void open(File file) throws ReaderException {
		try {
			this.entity = file.toString();
			this.csvReader = new CSVReader(new BufferedReader(new FileReader(
					file)));
		} catch (IOException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public void open(String url) throws ReaderException {
		this.open(new File(url));
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
	public DataRow readRow() throws ReaderException {
		int length = this.csvReader.length();
		Object[] result = new Object[length];

		for (int i = 1; i <= length; i++) {
			Object object = this.csvReader.get(i);
			result[i - 1] = object;
		}

		return new DataRow(result);
	}

	@Override
	public boolean next() throws ReaderException {
		try {
			return this.csvReader.readRow();
		} catch (IOException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public DataHeader readHeaders() throws ReaderException {
		try {
			String[] headers = this.csvReader.getHeader(false);
			return new DataHeader(headers);
		} catch (IOException e) {
			throw new ReaderException(e);
		}
	}

	@Override
	public String getEntity() throws ReaderException {
		return this.entity;
	}

	private class CSVReader extends AbstractCsvReader {
		CSVReader(final Reader reader) {
			super(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		}
		
		public boolean readRow() throws IOException {
			return super.readRow();
		}
	}
	
}
