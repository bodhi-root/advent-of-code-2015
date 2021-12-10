package day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part 1 of this was easy.  Part 2 stumped me.  A brute-force approach
 * would not work.  I even tried implementing a CYK algorithm as someone
 * suggested (before finding out that it didn't really apply since the
 * rules are not in Chomsky Normal Form).  Finally, I found someone
 * on Reddit who had a Java solution.  This doesn't seem to be a general
 * solution.  It seems like a greedy algorithm that just gets lucky and
 * reshuffles the transformation rules until the algorithm works.
 * 
 * Part 1 solution from:
 * https://www.reddit.com/r/adventofcode/comments/3xflz8/day_19_solutions/
 * 
 * Part 2 solution from:
 * https://www.reddit.com/r/adventofcode/comments/3xglof/day_19_part_2java_part_1_works_part_2_results_in/
 */
public class Advent19 {

    public static String replace(String s, String in, String out, int position) {
        return s.substring(0, position) + out + s.substring(position + in.length());
    }
    public static void main(String[] args) throws IOException {
    	List<String []> transforms = new ArrayList<>();
    	String molecule;
    	
    	BufferedReader in = new BufferedReader(new FileReader(new File("files/day19/input.txt")));
    	try {
    		String line;
    		while ((line = in.readLine()) != null) {
    			line = line.trim();
    			if (line.isEmpty())
    				break;
    			
    			int index = line.indexOf('=');
    			String left = line.substring(0, index).trim();
    			String right = line.substring(index+2).trim();
    			transforms.add(new String [] {left, right});
    		}
    		
    		molecule = in.readLine();
    	}
    	finally {
    		in.close();
    	}
    	
    	List<String> output = new ArrayList<>();
        for (String[] each : transforms) {
            int position = 0;
            while ((position = molecule.indexOf(each[0], position)) >= 0) {
                output.add(replace(molecule, each[0], each[1], position));
                position += each[0].length();
            }
        }

        long count = output.stream()
                .distinct()
                .count();

        System.out.println("Part 1: " + count);
        
        //part 2
        
        Collections.shuffle(transforms);
		int count2 = 0;
		int reshuffleCount = 0;
		String tmp = new String(molecule);
		while (!tmp.equals("e")) {
			String last = tmp;
			for (String[] trans : transforms) {
				if (tmp.contains(trans[1])) {
					tmp = replace(tmp, trans[1], trans[0], tmp.indexOf(trans[1]));
					// System.out.println(medicine);

					count2++;
				}
			}
			if (last.equals(tmp) && (!tmp.equals("e"))) {
				Collections.shuffle(transforms);
				tmp = new String(molecule);
				count2 = 0;
				reshuffleCount++;
			}
		}

		System.out.println("Part 2: Transforms taken = " + count2 + " Reshuffled " + reshuffleCount + " times.");
    }
}
