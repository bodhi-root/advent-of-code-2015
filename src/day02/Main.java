package day02;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.FileUtil;

public class Main {
	
	static class Dimensions {
		
		int [] dims;
		
		public Dimensions(int [] dims) {
			this.dims = dims;
		}
		
		public static Dimensions fromText(String text) {
			String [] parts = text.split("x");
			int [] dims = new int[parts.length];
			for (int i=0; i<dims.length; i++)
				dims[i] = Integer.parseInt(parts[i]);
			return new Dimensions(dims);
		}
		
		public int getRequiredSquareFeet() {
			int sum = 0;
			int minSide = Integer.MAX_VALUE;
			
			int area;
			for (int i=0; i<dims.length; i++) {
				area = dims[i] * dims[(i+1)%dims.length];
				sum += 2 * area;
				minSide = Math.min(minSide, area);
			}
			
			sum += minSide;
			return sum;
		}
		
		public int getRequiredBowLength() {
			int minPerimeter = Integer.MAX_VALUE;
			
			int volume = 1;
			int perimeter;
			for (int i=0; i<dims.length; i++) {
				perimeter = (dims[i] + dims[(i+1)%dims.length]) * 2;
				minPerimeter = Math.min(minPerimeter, perimeter);
				
				volume *= dims[i];
			}
			
			return minPerimeter + volume;
		}
		
	}
	
	public static void testPart1() {
		Dimensions [] dims = new Dimensions [] {
				Dimensions.fromText("2x3x4"),
				Dimensions.fromText("1x1x10")
		};
		
		for (Dimensions dim : dims) {
			System.out.println(dim.getRequiredSquareFeet());
			System.out.println("Bow = " + dim.getRequiredBowLength());
		}
	}
	
	public static void solvePart1and2() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day02/input.txt"));
		List<Dimensions> dimensions = new ArrayList<>(lines.size());
		for (String line : lines)
			dimensions.add(Dimensions.fromText(line));
		
		long total = 0;
		long totalBowLength = 0;
		for (Dimensions dim : dimensions) {
			total += dim.getRequiredSquareFeet();
			totalBowLength += dim.getRequiredBowLength();
		}
		
		System.out.println("Total Square Feet = " + total);
		System.out.println("Total Bow Length = " + totalBowLength);
	}
	
	public static void main(String [] args) {
		try {
			testPart1();
			solvePart1and2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
