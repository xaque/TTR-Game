package cs340.game.client.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cs340.game.R;
import cs340.game.client.AppLayerFacade;
import cs340.game.client.Presenters.GameListPresenter;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameList;


/**
 * Created by Tyler on 9/26/2018.
 */

public class GameListActivity extends AppCompatActivity implements View.OnClickListener {

    private GameListPresenter presenter;
    private GameAdapter gameAdapter;
    private RecyclerView gameListRecyclerView;
    private EditText gameName;
    private Button createGameButton;
    private Button joinGameButton;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        gameListRecyclerView = findViewById(R.id.gameList);
        gameName = (EditText) findViewById(R.id.name_game);
        createGameButton = (Button) findViewById(R.id.create_game_button);
        joinGameButton = (Button) findViewById(R.id.join_button);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        username = getIntent().getStringExtra("username");
        Toast.makeText(this, "You are logged in as " + username, Toast.LENGTH_SHORT).show();

        createGameButton.setOnClickListener(this);

        presenter = new GameListPresenter(this);
        updateUI();
    }

    public void onCreateGameResponse(boolean isCreateGameSuccess) {
        if (isCreateGameSuccess) {
            startActivity(new Intent(this, GameLobbyActivity.class));
            this.finish();
        }
    }

    public GameListActivity getGameListContext() {
        return this;
    }

    public void onJoinGameResponse(boolean isJoinGameSuccess) {

    }

    public void onLogOutResponse(boolean isLogOutSuccess) {
        if (isLogOutSuccess) {
            startActivity(new Intent( this, MainActivity.class));
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        GameList gameList = AppLayerFacade.getInstance().getAllGames();
        System.out.println(gameList.toString());
        gameAdapter = new GameAdapter(gameList);
        gameListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameListRecyclerView.setAdapter(gameAdapter);
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        System.out.println("onClick()");
        switch(v.getId()){
            case R.id.create_game_button:
                String name = gameName.getText().toString();
                System.out.println("CREATE GAME");
                presenter.createGame(name);
                break;
            case R.id.join_button:
                //get game name??
                String gameToJoin = "gane";
                System.out.println("JOIN GAME");
                presenter.joinGame(gameToJoin);
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        presenter.logout();

        return true;
    }


    private class GameHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mGameListItem;
        private TextView mGameName;
        private TextView mNumberPlayers;
        private TextView mStatus;
        private Button mJoinButton;

        private Game mGame;

        private GameHolder(View itemView) {
            super(itemView);

            mGameListItem = itemView.findViewById(R.id.game_list_item);
            mGameName = itemView.findViewById(R.id.name);
            mNumberPlayers = itemView.findViewById(R.id.number_players);
            mStatus = itemView.findViewById(R.id.status);
            mJoinButton = itemView.findViewById(R.id.join_button);
            mJoinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getGameListContext(), GameLobbyActivity.class);
                    intent.putExtra("gameName", mGame.getName());
                    startActivity(intent);
                }
            });
        }

        private void bindGame(Game game) {
            mGame = game;
            mGameName.setText(mGame.getName());
            switch(game.GetGameSize()) {
                case 0:
                    mNumberPlayers.setText(R.string.zero_five);
                    break;
                case 1:
                    mNumberPlayers.setText(R.string.one_five);
                    break;
                case 2:
                    mNumberPlayers.setText(R.string.two_five);
                    break;
                case 3:
                    mNumberPlayers.setText(R.string.three_five);
                    break;
                case 4:
                    mNumberPlayers.setText(R.string.four_five);
                    break;
                case 5:
                    mNumberPlayers.setText(R.string.five_five);
                    break;
            }
            if(game.isGameStarted()) {
                mStatus.setText(R.string.status_started);
            } else {
                mStatus.setText(R.string.status_waiting);
            }
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        private GameList mGames;

        private GameAdapter(GameList games) {
            mGames = games;
        }

        @Override
        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getGameListContext());
            View view = layoutInflater.inflate(R.layout.game_list_item, parent, false);
            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(GameHolder holder, int position) {
            Game game = mGames.GetGames().get(position);
            holder.bindGame(game);
        }

        @Override
        public int getItemCount() {
            if(mGames == null) {
                return 0;
            }
            return mGames.size();
        }
    }

}
