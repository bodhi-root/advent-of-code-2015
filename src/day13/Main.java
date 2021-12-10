package day13;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.FileUtil;

public class Main {
	
	
	
	static class Person {
		
		String name;
		Map<String, Integer> preferences = new HashMap<>();
		
		public Person(String name) {
			this.name = name;
		}
		
		public void setPreference(String name, int value) {
			preferences.put(name, value);
		}
		public int getPreference(String name) {
			return preferences.get(name);
		}
		
	}
	
	static class World {
		
		Map<String, Person> peopleMap = new HashMap<>();
		
		public void add(Person person) {
			peopleMap.put(person.name, person);
		}
		
		public Person getPerson(String name) {
			Person person = peopleMap.get(name);
			if (person == null) {
				person = new Person(name);
				peopleMap.put(name, person);
			}
			return person;
		}
		
		public void optimize() {
			Optimizer optimizer = new Optimizer(this);
			optimizer.optimize();
			
			for (int i=0; i<optimizer.bestOrder.length; i++)
				System.out.println(i + ": " + optimizer.bestOrder[i].name);
			System.out.println();
			
			System.out.println("Score = " + optimizer.bestScore);
		}
		
	}
	
	static class Optimizer {
		
		World world;
		
		Person [] bestOrder;
		long bestScore;
		
		public Optimizer(World world) {
			this.world = world;
		}
		
		public void optimize() {
			Collection<Person> people = world.peopleMap.values();
			
			Person [] sitting = new Person[people.size()];
			Set<Person> remaining = new HashSet<>(people);
			
			for (Person person : people) {
				sitting[0] = person;
				
				remaining.remove(person);
				expandSitting(sitting, 1, remaining);
				remaining.add(person);
			}
		}
		
		protected void expandSitting(Person [] sitting, int nextIndex, Set<Person> remaining) {
			List<Person> nextPeople = new ArrayList<>(remaining);
			for (Person person : nextPeople) {
				
				sitting[nextIndex] = person;
				
				if (nextIndex == sitting.length-1) {
					long score = score(sitting);
					if (bestOrder == null || score > bestScore) {
						bestOrder = Arrays.copyOf(sitting, sitting.length);
						bestScore = score;
					}
				} else {
					remaining.remove(person);
					expandSitting(sitting, nextIndex+1, remaining);
					remaining.add(person);
				}
				
			}
		}
		
		public long score(Person [] order) {
			long score = 0;
			for (int i=0; i<order.length; i++) {
				int priorIdx = i - 1;
				if (priorIdx < 0)
					priorIdx += order.length;
				
				int nextIdx = i + 1;
				if (nextIdx >= order.length)
					nextIdx -= order.length;
				
				score += order[i].getPreference(order[priorIdx].name);
				score += order[i].getPreference(order[nextIdx].name);
			}
			return score;
		}
		
	}
	
	public static World readInput(File file) throws IOException {
		World world = new World();
		
		List<String> lines = FileUtil.readLinesFromFile(file);
		for (String line : lines) {
			//example: Alice would lose 57 happiness units by sitting next to Bob.
			if (line.endsWith("."))
				line = line.substring(0, line.length()-1);
			
			String [] parts = line.split("\\s+");
			
			String name = parts[0];
			Person person = world.getPerson(name);
			
			String gainLose = parts[2];
			int units = Integer.parseInt(parts[3]);
			
			if (gainLose.equals("lose"))
				units = -units;
			
			String nextPerson = parts[parts.length-1];
			person.setPreference(nextPerson, units);
		}
		
		return world;
	}
	
	public static void testPart1() throws Exception {
		World world = readInput(new File("files/day13/test.txt"));
		world.optimize();
	}
	
	public static void solvePart1() throws Exception {
		World world = readInput(new File("files/day13/input.txt"));
		world.optimize();
	}
	
	public static void solvePart2() throws Exception {
		World world = readInput(new File("files/day13/input.txt"));
		Collection<Person> people = world.peopleMap.values();
		
		Person me = new Person("ME");
		world.add(me);
		
		for (Person person : people) {
			me.setPreference(person.name, 0);
			person.setPreference("ME", 0);
		}
		
		world.optimize();
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
