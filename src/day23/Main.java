package day23;

import java.io.File;
import java.util.List;

import common.FileUtil;

public class Main {
	
	public static void testPart1() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day23/test.txt"));
		Computer computer = new Computer();
		computer.runProgram(lines);
		System.out.println(computer.getRegisterValue("a"));
	}
	
	public static void solvePart1() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day23/input.txt"));
		Computer computer = new Computer();
		computer.runProgram(lines);
		System.out.println(computer.getRegisterValue("b"));
	}
	
	public static void solvePart2() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day23/input.txt"));
		Computer computer = new Computer();
		computer.setRegisterValue("a", 1);
		computer.runProgram(lines);
		System.out.println(computer.getRegisterValue("b"));
	}
	
	public static void main(String [] args) {
		try {
			//testPart1();
			solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
