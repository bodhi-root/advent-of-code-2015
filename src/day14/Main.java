package day14;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import common.FileUtil;

public class Main {
	
	static class Reindeer {
		
		String name;
		int flyingSpeed;
		int flyingTime;
		int restingTime;
		
		public Reindeer(String name) {
			this.name = name;
		}
		
		public static Reindeer parseFrom(String line) {
			//sample:
			//Vixen can fly 8 km/s for 8 seconds, but then must rest for 53 seconds.
			
			String [] parts = line.split("\\s+");
			Reindeer reindeer = new Reindeer(parts[0]);
			reindeer.flyingSpeed = Integer.parseInt(parts[3]);
			reindeer.flyingTime = Integer.parseInt(parts[6]);
			reindeer.restingTime = Integer.parseInt(parts[13]);
			
			return reindeer;
		}
	}
	
	static class World {
		
		List<Reindeer> reindeerList = new ArrayList<>();
		
		public void add(Reindeer reindeer) {
			reindeerList.add(reindeer);
		}
		
		public Simulation createSimulation() {
			List<ReindeerSim> sims = new ArrayList<>();
			for (Reindeer reindeer : reindeerList)
				sims.add(new ReindeerSim(reindeer));
			
			return new Simulation(sims);
		}
		
		public static World readFromFile(File file) throws IOException {
			World world = new World();
			
			List<String> lines = FileUtil.readLinesFromFile(file);
			for (String line : lines) {
				world.add(Reindeer.parseFrom(line));
			}
			
			return world;
		}
		
	}
	
	static enum State {FLYING, RESTING};
	
	static class ReindeerSim {
		
		Reindeer reindeer;
		
		long t = 0;
		long distance = 0;
		long points = 0;
		
		State state;
		long switchStateAt;
		
		public ReindeerSim(Reindeer reindeer) {
			this.reindeer = reindeer;
			
			this.state = State.FLYING;
			this.switchStateAt = reindeer.flyingTime;
		}
		
		public void step() {
			t++;
			
			if (state == State.FLYING)
				distance += reindeer.flyingSpeed;
			
			if (t == switchStateAt) {
				State nextState;
				int time;
				
				switch(state) {
				case FLYING: 
					nextState = State.RESTING; 
					time = reindeer.restingTime; 
					break;
				case RESTING: 
					nextState = State.FLYING; 
					time = reindeer.flyingTime; 
					break;
				default: throw new IllegalStateException();
				}
				
				this.state = nextState;
				switchStateAt = t + time; 
			}
		}
		
	}
	
	static class Simulation {
		
		List<ReindeerSim> reindeerList;
		long t = 0;
		
		public Simulation(List<ReindeerSim> reindeer) {
			this.reindeerList = reindeer;
		}
		
		public void step() {
			t++;
			for (ReindeerSim reindeer : reindeerList)
				reindeer.step();
			
			//award points:
			ReindeerSim best = null;
			
			for (ReindeerSim reindeer : reindeerList) {
				if (best == null || reindeer.distance > best.distance)
					best = reindeer;
			}
			for (ReindeerSim reindeer : reindeerList) {
				if (reindeer.distance == best.distance)
					reindeer.points++;
			}
		}
		
		public void printState(PrintStream out) {
			ReindeerSim bestDistance = null;
			ReindeerSim bestPoints = null;
			
			for (ReindeerSim reindeer : reindeerList) {
				out.println(reindeer.reindeer.name + ": " + reindeer.distance + " (" + reindeer.points + " points)");
				
				if (bestDistance == null || reindeer.distance > bestDistance.distance)
					bestDistance = reindeer;
				if (bestPoints == null || reindeer.points > bestPoints.points)
					bestPoints = reindeer;
			}
			
			out.println();
			out.println("Best Distance: " + bestDistance.reindeer.name + " = " + bestDistance.distance);
			out.println("Best Points: " + bestPoints.reindeer.name + " = " + bestPoints.points);
		}
		
	}
	
	public static void testPart1and2() throws Exception {
		World world = World.readFromFile(new File("files/day14/test.txt"));
		
		Simulation sim = world.createSimulation();
		for (int i=0; i<1000; i++)
			sim.step();
		
		sim.printState(System.out);
	}
	
	public static void solvePart1and2() throws Exception {
		World world = World.readFromFile(new File("files/day14/input.txt"));
		
		Simulation sim = world.createSimulation();
		for (int i=0; i<2503; i++)
			sim.step();
		
		sim.printState(System.out);
	}
	
	public static void main(String [] args) {
		try {
			testPart1and2();
			System.out.println();
			
			solvePart1and2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
