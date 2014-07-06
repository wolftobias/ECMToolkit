package main.java.de.tw.ecm.toolkit.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import main.java.de.tw.ecm.toolkit.data.Values;

import org.apache.commons.lang3.builder.Builder;

@XmlTransient
public abstract class BaseViews<T> extends Values<T> implements
		Builder {

	protected String defaultView;

	public BaseViews() {
	}

	@XmlAttribute
	public String getDefault() {
		return defaultView;
	}

	public void setDefault(String defaultView) {
		this.defaultView = defaultView;
	}
}