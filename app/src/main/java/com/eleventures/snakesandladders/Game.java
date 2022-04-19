package com.eleventures.snakesandladders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Game {

	private final static int MAX_PLAYERS = 4;

	private final Board mBoard = new Board();
	private final PairOfDice mDice = new PairOfDice();
	private final Player mPlayerComputer;
	private final Player mPlayerHuman;

	public Game(Player player) {
		mPlayerHuman = player;
		mPlayerComputer = new Player(true);
	}

	public PlayTurnResult playTurn(boolean isComputer) {
		return playTurn(isComputer?mPlayerComputer:mPlayerHuman);
	}

	private PlayTurnResult playTurn(Player player) {
		int rollDice = mDice.rollDice();
		PlayTurnResult result = mBoard.playTurn(player.getCurrentPosition(), rollDice);
		player.setCurrentPosition(result.mNewPosition);
		String message = player.getName() + ":\n" + result.mMessage;
		return new PlayTurnResult(result.mNewPosition, result.mIsWinner, message);
	}

	public void resetGame() {
		Player.clearStatic();
		mDice.reset();
	}
}
