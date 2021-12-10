package day12;

import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import common.FileUtil;

public class Main {
	
	public static JsonElement readInput(File file) throws Exception {
		String line = FileUtil.readLineFromFile(file);
		return JsonParser.parseString(line);
	}
	
	static class Stats {
		
		int numberCount = 0;
		long numberSum = 0;
		
		public void add(Stats stats) {
			this.numberCount += stats.numberCount;
			this.numberSum += stats.numberSum;
		}
		
	}
	
	protected static boolean containsValue(JsonObject obj, Set<String> values) {
		JsonElement child;
		for (Entry<String, JsonElement> entry : obj.entrySet()) {
			child = entry.getValue();
			if (child.isJsonPrimitive()) {
				JsonPrimitive prim = child.getAsJsonPrimitive();
				if (values.contains(prim.getAsString()))
					return true;
			}
		}
		return false;
	}
	
	public static Stats getStatsFor(JsonElement elem, Set<String> ignoreList) {
		Stats stats = new Stats();
		
		if (elem.isJsonArray()) {
			JsonArray array = elem.getAsJsonArray();
			for (JsonElement child : array)
				stats.add(getStatsFor(child, ignoreList));;
		} 
		else if (elem.isJsonObject()) {
			JsonObject obj = elem.getAsJsonObject();
			if (!containsValue(obj, ignoreList)) {
				for (Entry<String, JsonElement> entry : obj.entrySet())
					stats.add(getStatsFor(entry.getValue(), ignoreList));
			}
		}
		else if (elem.isJsonPrimitive()) {
			JsonPrimitive prim = elem.getAsJsonPrimitive();
			if (prim.isNumber()) {
				stats.numberCount++;
				stats.numberSum += prim.getAsLong();
			}
		}
		else if (elem.isJsonNull()) {
			//do nothing
		}
		
		return stats;
	}
	
	
	public static void solvePart1() throws Exception {
		Set<String> ignoreList = new HashSet<>();	//empty list
		
		JsonElement input = readInput(new File("files/day12/input.txt"));
		Stats stats = getStatsFor(input, ignoreList);
		System.out.println("Count = " + stats.numberCount);
		System.out.println("Sum = " + stats.numberSum);
	}
	
	public static void solvePart2() throws Exception {
		Set<String> ignoreList = new HashSet<>();
		ignoreList.add("red");
		
		JsonElement input = readInput(new File("files/day12/input.txt"));
		Stats stats = getStatsFor(input, ignoreList);
		System.out.println("Count = " + stats.numberCount);
		System.out.println("Sum = " + stats.numberSum);
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
