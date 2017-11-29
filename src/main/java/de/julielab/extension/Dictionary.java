package de.julielab.extension;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dict")
public class Dictionary {
	
	private List<DictToken> tokens = new ArrayList<>();
	
	  @XmlElement( name = "token" )
	  public List<DictToken> getTokens()
	  {
	    return tokens;
	  }

	  public void setTokens( List<DictToken> tokens )
	  {
	    this.tokens = tokens;
	  }

}
