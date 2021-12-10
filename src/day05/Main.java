package day05;

import java.io.File;
import java.util.List;

import common.FileUtil;

public class Main {
	
	static interface Validator {
		public boolean isValid(String text);
	}
	
	static Validator PART1_VALIDATOR = new Validator() {
		
		//now allowed:
		String [] NOT_ALLOWED = new String [] {
				"ab",
				"cd",
				"pq",
				"xy"
		};
		
		public boolean isValid(String text) {
			char [] chars = text.toCharArray();
			
			//3 vowels:
			int vowelCount = 0;
			
			char [] vowels = new char [] {'a','e','i','o','u'};
			for (int i=0; i<chars.length; i++) {
				for (int j=0; j<vowels.length; j++) {
					if (chars[i] == vowels[j]) {
						vowelCount++;
						if (vowelCount == 3) {
							//break loop:
							i = chars.length;
							j = vowels.length;
						}
					}
				}
			}
			if (vowelCount < 3)
				return false;
			
			//double letter:
			boolean valid = false;
			for (int i=1; i<chars.length; i++) {
				if (chars[i-1] == chars[i]) {
					valid = true;
					break;
				}
			}
			if (!valid)
				return false;
			
			for (int i=0; i<NOT_ALLOWED.length; i++) {
				if (text.indexOf(NOT_ALLOWED[i]) >= 0)
					return false;
			}
			
			return true;
		}
		
	};
	
	public static Validator PART2_VALIDATOR = new Validator() {
		
		public boolean isValid(String text) {
			
			boolean valid = false;
			for (int i=0; i<text.length()-2; i++) {
				String substring = text.substring(i, i+2);
				if (text.indexOf(substring, i+2) >= 0) {
					valid = true;
					break;
				}
			}
			if (!valid)
				return false;
			
			valid = false;
			for (int i=0; i<text.length()-2; i++) {
				if (text.charAt(i) == text.charAt(i+2)) {
					valid = true;
					break;
				}
			}
			return valid;
		}
		
	};
	
	
	public static void testPart1() {
		String [] test = new String [] {
				"ugknbfddgicrmopn", // is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
				"aaa",				// is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
				"jchzalrnumimnmhp",	// is naughty because it has no double letter.
				"haegwjzuvuyypxyu",	// is naughty because it contains the string xy.
				"dvszwmarrgswjxmb"	// is naughty because it contains only one vowel.
		};
		
		for (int i=0; i<test.length; i++) {
			System.out.println(test[i] + ": " + PART1_VALIDATOR.isValid(test[i]));
		}
	}
	
	public static void run(Validator validator) throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day05/input.txt"));
		int count = 0;
		for (String line : lines) {
			if (validator.isValid(line))
				count++;
		}
		System.out.println("Valid Count: " + count + " (out of " + lines.size() + ")");
	}
	
	public static void solvePart1() throws Exception {
		run(PART1_VALIDATOR);
	}
	
	public static void solvePart2() throws Exception {
		run(PART2_VALIDATOR);
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
