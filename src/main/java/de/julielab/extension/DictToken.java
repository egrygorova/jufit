package de.julielab.extension;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="token")
public class DictToken {
	String canonical;
	String codeType;
	String codeValue;
	List<Variant> variants = new ArrayList<>();
	
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
	
	@XmlElement( name = "variant" )
	  public List<Variant> getVariants()
	  {
	    return variants;
	  }

	  public void setVariants(List<Variant> variants)
	  {
	    this.variants = variants;
	  }
	
	
}
