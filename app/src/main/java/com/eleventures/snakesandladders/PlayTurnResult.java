package com.eleventures.snakesandladders;

class PlayTurnResult {
    final int mNewPosition;
    final boolean mIsWinner;
    final String mMessage;

    public PlayTurnResult(int mNewPosition, boolean mIsWinner, String mMessage) {
        this.mNewPosition = mNewPosition;
        this.mIsWinner = mIsWinner;
        this.mMessage = mMessage;
    }
}
