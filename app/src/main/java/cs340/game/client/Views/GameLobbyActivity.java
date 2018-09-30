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
        gameName.setText("Ticket To Ride"); //TODO: put the real name of the game here

        //This isn't right--If you are joining a game, you won't be player 1. I'll have to figure out how to do that.
        //It'll be something like, populatePlayerList, which just pulls the players from the game object in the facade?
        player1 = (TextView) findViewById(R.id.player1);
        player1.setText("Tyler");

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

}
