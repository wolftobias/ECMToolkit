package main.java.de.tw.ecm.toolkit.data.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.supercsv.io.AbstractCsvWriter;
import org.supercsv.prefs.CsvPreference;

public class CSVDataWriter extends AbstractDataWriter {

	private CSVWriter csvWriter;
	
	public CSVDataWriter() {
	}
	
	public CSVDataWriter(String file) throws WriterException {
		this.open(file);
	}

	public CSVDataWriter(File file) throws WriterException {
		this.open(file);
	}

	public void open(File file) throws WriterException {
		try {
			this.csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(
					file)));
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void open(String url) throws WriterException {
		this.open(new File(url));
	}

	@Override
	public void close() throws WriterException {
		try {
			this.csvWriter.flush();
			this.csvWriter.close();
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void create(Object[] items) throws WriterException {
		try {
			this.csvWriter.write(items);
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void update() throws WriterException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() throws WriterException {
		// TODO Auto-generated method stub

	}

	private class CSVWriter extends AbstractCsvWriter {
		CSVWriter(final Writer writer) {
			super(writer, CsvPreference.STANDARD_PREFERENCE);
		}

		public void write(Object... columns) throws IOException {
			this.writeRow(columns);
		}
	}
}
