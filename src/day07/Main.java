package day07;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import common.FileUtil;

public class Main {
	
	static class Computer {
		
		Map<String, Long> wires = new HashMap<>(1000);
		
		public void set(String wireId, long value) {
			wires.put(wireId, value);
		}
		public long get(String wireId) {
			Long value = wires.get(wireId);
			if (value == null)
				throw new IllegalStateException("Attempt to access wire " + wireId + " before it was set");
			
			return value.longValue();
		}
		
		static final long MASK_16BIT = 0xFFFF;
		
		/**
		 * Tries to run the command. This will make sure that any
		 * referenced input wires are already set.  If not, we
		 * cannot run the command and instead return false.
		 */
		public boolean run(String command) {
			String [] parts = command.split("\\s+");
			String outputId = parts[parts.length-1];
			
			Long value = null;
			
			if (parts[0].equals("NOT")) {
				Long x = valueOf(parts[1]);
				if (x == null)
					return false;
				
				value = (~x) & MASK_16BIT;
			} else if (parts[1].equals("AND")) {
				Long x = valueOf(parts[0]);
				Long y = valueOf(parts[2]);
				if (x == null || y == null)
					return false;
				
				value = (x & y) & MASK_16BIT;
			} else if (parts[1].equals("OR")) {
				Long x = valueOf(parts[0]);
				Long y = valueOf(parts[2]);
				if (x == null || y == null)
					return false;
				
				value = (x | y) & MASK_16BIT;
			} else if (parts[1].equals("LSHIFT")) {
				Long x = valueOf(parts[0]);
				Long y = valueOf(parts[2]);
				if (x == null || y == null)
					return false;
				
				value = (x << y) & MASK_16BIT;
			} else if (parts[1].equals("RSHIFT")) {
				Long x = valueOf(parts[0]);
				Long y = valueOf(parts[2]);
				if (x == null || y == null)
					return false;
				
				value = (x >>> y) & MASK_16BIT;
			} else {
				value = valueOf(parts[0]);
				if (value == null)
					return false;
			}
			
			set(outputId, value);
			return true;
		}
		
		Pattern CHAR_PATTERN = Pattern.compile("^[a-zA-Z]+$");
		
		/**
		 * Returns a value from the given text.  The text can be
		 * a wire ID or an actual number.
		 */
		protected Long valueOf(String text) {
			if (CHAR_PATTERN.matcher(text).matches())
				return wires.get(text);
			else
				return Long.valueOf(text);
		}
		
	}
	
	public static void testPart1() {
		String [] commands = new String [] {
				"123 -> x",
				"456 -> y",
				"x AND y -> d",
				"x OR y -> e",
				"x LSHIFT 2 -> f",
				"y RSHIFT 2 -> g",
				"NOT x -> h",
				"NOT y -> i"
		};
		
		Computer computer = new Computer();
		for (String command : commands)
			computer.run(command);
		
		for (Map.Entry<String, Long> entry : computer.wires.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
	/**
	 * Out actual input was not nicely ordered (like the test data).
	 * To deal with this, we iterate through the commands and try to
	 * run any commands that have not yet succeeded. We keep doing 
	 * this until there are no new commands being run.
	 */
	public static void runSafely(List<String> commands) {
		Computer computer = new Computer();
		
		boolean [] commandRun = new boolean[commands.size()];
		
		boolean running = true;
		while (running) {
		
			int runCount = 0;
			for (int i=0; i<commands.size(); i++) {
				if (!commandRun[i]) {
					if (computer.run(commands.get(i))) {
						commandRun[i] = true;
						runCount++;
					}
				}
			}
			
			if (runCount == 0)
				running = false;
		}
		
		System.out.println("a = " + computer.get("a"));
	}
	
	public static void solvePart1() throws Exception {
		List<String> commands = FileUtil.readLinesFromFile(new File("files/day07/input.txt"));
		runSafely(commands);
	}
	
	public static void solvePart2() throws Exception {
		List<String> commands = FileUtil.readLinesFromFile(new File("files/day07/input.txt"));
		
		//override value on wire b:
		int index = commands.indexOf("1674 -> b");
		commands.set(index, "46065 -> b");
		
		runSafely(commands);
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
