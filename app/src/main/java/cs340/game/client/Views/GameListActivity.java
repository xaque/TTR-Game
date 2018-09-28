package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs340.game.R;
import cs340.game.client.Presenters.GameListPresenter;


/**
 * Created by Tyler on 9/26/2018.
 */

public class GameListActivity extends AppCompatActivity implements View.OnClickListener {

    private GameListPresenter presenter;

    EditText gameName;
    Button createGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        gameName = (EditText) findViewById(R.id.name_game);
        createGameButton = (Button) findViewById(R.id.create_game_button);

        createGameButton.setOnClickListener(this);

        presenter = new GameListPresenter(this);
    }

    public void onCreateGameResponse(boolean isCreateGameSuccess) {
        if (isCreateGameSuccess) {
            startActivity(new Intent(this, GameLobbyActivity.class));
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.create_game_button:
                String name = gameName.getText().toString();
                presenter.createGame(name);
                break;

        }
    }
}
