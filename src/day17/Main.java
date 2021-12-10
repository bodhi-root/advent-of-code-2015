package day17;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import common.CollectionUtils;
import common.FileUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Main {
	
	public static void solvePart1() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day17/input.txt"));
		List<Integer> values = new ArrayList<>();
		for (String line : lines)
			values.add(Integer.parseInt(line));
		
		/*
		visitAllCombinations(values, new Visitor() {
			public boolean visit(List values) {
				System.out.println(CollectionUtils.toString(values));
				return true;
			}
		});
		*/
		
		VisitorPart1 part1 = new VisitorPart1();
		visitAllCombinations(values, part1);
		System.out.println("Count = " + part1.count);
		
		VisitorPart2 part2 = new VisitorPart2();
		visitAllCombinations(values, part2);
		part2.printAnswer();
	}
	
	static interface Visitor {
		public boolean visit(List values);
	}
	
	static class VisitorPart1 implements Visitor {
		int count = 0;
		
		public boolean visit(List path) {
			int sum = 0;
			for (Object o : path)
				sum += ((Integer)o).intValue();
			if (sum == 150) {
				//System.out.println(CollectionUtils.toString(path));
				this.count++;
			}
			
			return sum < 150;
		}
	}
	
	static class VisitorPart2 implements Visitor {
		
		List<List> paths = new ArrayList<>();
		
		public boolean visit(List path) {
			
			int sum = 0;
			for (Object o : path)
				sum += ((Integer)o).intValue();
			if (sum == 150) {
				paths.add(new ArrayList(path));
			}
			
			return sum < 150;
		}
		
		public void printAnswer() {
			System.out.println("All Solutions = " + paths.size());
			
			int minLength = paths.get(0).size();
			for (int i=1; i<paths.size(); i++)
				minLength = Math.min(minLength, paths.get(i).size());
			System.out.println("Min Length = " + minLength);
			
			int count = 0;
			for (List path : paths) {
				if (path.size() == minLength)
					count++;
			}
			System.out.println("Min Length Solutions = " + count);
		}
	}
	
	protected static void visitAllCombinations(List values, Visitor visitor) {
		List path = new ArrayList();
		visitCombinationsInner(path, values, visitor);
	}
	protected static void visitCombinationsInner(List path, List unusedValues, Visitor visitor) {
		for (int i=0; i<unusedValues.size(); i++) {
			/*
			//this was permuatations:
			Object value = unusedValues.remove(i);
			path.add(value);
			boolean keepGoing = visitor.visit(path);
			
			if (keepGoing)
				visitInner(path, unusedValues, visitor);
			
			path.remove(path.size()-1);
			unusedValues.add(i, value);
			*/
	
			path.add(unusedValues.get(i));
			boolean keepGoing = visitor.visit(path);
			
			if (keepGoing)
				visitCombinationsInner(path, unusedValues.subList(i+1, unusedValues.size()), visitor);
			
			path.remove(path.size()-1);
		}
	}
	
	public static void solvePart2() throws Exception {
		
	}
	
	public static void main(String [] args) {
		try {
			solvePart1();
			//solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
