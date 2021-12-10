package day01;

import java.io.File;

import common.FileUtil;

public class Main {

	public static void solvePart1() throws Exception {
		String line = FileUtil.readLineFromFile(new File("files/day01/input.txt"));
		char [] chars = line.toCharArray();
		
		int position = 0;
		for (int i=0; i<chars.length; i++) {
			if (chars[i] == '(')
				position++;
			else if (chars[i] == ')')
				position--;
			else
				throw new IllegalArgumentException("Invalid character: " + chars[i]);
		}
		
		System.out.println("Position = " + position);
	}
	
	public static void solvePart2() throws Exception {
		String line = FileUtil.readLineFromFile(new File("files/day01/input.txt"));
		char [] chars = line.toCharArray();
		
		int position = 0;
		for (int i=0; i<chars.length; i++) {
			if (chars[i] == '(')
				position++;
			else if (chars[i] == ')')
				position--;
			else
				throw new IllegalArgumentException("Invalid character: " + chars[i]);
			
			if (position < 0) {
				System.out.println("Basement entered at position: " + (i + 1));
				break;
			}
		}
		
		System.out.println("Position = " + position);
	}
	
	public static void main(String [] args) {
		try {
			//solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
