package redblack2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class RBTTester {
	static class NanoTimer {
		private static long startTime;
		private static long endTime;

		public static void startTimer() {
			startTime = System.nanoTime();
		}

		public static void endTimer() {
			endTime = System.nanoTime();
		}

		public static long getTime() {
			return endTime - startTime;
		}
	}
	
	@Test
	// Test the Red Black Tree
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		
		try {
			File di = new File(new File("").getAbsolutePath() + "\\data\\dictionary.txt");
			String[] dict = FileToArray.fileLinesToArray(di);
			File po = new File(new File("").getAbsolutePath() + "\\data\\poem.txt");
			String[] poem = FileToArray.fileLinesToArray(po);
			
			// checks validity of the dict array
			FileReader inFile = new FileReader(di);
			BufferedReader buffIn = new BufferedReader(inFile);
			String currentLine;
			int ind = 0;
			while ((currentLine = buffIn.readLine()) != null) {
				assertEquals(currentLine, dict[ind]);
				ind++;
			}

			buffIn.close();
			inFile.close();
			
			// checks validity of poem
			FileReader inFile2 = new FileReader(po);
			BufferedReader buffIn2 = new BufferedReader(inFile2);
			String currentLine2;
			int ind2 = 0;
			while ((currentLine2 = buffIn2.readLine()) != null) {
				assertEquals(currentLine2, poem[ind2]);
				ind2++;
			}

			buffIn2.close();
			inFile2.close();
			
			NanoTimer.startTimer();
			RedBlackTree<String> tree = new RedBlackTree<>();
			int s = 0;
			for (String words : dict) {
				tree.addNode(words);
			}
			NanoTimer.endTimer();
			System.out.println("Dictionary created in " + NanoTimer.getTime()/(1e6) + "ms");
			
			String[] splitted = FileToArray.splitIntoWords(poem);
			
			NanoTimer.startTimer();
			System.out.println(FileToArray.dictionaryStats(tree, splitted));
			NanoTimer.endTimer();
			System.out.println("Looked up the poem's words in " + NanoTimer.getTime()/(1e6) + "ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String makeString(RedBlackTree<String> t) {
		class MyVisitor implements RedBlackTree.Visitor<String> {
			String result = "";

			public void visit(RedBlackTree.Node<String> n) {
				result = result + n.key;
			}
		}
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RedBlackTree<String> t) {
		class MyVisitor implements RedBlackTree.Visitor<String> {
			String result = "";

			public void visit(RedBlackTree.Node<String> n) {
				if (!(n.key).equals("")) {
					if (n.parent == null) {
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + "\n";
					} else {
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + n.parent.key + "\n";
					}
				}

			}
		}
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}
	// add this in your class
	// public static interface Visitor
	// {
	// /**
	// This method is called at each node.
	// @param n the visited node
	// */
	// void visit(Node n);
	// }

	// public void preOrderVisit(Visitor v)
	// {
	// preOrderVisit(root, v);
	// }

	// private static void preOrderVisit(Node n, Visitor v)
	// {
	// if (n == null) return;
	// v.visit(n);
	// preOrderVisit(n.left, v);
	// preOrderVisit(n.right, v);
	// }

}
