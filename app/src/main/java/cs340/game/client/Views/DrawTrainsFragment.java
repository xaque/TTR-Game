package cs340.game.client.Views;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

import cs340.game.R;

public class DrawTrainsFragment extends DialogFragment implements View.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setMessage(R.string.welcome)
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

        ImageView card1 = getDialog().findViewById(R.id.card1);
        ImageView card2 = getDialog().findViewById(R.id.card2);
        ImageView card3 = getDialog().findViewById(R.id.card3);
        ImageView card4 = getDialog().findViewById(R.id.card4);
        ImageView card5 = getDialog().findViewById(R.id.card5);
        ImageView deck = getDialog().findViewById(R.id.deck);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        deck.setOnClickListener(this);

    }

    public void unselectCards(){
        ImageView card1 = getDialog().findViewById(R.id.card1);
        card1.setColorFilter(Color.argb(0,0,0,0));
        ImageView card2 = getDialog().findViewById(R.id.card2);
        card2.setColorFilter(Color.argb(0,0,0,0));
        ImageView card3 = getDialog().findViewById(R.id.card3);
        card3.setColorFilter(Color.argb(0,0,0,0));
        ImageView card4 = getDialog().findViewById(R.id.card4);
        card4.setColorFilter(Color.argb(0,0,0,0));
        ImageView card5 = getDialog().findViewById(R.id.card5);
        card5.setColorFilter(Color.argb(0,0,0,0));
        ImageView deck = getDialog().findViewById(R.id.deck);
        deck.setColorFilter(Color.argb(0,0,0,0));
    }

    public void selectCard(int id){
        ImageView card = getDialog().findViewById(id);
        card.setColorFilter(Color.argb(90, 0, 0, 0));
        //I'll need a way to tag which card has been selected. But that can wait
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.deck:

            default:
                unselectCards();
                selectCard(view.getId());
                break;
        }



    }
}
