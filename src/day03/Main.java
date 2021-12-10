package day03;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import common.FileUtil;

public class Main {
	
	static class Location {
		
		int x;
		int y;
		
		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int hashCode() {
			return x ^ 7 + y;
		}
		public boolean equals(Object o) {
			if (o instanceof Location) {
				Location that = (Location)o;
				return this.x == that.x &&
					   this.y == that.y;
			}
			return false;
		}
		
		public String toString() {
			return "(" + x + "," + y + ")";
		}
		
		public Location copy() {
			return new Location(x, y);
		}
		
	}
	
	static class Counter {
		
		int count = 0;
		
	}
	
	static class World {
		
		Map<Location, Counter> map = new HashMap<>();
		
		public Counter getCounter(Location loc) {
			Counter counter = map.get(loc);
			if (counter == null) {
				counter = new Counter();
				map.put(loc.copy(), counter);
			}
			return counter;
		}
		public Counter getCounter(int x, int y) {
			return getCounter(new Location(x, y));
		}
		
		public int getCountsMoreThan(int x) {
			int count = 0;
			for (Counter counter : map.values()) {
				if (counter.count > x)
					count++;
			}
			return count;
		}
		
	}
	
	public static void solvePart1() throws Exception {
	
		String line = FileUtil.readLineFromFile(new File("files/day03/input.txt"));
		//String line = "^>v<";
		
		World world = new World();
		
		int x = 0;
		int y = 0;
		
		world.getCounter(x, y).count++;
		
		char [] chars = line.toCharArray();
		for (int i=0; i<chars.length; i++) {
			switch(chars[i]) {
			case '^': y++; break;
			case 'v': y--; break;
			case '>': x++; break;
			case '<': x--; break;
			default: throw new IllegalArgumentException("Unexpected char: " + chars[i]);
			}
			
			world.getCounter(x, y).count++;
		}
		
		System.out.println("Answer: " + world.getCountsMoreThan(0));
	}
	
	public static void solvePart2() throws Exception {
		
		String line = FileUtil.readLineFromFile(new File("files/day03/input.txt"));
		//line = "^v";
		//line = "^>v<";
		//line = "^v^v^v^v^v";
		
		World world = new World();
		
		Location [] locations = new Location [] {
				new Location(0, 0),
				new Location(0, 0)
		};
		
		for (Location loc : locations)
			world.getCounter(loc).count++;
		
		char [] chars = line.toCharArray();
		int nextToMove = 0;
		
		StringBuilder s = new StringBuilder();
		
		for (int i=0; i<chars.length; i++) {
			s.setLength(0);
			s.append(i).append(": Santa ").append(nextToMove).append(": ");
			
			Location loc = locations[nextToMove];
			nextToMove = (nextToMove + 1) % locations.length;
			
			s.append(loc).append(' ').append(chars[i]).append(' ');
			
			switch(chars[i]) {
			case '^': loc.y++; break;
			case 'v': loc.y--; break;
			case '>': loc.x++; break;
			case '<': loc.x--; break;
			default: throw new IllegalArgumentException("Unexpected char: " + chars[i]);
			}
			
			s.append(loc);
			System.out.println(s.toString());
			
			world.getCounter(loc).count++;
			//if (i == 10)
			//	break;
		}
		
		//higher than 236:
		System.out.println("Answer: " + world.getCountsMoreThan(0));
		System.out.println(world.map.size());
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
