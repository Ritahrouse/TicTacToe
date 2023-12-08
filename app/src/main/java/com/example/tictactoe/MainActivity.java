package com.example.tictactoe;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore,playerToScore,playerStatus;
    private Button [] buttons=new Button[9];
    private Button resetGame;

    private int playerOneScoreCount,playerTwoScoreCount,rountCount;
    boolean activePlayer;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    int [][] winningPositions={{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };
    //private String defType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore= findViewById(R.id.textView2);
        playerToScore=findViewById(R.id.textView3);
       playerStatus= findViewById(R.id.playerStatus);

        resetGame= findViewById(R.id.button19);
        for(int i=0;i< buttons.length;i++)
        {
             String buttonID="button1"+i;
            //String defType;
            int resourceID=getResources().getIdentifier(buttonID, "id" ,getPackageName());
             buttons[i]=(Button) findViewById(resourceID);
             buttons[i].setOnClickListener(this);

        }
        rountCount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer=true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            ((Button)v).setText("X");
            //((Button)v).setTextColor(Color.parseColor(colorString:"#FFC34A"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)v).setText("O");
           // ((Button)v).setTextColor();
            gameState[gameStatePointer]=1;
        }
        rountCount++;
        //Context context;
        if(checkWinner()){
            if(activePlayer){
            playerOneScoreCount++;
            updatePlayerScore();
            Toast.makeText(this,"Player One Won!",Toast.LENGTH_SHORT).show();
            playAgain();
            }
            else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!",Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this,"No winner!",Toast.LENGTH_SHORT).show();

        }else{
            activePlayer=!activePlayer;
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("player one is winning!");
        }else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("player Two is winning!");
        }else{
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });

    }
    public boolean checkWinner(){
        boolean winnerResult= false;
        for(int [] winningPosition: winningPositions){
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]]==gameState[winningPosition[2]]&&gameState[winningPosition[0]]!= 2){
                winnerResult= true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerToScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        rountCount=0;
        activePlayer=true;
        for(int i=0;i< buttons.length;i++)
        {
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}