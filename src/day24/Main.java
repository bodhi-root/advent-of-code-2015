package day24;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.FileUtil;

public class Main {
	
	static class Group {
		
		int [] values;
		boolean [] include;
		int size;
		
		public Group(int [] values, boolean [] include) {
			this.values = values;
			this.include = include;
			
			this.size = 0;
			for (int i=0; i<include.length; i++) {
				if (include[i])
					size++;
			}
		}
		
		public int size() {
			return size;
		}
		public long getProduct() {
			long product = 1;
			for (int i=0; i<include.length; i++) {
				if (include[i])
					product *= values[i];
			}
			return product;
		}
		
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("[");
			for (int i=0; i<include.length; i++) {
				if (include[i]) {
					if (s.length() > 1)
						s.append(",");
					s.append(values[i]);
				}
			}
			s.append("]");
			return s.toString();
		}
		
	}
	
	public static List<Group> buildGroupsWithSum(int [] values, int targetSum) {
		List<Group> groups = new ArrayList<>();
		
		boolean [] include = new boolean[values.length];
		int sum = 0;
		
		int incIndex = 0;
		
		while (incIndex < values.length) {
	
			for (incIndex=0; incIndex<values.length; incIndex++) {
				if (include[incIndex]) {
					include[incIndex] = false;
					sum -= values[incIndex];
				} else {
					include[incIndex] = true;
					sum += values[incIndex];
					if (sum == targetSum)
						groups.add(new Group(values, Arrays.copyOf(include, include.length)));
					break;
				}
			}			
		}
		
		return groups;
	}
	
	
	public static void findBestSet(int [] values, int numGroups) {
		
		int sum = 0;
		for (int i=0; i<values.length; i++)
			sum += values[i];
		
		int targetSum = sum / numGroups;
		
		System.out.println("Sum: " + sum);
		System.out.println("Target Sum: " + targetSum);
		
		//find all groups with this sum:
		List<Group> groups = buildGroupsWithSum(values, targetSum);
		System.out.println("Found " + groups.size() + " groups");
		
		long score = getScore(groups);
		System.out.println("Score: " + score);
	}
	
	public static long getScore(List<Group> groups) {
		int minLength = groups.get(0).size();
		for (Group group : groups)
			minLength = Math.min(minLength, group.size());
		
		System.out.println("Min Length: " + minLength);
		
		long minProduct = Long.MAX_VALUE;
		Group bestGroup = null;
		
		for (Group group : groups) {
			if (group.size() == minLength) {
				long product = group.getProduct();
				System.out.println(group.toString() + " (QE = " + product + ")");
				if (product < minProduct) {
					minProduct = product;
					bestGroup = group;
				}
			}
		}
		System.out.println("Best Group: " + bestGroup.toString());
		
		return minProduct;
	}
	
	public static void main(String [] args) {
		try {
			List<String> lines = FileUtil.readLinesFromFile(new File("files/day24/input.txt"));
			int [] values = new int[lines.size()];
			for (int i=0; i<values.length; i++)
				values[i] = Integer.parseInt(lines.get(i));
			
			findBestSet(values, 3);
			findBestSet(values, 4);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
