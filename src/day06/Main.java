package day06;

import java.io.File;
import java.util.List;

import common.FileUtil;

public class Main {
	
	public static void solvePart1() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day06/input.txt"));
		Lights lights = new Lights();
		for (String line : lines)
			lights.run(line);
		
		System.out.println(lights.getTotalOnCount());
	}
	
	public static void solvePart2() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day06/input.txt"));
		Lights2 lights = new Lights2();
		for (String line : lines)
			lights.run(line);
		
		System.out.println(lights.getTotalBrightness());
	}
	
	public static void main(String [] args) {
		try {
			solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
