package com.eleventures.snakesandladders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.eleventures.snakesandladders.views.AutoDismissDialog;
import com.eleventures.snakesandladders.views.RollingDiceAutoDismissProgress;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    private Game mGame = null;
    private AutoDismissDialog mRollingDiceProgressDialog;
    private TextView mConsole, mConsoleComputer;

    private Button mRollDiceButton;
    //    private TableLayout mBoardTable;

    private final View.OnClickListener mRollDiceListener = v -> {
        if(mGame == null){
            showPlayerInfoDialog();
        }else{
            play();
        }
    };
    private View.OnClickListener mResetGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetGame();
        }
    };

    private void play() {
        mRollDiceButton.setEnabled(false);
        showRollDiceProgress();
        mRollDiceButton.setEnabled(true);
    }

    private void showRollDiceProgress() {
        mRollingDiceProgressDialog.show();
    }

    private final DialogInterface.OnDismissListener mRollDiceDialogDismissListener = dialog -> {
        updateViews(mGame.playTurn( false), false);
        updateViews(mGame.playTurn(true), true);
    };

    private void updateViews(PlayTurnResult result, boolean isComputer) {
        StringBuilder strBuilder = new StringBuilder(result.mMessage).append("\n\n");
        if(!isComputer) {
            mConsole.setText(strBuilder.toString());
        }else{
            mConsoleComputer.setText(strBuilder.toString());
        }
        anyoneWon(result);
    }

    private void showPlayerInfoDialog(){
        AlertDialog playerInfoDialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.player_colours, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Player player = new Player(getResources().getStringArray(R.array.player_colours)[which]);
                        mGame = new Game(player);
                        play();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();
        playerInfoDialog.show();
    }

    private void anyoneWon(PlayTurnResult result) {
        if(result.mIsWinner){
            showWinnerDialog(result);
            resetGame();
        }
    }

    private void resetGame() {
        if(mGame != null) {
            mGame.resetGame();
            mGame = null;
        }
        mConsole.setText(R.string.welcome);
        mConsoleComputer.setText(R.string.computerInfo);
    }

    private void showWinnerDialog(PlayTurnResult result) {
        new AlertDialog.Builder(this).setMessage(result.mMessage).show();
    }

    private final View.OnClickListener mQuitListener = v -> {
        resetGame();
        finish();
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetGame();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setMessage(getString(R.string.welcomeDialog));
        dialog.create().show();
        mRollDiceButton = findViewById(R.id.rollDiceButton);
        Button mQuitButton = findViewById(R.id.quitButton);
        Button resetGameButton = findViewById(R.id.resetButton);
//        mBoardTable = findViewById(R.id.gameboard);
        mConsole = findViewById(R.id.console);
        mConsoleComputer = findViewById(R.id.consoleComputer);

        mRollDiceButton.setOnClickListener(mRollDiceListener);
        mQuitButton.setOnClickListener(mQuitListener);
        resetGameButton.setOnClickListener(mResetGameListener);

        mRollingDiceProgressDialog = new RollingDiceAutoDismissProgress(this, 0, null, mRollDiceDialogDismissListener);
        mRollingDiceProgressDialog.setTitle("Rolling dice!");
        mRollingDiceProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetGame();
    }
}