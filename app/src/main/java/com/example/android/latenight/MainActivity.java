package com.example.android.latenight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private Button[][] buttons = new Button[3][3];

    public boolean player1Turn = true;

    public int numbOfRounds;

    private int player1Points;
    private int player2Points;
    public TextView textView;
    public TextView textViewPlayer1;
    public TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =  findViewById(R.id.dialogue);
        textViewPlayer1 = findViewById(R.id.text_p1);
        textViewPlayer2 = findViewById(R.id.text_p2);

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                String buttonID = "button_" + m + n;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[m][n] = findViewById(resID);
                buttons[m][n].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                textView.setText("Click a button to start.");

            }
        });
        Button SinglePlayer=  findViewById(R.id.button_single);

        // Capture button clicks
        SinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        NewActivity.class);
                startActivity(myIntent);
            }
        });




    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {


            ((Button) v).setText("X");
        } else {

            ((Button) v).setText("O");

        }

        numbOfRounds++;

        if (checkWin()) {
            if (player1Turn) {
                player1Wins();
                Toast.makeText(this, "Game Over!!", Toast.LENGTH_SHORT).show();

            } else {
                player2Wins();
                Toast.makeText(this, "Game Over!!", Toast.LENGTH_SHORT).show();

            }
        } else if (numbOfRounds == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkWin() {
        String[][] field = new String[3][3];

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                field[m][n] = buttons[m][n].getText().toString();
            }
        }

        for (int m = 0; m < 3; m++) {
            if (field[m][0].equals(field[m][1])
                    && field[m][0].equals(field[m][2])
                    && !field[m][0].equals("")) {
                return true;
            }
        }

        for (int m = 0; m < 3; m++) {
            if (field[0][m].equals(field[1][m])
                    && field[0][m].equals(field[2][m])
                    && !field[0][m].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

       else if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!Try Again!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                buttons[m][n].setText("");
            }

            textView.setText("Click a button to start.");


        }

        numbOfRounds = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", numbOfRounds);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        numbOfRounds = savedInstanceState.getInt("numbOfRounds");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}

