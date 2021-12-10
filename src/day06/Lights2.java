package day06;

public class Lights2 {

	int [][] lights = new int[1000][1000];
	
	public long getTotalBrightness() {
		long count = 0;
		for (int i=0; i<1000; i++) {
			for (int j=0; j<1000; j++) {
				count += lights[i][j];
			}
		}
		return count;
	}
	
	public void turnOn(int x1, int y1, int x2, int y2) {
		for (int i=y1; i<=y2; i++) {
			for (int j=x1; j<=x2; j++) {
				lights[i][j]++;
			}
		}
	}
	public void turnOff(int x1, int y1, int x2, int y2) {
		for (int i=y1; i<=y2; i++) {
			for (int j=x1; j<=x2; j++) {
				if (lights[i][j] > 0)
					lights[i][j]--;
			}
		}
	}
	public void toggle(int x1, int y1, int x2, int y2) {
		for (int i=y1; i<=y2; i++) {
			for (int j=x1; j<=x2; j++) {
				lights[i][j] += 2;
			}
		}
	}
	
	//example: toggle 461,550 through 564,900
	public void run(String command) {
		String action = null;
		String remaining = null;
		
		String [] actions = new String [] {"toggle", "turn on", "turn off"};
		
		for (int i=0; i<actions.length; i++) {
			if (command.startsWith(actions[i])) {
				action = actions[i];
				remaining = command.substring(actions[i].length() + 1);
			}
		}
		if (action == null) {
			throw new IllegalArgumentException("Invalid command: " + command);
		}
		
		String [] parts = remaining.split("\\s+");
		String [] xy1 = parts[0].split(",");
		String [] xy2 = parts[2].split(",");
		
		int x1 = Integer.parseInt(xy1[0]);
		int y1 = Integer.parseInt(xy1[1]);
		int x2 = Integer.parseInt(xy2[0]);
		int y2 = Integer.parseInt(xy2[1]);
		
		if (action.equals("toggle"))
			toggle(x1, y1, x2, y2);
		else if (action.equals("turn on"))
			turnOn(x1, y1, x2, y2);
		else if (action.equals("turn off"))
			turnOff(x1, y1, x2, y2);
	}
	
}
