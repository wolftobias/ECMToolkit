package main.java.de.tw.ecm.toolkit.data;

import java.util.Locale;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder = { "name", "caption" })
public class Attribute {

	private String name;

	private Caption caption;

	private String type;

	private int size;

	private String nativeType;

	private boolean primaryKey = false;

	public Attribute() {
	}

	public Attribute(String name) {
		this.name = name;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	@XmlTransient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlTransient
	public Class getTypeClass() throws ClassNotFoundException {
		return Class.forName(this.getType());
	}

	@XmlTransient
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@XmlTransient
	public String getNativeType() {
		return nativeType;
	}

	public void setNativeType(String nativeType) {
		this.nativeType = nativeType;
	}

	@XmlTransient
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	@XmlType(propOrder = { "text", "localeStr" })
	public static class Caption {

		private String text;

		private Locale locale;

		public Caption() {
			this.locale = Locale.getDefault();
		}

		public Caption(String text) {
			this.text = text;
			this.locale = Locale.getDefault();
		}

		public Caption(String text, Locale locale) {
			this.text = text;
			this.locale = locale;
		}

		public Caption(String text, String locale) {
			this.text = text;
			this.locale = new Locale(locale);
		}

		@XmlValue
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@XmlAttribute(name = "locale")
		public String getLocaleStr() {
			return locale.toString();
		}

		public void setLocaleStr(String locale) {
			this.locale = new Locale(locale);
		}

		@XmlTransient
		public Locale getLocale() {
			return locale;
		}

		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		public boolean equalsIgnoreLocale(Object obj) {
			if (obj instanceof Caption) {
				Caption caption = (Caption) obj;
				if (this.text.equals(caption.getText()))
					return true;
				else
					return false;
			} else if (obj instanceof String) {
				if (this.text.equals((String) obj))
					return true;
				else
					return false;
			} else
				return super.equals(obj);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.name.equals(obj);
	}
	
	@Override
	public String toString() {
		return name.toString() + this.caption;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}	
}
