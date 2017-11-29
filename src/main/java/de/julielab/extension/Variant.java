package de.julielab.extension;

import javax.xml.bind.annotation.XmlAttribute;

public class Variant {
	String base;

	public String getBase() {
		return base;
	}
	@XmlAttribute
	public void setBase(String base) {
		this.base = base;
	}
	
}
