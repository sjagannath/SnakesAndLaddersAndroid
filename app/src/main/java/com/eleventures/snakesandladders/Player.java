package com.eleventures.snakesandladders;

import com.eleventures.snakesandladders.exceptions.TooManyPlayersException;

import java.util.HashSet;
import java.util.Set;

public class Player {

	private static final String COMPUTER = "COMPUTER";
	private int mCurrentPosition;
	private final String mName;
	private final COLOUR mPlayerColour;
	private final boolean mIsComputer;
	private final static Set<COLOUR> mOccupiedColours = new HashSet<>();

	public Player(boolean mIsComputer){
		this(COMPUTER , true);
	}
	public Player(String name){
		this(name , false);
	}

	private Player(String name, boolean isComputer) {
		COLOUR colour;
		if(!isComputer){
			mName = (name == null || name.isEmpty())?getAvailableColour().name():name;
			mPlayerColour = COLOUR.valueOf(name);
		}else{
			mName = name;
			mPlayerColour = getAvailableColour();
		}
		mCurrentPosition = 0;
		mIsComputer = isComputer;
	}

	public static void clearStatic(){
		mOccupiedColours.clear();
	}

	public String getPlayerColour() {
		return mPlayerColour.name();
	}
	public int getCurrentPosition() {
		return mCurrentPosition;
	}

	public void setCurrentPosition(int mCurrentPosition) {
		this.mCurrentPosition = mCurrentPosition;
	}

	public String getName() {
		return mName;
	}

	public boolean isComputer() {
		return mIsComputer;
	}

	private COLOUR getAvailableColour() throws TooManyPlayersException {
		for (COLOUR colour : COLOUR.values()) {
			if (!mOccupiedColours.contains(colour)) {
				mOccupiedColours.add(colour);
				return colour;
			}
		}
		throw new TooManyPlayersException();
	}
	public enum COLOUR {
		RED, BLUE, GREEN, YELLOW
	}
}
