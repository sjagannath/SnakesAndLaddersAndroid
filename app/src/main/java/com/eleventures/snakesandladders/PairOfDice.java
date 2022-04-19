package com.eleventures.snakesandladders;

import java.util.Random;

public class PairOfDice {
	private class Die{
		private final int MAX_NUM = 6;
		private int mCurrentNum = 0;
		private final Random mRandom = new Random();
		
		void roll() {
			int rolled = mRandom.nextInt(6);
			mCurrentNum = rolled == 0?1: rolled;
		}
		
		int getRolledValue() {
			if(mCurrentNum == 0) roll();
			return mCurrentNum;
		}
		
		void resetDie() {
			mCurrentNum = 0;
		}
	}
	
	private final Die mOne, mTwo;
	public PairOfDice() {
		mOne = new Die();
		mTwo = new Die();
	}
	
	public int rollDice() {
		mOne.roll();
		mTwo.roll();
		return mOne.getRolledValue() + mTwo.getRolledValue();
	}
	
	public void reset() {
		mOne.resetDie();
		mTwo.resetDie();
	}
}
