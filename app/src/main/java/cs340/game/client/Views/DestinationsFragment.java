package cs340.game.client.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.DestinationsTabPresenter;
import cs340.game.client.ViewInterface.IView;
import cs340.game.shared.models.DestinationCard;

public class DestinationsFragment extends Fragment implements IView {

    private DestinationsTabPresenter presenter;

    private RecyclerView destinationsRecyclerView;
    private DestinationAdapter destinationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dest_tab, container, false);
        destinationsRecyclerView = v.findViewById(R.id.destinations_reyclerview);

        presenter = new DestinationsTabPresenter(this);
        return v;
    }

    @Override
    public void setUp(Object data) {

    }

    @Override
    public void update(Object obj) {
        if(obj instanceof ArrayList) {
            final ArrayList<DestinationCard> destinationList = (ArrayList<DestinationCard>) obj;
            final Context context = this.getContext();
            Objects.requireNonNull(this.getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    destinationsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    destinationAdapter = new DestinationAdapter(destinationList);
                    destinationsRecyclerView.setAdapter(destinationAdapter);
                }
            });
        }
    }

    @Override
    public void onError(String message) {

    }

    private class DestinationHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout destinationListItem;
        private DestinationCard destination;
        private TextView city1;
        private TextView city2;
        private TextView value;

        private String message;

        private DestinationHolder(View itemView) {
            super(itemView);

            destinationListItem = itemView.findViewById(R.id.destination_item);
            city1 = itemView.findViewById(R.id.city1);
            city2 = itemView.findViewById(R.id.city2);
            value = itemView.findViewById(R.id.value);
        }

        private void bindDestination(DestinationCard dest) {
            this.destination = dest;
            city1.setText(dest.getCity1().toString());
            city2.setText(dest.getCity2().toString());
            value.setText(Integer.toString(dest.getPointValue()));
        }
    }

    private class DestinationAdapter extends RecyclerView.Adapter<DestinationHolder> {

        private ArrayList<DestinationCard> mDestinations;

        private DestinationAdapter(ArrayList<DestinationCard> destinations) {
            mDestinations = destinations;
        }

        @Override
        public DestinationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.destination_list_item, parent, false);
            return new DestinationHolder(view);
        }

        @Override
        public void onBindViewHolder(DestinationHolder holder, int position) {
            DestinationCard dest = mDestinations.get(position);
            holder.bindDestination(dest);
        }

        @Override
        public int getItemCount() {
            if(mDestinations == null) {
                return 0;
            }
            return mDestinations.size();
        }
    }
}



