package cs340.game.client.Views;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cs340.game.R;
import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DestinationsPresenter;
import cs340.game.shared.models.DestinationCard;

public class DestinationsFragment extends Fragment {

    private DestinationsPresenter presenter;

    private RecyclerView destinationsRecyclerView;
    private DestinationAdapter destinationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dest_tab, container, false);
        destinationsRecyclerView = v.findViewById(R.id.destinations_reyclerview);

        presenter = new DestinationsPresenter(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        List<DestinationCard> destinationList = InGameFacade.getInstance().getCurrentPlayer().getDestinationCards();
        destinationAdapter = new DestinationAdapter(destinationList);
        destinationsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        destinationsRecyclerView.setAdapter(destinationAdapter);
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

        private List<DestinationCard> mDestinations;

        private DestinationAdapter(List<DestinationCard> destinations) {
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



