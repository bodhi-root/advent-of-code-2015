package day18;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import common.FileUtil;

public class Main {
	
	static class Game {
		
		char [][] board = new char[100][100];
		int mode = 1;
		
		public void step() {
			//A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
			//A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
			
			char [][] newBoard = new char[100][100];
			for (int i=0; i<100; i++) {
				for (int j=0; j<100; j++) {
					
					char value = board[i][j];
					int count = getNeighborCount(i, j, '#');
					
					if (value == '#') {
						if (count == 2 || count == 3)
							newBoard[i][j] = '#';
						else
							newBoard[i][j] = '.';
					}
					else {
						if (count == 3)
							newBoard[i][j] = '#';
						else
							newBoard[i][j] = '.';
					}
					
					if (mode == 2) {
						newBoard[0][0] = '#';
						newBoard[0][99] = '#';
						newBoard[99][0] = '#';
						newBoard[99][99] = '#';
					}
				}
			}
			
			this.board = newBoard;
		}
		
		public int getCount(char ch) {
			int count = 0;
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) {
					if (board[i][j] == ch)
						count++;
				}
			}
			return count;
		}
		
		public int getNeighborCount(int i, int j, char ch) {
			int count = 0;
			for (int di=-1; di<=1; di++) {
				for (int dj=-1; dj<=1; dj++) {
					if (di == 0 && dj == 0)
						continue;
					
					int iTmp = i + di;
					int jTmp = j + dj;
					
					if (iTmp < 0 || iTmp >= board.length || jTmp < 0 || jTmp >= board[iTmp].length)
						continue;
					
					if (board[iTmp][jTmp] == ch)
						count++;
				}
			}
			return count;
		}
		
		public void load(File file) throws IOException {
			List<String> lines = FileUtil.readLinesFromFile(file);
			for (int i=0; i<lines.size(); i++) {
				char [] values = lines.get(i).toCharArray();
				for (int j=0; j<values.length; j++)
					this.board[i][j] = values[j];
			}
		}
		
		public void print(PrintStream out) {
			StringBuilder s = new StringBuilder(100);
			for (int i=0; i<board.length; i++) {
				s.setLength(0);
				for (int j=0; j<board[i].length; j++)
					s.append(board[i][j]);
				out.println(s.toString());
			}
		}
		
	}
	
	public static void solvePart1() throws Exception {
		Game game = new Game();
		game.load(new File("files/day18/input.txt"));
		game.print(System.out);
		System.out.println(game.getCount('#'));
		for (int i=0; i<100; i++)
			game.step();
		System.out.println(game.getCount('#'));
	}
	
	public static void solvePart2() throws Exception {
		Game game = new Game();
		game.load(new File("files/day18/input.txt"));
		
		game.mode = 2;		//part 2
		game.board[0][0] = '#';
		game.board[0][99] = '#';
		game.board[99][0] = '#';
		game.board[99][99] = '#';
		
		game.print(System.out);
		System.out.println(game.getCount('#'));
		for (int i=0; i<100; i++)
			game.step();
		System.out.println(game.getCount('#'));
	}
	
	public static void main(String [] args) {
		try {
			//solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
