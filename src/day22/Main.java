package day22;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static class Player {
		
		int hitPoints;
		int damage;
		int armor;
		int mana;
		
		public Player(int hitPoints, int damage, int armor, int mana) {
			this.hitPoints = hitPoints;
			this.damage = damage;
			this.armor = armor;
			this.mana = mana;
		}
		
		public Player copy() {
			return new Player(hitPoints, damage, armor, mana);
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
	
	static abstract class Spell {
		
		String name;
		int cost;
		
		public Spell(String name, int cost) {
			this.name = name;
			this.cost = cost;
		}
		
		public int getCost() {
			return cost;
		}
		
		public abstract void cast(Game game);
		
		public abstract boolean canCast(Game game);
		
	}
	
	//Magic Missile costs 53 mana. It instantly does 4 damage.
	static class MagicMissileSpell extends Spell {
		public MagicMissileSpell() {
			super("Magic Missile", 53);
		}
		public boolean canCast(Game game) {
			return true;
		}
		public void cast(Game game) {
			game.boss.hitPoints -= 4;
		}
	}
	
	//Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
	static class DrainSpell extends Spell {
		public DrainSpell() {
			super("Drain", 73);
		}
		public boolean canCast(Game game) {
			return true;
		}
		public void cast(Game game) {
			game.boss.hitPoints -= 2;
			game.player.hitPoints += 2;
		}
	}
	
	//Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
	static class ShieldSpell extends Spell {
		public ShieldSpell() {
			super("Shield", 113);
		}
		public boolean canCast(Game game) {
			return game.shieldSpellTurns == 0;
		}
		public void cast(Game game) {
			game.player.armor = 7;
			game.shieldSpellTurns = 6;
		}
	}
	
	//Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
	static class PoisonSpell extends Spell {
		public PoisonSpell() {
			super("Poison", 173);
		}
		public boolean canCast(Game game) {
			return true;
		}
		public void cast(Game game) {
			game.poisonSpellTurns = 6;
		}
	}
	
	//Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
	static class RechargeSpell extends Spell {
		public RechargeSpell() {
			super("Recharge", 229);
		}
		public boolean canCast(Game game) {
			return true;
		}
		public void cast(Game game) {
			game.rechargeSpellTurns = 5;
		}
	}
	
	static class Game {
		
		Player player;
		Player boss;
		boolean verbose = true;
		boolean hard = false;
		
		int shieldSpellTurns = 0;
		int poisonSpellTurns = 0;
		int rechargeSpellTurns = 0;
		
		int manaSpent = 0;
		
		public Game(Player player, Player boss) {
			this.player = player;
			this.boss = boss;
		}
		public Game() {
			this(new Player(50, 0, 0, 500), new Player(55, 8, 0, 0));
		}
		
		public Game copy() {
			Game game = new Game(player.copy(), boss.copy());
			game.verbose = verbose;
			game.hard = hard;
			game.shieldSpellTurns = shieldSpellTurns;
			game.poisonSpellTurns = poisonSpellTurns;
			game.rechargeSpellTurns = rechargeSpellTurns;
			game.manaSpent = manaSpent;
			return game;
		}
		
		public boolean playerTurn(Spell spell) {
			if (verbose) {
				System.out.println("\n-- Player turn --");
				printState();
			}
			
			if (hard) {
				player.hitPoints--;
				if (player.hitPoints <= 0)
					return false;
			}
			
			applyEffects();
			if (boss.hitPoints <= 0)
				return false;
			
			if (verbose)
				System.out.println("Player casts " + spell.name + ".");
			
			player.mana -= spell.cost;
			this.manaSpent += spell.cost;
			spell.cast(this);
			if (boss.hitPoints <= 0)
				return false;
			
			return true;
		}
		
		public boolean bossTurn() {
			if (verbose) {
				System.out.println("\n-- Boss turn --");
				printState();
			}
			
			applyEffects();
			if (boss.hitPoints <= 0)
				return false;
			
			int damage = Math.max(boss.damage - player.armor, 1);
			if (verbose)
				System.out.println("Boss attacks for " + damage + " damage.");
			
			player.hitPoints -= damage;
			return player.hitPoints > 0;
		}
		
		protected void printState() {
			System.out.println("- Player has " + player.hitPoints + " hit points, " + player.armor + " armor, " + player.mana + " mana");
			System.out.println("- Boss has " + boss.hitPoints + " hit points");
		}
		
		protected void applyEffects() {
			
			if (shieldSpellTurns > 0) {
				shieldSpellTurns--;
				if (verbose)
					System.out.println("Shield's timer is now " + shieldSpellTurns);
				
				if (shieldSpellTurns == 0) {
					if (verbose) 
						System.out.println("Shield wears off, decreasing armor by 7.");
					player.armor = 0;
				}
			}
			
			if (poisonSpellTurns > 0) {
				poisonSpellTurns--;
				boss.hitPoints -= 3;
				if (verbose)
					System.out.println("Poison deals 3 damage; its timer is now " + poisonSpellTurns + ".");
			}
			
			if (rechargeSpellTurns > 0) {
				rechargeSpellTurns--;
				player.mana += 101;
				if (verbose)
					System.out.println("Recharge provides 101 mana; its timer is now " + rechargeSpellTurns + ".");
			}
		}
		
	}
	
	public static void runTest() {
		Game game = new Game();
		
		//For example, suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage:
		game.player = new Player(10, 0, 0, 250);
		game.boss = new Player(13, 8, 0, 0);
		
		game.playerTurn(new PoisonSpell());
		//-- Player turn --
		//- Player has 10 hit points, 0 armor, 250 mana
		//- Boss has 13 hit points
		//Player casts Poison.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 10 hit points, 0 armor, 77 mana
		//- Boss has 13 hit points
		//Poison deals 3 damage; its timer is now 5.
		//Boss attacks for 8 damage.

		game.playerTurn(new MagicMissileSpell());
		//-- Player turn --
		//- Player has 2 hit points, 0 armor, 77 mana
		//- Boss has 10 hit points
		//Poison deals 3 damage; its timer is now 4.
		//Player casts Magic Missile, dealing 4 damage.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 2 hit points, 0 armor, 24 mana
		//- Boss has 3 hit points
		//Poison deals 3 damage. This kills the boss, and the player wins.
		
		System.out.println();
		System.out.println("Boss HP: " + game.boss.hitPoints);
		System.out.println("Player HP: " + game.player.hitPoints);
	}
	
	public static void runTest2() {
		Game game = new Game();
		
		//Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
		game.player = new Player(10, 0, 0, 250);
		game.boss = new Player(14, 8, 0, 0);
		
		game.playerTurn(new RechargeSpell());
		//-- Player turn --
		//- Player has 10 hit points, 0 armor, 250 mana
		//- Boss has 14 hit points
		//Player casts Recharge.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 10 hit points, 0 armor, 21 mana
		//- Boss has 14 hit points
		//Recharge provides 101 mana; its timer is now 4.
		//Boss attacks for 8 damage!

		game.playerTurn(new ShieldSpell());
		//-- Player turn --
		//- Player has 2 hit points, 0 armor, 122 mana
		//- Boss has 14 hit points
		//Recharge provides 101 mana; its timer is now 3.
		//Player casts Shield, increasing armor by 7.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 2 hit points, 7 armor, 110 mana
		//- Boss has 14 hit points
		//Shield's timer is now 5.
		//Recharge provides 101 mana; its timer is now 2.
		//Boss attacks for 8 - 7 = 1 damage!

		game.playerTurn(new DrainSpell());
		//-- Player turn --
		//- Player has 1 hit point, 7 armor, 211 mana
		//- Boss has 14 hit points
		//Shield's timer is now 4.
		//Recharge provides 101 mana; its timer is now 1.
		//Player casts Drain, dealing 2 damage, and healing 2 hit points.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 3 hit points, 7 armor, 239 mana
		//- Boss has 12 hit points
		//Shield's timer is now 3.
		//Recharge provides 101 mana; its timer is now 0.
		//Recharge wears off.
		//Boss attacks for 8 - 7 = 1 damage!

		game.playerTurn(new PoisonSpell());
		//-- Player turn --
		//- Player has 2 hit points, 7 armor, 340 mana
		//- Boss has 12 hit points
		//Shield's timer is now 2.
		//Player casts Poison.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 2 hit points, 7 armor, 167 mana
		//- Boss has 12 hit points
		//Shield's timer is now 1.
		//Poison deals 3 damage; its timer is now 5.
		//Boss attacks for 8 - 7 = 1 damage!

		game.playerTurn(new MagicMissileSpell());
		//-- Player turn --
		//- Player has 1 hit point, 7 armor, 167 mana
		//- Boss has 9 hit points
		//Shield's timer is now 0.
		//Shield wears off, decreasing armor by 7.
		//Poison deals 3 damage; its timer is now 4.
		//Player casts Magic Missile, dealing 4 damage.

		game.bossTurn();
		//-- Boss turn --
		//- Player has 1 hit point, 0 armor, 114 mana
		//- Boss has 2 hit points
		//Poison deals 3 damage. This kills the boss, and the player wins.
		
		System.out.println();
		System.out.println("Boss HP: " + game.boss.hitPoints);
		System.out.println("Player HP: " + game.player.hitPoints);
	}
	
	static List<Spell> ALL_SPELLS = new ArrayList<>();
	static {
		ALL_SPELLS.add(new MagicMissileSpell());
		ALL_SPELLS.add(new DrainSpell());
		ALL_SPELLS.add(new ShieldSpell());
		ALL_SPELLS.add(new PoisonSpell());
		ALL_SPELLS.add(new RechargeSpell());
	}
	
	public static void solvePart1() throws Exception {
		Game game = new Game();
		game.verbose = false;
		int best = depthFirstSearch(game, Integer.MAX_VALUE);
		System.out.println("Part 1: " + best);
	}
	
	public static void solvePart2() throws Exception {
		Game game = new Game();
		game.verbose = false;
		game.hard = true;
		int best = depthFirstSearch(game, Integer.MAX_VALUE);
		System.out.println("Part 2: " + best);
	}
	
	public static int depthFirstSearch(Game game, int bestScore) {
		//int bestScore = Integer.MAX_VALUE;
		
		for (Spell spell : ALL_SPELLS) {
			if (spell.canCast(game) && game.player.mana >= spell.cost) {
				Game copy = game.copy();
				
				//player turn (check for end of game)
				if (!copy.playerTurn(spell)) {
					if (copy.player.hitPoints <= 0)
						return Integer.MAX_VALUE;
					else
						bestScore = Math.min(bestScore, copy.manaSpent);
				}
				
				//boss turn (check for end of game)
				else if (!copy.bossTurn()) {
					if (copy.player.hitPoints <= 0)
						return Integer.MAX_VALUE;
					else
						bestScore = Math.min(bestScore, copy.manaSpent);
				}
				
				//game still going:
				else {
					if (copy.manaSpent < bestScore) {
						int score = depthFirstSearch(copy, bestScore);
						bestScore = Math.min(bestScore, score);
					}
				}
			}
		}
		
		return bestScore;
	}
	
	public static void main(String [] args) {
		try {
			//runTest();
			//runTest2();
			solvePart1();
			solvePart2();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
