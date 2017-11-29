import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXB;

public class TransformToConceptMapperDict {
	static List<String> dictTokens = new ArrayList<>();
	static List<DictToken> dictTokensXML = new ArrayList<>();

	public static void main(String args[]) {

		try {
			String content = new String(Files.readAllBytes(Paths.get("gazetterTest")));
			String[] entrys = content.split("\n");
			String s = null;
			for (int i = 0; i < entrys.length; i++) {
				s = extractContent(entrys[i]);
				dictTokens.add(s);
			}

			for (String dictToken : dictTokens) {
			// System.out.println(dictToken);
				createDictToken(dictToken);
			}
		} catch (IOException e) {
		}

		// JAXBContext jaxbContext = JAXBContext.newInstance(DictToken.class);
		// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		//
		// // output pretty printed
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//
		// jaxbMarshaller.marshal(token, file);
		// jaxbMarshaller.marshal(token, System.out);

//		Variant v1 = new Variant();
//		v1.setBase("Schmerz abdominal");
//
//		Variant v2 = new Variant();
//		v2.setBase("Hirnnerv VI");
//
//		DictToken token = new DictToken();
//		token.setCanonical("Bauchschmerz");
//		token.setCodeType("UMLS");
//		token.setCodeValue("C0000737");
//		token.setVariants(Arrays.asList(v1));
//
//		DictToken token2 = new DictToken();
//		token2.setCanonical("Bauchmuskeln");
//		token2.setCodeType("UMLS");
//		token2.setCodeValue("C0000739");
//
//		DictToken token3 = new DictToken();
//		token3.setCanonical("Nervus abducens");
//		token3.setCodeType("UMLS");
//		token3.setCodeValue("C0000741");
//		token3.setVariants(Arrays.asList(v2));


	}

	private static void createDictToken(String dictToken) {
		List<String> canonicals = new ArrayList<>();
		List<String> codeTypes = new ArrayList<>();
		List<String> codeValues = new ArrayList<>();

		String[] parts = dictToken.split("\t|\\s+");
//		for (String part : parts) {
////			 System.out.println(part);
////			 System.out.println("-------------");
//		}

		for (int i = 0; i < parts.length; i++) {
			if (parts[i].contains("UMLS")) {
				codeTypes.add(parts[i]);
			}

			else if (parts[i].matches("[C]{1}\\d{7}")) {
				codeValues.add(parts[i]);
			} else if (!(parts[i].matches("[C]{1}\\d{7}") && parts[i].contains("UMLS"))) {
				canonicals.add(parts[i]);
			}

		}

		 for (String cs : canonicals) {
		 System.out.println(cs);
		 }
		 
		 for (String codeType : codeTypes) {
		 System.out.println(codeType);
		 }
		 
		 for (String cc : codeValues) {
		 System.out.println(cc);
		 }
		 
		 createTokensForXML(canonicals, codeTypes, codeValues);
		 
		// TODO jedes erste part muss in canonicals Liste
		// jedes zweite part muss in codeTypes Liste
		// jedes dritte part muss in codeValues Liste

	}

	private static void createTokensForXML(List<String> canonicals, List<String> codeTypes, List<String> codeValues) {
		DictToken token = new DictToken();
		for (String canonical : canonicals) {
			token.setCanonical(canonical);
		}
		for (String codeType : codeTypes) {
			token.setCodeType(codeType);
		}
		for (String codeValue : codeValues) {
			token.setCodeValue(codeValue);
		}

		//token.setVariants(variants);
		createXML(token);
	}

	private static void createXML(DictToken token) {
		dictTokensXML.add(token);

		Dictionary dict = new Dictionary();
		dict.setTokens(dictTokensXML);

		File file = new File("gazetter.xml");
		JAXB.marshal(dict, file);
	}

	private static String extractContent(String entry) {
		
		entry = entry.replaceAll("@+", "");

		entry = entry.replaceAll("ANY", "");

		entry = entry.replaceAll("SC", "S\tC");

		return entry;
	}
}
