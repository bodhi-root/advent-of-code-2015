package day04;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Main {
	
	public static String md5(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(input.getBytes());
	    byte [] digest = md.digest();
	    return DatatypeConverter.printHexBinary(digest).toUpperCase();
	}
	
	public static void solve(String input, int zeroCount) throws NoSuchAlgorithmException {
		String hash;
		int value = 1;
		boolean isValid;
		while(true) {
			hash = md5(input + String.valueOf(value));
			
			isValid = true;
			for (int i=0; i<zeroCount; i++) {
				if (hash.charAt(i) != '0') {
					isValid = false;
					break;
				}
			}
			
			if (isValid)
				break;
			
			value++;
		}
		
		System.out.println("Answer = " + value);
		System.out.println("Hash(" + input + String.valueOf(value) + ") = " + md5(input + String.valueOf(value)));
	}
	
	public static void solvePart1() throws Exception {
		//System.out.println(md5("abcdef609043"));
		
		solve("abcdef", 5);
		solve("ckczppom", 5);
	}
	
	public static void solvePart2() throws Exception {
		solve("ckczppom", 6);
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
