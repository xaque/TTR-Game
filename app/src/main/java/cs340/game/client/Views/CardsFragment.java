package cs340.game.client.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.CardsPresenter;
import cs340.game.client.ViewInterface.IView;

public class CardsFragment extends Fragment implements IView {

    private CardsPresenter presenter;

    private TextView red_count;
    private TextView blue_count;
    private TextView yellow_count;
    private TextView black_count;
    private TextView green_count;
    private TextView white_count;
    private TextView purple_count;
    private TextView orange_count;
    private TextView wild_count;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cards_tab, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        FragmentActivity activity = getActivity();
        assert activity != null;
        red_count = activity.findViewById(R.id.red_count);
        blue_count = activity.findViewById(R.id.blue_count);
        yellow_count = activity.findViewById(R.id.yellow_count);
        black_count = activity.findViewById(R.id.black_count);
        green_count = activity.findViewById(R.id.green_count);
        white_count = activity.findViewById(R.id.white_count);
        orange_count = activity.findViewById(R.id.orange_count);
        purple_count = activity.findViewById(R.id.purple_count);
        wild_count = activity.findViewById(R.id.wild_count);

        presenter = new CardsPresenter(this);
    }

    public void setRed_count(String count) {
        this.red_count.setText(count);
    }

    public void setBlue_count(String count) {
        this.blue_count.setText(count);
    }

    public void setYellow_count(String count) {
        this.yellow_count.setText(count);
    }

    public void setBlack_count(String count) {
        this.black_count.setText(count);
    }

    public void setGreen_count(String count) {
        this.green_count.setText(count);
    }

    public void setWhite_count(String count) {
        this.white_count.setText(count);
    }

    public void setOrange_count(String count) {
        this.orange_count.setText(count);
    }

    public void setPurple_count(String count) {
        this.purple_count.setText(count);
    }

    public void setWild_count(String count) {
        this.wild_count.setText(count);
    }

    @Override
    public void update(Object data) {
        if(data instanceof Map) {
            final Map<String, String> hand = (Map<String, String>) data;
            Objects.requireNonNull(this.getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setRed_count(hand.get("red"));
                    setBlack_count(hand.get("black"));
                    setBlue_count(hand.get("blue"));
                    setGreen_count(hand.get("green"));
                    setOrange_count(hand.get("orange"));
                    setPurple_count(hand.get("purple"));
                    setWhite_count(hand.get("white"));
                    setWild_count(hand.get("wild"));
                    setYellow_count(hand.get("yellow"));
                }
            });
        }
    }

    @Override
    public void onError(String message) {

    }
}
