package day08;

import java.io.File;
import java.util.List;

import common.FileUtil;

public class Main {
	
	public static void runPart1(List<String> input) {
		int codeChars = 0;
		int stringChars = 0;
		
		for (String text : input) {
			char [] chars = text.toCharArray();
			codeChars += chars.length;
			
			if (chars[0] != '"' || chars[chars.length-1] != '"')
				throw new IllegalArgumentException("Strings should begin and end with quotes");
					
			char nextChar;
			for (int i=1; i<chars.length-1; i++) {
				if (chars[i] == '\\') {
					nextChar = chars[i+1];
					if (nextChar == '\\' ||
						nextChar == '"') {
						
						stringChars++;
						i++;
					} else if (nextChar == 'x') {
						stringChars++;
						i += 3;
					} else {
						throw new IllegalArgumentException("Invalid escape char: \\" + nextChar);
					}
				} else {
					stringChars++;
				}
			}
		}
		
		System.out.println("Code Chars = " + codeChars);
		System.out.println("String Chars = " + stringChars);
		System.out.println("Difference = " + (codeChars - stringChars));
	}
	
	public static void testPart1() throws Exception {
		List<String> input = FileUtil.readLinesFromFile(new File("files/day08/test.txt"));
		runPart1(input);
	}
	
	public static void solvePart1() throws Exception {
		List<String> input = FileUtil.readLinesFromFile(new File("files/day08/input.txt"));
		runPart1(input);
	}
	
	public static void runPart2(List<String> input) {
		int originalLength = 0;
		int newLength = 0;
		
		StringBuilder buff = new StringBuilder();
		for (String text : input) {
			char [] chars = text.toCharArray();
			originalLength += chars.length;
			
			buff.setLength(0);
			buff.append('"');
			for (int i=0; i<chars.length; i++) {
				if (chars[i] == '\\' ||
					chars[i] == '"') {
					buff.append('\\');
				}
				buff.append(chars[i]);
			}
			buff.append('"');
			
			newLength += buff.length();
		}
		
		System.out.println("Original Length = " + originalLength);
		System.out.println("New Length = " + newLength);
		System.out.println("Difference = " + (newLength - originalLength));
	}
	
	public static void testPart2() throws Exception {
		List<String> input = FileUtil.readLinesFromFile(new File("files/day08/test.txt"));
		runPart2(input);
	}
	
	public static void solvePart2() throws Exception {
		List<String> input = FileUtil.readLinesFromFile(new File("files/day08/input.txt"));
		runPart2(input);
	}
	
	public static void main(String [] args) {
		try {
			//testPart1();
			//solvePart1();
			testPart2();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
