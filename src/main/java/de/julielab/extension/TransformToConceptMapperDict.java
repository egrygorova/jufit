package de.julielab.extension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TransformToConceptMapperDict {

	static List<String> dictTokens = new ArrayList<>();
	static List<DictToken> dictTokensXML = new ArrayList<>();
	static List<DictToken> dictTokensWithVariants = new ArrayList<>();
	static List<String> canonicals = new ArrayList<>();
	static List<String> codeTypes = new ArrayList<>();
	static List<String> codeValues = new ArrayList<>();
	static List<String> variants = new ArrayList<>();
	static List<String> base = new ArrayList<>();
	// static HashMap<String, String> tokenWithOutCUI = new HashMap<>();
	// static Map<String,HashMap<String,String>> tokenWithCUI = new
	// HashMap<String, HashMap<String, String>>();
	// static Map<String, String> tokenEnd = new HashMap<>();
	// static List<String> tmp = new ArrayList<>();
	static Multimap<String, String> token = ArrayListMultimap.create();

	public static void main(String args[]) throws ParserConfigurationException, TransformerException {

		try {
			String content = new String(Files.readAllBytes(Paths.get("src/main/resources/gazetterTest")));
			String[] entrys = content.split("\n");
			String s = null;
			for (int i = 0; i < entrys.length; i++) {
				s = extractContent(entrys[i]);
				dictTokens.add(s);
			}

			for (String dictToken : dictTokens) {
				List<String> parts = Arrays.asList(dictToken.split("\t"));
				canonicals.add(parts.get(0));
				codeTypes.add(parts.get(1));
				codeValues.add(parts.get(2));
			}

			for (int i = 0; i < codeValues.size(); i++) {
				dictTokensXML.add(createTokenForXML(canonicals.get(i), codeTypes.get(i), codeValues.get(i)));
			}

			for (int j = 0; j < dictTokensXML.size() -1; j++) {
				for (int k = j+1; k < dictTokensXML.size(); k++) {
					if (dictTokensXML.get(j).getCodeValue().equals(dictTokensXML.get(k).getCodeValue())) {
						System.out.println(dictTokensXML.get(j).getCodeValue() + " is a variant of " + dictTokensXML.get(k).getCodeValue());
						Variant variant = createVariantForToken(dictTokensXML.get(j), dictTokensXML.get(k));
						variants.add(variant.getBase());
						DictToken completeToken = createTokenWithVariant(dictTokensXML.get(j), variants);
						dictTokensWithVariants.add(completeToken);
						dictTokensXML.remove(dictTokensXML.get(k));
					} else {
						dictTokensWithVariants.add(dictTokensXML.get(j));
					}
				}
			}
			
			
			createXML(dictTokensWithVariants);
			

			// Stand: alle Attribute extrahiert, nun die CUIs zählen

//			for (int i = 0; i < codeValues.size() - 1; i++) {
//				for (int k = i + 1; k < codeValues.size(); k++) {
//					if (codeValues.get(i).equals(codeValues.get(k))) {
//						System.out.println(k + " is a variant of " + i);
//						// createVariantofToken(codeValues.get(i),
//						// codeValues.get(k));
//					}
//				}
//			}

			for (int i = 0; i < codeValues.size(); i++) {
				token.put(codeValues.get(i), canonicals.get(i));
			}

			System.out.println(token);
			System.out.println(token.entries());
			System.out.println(token.size());
			System.out.println(token.keySet() + " " + token.keys());

			for (Map.Entry<String, String> entry : token.entries()) {
				System.out.println(entry.getKey() + "/" + entry.getValue());

			}

		} catch (IOException e) {
		}
	
	}

	// private static void createVariantofToken(String string, String string2) {
	// Variant variant = new Variant();
	//
	// }

	// private static void createTokensForXMLWithMap(Multimap<String, String>
	// token2) {
	// DictToken dictToken = new DictToken();
	// for (Map.Entry<String, String> p : token2.entries()) {
	// dictToken.setCanonical(p.getValue());
	// dictToken.setCodeValue(p.getKey());
	// dictToken.setCodeType("UMLS");
	// }
	//
	// // createXML(dictToken);
	// }
	private static Variant createVariantForToken(DictToken dictToken, DictToken newVariant) {
		Variant variant = new Variant();
		variant.setBase(newVariant.getCanonical());
		dictToken.setVariants(variants2);
		return variant;
	}

	private static DictToken createTokenForXML(String canonical, String codeType, String codeValue)
			throws ParserConfigurationException, TransformerException {
		DictToken token = new DictToken();
		token.setCanonical(canonical);
		token.setCodeType(codeType);
		token.setCodeValue(codeValue);
		//token.setVariants(variants);

		return token;
	}
	
	private static DictToken createTokenWithVariant(DictToken token, List<String> variants2) {
		token.setVariants(variants2);
		return token;
	}

	private static void createXML(List<DictToken> dictTokenXML)
			throws ParserConfigurationException, TransformerException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
		Document doc = docbuilder.newDocument();

		Element rootElement = doc.createElement("dict");
		doc.appendChild(rootElement);

		for (DictToken dictToken : dictTokenXML) {
			createXMLEntry(dictToken, doc, rootElement);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("src/main/resources/gazetter.xml"));
		transformer.transform(source, result);

		// Output to console for testing
		StreamResult consoleResult = new StreamResult(System.out);
		transformer.transform(source, consoleResult);
		// dictTokensXML.add(token);
		//
		// Dictionary dict = new Dictionary();
		// dict.setTokens(dictTokensXML);
		//
		// File file = new File("src/main/resources/gazetter.xml");
		// JAXB.marshal(dict, file);
	}

	private static void createXMLEntry(DictToken dictToken, Document doc, Element rootElement) {

		Element token = doc.createElement("token");
		rootElement.appendChild(token);

		Attr canonical = doc.createAttribute("canonical");
		canonical.setValue(dictToken.getCanonical());
		token.setAttributeNode(canonical);

		Attr codeType = doc.createAttribute("codeType");
		codeType.setValue(dictToken.getCodeType());
		token.setAttributeNode(codeType);

		Attr codeValue = doc.createAttribute("codeValue");
		codeValue.setValue(dictToken.getCodeValue());
		token.setAttributeNode(codeValue);
		
		Element variant = doc.createElement("variant");
		token.appendChild(variant);

		Attr base = doc.createAttribute("base");
		variant.setAttributeNode(base);


	}

	private static String extractContent(String entry) {

		entry = entry.replaceAll("@+", "");

		entry = entry.replaceAll("ANY", "");

		entry = entry.replaceAll("SC", "S\tC");

		return entry;
	}
}
