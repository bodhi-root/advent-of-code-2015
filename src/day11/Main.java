package day11;

public class Main {
	
	static interface Validator {
		
		boolean isValid(String text);
		
	}
	
	static class Part1Validator implements Validator {
		
		static final char [] NOT_ALLOWED = new char [] {'i','o','l'};
		static final String [] RUNS = new String[24];
		static final String [] PAIRS = new String[26];
		
		static {
			for (char startChar='a'; startChar<='x'; startChar++) {
				RUNS[(int)(startChar-'a')] = new String(
						new char [] {startChar, (char)(startChar + 1), (char)(startChar+2)}
				);
			}
			for (char startChar='a'; startChar<='z'; startChar++) {
				PAIRS[(int)(startChar-'a')] = new String(new char [] {startChar, startChar});
			}
		};
		
		public boolean isValid(String text) {
			
			char [] chars = text.toCharArray();
			
			// Passwords must include one increasing straight of at least three letters, 
			// like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; 
			// abd doesn't count.
			
			boolean valid = false;
			for (int i=0; i<RUNS.length; i++) {
				if (text.indexOf(RUNS[i]) >= 0) {
					valid = true;
					break;
				}
			}
			if (!valid)
				return false;
			
			// Passwords may not contain the letters i, o, or l, as these letters can be 
			// mistaken for other characters and are therefore confusing.
			
			for (int i=0; i<chars.length; i++) {
				for (int j=0;j <NOT_ALLOWED.length; j++) {
					if (chars[i] == NOT_ALLOWED[j])
						return false;
				}
			}
			
			// Passwords must contain at least two different, non-overlapping pairs of 
			// letters, like aa, bb, or zz.
			
			valid = false;
			int pairCount = 0;
			for (int i=0; i<PAIRS.length; i++) {
				if (text.indexOf(PAIRS[i]) >= 0) {
					pairCount++;
					if (pairCount == 2) {
						valid = true;
						break;
					}
				}
			}
			
			return valid;
		}
		
	}
	
	public static String increment(String text) {
		char [] chars = text.toCharArray();
		
		int nextIndex = chars.length-1;
		while (nextIndex >= 0) {
			if (chars[nextIndex] == 'z') {
				chars[nextIndex] = 'a';
				nextIndex--;
			}
			else {
				chars[nextIndex] = (char)(chars[nextIndex] + 1);
				break;
			}
		}
	
		return new String(chars);
	}
	
	public static void run(String password) {
		Validator validator = new Part1Validator();
		
		String newPassword = increment(password);
		while (!validator.isValid(newPassword))
			newPassword = increment(newPassword);
		
		System.out.println(newPassword);
	}
	
	public static void testPart1() {
		run("abcdefgh");
		run("ghijklmn");
	}
	
	public static void solvePart1() throws Exception {
		run("hxbxwxba");
	}
	
	public static void solvePart2() throws Exception {
		run("hxbxxyzz");
	}
	
	public static void main(String [] args) {
		try {
			testPart1();
			solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
