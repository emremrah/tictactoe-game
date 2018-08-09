package com.android.emremrah.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0 means X
    private int moveTurn = 0;
    private boolean[] clickables = {true, true, true, true, true, true, true, true, true};

    // 1'den 9'a numaralı yerlerde hangi taşların bulunduğunun kaydedilmesi
    private int[] boardState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Bu numaralarda aynı tür taş bulunuyorsa, oyun bitmiş ve o taşın sahibi kazanmış demektir
    private int[][] winningStates = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {2, 4, 6}, {0, 4, 8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Move(View view) {
        ImageView pin = (ImageView) view;
        if (getClickables(pin)) {
            MakeMove(pin);
            MoveAnimation(pin);
            setClickables(pin);
            if (checkGameState()) {
                endGame();
            } else {
                // Oyun bitmemişse oynama sırasını değiştir
                moveTurn = (moveTurn==0) ? 1 : 0;
            }
        }
    }

    public void newGameClick(View view) {
        resetGame(view);
    }

    public void MakeMove(ImageView imageView) {

        /*Sıra X'de ise, ilgili yere X koy; yer numarasına göre (tag) boardState'e oraya hangi taş
        konulduysa onu yaz.*/
        if (moveTurn == 0) {
            imageView.setImageResource(R.drawable.x);
            boardState[Integer.parseInt(imageView.getTag().toString())] = moveTurn;
        }
        else {
            imageView.setImageResource(R.drawable.o);
            boardState[Integer.parseInt(imageView.getTag().toString())] = moveTurn;
        }
    }

    public void MoveAnimation (ImageView imageView) {
        imageView.setTranslationY(-1500f);
        imageView.setTranslationX(1000f);
        imageView.animate().translationX(0f).translationY(0f).rotationBy(180f).setDuration(500);
    }

    public boolean getClickables(ImageView imageView) {
        return clickables[Integer.parseInt(imageView.getTag().toString())];
    }

    public void setClickables(ImageView imageView) {
        clickables[Integer.parseInt(imageView.getTag().toString())] = false;
    }

    public boolean checkGameState() {
        for (int[] winningState : winningStates) {
            if (boardState[winningState[0]] == boardState[winningState[1]] &&
                    boardState[winningState[1]] == boardState[winningState[2]] &&
                    boardState[winningState[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    public void endGame() {
        // Tüm hücreleri tıklanamaz yap
        for (int i = 0; i < clickables.length; i++) clickables[i] = false;

        Toast.makeText(this, (moveTurn == 0) ? "X WON!" : "O WON!", Toast.LENGTH_SHORT).show();
    }

    public void resetGame(View view) {
        for (int i = 0; i < clickables.length; i++) clickables[i] = true;
        for (int i = 0; i < boardState.length; i++) boardState[i] = 2;
        moveTurn = 0;

        try {
            android.support.v7.widget.GridLayout boardLayout = findViewById(R.id.boardLayout);
            for (int i = 0; i < boardLayout.getChildCount(); i++) {
                ((ImageView) boardLayout.getChildAt(i)).setImageResource(0);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
