package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cs340.game.R;
import cs340.game.client.Presenters.GameLobbyPresenter;


/**
 * Created by Tyler on 9/26/2018.
 */

public class GameLobbyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGameButton;
    private TextView gameName;

    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;
    private TextView player5;

    private GameLobbyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        startGameButton = (Button) findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(this);

        gameName = (TextView) findViewById(R.id.gameName);

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        player3 = (TextView) findViewById(R.id.player3);
        player4 = (TextView) findViewById(R.id.player4);
        player5 = (TextView) findViewById(R.id.player5);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        presenter = new GameLobbyPresenter(this);
    }

    public void onStartGameResponse(boolean isStartGameSuccess) {
        if (isStartGameSuccess) {
            startActivity(new Intent(this, GameActivity.class));
            this.finish();
        }
    }

    public void onLeaveGameResponse(boolean isLeaveSuccess) {
        startActivity(new Intent(this, GameListActivity.class));
        this.finish();
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_game_button:
                presenter.startGame();
                break;

        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        presenter.leaveGame();

        return true;
    }

    public void setGameName(String name) {
        gameName.setText(name);
    }

    public void setPlayer1(String name){
        player1.setText(name);
    }

    public void setPlayer2(String name){
        player2.setText(name);
    }

    public void setPlayer3(String name){
        player3.setText(name);
    }

    public void setPlayer4(String name){
        player4.setText(name);
    }

    public void setPlayer5(String name){
        player5.setText(name);
    }

}
