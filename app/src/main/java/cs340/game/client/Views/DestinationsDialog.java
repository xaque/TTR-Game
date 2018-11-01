package cs340.game.client.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.DestinationsDialogPresenter;
import cs340.game.shared.models.DestinationCard;

public class DestinationsDialog extends DialogFragment {

    private ArrayList<DestinationCard> selectedDestinationCards;
    private ArrayList<DestinationCard> destinationCards;

    private DestinationCard destinationCard1;
    private DestinationCard destinationCard2;
    private DestinationCard destinationCard3;


    private boolean isStartOfGame;

    private DestinationsDialogPresenter presenter;
    private ConstraintLayout destCard1;
    private TextView destText1;
    private ConstraintLayout destCard2;
    private TextView destText2;
    private ConstraintLayout destCard3;
    private TextView destText3;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        presenter = new DestinationsDialogPresenter(this);
        //presenter.drawDestinationCards();

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        selectedDestinationCards = new ArrayList<>();
        isStartOfGame = false;
        if(savedInstanceState != null && savedInstanceState.containsKey("startOfGame")) {
            isStartOfGame = (boolean)savedInstanceState.get("startOfGame");
        }

        builder.setMessage("Pick at least 2 Destination Cards.")
                .setTitle(R.string.drawDestinations);

        builder.setPositiveButton(R.string.ok, null);

        builder.setView(R.layout.dest_dialog);

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // User clicked OK button
//                        if(isStartOfGame) {
                            if(selectedDestinationCards.size() >= 2) {
                                submitDestinationCardSelection();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "You must select at least 2 destination cards to keep at the beginning of the game.", Toast.LENGTH_LONG).show();
                            }
//                        } else {
//                            if (selectedDestinationCards.size() >= 1) {
//                                submitDestinationCardSelection();
//                                dialog.dismiss();
//                            } else {
//                                Toast.makeText(getActivity(), "You must select at least 1 destination card to keep.", Toast.LENGTH_LONG).show();
//                            }
//                        }
                    }
                });
            }
        });

        return dialog;
    }

    public void updateUI() {

    }

    public void submitDestinationCardSelection() {
      //  presenter.acceptDestinationCards(selectedDestinationCards);
        presenter.discardDestinationCards(destinationCards);
    }

    @Override
    public void onStart(){
        super.onStart();

        destinationCards = presenter.getDestinationCards_StartOfGame();
        destinationCard1 = destinationCards.get(0);
        destinationCard2 = destinationCards.get(1);
        destinationCard3 = destinationCards.get(2);

        destCard1 = getDialog().findViewById(R.id.dest_card_1);
        destCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDestinationCards.contains(destinationCard1)) {
                    v.setBackgroundColor(getResources().getColor(R.color.lightgray));
                    destinationCards.add(destinationCard1);
                    selectedDestinationCards.remove(destinationCard1);
                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.darkgray));
                    selectedDestinationCards.add(destinationCard1);
                    destinationCards.remove(destinationCard1);
                }
            }
        });
        destText1 = getDialog().findViewById(R.id.dest_text_1);
        destText1.setText(destinationCard1.getCardString());

        destCard2 = getDialog().findViewById(R.id.dest_card_2);
        destCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDestinationCards.contains(destinationCard2)) {
                    v.setBackgroundColor(getResources().getColor(R.color.lightgray));
                    destinationCards.add(destinationCard2);
                    selectedDestinationCards.remove(destinationCard2);
                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.darkgray));
                    selectedDestinationCards.add(destinationCard2);
                    destinationCards.remove(destinationCard2);
                }
            }
        });
        destText2 = getDialog().findViewById(R.id.dest_text_2);
        destText2.setText(destinationCard2.getCardString());

        destCard3 = getDialog().findViewById(R.id.dest_card_3);
        destCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDestinationCards.contains(destinationCard3)) {
                    v.setBackgroundColor(getResources().getColor(R.color.lightgray));
                    destinationCards.add(destinationCard3);
                    selectedDestinationCards.remove(destinationCard3);
                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.darkgray));
                    selectedDestinationCards.add(destinationCard3);
                    destinationCards.remove(destinationCard3);
                }
            }
        });
        destText3 = getDialog().findViewById(R.id.dest_text_3);
        destText3.setText(destinationCard3.getCardString());


    }

    public void onError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
