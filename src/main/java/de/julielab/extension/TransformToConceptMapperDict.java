package de.julielab.extension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransformToConceptMapperDict {
	//Input - JuFiT-WB
	//Output - CM-WB
	public static void main(String[] args) {
		String pathToGazetteer = "src/main/resources/gazetterTest";
		
		try {
			String content = new String(Files.readAllBytes(Paths.get(pathToGazetteer)));
			System.out.println(content);
			System.out.println("------------------------");
			replaceContent(content);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private static void replaceContent(String content) {
		String cuiRegex = "[C]{1}\\d{7}";
		content = content.replaceAll("@+", "");
		content = content.replaceAll("ANY", "");
		content = content.replaceAll("UMLS", "\n\tCodeType=UMLS");
		content = content.replaceAll("SC", "S\tCodeValue=C");
		content = "<token canonical=" + content + "\n</token>";
		System.out.println(content);
		compareCuis(cuiRegex);
		// TODO Auto-generated method stub
		
	}

	private static void compareCuis(String cuiRegex) {
		
		// TODO Auto-generated method stub
		
	}
}
