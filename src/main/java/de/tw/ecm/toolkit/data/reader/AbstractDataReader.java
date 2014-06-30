package main.java.de.tw.ecm.toolkit.data.reader;

import main.java.de.tw.ecm.toolkit.data.Entity;

import com.sun.istack.internal.logging.Logger;

public abstract class AbstractDataReader implements DataReader {

	Logger log = Logger.getLogger(AbstractDataReader.class);

	protected Entity entity;
}
