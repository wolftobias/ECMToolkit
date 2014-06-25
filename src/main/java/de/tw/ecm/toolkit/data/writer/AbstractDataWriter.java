package main.java.de.tw.ecm.toolkit.data.writer;

import main.java.de.tw.ecm.toolkit.data.DataRow;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataWriter implements DataWriter {

	Logger log = Logger.getLogger(AbstractDataWriter.class);

	public void writeRow(DataRow row) throws WriterException {
		Object[] values = row.toArray();
		this.writeRow(values);
	}
}
