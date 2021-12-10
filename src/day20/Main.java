package day20;

public class Main {
	
	public static int countPresents(int value) {
		if (value == 1)
			return 10;
		
		int divisors = 1 + value;	//every number has 1 and itself
		int max = value / 2;
		for (int i=2; i<=max; i++) {
			//System.out.println("  " + i + " - " + (value%i));
			if (value % i == 0)
				divisors+=i;
		}
		return divisors * 10;
	}
	
	public static int countPresents2(int value) {
		if (value == 1)
			return 11;
		
		int presents = value * 11;	//every number has itself
		
		int max = value / 2;
		int min = (int)Math.ceil(value / 50.0);
		
		for (int i=min; i<=max; i++) {
			//System.out.println("  " + i + " - " + (value%i));
			if (value % i == 0)
				presents += i * 11;
		}
		return presents;
	}
	
	public static void testPart1() {
		for (int i=1; i<=10; i++) {
			int presents = countPresents(i);
			System.out.println(i+ ": " + presents);
		}
	}
	
	public static void solvePart1() throws Exception {
		boolean running = true;
		int value = 1;
		int maxPresents = 1;
		while (running) {
			int presents = countPresents(value);
			maxPresents = Math.max(maxPresents, presents);
			if (value % 100000 == 0)
				System.out.println(value + " - " + maxPresents);
			if (presents >= 36000000) {
				System.out.println("SOLUTION: " + value + " (" + presents + " presents)");
				running = false;
			}
			value++;
		}
	}
	
	public static void solvePart2() throws Exception {
		boolean running = true;
		int value = 1;
		int maxPresents = 1;
		while (running) {
			int presents = countPresents2(value);
			maxPresents = Math.max(maxPresents, presents);
			if (value % 100000 == 0)
				System.out.println(value + " - " + maxPresents);
			if (presents >= 36000000) {
				System.out.println("SOLUTION: " + value + " (" + presents + " presents)");
				running = false;
			}
			value++;
		}
	}
	
	public static void main(String [] args) {
		try {
			//brute force solution takes several minutes to run
			//(there's probably a smarter way, but this works)
			
			//CONSIDER: if we calculate the prime factorization of each number (using the sieve
			//of erasthmus to get the prime numbers going) we could take every combination
			//of the prime factors and figure this out.
			
			//solvePart1();	//831600
			solvePart2();	//884520
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
