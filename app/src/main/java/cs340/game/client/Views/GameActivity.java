package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import cs340.game.R;
import cs340.game.client.Presenters.GamePresenter;


/**
 * Created by Tyler on 9/27/2018.
 */

public class GameActivity extends AppCompatActivity {

    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        presenter = new GamePresenter(this);

        Toast.makeText(this, "The game has started. Good luck", Toast.LENGTH_SHORT).show();
    }

    public void onLeaveGameResponse(boolean isLeaveSuccess) {
        startActivity(new Intent(this, GameListActivity.class));
        this.finish();
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        presenter.leaveGame();

        return true;
    }

}
