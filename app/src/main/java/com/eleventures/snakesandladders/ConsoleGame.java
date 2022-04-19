package com.eleventures.snakesandladders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Hashtable;

public class ConsoleGame {

	private final static int MAX_PLAYERS = 4;
	private static ArrayList<Player> mPlayers;

	private static enum CMD {
		CMD_ROLL_NEXT, CMD_QUIT_PLAYER, CMD_QUIT_GAME;
	}

	private static final Board mBoard = new Board();
	private static final PairOfDice mDice = new PairOfDice();

	private final String COMPUTER = "Computer";
	private static final BufferedReader mInputReader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		try {
			getPlayerNames();
			informRules();
			play();
		} catch (IOException e) {
			System.out.println("Something went wrong getting input stream." + e.getMessage());
		} finally {
			resetGame();
			try {
				mInputReader.close();
			} catch (IOException e) {
				System.out.println("Something went wrong closing input stream." + e.getMessage());
			}
		}
	}

	private static void play() throws NumberFormatException, IOException {

		while (true) {

			while (!mPlayers.isEmpty()){
				for (Player player:
					 mPlayers) {

					int input = userPrompt(player);

					if (input == CMD.CMD_QUIT_GAME.ordinal()) {
						System.out.println("Quitting game. Thanks for playing!");
						return;
					} else if (input == CMD.CMD_QUIT_PLAYER.ordinal()) {
						System.out.println("Killing player.");
						mPlayers.remove(player.getName());
						if (onlyComputerLeft()) {
							System.out.println("Computer quits too! Thanks for playing!");
							return;
						}
					} else if (input == CMD.CMD_ROLL_NEXT.ordinal()) {
						playTurn(player);
					} else {
						System.out.println("Invalid choice. Lose your turn!");
					}
				}
				if(mPlayers.isEmpty()) {
					System.out.println("No one left to play. Quitting game. Thanks for playing!");
					return;
				}
			}
		}
	}

	private static boolean onlyComputerLeft() {
		return mPlayers.size() == 1 && mPlayers.get(0).isComputer();
	}

	public static void playTurn(Player player) {
		int rollDice = mDice.rollDice();
		PlayTurnResult result = mBoard.playTurn(player.getCurrentPosition(), rollDice);
		player.setCurrentPosition(result.mNewPosition);
		System.out.println(result.mMessage);
		String message = player.getName() + ":\n" + result.mMessage;
		if (result.mIsWinner){
			declareWinner(player);
			mPlayers.remove(player);
		}
	}

	private boolean hasReachedObjective(int position) {
		return position >= mBoard.getFinalPosition();
	}

	public static void resetGame() {
		mPlayers.clear();
		Player.clearStatic();
		mDice.reset();
	}

	/**
	 * Only for use in console version
	 * @throws IOException
	 */
	private static void getPlayerNames() throws IOException {
		System.out.println(
				"Welcome to Snakes and Ladders!\n" + "Please tell me how many players. The minumum is 1 player and the "
						+ "maximum allowed is " + MAX_PLAYERS);
		int playerCount = 0;
		try {
			playerCount = Integer.valueOf(mInputReader.readLine());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid input. Please enter a proper number.");
		}

		if (playerCount <= 0 || playerCount > MAX_PLAYERS) {
			throw new IllegalArgumentException("Invalid input. Please enter a proper number.");
		}
		for (int i = 0; i < playerCount; i++) {
			System.out.println("Please enter player " + (i + 1) + " name.");
			String name = mInputReader.readLine();
			mPlayers.add(new Player(name));
		}
		if (playerCount == 1) {
			mPlayers.add(new Player(true));
		}
	}

	private static int userPrompt(Player player) throws NumberFormatException, IOException {
		System.out.println("Ready player " + player.getName() + "?\n" + CMD.CMD_QUIT_PLAYER.ordinal()
				+ " to quit you from the game.\n" + CMD.CMD_QUIT_GAME.ordinal() + " to stop the game.\n"
				+ CMD.CMD_ROLL_NEXT.ordinal() + " to roll the dice and play.\n");
		if (player.isComputer()) {
			return CMD.CMD_ROLL_NEXT.ordinal();
		}
		return Integer.valueOf(mInputReader.readLine());
	}

	private static void declareWinner(Player player) {
		System.out.println("Congratulations player " + player.getName()
				+ "!\nYou have reached the end of the board!\n");
	}

	private static void informRules() throws IOException {
		System.out.println("We have " + mPlayers.size() + " players.");
		System.out.println("Each player will roll the dice." + "The dice can roll a maximum of 12.\n"
				+ "Whatever square you land on may have a snake or a ladder.\n"
				+ "If you are bit by a snake, you will move backwards, till you\n"
				+ "land on the square where it's tail is.\n"
				+ "If you reach a ladder, you will move forwards, till you land\n"
				+ "on the square where the ladder ends.\n\n"
				+ "At any point, a player may choose to quit the game by typing in " + CMD.CMD_QUIT_PLAYER
				+ " when prompted at his or her turn.\n\n" + "You may also collectively quit the game by typing in "
				+ CMD.CMD_QUIT_GAME.ordinal() + " when prompted at any player's turn.\n\n");
		System.out.println(
				"To simply roll the dice and try your luck, type in " + CMD.CMD_ROLL_NEXT.ordinal() 
				+ " at your turn.\nPress enter to move forward.");
		mInputReader.readLine();
		
	}
}
