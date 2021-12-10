package day25;

public class Main {
	
	public static void solvePart1() throws Exception {
		
		int startRow = 1;
		
		int row = startRow;
		int col = 1;
		
		long value = 20151125;
		
		while(true) {
			
			//System.out.println(row + "," + col + " = " + value);
			//if (row == 10)
			//	break;
			
			if (row == 2981 && col == 3075) {
				System.out.println(value);
				break;
			}
			
			if (row == 1) {
				startRow++;
				row = startRow;
				col = 1;
			} else {
				row--;
				col++;
			}
			
			value = (value * 252533) % 33554393;
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
