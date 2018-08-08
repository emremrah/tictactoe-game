package com.android.emremrah.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int moveTurn = 0; // 0 means X
    private boolean[] clickables = {true, true, true, true, true, true, true, true, true};
    private int[] boardState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningStates = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {2, 4, 6}, {0, 4, 8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();
    }

    public void Initialize() {
    }

    public void Move(View view) {
        ImageView pin = (ImageView) view;
        if (getClickables(pin)) {
            MakeMove(pin);
            MoveAnimation(pin);
            setClickables(pin);
            if (checkGameState()) {
                Toast.makeText(this, (moveTurn == 0) ? "X WON!" : "O WON!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setClickables(ImageView imageView) {
        clickables[Integer.parseInt(imageView.getTag().toString())] = false;
    }

    public boolean getClickables(ImageView imageView) {
        return clickables[Integer.parseInt(imageView.getTag().toString())];
    }

    public void MakeMove(ImageView imageView) {

        /*Sıra X'de ise, ilgili yere X koy; yer numarasına göre (tag) boardState'e oraya hangi taş
        konulduysa onu yaz.*/
        if (moveTurn == 0) {
            imageView.setImageResource(R.drawable.x);
            boardState[Integer.parseInt(imageView.getTag().toString())] = moveTurn;
            moveTurn = 1;
        }
        else {
            imageView.setImageResource(R.drawable.o);
            boardState[Integer.parseInt(imageView.getTag().toString())] = moveTurn;
            moveTurn = 0;
        }
    }

    public void newButtonClick(View view) {

    }

    public boolean checkGameState() {
        for (int[] winningState : winningStates) {
            if (((boardState[winningState[0]] == boardState[winningState[1]]) && boardState[winningState[0]] != 2)
                    && boardState[winningState[1]] == boardState[winningState[2]]) {
                return true;
            }
        }
        return false;
    }

    public void MoveAnimation (ImageView imageView) {
        imageView.setTranslationY(-1500f);
        imageView.setTranslationX(1000f);
        imageView.animate().translationX(0f).translationY(0f).rotationBy(180f).setDuration(750);
    }
}
