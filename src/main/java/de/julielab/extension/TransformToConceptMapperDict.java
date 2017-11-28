package de.julielab.extension;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class TransformToConceptMapperDict {
	
	public static void main (String args[]) {
	
	DictToken token = new DictToken();
	token.setCanonical("haha");
	token.setCodeType("UMLS");
	token.setCodeValue("Codevalue");

	  try {

		File file = new File("src/main/resources/gazetter.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(DictToken.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(token, file);
		jaxbMarshaller.marshal(token, System.out);

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
	
	}
	

}
