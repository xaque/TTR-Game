package cs340.game.client.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cs340.game.R;
import cs340.game.client.Presenters.CardsPresenter;

public class CardsFragment extends Fragment {

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

}
