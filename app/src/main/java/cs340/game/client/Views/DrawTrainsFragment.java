package cs340.game.client.Views;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import cs340.game.R;
import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.shared.Color;

public class DrawTrainsFragment extends DialogFragment implements View.OnClickListener {

    private ImageView card1;
    private ImageView card2;
    private ImageView card3;
    private ImageView card4;
    private ImageView card5;
    private ImageView deck;
    private TextView cardsLeft;

    private DrawTrainsPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setMessage(R.string.draw_train)
                .setTitle(R.string.drawTrains);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setView(R.layout.draw_trains_dialog);

        return builder.create();
    }

    @Override
    public void onStart(){
        super.onStart();

        card1 = getDialog().findViewById(R.id.card1);
        card2 = getDialog().findViewById(R.id.card2);
        card3 = getDialog().findViewById(R.id.card3);
        card4 = getDialog().findViewById(R.id.card4);
        card5 = getDialog().findViewById(R.id.card5);
        deck = getDialog().findViewById(R.id.deck);
        cardsLeft = getDialog().findViewById(R.id.cards_in_deck);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        deck.setOnClickListener(this);

        presenter = new DrawTrainsPresenter(this);

    }

    public void setCard(int cardNumber, Color color) {
        int imageID = getImageID(color);

        switch(cardNumber) {
            case 1:
                card1.setImageResource(imageID);
                break;
            case 2:
                card2.setImageResource(imageID);
                break;
            case 3:
                card3.setImageResource(imageID);
                break;
            case 4:
                card4.setImageResource(imageID);
                break;
            case 5:
                card5.setImageResource(imageID);
                break;
        }
    }

    public int getImageID(Color color) {
        switch(color) {
            case YELLOW:
                return R.mipmap.yellow;
            case ORANGE:
                return R.mipmap.orange;
            case WHITE:
                return R.mipmap.white;
            case GREEN:
                return R.mipmap.green;
            case BLACK:
                return R.mipmap.black;
            case WILD:
                return R.mipmap.wild;
            case PINK:
                return R.mipmap.purple;
            case BLUE:
                return R.mipmap.blue;
            case RED:
                return R.mipmap.red;
            default:
                return R.mipmap.red;
        }
    }

    public void unselectCards(){
        ImageView card1 = getDialog().findViewById(R.id.card1);
        card1.setColorFilter(android.graphics.Color.argb(0,0,0,0));
        ImageView card2 = getDialog().findViewById(R.id.card2);
        card2.setColorFilter(android.graphics.Color.argb(0,0,0,0));
        ImageView card3 = getDialog().findViewById(R.id.card3);
        card3.setColorFilter(android.graphics.Color.argb(0,0,0,0));
        ImageView card4 = getDialog().findViewById(R.id.card4);
        card4.setColorFilter(android.graphics.Color.argb(0,0,0,0));
        ImageView card5 = getDialog().findViewById(R.id.card5);
        card5.setColorFilter(android.graphics.Color.argb(0,0,0,0));
        ImageView deck = getDialog().findViewById(R.id.deck);
        deck.setColorFilter(android.graphics.Color.argb(0,0,0,0));
    }

    public void selectCard(int id){
        ImageView card = getDialog().findViewById(id);
        card.setColorFilter(android.graphics.Color.argb(90, 0, 0, 0));
        //I'll need a way to tag which card has been selected. But that can wait
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.deck:
                //Testing
                InGameFacade.getInstance().drawTrainCardFromDeck();
            default:
                unselectCards();
                selectCard(view.getId());
                break;
        }



    }


}
