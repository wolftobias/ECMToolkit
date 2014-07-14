package main.java.de.tw.ecm.toolkit.data.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import main.java.de.tw.ecm.toolkit.data.DataRow;

import org.supercsv.io.AbstractCsvWriter;
import org.supercsv.prefs.CsvPreference;

public class CSVDataWriter extends AbstractDataWriter {

	private CSVWriter csvWriter;

	public CSVDataWriter() throws WriterException {
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
	public void open(String file) throws WriterException {
		this.open(new File(file));
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
	public void createRow(DataRow row) throws WriterException {
		try {
			this.csvWriter.write(row.toArray());
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	@Override
	public void updateRow(DataRow items) throws WriterException {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteRow(DataRow items) throws WriterException {
		// TODO Auto-generated method stub
	}

	public void writeHeader(List<String> headers) throws WriterException {
		try {
			this.csvWriter.writeHeader((String[])headers.toArray(new String[headers.size()]));
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	public void writeComment(String comment) throws WriterException {
		try {
			this.csvWriter.writeComment(comment);
		} catch (IOException e) {
			throw new WriterException(e);
		}
	}

	private class CSVWriter extends AbstractCsvWriter {
		CSVWriter(final Writer writer) {
			super(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		}

		public void write(Object... columns) throws IOException {
			this.writeRow(columns);
		}
	}
}
