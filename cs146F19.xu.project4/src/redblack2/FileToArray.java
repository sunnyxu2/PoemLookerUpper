package redblack2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToArray {
	public FileToArray() throws IOException {
		
	}

	public static String[] fileLinesToArray(File inputFile) throws IOException {
		FileReader inFile = new FileReader(inputFile);
		BufferedReader buffIn = new BufferedReader(inFile);
		ArrayList<String> tempArray = new ArrayList<>();
		String currentLine; // skip that first line for the purpose of this assignment
		while ((currentLine = buffIn.readLine()) != null) {
			tempArray.add(currentLine);
		}

		buffIn.close();
		inFile.close();
		return tempArray.toArray(new String[0]);
	}
	
	public static String[] splitIntoWords(String[] lines) {
		String total = "";
		for (String words : lines) {
			total += words.toLowerCase() + " ";
		}
		total = total.replaceAll("\\p{Punct}", "");
		total = total.replaceAll("”", "");
		total = total.replaceAll("“", "");
		return total.split("\\s");
	}
	
	public static String dictionaryStats(RedBlackTree<String> dictionary, String[] wordsOfFile) {
		int identifiedWords = 0;
		int unIdentifiedWords = 0;
		for (String words : wordsOfFile) {
			RedBlackTree.Node<String> node = dictionary.lookup(words); 
			if (node != null) {
				if (node.key.equals(words)) {
					//System.out.println("Found " + words + " in dictionary."); 
					identifiedWords++;
				} else {
					//System.out.println("Did not find" + words + " in dictionary.");
					unIdentifiedWords++;
				}
			} else {
				unIdentifiedWords++;
			}
		}
		return "Identified words: " + identifiedWords + "\nUnidentified words: " + unIdentifiedWords;
	}
}

