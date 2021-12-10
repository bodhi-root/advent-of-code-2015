package day16;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.FileUtil;

public class Main {
	
	static class Props {
		
		String name;
		Map<String, Integer> props = new HashMap<>();
		
		public static Props parse(String line) {
		
			Props props = new Props();
			
			//Sue 1: cars: 9, akitas: 3, goldfish: 0
			int index = line.indexOf(':');
			props.name = line.substring(0, index);
			
			String [] fields = line.substring(index+1).trim().split(",");
			for (String field : fields) {
				String [] parts = field.trim().split(":");
				props.props.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
			}
			
			return props;
		}
		
		public boolean isMatchForTarget(Props target) {
			for (Map.Entry<String, Integer> entry : props.entrySet()) {
				Integer value = target.props.get(entry.getKey());
				if (value != null && value.intValue() != entry.getValue().intValue())
					return false;
			}
			return true;
		}
		
		public boolean isMatchForTarget2(Props target) {
			for (Map.Entry<String, Integer> entry : props.entrySet()) {
				String propName = entry.getKey();
				Integer targetValue = target.props.get(propName);
				if (targetValue != null) {
					Integer value = entry.getValue();
					
					//cats and trees readings indicates that there are greater than that many
					if (propName.equals("cats") || propName.equals("trees")) {
						if (value.intValue() <= targetValue.intValue())
							return false;
					}
					
					//pomeranians and goldfish readings indicate that there are fewer than that many
					else if (propName.equals("pomeranians") || propName.equals("goldfish")) {
						if (value.intValue() >= targetValue.intValue())
							return false;
					}
					
					else if (value.intValue() != targetValue.intValue()) {
						return false;
					}
				}
			}
			return true;
		}
		
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(name).append(" {");
			
			int count = 0;
			for (Map.Entry<String, Integer> entry : props.entrySet()) {
				if (count > 0)
					s.append(",");
				s.append(entry.getKey()).append(" = ").append(entry.getValue());
				count++;
			}
			
			s.append("}");
			return s.toString();
		}
		
	}
	
	public static void solvePart1() throws Exception {
		List<String> lines = FileUtil.readLinesFromFile(new File("files/day16/input.txt"));
		List<Props> props = new ArrayList<>();
		
		for (String line : lines) {
			Props prop = Props.parse(line);
			props.add(prop);
			//System.out.println(prop);
		}
		
		Props target = Props.parse(
				"Target: children: 3, cats: 7, samoyeds: 2, pomeranians: 3, akitas: 0," + 
				"vizslas: 0, goldfish: 5, trees: 3, cars: 2, perfumes: 1");
		
		System.out.println("Part 1:");
		for (Props prop : props) {
			if (prop.isMatchForTarget(target))
				System.out.println(prop);
		}
		
		System.out.println("Part 2:");
		for (Props prop : props) {
			if (prop.isMatchForTarget2(target))
				System.out.println(prop);
		}
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
