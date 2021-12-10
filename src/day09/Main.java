package day09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.FileUtil;

public class Main {
	
	static class Path {
		
		List<String> path = new ArrayList<>();
		List<Integer> distances = new ArrayList<>();
		
		int totalDistance = 0;
		
		public Path(String start) {
			path.add(start);
		}
		private Path() {
			//do nothing
		}
		
		public void connectTo(String loc, int distance) {
			path.add(loc);
			distances.add(distance);
			
			this.totalDistance += distance;
		}
		
		public String getLastLocation() {
			return path.get(path.size()-1);
		}
		
		public void removeLastPoint() {
			path.remove(path.size()-1);
			if (!distances.isEmpty()) {
				int distance = distances.remove(distances.size()-1);
				this.totalDistance -= distance;
			}
		}
		
		public Path copy() {
			Path copy = new Path();
			copy.path.addAll(this.path);
			copy.distances.addAll(this.distances);
			copy.totalDistance = this.totalDistance;
			return copy;
		}
		
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("Path: {");
		    for (int i=0; i<path.size(); i++) {
		    	if (i > 0)
		    		s.append(" => ");
		    	s.append(path.get(i));
		    }
			s.append("}");
			return s.toString();
		}
		
	}
	
	static Comparator<Path> SHORTEST_DISTANCE = new Comparator<Path>() {

		public int compare(Path o1, Path o2) {
			return Integer.compare(o1.totalDistance, o2.totalDistance);
		}		
		
	};

	static Comparator<Path> LONGEST_DISTANCE = SHORTEST_DISTANCE.reversed();
	
	/*
	static Comparator<Path> LONGEST_DISTANCE = new Comparator<Path>() {

		public int compare(Path o1, Path o2) {
			return -Integer.compare(o1.totalDistance, o2.totalDistance);
		}		
		
	};
	*/
	
	static class World {
		
		Map<String, Integer> distances = new HashMap<>(100*100);
		Set<String> locations = new HashSet<>(100);
		
		public static World loadFrom(File file) throws IOException {
			World world = new World();
			
			//example: London to Dublin = 464
			List<String> lines = FileUtil.readLinesFromFile(file);
			for (String line : lines) {
				String [] parts = line.split("\\s+");
				String loc1 = parts[0];
				String loc2 = parts[2];
				int distance = Integer.parseInt(parts[4]);
				
				world.locations.add(loc1);
				world.locations.add(loc2);
				
				world.distances.put(loc1 + ":" + loc2, distance);
				world.distances.put(loc2 + ":" + loc1, distance);
			}
			return world;
		}
		
		public int getDistance(String loc1, String loc2) {
			return distances.get(loc1 + ":" + loc2).intValue();
		}
		
		public Path getBestPath(Comparator<Path> comparator) {
			PathVisitor visitor = new PathVisitor(this, comparator);
			visitor.visitAll();
			return visitor.bestPath;
		}
		public Path getBestPath() {
			return getBestPath(SHORTEST_DISTANCE);
		}
		
	}
	
	static class PathVisitor {
		
		World world;
		Comparator<Path> comparator;
		
		Path bestPath = null;
		
		public PathVisitor(World world, Comparator<Path> comparator) {
			this.world = world;
			this.comparator = comparator;
		}
		
		public void visitAll() {
			for (String loc : world.locations) {
				Path path = new Path(loc);
				
				Set<String> remainingLocs = new HashSet<>(world.locations);
				remainingLocs.remove(loc);
				
				extendPath(path, remainingLocs);
			}
		}
		
		protected void extendPath(Path path, Set<String> remainingLocs) {
			
			//if path is done, compare to best path:
			if (remainingLocs.isEmpty()) {
				if (bestPath == null || comparator.compare(path, bestPath) < 0) {
					bestPath = path.copy();
					//System.out.println("New best path");
					//System.out.println("  " + bestPath);
					//System.out.println("  Distance = " + bestPath.totalDistance);
				}
				
				return;
			}
			
			//create copy (since we'll be modifying remainingLocs)
			List<String> nextLocations = new ArrayList<>(remainingLocs);
			
			String lastLoc = path.getLastLocation();
			for (String nextLoc : nextLocations) {
				int distance = world.getDistance(lastLoc, nextLoc);
				
				//short circuit if we know our path is already not the best:
				//(only works for shortest path)
				//if (bestPath != null && path.totalDistance + distance >= bestPath.totalDistance)
				//	continue;
				
				path.connectTo(nextLoc, distance);
				remainingLocs.remove(nextLoc);
				
				extendPath(path, remainingLocs);
				
				path.removeLastPoint();
				remainingLocs.add(nextLoc);
			}
		}
		
	}
	
	public static void testPart1() throws Exception {
		World world = World.loadFrom(new File("files/day09/test.txt"));
		Path path = world.getBestPath();
		
		System.out.println(path);
		System.out.println("Total Distance: " + path.totalDistance);
	}
	
	public static void solvePart1() throws Exception {
		World world = World.loadFrom(new File("files/day09/input.txt"));
		Path path = world.getBestPath();
		
		System.out.println(path);
		System.out.println("Total Distance: " + path.totalDistance);
	}
	
	public static void testPart2() throws Exception {
		World world = World.loadFrom(new File("files/day09/test.txt"));
		Path path = world.getBestPath(LONGEST_DISTANCE);
		
		System.out.println(path);
		System.out.println("Total Distance: " + path.totalDistance);
	}
	
	public static void solvePart2() throws Exception {
		World world = World.loadFrom(new File("files/day09/input.txt"));
		Path path = world.getBestPath(LONGEST_DISTANCE);
		
		System.out.println(path);
		System.out.println("Total Distance: " + path.totalDistance);
	}
	
	public static void main(String [] args) {
		try {
			testPart1();
			solvePart1();
			testPart2();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
