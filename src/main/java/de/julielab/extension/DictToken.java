package de.julielab.extension;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="token")
public class DictToken {
	String canonical;
	String codeType;
	String codeValue;
	String variantBase;
	
	public DictToken() {
		
	}
	
	public String getCanonical() {
		return canonical;
	}
	
	@XmlAttribute
	public void setCanonical(String canonical) {
		this.canonical = canonical;
	}
	public String getCodeType() {
		return codeType;
	}
	
	@XmlAttribute
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeValue() {
		return codeValue;
	}
	
	@XmlAttribute
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getVariantBase() {
		return variantBase;
	}
	
	@XmlElement
	public void setVariantBase(String variantBase) {
		this.variantBase = variantBase;
	}
	
	
}
