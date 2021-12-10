package day21;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static class Player {
		
		int hitPoints;
		int damage;
		int armor;
		
		public Player(int hitPoints, int damage, int armor) {
			this.hitPoints = hitPoints;
			this.damage = damage;
			this.armor = armor;
		}
		
		public int simulateFightWith(Player opponent) {
			int myHitPoints = this.hitPoints;
			int opponentHitPoints = opponent.hitPoints;
			
			int myDamage = Math.max(this.damage - opponent.armor, 1);
			int opponentDamage = Math.max(opponent.damage - this.armor, 1);
			
			while(true) {
				opponentHitPoints -= myDamage;
				if (opponentHitPoints <= 0)
					return myHitPoints;
				
				myHitPoints -= opponentDamage;
				if (myHitPoints <= 0)
					return 0;
			}
		}
		
	}
	
	static class Item {
		
		String name;
		int cost;
		int damage;
		int armor;
		
		public Item(String name, int cost, int damage, int armor) {
			this.name = name;
			this.cost = cost;
			this.damage = damage;
			this.armor = armor;
		}
		
	}
	
	static class Game {
		
		Player boss;
		List<Item> weapons;
		List<Item> armor;
		List<Item> rings;
		
		public Game() {
			init();
		}
		
		public void init() {
			boss = new Player(104, 8, 1);
			
			//Weapons:    Cost  Damage  Armor
			//Dagger        8     4       0
			//Shortsword   10     5       0
			//Warhammer    25     6       0
			//Longsword    40     7       0
			//Greataxe     74     8       0
			
			weapons = new ArrayList<>();
			weapons.add(new Item("Dagger", 8, 4, 0));
			weapons.add(new Item("Shortsword", 10, 5, 0));
			weapons.add(new Item("Warhammer", 25, 6, 0));
			weapons.add(new Item("Longsword", 40, 7, 0));
			weapons.add(new Item("Greataxe", 74, 8, 0));
			
			//Armor:      Cost  Damage  Armor
			//Leather      13     0       1
			//Chainmail    31     0       2
			//Splintmail   53     0       3
			//Bandedmail   75     0       4
			//Platemail   102     0       5

			armor = new ArrayList<>();
			armor.add(new Item("Leather", 13, 0, 1));
			armor.add(new Item("Chainmail", 31, 0, 2));
			armor.add(new Item("Splintmail", 53, 0, 3));
			armor.add(new Item("Bandedmail", 75, 0, 4));
			armor.add(new Item("Platemail", 102, 0, 5));
			
			//Rings:      Cost  Damage  Armor
			//Damage +1    25     1       0
			//Damage +2    50     2       0
			//Damage +3   100     3       0
			//Defense +1   20     0       1
			//Defense +2   40     0       2
			//Defense +3   80     0       3
			
			rings = new ArrayList<>();
			rings.add(new Item("Damage +1", 25, 1, 0));
			rings.add(new Item("Damage +2", 50, 2, 0));
			rings.add(new Item("Damage +3", 100, 3, 0));
			rings.add(new Item("Defense +1", 20, 0, 1));
			rings.add(new Item("Defense +2", 40, 0, 2));
			rings.add(new Item("Defense +3", 80, 0, 3));
		}
		
	}
	
	public static void test() {
		Player player1 = new Player(8, 5, 5);
		Player player2 = new Player(12, 7, 2);
		System.out.println(player1.simulateFightWith(player2));
	}
	
	public static void solvePart1() throws Exception {
		
		//You must buy:
		//1 weapon;
		//0-1 armor
		//0-2 rings
		//The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
		
		Game game = new Game();
		
		int idxBestWeapon = -1;
		int idxBestArmor = -1;
		int idxBestRing1 = -1;
		int idxBestRing2 = -1;
		int minCost = Integer.MAX_VALUE;
		
		//iterate over all combinations (-1 = none)
		for (int idxWeapon = 0; idxWeapon < game.weapons.size(); idxWeapon++) {
			for (int idxArmor = -1; idxArmor < game.armor.size(); idxArmor++) {
				for (int idxRing1 = -1; idxRing1 < game.rings.size(); idxRing1++) {
					for (int idxRing2 = -1; idxRing2 < game.rings.size(); idxRing2++) {
						
						//throw out invalid configurations:
						//(can't wear two of same ring):
						if (idxRing1 >= 0 && idxRing2 >= 0 && idxRing1 == idxRing2)
							continue;
						
						int cost = game.weapons.get(idxWeapon).cost +
							     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).cost) +
							     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).cost) +
							     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).cost);
						
						int damage = game.weapons.get(idxWeapon).damage +
								     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).damage) +
								     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).damage) +
								     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).damage);
						
						int armor = game.weapons.get(idxWeapon).armor +
							     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).armor) +
							     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).armor) +
							     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).armor);
						
						Player player = new Player(100, damage, armor);
						
						if (cost < minCost && player.simulateFightWith(game.boss) > 0) {
							minCost = cost;
							idxBestWeapon = idxWeapon;
							idxBestArmor = idxArmor;
							idxBestRing1 = idxRing1;
							idxBestRing2 = idxRing2;
						}
					}
				}
			}
			
		}
		
		System.out.println("Min Cost: " + minCost);
		System.out.println("Weapon: " + game.weapons.get(idxBestWeapon).name);
		System.out.println("Armor: " + (idxBestArmor < 0 ? "(none)" : game.armor.get(idxBestArmor).name));
		System.out.println("Ring 1: " + (idxBestRing1 < 0 ? "(none)" : game.rings.get(idxBestRing1).name));
		System.out.println("Ring 2: " + (idxBestRing2 < 0 ? "(none)" : game.rings.get(idxBestRing2).name));
	}
	
	public static void solvePart2() throws Exception {
		Game game = new Game();
		
		int idxBestWeapon = -1;
		int idxBestArmor = -1;
		int idxBestRing1 = -1;
		int idxBestRing2 = -1;
		int maxCost = Integer.MIN_VALUE;
		
		//iterate over all combinations (-1 = none)
		for (int idxWeapon = 0; idxWeapon < game.weapons.size(); idxWeapon++) {
			for (int idxArmor = -1; idxArmor < game.armor.size(); idxArmor++) {
				for (int idxRing1 = -1; idxRing1 < game.rings.size(); idxRing1++) {
					for (int idxRing2 = -1; idxRing2 < game.rings.size(); idxRing2++) {
						
						//throw out invalid configurations:
						//(can't wear two of same ring):
						if (idxRing1 >= 0 && idxRing2 >= 0 && idxRing1 == idxRing2)
							continue;
						
						int cost = game.weapons.get(idxWeapon).cost +
							     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).cost) +
							     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).cost) +
							     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).cost);
						
						int damage = game.weapons.get(idxWeapon).damage +
								     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).damage) +
								     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).damage) +
								     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).damage);
						
						int armor = game.weapons.get(idxWeapon).armor +
							     (idxArmor < 0 ? 0 : game.armor.get(idxArmor).armor) +
							     (idxRing1 < 0 ? 0 : game.rings.get(idxRing1).armor) +
							     (idxRing2 < 0 ? 0 : game.rings.get(idxRing2).armor);
						
						Player player = new Player(100, damage, armor);
						
						if (cost > maxCost && player.simulateFightWith(game.boss) == 0) {
							maxCost = cost;
							idxBestWeapon = idxWeapon;
							idxBestArmor = idxArmor;
							idxBestRing1 = idxRing1;
							idxBestRing2 = idxRing2;
						}
					}
				}
			}
			
		}
		
		System.out.println("Max Cost: " + maxCost);
		System.out.println("Weapon: " + game.weapons.get(idxBestWeapon).name);
		System.out.println("Armor: " + (idxBestArmor < 0 ? "(none)" : game.armor.get(idxBestArmor).name));
		System.out.println("Ring 1: " + (idxBestRing1 < 0 ? "(none)" : game.rings.get(idxBestRing1).name));
		System.out.println("Ring 2: " + (idxBestRing2 < 0 ? "(none)" : game.rings.get(idxBestRing2).name));
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
