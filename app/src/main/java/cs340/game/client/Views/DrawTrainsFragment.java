package cs340.game.client.Views;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
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
    private Activity activity;

    private int selectedId;

    private DrawTrainsPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setMessage(R.string.draw_train)
                .setTitle(R.string.drawTrains);

        builder.setPositiveButton(R.string.ok, null);

        builder.setNegativeButton(R.string.cancel, null);

        builder.setView(R.layout.draw_trains_dialog);

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button ok_button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                ok_button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // User clicked OK button
                        presenter.ok_clicked(selectedId);
                    }
                });

                Button back_button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                back_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.back_clicked();
                    }
                });
            }
        });

        return dialog;
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

        selectedId = -1;

        presenter = new DrawTrainsPresenter(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public Activity getTheActivity() {
        return this.activity;
    }

    public void closeDialog(){
        getDialog().dismiss();
    }

    public void updateCardsLeft(String cards) {
        cardsLeft.setText(cards);
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
        selectedId = -1;
    }

    public void selectCard(int id){
        ImageView card = getDialog().findViewById(id);
        card.setColorFilter(android.graphics.Color.argb(90, 0, 0, 0));
    }

    @Override
    public void onClick(View view) {
        unselectCards();
        
        switch(view.getId()){
            case R.id.card1:
                selectedId = 0;
                break;
            case R.id.card2:
                selectedId = 1;
                break;
            case R.id.card3:
                selectedId = 2;
                break;
            case R.id.card4:
                selectedId = 3;
                break;
            case R.id.card5:
                selectedId = 4;
                break;
            case R.id.deck:
                selectedId = 5;
                break;
        }

        selectCard(view.getId());



    }

    public void onError(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
