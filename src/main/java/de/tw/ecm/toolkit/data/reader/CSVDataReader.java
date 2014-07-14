package main.java.de.tw.ecm.toolkit.data.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import main.java.de.tw.ecm.toolkit.data.Attribute;
import main.java.de.tw.ecm.toolkit.data.Attributes;
import main.java.de.tw.ecm.toolkit.data.DataHeader;
import main.java.de.tw.ecm.toolkit.data.DataRow;
import main.java.de.tw.ecm.toolkit.data.Entity;

import org.supercsv.io.AbstractCsvReader;
import org.supercsv.prefs.CsvPreference;

public class CSVDataReader extends AbstractDataReader {

	private CSVReader csvReader;

	protected File file;

	private DataHeader dataHeader;

	public CSVDataReader(String file, Entity entity) throws ReaderException {
		this(new File(file), entity);
	}

	public CSVDataReader(File file, Entity entity) throws ReaderException {
		this.file = file;
		this.entity = entity;
		this.open(file);
		this._readHeaders();
	}

	public void open(File file) throws ReaderException {
		try {
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
		List<String> names = this.dataHeader.getNames();

		for (int i = 0; i < length; i++) {
			Object value = this.csvReader.get(i + 1);
			Object newValue = this.convertToJavaTypes(names.get(i), value);
			result[i] = newValue;
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
		return this.dataHeader;
	}

	@Override
	public Entity getEntity() throws ReaderException {
		return this.entity;
	}

	@Override
	public int getRowCount(String query) throws ReaderException {
		return this.csvReader.length();
	}

	private void _readHeaders() throws ReaderException {
		try {
			this.dataHeader = new DataHeader();
			String[] headers = this.csvReader.getHeader(false);

			for (int i = 0; i < headers.length; i++) {
				String header = headers[i];
				Attributes attributes = this.entity.getAttributes();
				Attribute attribute = attributes.getByCaption(header);
				
				if(attribute == null)
					attribute = attributes.getByName(header);

				dataHeader.add(attribute.getName(), attribute.getCaption());
			}
		} catch (IOException e) {
			throw new ReaderException(e);
		}
	}

	private Object convertToJavaTypes(String header, Object value) {
		Object newValue = null;
		try {
			if (value != null) {
				Attribute attribute = this.entity.getAttributes().getByCaption(
						header);

				if (attribute == null)
					attribute = this.entity.getAttributes().getByName(header);

				Class typeClass = attribute.getTypeClass();
				if (typeClass.equals(Timestamp.class)) {
					newValue = this.parseTimestamp(value.toString());
				} else if (typeClass.equals(Date.class)) {
					newValue = this.parseDate(value.toString());
				} else if (typeClass.equals(Time.class)) {
					newValue = Time.valueOf(value.toString());
				} else if (typeClass.equals(Short.class)) {
					newValue = Short.valueOf(value.toString());
				} else if (typeClass.equals(Integer.class)) {
					newValue = Integer.valueOf(value.toString());
				} else if (typeClass.equals(Long.class)) {
					newValue = Long.valueOf(value.toString());
				} else if (typeClass.equals(Double.class)) {
					newValue = Double.valueOf(value.toString());
				} else {
					newValue = String.valueOf(value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newValue;
	}

	private Timestamp parseTimestamp(String str) {
		try {
			return Timestamp.valueOf(str);
		} catch (Exception e1) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"dd.MM.yyyy hh:mm:ss.SSSS");
				java.util.Date newDate = format.parse(str);
				return new Timestamp(newDate.getTime());
			} catch (ParseException e2) {
				try {
					SimpleDateFormat format = new SimpleDateFormat(
							"dd.MM.yyyy hh:mm:ss");
					java.util.Date newDate = format.parse(str);
					return new Timestamp(newDate.getTime());
				} catch (ParseException e3) {
					try {
						SimpleDateFormat format = new SimpleDateFormat(
								"dd.MM.yyyy hh:mm");
						java.util.Date newDate = format.parse(str);
						return new Timestamp(newDate.getTime());
					} catch (ParseException e4) {
						throw new RuntimeException(e4);
					}
				}
			}
		}
	}

	private Date parseDate(String str) {
		try {
			return Date.valueOf(str);
		} catch (Exception e1) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				java.util.Date newDate = format.parse(str);
				return new Date(newDate.getTime());
			} catch (ParseException e2) {
				throw new RuntimeException(e2);
			}
		}
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
