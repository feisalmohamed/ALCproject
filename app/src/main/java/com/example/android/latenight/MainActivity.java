package com.example.android.latenight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

/**
 * Declaring the variables
 * assigning the buttons an array
 */

    private Button[][] buttons = new Button[3][3];
    private Button reset;
    private Button gameHelp;

    private int player1Points;
    private int player2Points;

    private TextView textView;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    public boolean player1Turn = true;
    public int numbOfRounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =  findViewById(R.id.dialogue);
        textView.setText("Click a button to start.");
        textViewPlayer1 = findViewById(R.id.text_p1);
        textViewPlayer2 = findViewById(R.id.text_p2);
        /**
         * a for loop to store the buttons in 2 by 2 array
         * the buttons id's are located from the resource files
         */
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                String buttonID = "button_" + m + n;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[m][n] = findViewById(resID);
                buttons[m][n].setOnClickListener(this);
            }
        }
        /**
         *     button to reset the game board and score
         *    Capture button clicks

         */
        reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                textView.setText("Click a button to start.");

            }
        });
        /**
         * create an intent
         * locate the button in xml by it's id
         * Capture button clicks
         */
        gameHelp=  findViewById(R.id.button_single);

        // Capture button clicks
        gameHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        NewActivity.class);
                startActivity(myIntent);
            }
        });




    }

/**
 * method to set an X or an O on the buttons depending on the players
 * the players take turns
 * the if statement takes a boolean condition to determine a Winner or a draw
 */
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


            } else {
                player2Wins();

            }
        } else if (numbOfRounds == 9) {
            draw();

        } else {
            player1Turn = !player1Turn;
        }

    }

    /**
     * private boolean method to determine a winner
     * @return
     */
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

    /**
     * stores a new score for player 1
     * displays a toast to indicate player 1 won
     * implements method to updates player 1 points
     * implements method to reset the game board
     */
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Game Over!Player 1 wins ", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    /**
     * stores a new score for player 2
     * displays a toast to indicate player 2 won
     * implements method to updates player 2 points
     * implements method to reset the game board
     */
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Game Over!Player 2 wins ", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    /**
     * displays a toast on the screen to indicate a draw
     * implements method resetBoard
     */
    private void draw() {
        Toast.makeText(this, "Try Again! Draw! ", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    /**
     * methods to update the score and store the new score
     */
    private void updatePoints() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    /**
     * method to reset game board
     * displays starting instruction on a TextView
     */
    private void resetBoard() {
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                buttons[m][n].setText("");
            }




        }

        numbOfRounds = 0;
        player1Turn = true;
    }

    /**
     * method to reset the game score and board
     */

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

