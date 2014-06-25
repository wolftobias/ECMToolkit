package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

public class ECMProperties {

	private ArrayList<ECMProperty> properties = new ArrayList<>();

	public ECMProperties() {
	}

	public String getProperty(String key) {
		ECMProperty prop = this.getECMProperty(key);

		if (prop != null)
			return prop.getValue();
		else
			return null;
	}

	public void setProperty(String key, String value) {
		this.setECMProperty(new ECMProperty(key, value));
	}

	public void setECMProperty(ECMProperty property) {
		this.properties.add(property);
	}

	public ECMProperty getECMProperty(String key) {
		ECMProperty property;
		for (int i = 0; i < properties.size(); i++) {
			property = properties.get(i);
			if (property.getKey().equals(key))
				return property;
		}
		return null;
	}

	public List<ECMProperty> getProperties() {
		return this.properties;
	}

	@XmlType(name = "property")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ECMProperty {

		@XmlAttribute
		private String key;

		@XmlValue
		private String value;

		public ECMProperty() {
		}

		public ECMProperty(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
