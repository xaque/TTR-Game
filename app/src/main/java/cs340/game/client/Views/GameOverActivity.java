package cs340.game.client.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import cs340.game.R;
import cs340.game.client.Presenters.GameOverPresenter;
import cs340.game.client.Presenters.MainActivityPresenter;

public class GameOverActivity extends AppCompatActivity {

    private TextView winner;

    //PLAYER 1
    private TableRow player1_row;
    private TextView player1_name;
    private TextView player1_score;
    private TextView player1_dest_points;
    private TextView player1_dest_lost;
    private TextView player1_lp;
    //PLAYER 2
    private TableRow player2_row;
    private TextView player2_name;
    private TextView player2_score;
    private TextView player2_dest_points;
    private TextView player2_dest_lost;
    private TextView player2_lp;
    //PLAYER 3
    private TableRow player3_row;
    private TextView player3_name;
    private TextView player3_score;
    private TextView player3_dest_points;
    private TextView player3_dest_lost;
    private TextView player3_lp;
    //PLAYER 4
    private TableRow player4_row;
    private TextView player4_name;
    private TextView player4_score;
    private TextView player4_dest_points;
    private TextView player4_dest_lost;
    private TextView player4_lp;
    //PLAYER 5
    private TableRow player5_row;
    private TextView player5_name;
    private TextView player5_score;
    private TextView player5_dest_points;
    private TextView player5_dest_lost;
    private TextView player5_lp;

    private GameOverPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Initialize all the text views yay
        winner = findViewById(R.id.winner);

        player1_row = findViewById(R.id.player1_row);
        player1_name = findViewById(R.id.player1_name);
        player1_score = findViewById(R.id.player1_points);
        player1_dest_points = findViewById(R.id.player1_dest_points);
        player1_dest_lost = findViewById(R.id.player1_dest_lost);
        player1_lp = findViewById(R.id.player1_lp);

        player2_row = findViewById(R.id.player2_row);
        player2_name = findViewById(R.id.player2_name);
        player2_score = findViewById(R.id.player2_points);
        player2_dest_points = findViewById(R.id.player2_dest_points);
        player2_dest_lost = findViewById(R.id.player2_dest_lost);
        player2_lp = findViewById(R.id.player2_lp);

        player3_row = findViewById(R.id.player3_row);
        player3_name = findViewById(R.id.player3_name);
        player3_score = findViewById(R.id.player3_points);
        player3_dest_points = findViewById(R.id.player3_dest_points);
        player3_dest_lost = findViewById(R.id.player3_dest_lost);
        player3_lp = findViewById(R.id.player3_lp);

        player4_row = findViewById(R.id.player4_row);
        player4_name = findViewById(R.id.player4_name);
        player4_score = findViewById(R.id.player4_points);
        player4_dest_points = findViewById(R.id.player4_dest_points);
        player4_dest_lost = findViewById(R.id.player4_dest_lost);
        player4_lp = findViewById(R.id.player4_lp);

        player5_row = findViewById(R.id.player5_row);
        player5_name = findViewById(R.id.player5_name);
        player5_score = findViewById(R.id.player5_points);
        player5_dest_points = findViewById(R.id.player5_dest_points);
        player5_dest_lost = findViewById(R.id.player5_dest_lost);
        player5_lp = findViewById(R.id.player5_lp);



        presenter = new GameOverPresenter(this);
    }

    public void hidePlayer(int playerNumber){
        switch(playerNumber){
            case 1:
                player1_row.setVisibility(View.INVISIBLE);
                break;
            case 2:
                player2_row.setVisibility(View.INVISIBLE);
                break;
            case 3:
                player3_row.setVisibility(View.INVISIBLE);
                break;
            case 4:
                player4_row.setVisibility(View.INVISIBLE);
                break;
            case 5:
                player5_row.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void setPlayer1(String name, String points, String dest, String lost, String lp){
        player1_name.setText(name);
        player1_score.setText(points);
        player1_dest_points.setText(dest);
        player1_dest_lost.setText(lost);
        player1_lp.setText(lp);
    }

    public void setPlayer2(String name, String points, String dest, String lost, String lp){
        player2_name.setText(name);
        player2_score.setText(points);
        player2_dest_points.setText(dest);
        player2_dest_lost.setText(lost);
        player2_lp.setText(lp);
    }

    public void setPlayer3(String name, String points, String dest, String lost, String lp){
        player3_name.setText(name);
        player3_score.setText(points);
        player3_dest_points.setText(dest);
        player3_dest_lost.setText(lost);
        player3_lp.setText(lp);
    }

    public void setPlayer4(String name, String points, String dest, String lost, String lp){
        player4_name.setText(name);
        player4_score.setText(points);
        player4_dest_points.setText(dest);
        player4_dest_lost.setText(lost);
        player4_lp.setText(lp);
    }

    public void setPlayer5(String name, String points, String dest, String lost, String lp){
        player5_name.setText(name);
        player5_score.setText(points);
        player5_dest_points.setText(dest);
        player5_dest_lost.setText(lost);
        player5_lp.setText(lp);
    }
}
