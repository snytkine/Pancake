package com.snytkine.pancake2;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;

public class Pancake2 extends Activity implements OnClickListener {

	protected Handler myHandler = new Handler();
	Button btnPlay;
	TextView textResult;
	protected String[] aResults;
	protected Random myRand = new Random();
	protected int randNum = 4;
	protected String currentDraw;

	// static final String CURRENT_DRAW = "lastDraw";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int myInt = (null != savedInstanceState) ? savedInstanceState.getInt(
				"currentDraw", 4) : 4;

		aResults = new String[4];
		aResults[0] = "Rock";
		aResults[1] = "Paper";
		aResults[2] = "Scissors";
		aResults[3] = "Boo...";

		btnPlay = (Button) findViewById(R.id.btnPlay);
		textResult = (TextView) findViewById(R.id.resultText);
		btnPlay.setOnClickListener(this);

		if (myInt < 4) {
			/**
			 * Important to assign value of myInt to randNum otherwise if the
			 * phone is flipped twice, then the second time around the
			 * onSavedInstanseStace will pick up the initial value of randNum
			 * which is initially 4 (because it was never changed if user has
			 * not performed any new moves)
			 */
			randNum = myInt;
			textResult.setText(aResults[myInt]);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentDraw", randNum);

	}

	protected void onStop(Bundle outState) {
		super.onStop();
		randNum = 4;
		outState.putInt("currentDraw", randNum);
	}

	protected void onDestroy(Bundle outState) {
		super.onDestroy();
		randNum = 4;
		outState.putInt("currentDraw", randNum);
	}

	public synchronized void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnPlay:
			//v.setClickable(false);
			showNewDraw();
			break;
		}
	}

	/**
	 * Generates the new random result and displays it on screen but with the
	 * little delay By first clearing the result and then waiting 1/2 sec we
	 * make it visually clear that the next draw did occur, even if the next
	 * draw is the same as current one
	 * 
	 */
	protected void showNewDraw() {
		textResult.setBackgroundColor(Color.DKGRAY);
		textResult.setText("");
		getRandomResult();

		myHandler.postDelayed(new Runnable() {
			public void run() {
				textResult.setBackgroundColor(Color.WHITE);
				textResult.setText(currentDraw);
			}
		}, 500);
	}

	/**
	 * Sets the value of currentDraw randomly
	 */
	protected void getRandomResult() {
		randNum = myRand.nextInt(3);
		currentDraw = aResults[randNum];
	}
}