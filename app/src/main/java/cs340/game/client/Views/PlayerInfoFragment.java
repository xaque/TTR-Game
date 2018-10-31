package cs340.game.client.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.PlayerInfoPresenter;

public class PlayerInfoFragment extends DialogFragment {

    //PLAYER 1
    private TableRow player1_row;
    private TextView player1_name;
    private TextView player1_score;
    private TextView player1_trains;
    private TextView player1_cards;
    private TextView player1_dest;
    //PLAYER 2
    private TableRow player2_row;
    private TextView player2_name;
    private TextView player2_score;
    private TextView player2_trains;
    private TextView player2_cards;
    private TextView player2_dest;
    //PLAYER 3
    private TableRow player3_row;
    private TextView player3_name;
    private TextView player3_score;
    private TextView player3_trains;
    private TextView player3_cards;
    private TextView player3_dest;
    //PLAYER 4
    private TableRow player4_row;
    private TextView player4_name;
    private TextView player4_score;
    private TextView player4_trains;
    private TextView player4_cards;
    private TextView player4_dest;
    //PLAYER 5
    private TableRow player5_row;
    private TextView player5_name;
    private TextView player5_score;
    private TextView player5_trains;
    private TextView player5_cards;
    private TextView player5_dest;


    PlayerInfoPresenter presenter;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setTitle(R.string.playerInfo);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setView(R.layout.player_info_dialog);

        return builder.create();
    }

    @Override
    public void onStart(){
        super.onStart();

        //Initialize all the text views yay
        player1_row = getDialog().findViewById(R.id.player1_row);
        player1_name = getDialog().findViewById(R.id.player1_name);
        player1_score = getDialog().findViewById(R.id.player1_points);
        player1_trains = getDialog().findViewById(R.id.player1_trains);
        player1_cards = getDialog().findViewById(R.id.player1_cards);
        player1_dest = getDialog().findViewById(R.id.player1_dest);

        player2_row = getDialog().findViewById(R.id.player2_row);
        player2_name = getDialog().findViewById(R.id.player2_name);
        player2_score = getDialog().findViewById(R.id.player2_points);
        player2_trains = getDialog().findViewById(R.id.player2_trains);
        player2_cards = getDialog().findViewById(R.id.player2_cards);
        player2_dest = getDialog().findViewById(R.id.player2_dest);

        player3_row = getDialog().findViewById(R.id.player3_row);
        player3_name = getDialog().findViewById(R.id.player3_name);
        player3_score = getDialog().findViewById(R.id.player3_points);
        player3_trains = getDialog().findViewById(R.id.player3_trains);
        player3_cards = getDialog().findViewById(R.id.player3_cards);
        player3_dest = getDialog().findViewById(R.id.player3_dest);

        player4_row = getDialog().findViewById(R.id.player4_row);
        player4_name = getDialog().findViewById(R.id.player4_name);
        player4_score = getDialog().findViewById(R.id.player4_points);
        player4_trains = getDialog().findViewById(R.id.player4_trains);
        player4_cards = getDialog().findViewById(R.id.player4_cards);
        player4_dest = getDialog().findViewById(R.id.player4_dest);

        player5_row = getDialog().findViewById(R.id.player5_row);
        player5_name = getDialog().findViewById(R.id.player5_name);
        player5_score = getDialog().findViewById(R.id.player5_points);
        player5_trains = getDialog().findViewById(R.id.player5_trains);
        player5_cards = getDialog().findViewById(R.id.player5_cards);
        player5_dest = getDialog().findViewById(R.id.player5_dest);

        presenter = new PlayerInfoPresenter(this);

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

    public void setPlayer1(String name, String points, String trains, String cards, String dest){
        player1_name.setText(name);
        player1_score.setText(points);
        player1_trains.setText(trains);
        player1_cards.setText(cards);
        player1_dest.setText(dest);
    }

    public void setPlayer2(String name, String points, String trains, String cards, String dest){
        player2_name.setText(name);
        player2_score.setText(points);
        player2_trains.setText(trains);
        player2_cards.setText(cards);
        player2_dest.setText(dest);
    }

    public void setPlayer3(String name, String points, String trains, String cards, String dest){
        player3_name.setText(name);
        player3_score.setText(points);
        player3_trains.setText(trains);
        player3_cards.setText(cards);
        player3_dest.setText(dest);
    }

    public void setPlayer4(String name, String points, String trains, String cards, String dest){
        player4_name.setText(name);
        player4_score.setText(points);
        player4_trains.setText(trains);
        player4_cards.setText(cards);
        player4_dest.setText(dest);
    }

    public void setPlayer5(String name, String points, String trains, String cards, String dest){
        player5_name.setText(name);
        player5_score.setText(points);
        player5_trains.setText(trains);
        player5_cards.setText(cards);
        player5_dest.setText(dest);
    }




}
