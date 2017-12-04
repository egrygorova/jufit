package de.julielab.extension;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TransformToConceptMapperDict {

	static List<DictToken> dictTokensXML = new ArrayList<>();

	public static void main(String args[]) throws ParserConfigurationException, TransformerException {

		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get("src/main/resources/gazetterTest")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] entrys = content.split("\n");
		String s;
		for (int i = 0; i < entrys.length; i++) {
			s = extractContent(entrys[i]);
			List<String> parts = Arrays.asList((s.split("\t")));
			boolean found = false;
			for (DictToken dt : dictTokensXML) {
				if (dt.getCodeValue().equals(parts.get(2))) {
					found = true;
					dt.AddVariants(parts.get(0));
					System.out.println(parts.get(0));
					break;
				}
			}

			if (!found) {
				DictToken dictToken = new DictToken(parts.get(1), parts.get(2));
				dictToken.AddVariants(parts.get(0));
				System.out.println(parts.get(0));
				dictTokensXML.add(dictToken);
			}

		}

		System.out.println(dictTokensXML.size());
		for (DictToken d : dictTokensXML) {
			System.out.println(d.getCodeValue());
			for (String var : d.getVariants()) {
				System.out.println(var);
			}
			System.out.println(d.getVariants().size());
		}

		createXMLWithDOM(dictTokensXML);
	}

	private static void createXMLWithDOM(List<DictToken> dictTokensWithVariants)
			throws ParserConfigurationException, TransformerException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
		Document doc = docbuilder.newDocument();

		Element rootElement = doc.createElement("dict");
		doc.appendChild(rootElement);

		for (DictToken dictToken : dictTokensWithVariants) {
			createXMLEntry(dictToken, doc, rootElement);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("gazetterDOM.xml"));
		transformer.transform(source, result);

	}

	private static void createXMLEntry(DictToken dictToken, Document doc, Element rootElement) {

		Element token = doc.createElement("token");
		rootElement.appendChild(token);

		Attr canonical = doc.createAttribute("canonical");
		canonical.setValue(dictToken.getVariants().get(0));
		token.setAttributeNode(canonical);

		Attr codeType = doc.createAttribute("codeType");
		codeType.setValue(dictToken.getCodeType());
		token.setAttributeNode(codeType);

		Attr codeValue = doc.createAttribute("codeValue");
		codeValue.setValue(dictToken.getCodeValue());
		token.setAttributeNode(codeValue);

		if (dictToken.getVariants().size() > 1) {
			for (int i = 1; i < dictToken.getVariants().size(); ++i) {
				Element variant = doc.createElement("variant");
				token.appendChild(variant);
				Attr base = doc.createAttribute("base");
				base.setValue(dictToken.getVariants().get(i));
				variant.setAttributeNode(base);
			}

		}
	}

	private static String extractContent(String entry) {

		entry = entry.replaceAll("@+", "\t");

		entry = entry.replaceAll("ANY", "");

		entry = entry.replaceAll("---SYN", "");

		return entry;
	}
}