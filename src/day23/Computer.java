package day23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Computer {

	static class Value {
		
		int value = 0;
		
	}
	
	Map<String, Value> registers = new HashMap<>();
	
	public Value getRegister(String name) {
		Value value = registers.get(name);
		if (value == null) {
			value = new Value();
			registers.put(name, value);
		}
		return value;
	}
	
	public void setRegisterValue(String name, int value) {
		getRegister(name).value = value;
	}
	public int getRegisterValue(String name) {
		return getRegister(name).value;
	}
	
	public void printState() {
		StringBuilder s = new StringBuilder();
		s.append("{");
		for (Map.Entry<String, Value> entry : registers.entrySet()) {
			if (s.length() > 1)
				s.append(",");
			s.append(entry.getKey()).append(":").append(entry.getValue().value);
		}
		s.append("}");
		System.out.println(s.toString());
	}
	
	public void runProgram(List<String> lines) {
		
		int nextIndex = 0;

		while (nextIndex >= 0 && nextIndex < lines.size()) {
			
			String cmd = lines.get(nextIndex);
			//printState();
			//System.out.println("Running Cmd " + nextIndex + ": " + cmd);
			
			String [] parts = cmd.split("\\s+", 2);
			
			String action = parts[0];
			String [] argParts = parts[1].split(", ");
			
			//hlf r sets register r to half its current value, then continues with the next instruction.
			if (action.equals("hlf")) {
				Value x = getRegister(argParts[0]);
				x.value /= 2;
			}
			
			//tpl r sets register r to triple its current value, then continues with the next instruction.
			else if (action.equals("tpl")) {
				Value x = getRegister(argParts[0]);
				x.value *= 3;
			}
			
			//inc r increments register r, adding 1 to it, then continues with the next instruction.
			else if (action.equals("inc")) {
				Value x = getRegister(argParts[0]);
				x.value++;
			}
			
			//jmp offset is a jump; it continues with the instruction offset away relative to itself.
			else if (action.equals("jmp")) {
				int offset = Integer.parseInt(argParts[0]);
				nextIndex += offset;
				continue;
			}
			
			//jie r, offset is like jmp, but only jumps if register r is even ("jump if even").
			else if (action.equals("jie")) {
				String name = argParts[0];
				if (getRegister(name).value % 2 == 0) {
					int offset = Integer.parseInt(argParts[1]);
					nextIndex += offset;
					continue;
				}
			}
			//jio r, offset is like jmp, but only jumps if register r is 1 ("jump if one", not odd).
			else if (action.equals("jio")) {
				String name = argParts[0];
				if (getRegister(name).value == 1) {
					int offset = Integer.parseInt(argParts[1]);
					nextIndex += offset;
					continue;
				}
			}
			else {
				throw new IllegalArgumentException("Unknown command: " + cmd);
			}
			
			nextIndex++;
		}
	}
	
}
