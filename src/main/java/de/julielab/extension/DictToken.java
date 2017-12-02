package de.julielab.extension;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="token")
public class DictToken {
	private String codeType;
	private String codeValue;
	private List<String> variants = new ArrayList<>();
	
	public DictToken(String codeType, String codeValue) {
		this.codeType = codeType;
		this.codeValue = codeValue;
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
	  public List<String> getVariants()
	  {
	    return variants;
	  }

	  
	  public void AddVariants(String canon)
	  {
		  variants.add(canon);
	  }
	
	
}

