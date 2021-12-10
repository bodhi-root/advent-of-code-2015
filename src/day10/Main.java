package day10;

public class Main {
	
	public static String expand(String text) {
		char [] chars = text.toCharArray();
		
		StringBuilder buff = new StringBuilder();
		
		for (int i=0; i<chars.length; i++) {
			char ch = chars[i];
			
			//get count:
			int count = 1;
			for (int j=i+1; j<chars.length; j++) {
				if (chars[j] == ch)
					count++;
				else
					break;
			}
			i += (count - 1);
			
			buff.append(count);
			buff.append(ch);
		}
		
		return buff.toString();
	}
	
	public static void testPart1() {
		String text = "1";
		for (int i=0; i<5; i++) {
			text = expand(text);
			System.out.println(text);
		}
		System.out.println();
	}
	
	public static void solvePart1() throws Exception {
		String text = "1113222113";
		for (int i=0; i<40; i++)
			text = expand(text);
		
		//System.out.println(text);
		System.out.println("Length = " + text.length());
	}
	
	public static void solvePart2() throws Exception {
		String text = "1113222113";
		for (int i=0; i<50; i++)
			text = expand(text);
		
		//System.out.println(text);
		System.out.println("Length = " + text.length());
	}
	
	public static void main(String [] args) {
		try {
			//testPart1();
			//solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
