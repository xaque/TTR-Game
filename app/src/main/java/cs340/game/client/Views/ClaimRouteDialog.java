package cs340.game.client.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.Presenters.ClaimRoutePresenter;
import cs340.game.shared.Color;
import cs340.game.shared.models.Route;

public class ClaimRouteDialog extends DialogFragment {

    private ClaimRoutePresenter presenter;

    private RecyclerView routesRecyclerView;
    private ClaimRouteDialog.RouteAdapter routeAdapter;
    private EditText routeQuery;

    private int selectedId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        presenter = new ClaimRoutePresenter(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setMessage("Pick the route you want to claim.")
                .setTitle(R.string.claimRoute);

        builder.setPositiveButton(R.string.ok, null);

        builder.setView(R.layout.claim_route_dialog);

        final AlertDialog dialog = builder.create();

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // User clicked OK button
                        if(selectedId == -1){
                            dialog.dismiss();
                        } else {
                            presenter.claimRoute(selectedId);
                            //dialog.dismiss();
                        }

                    }
                });
            }
        });

        return dialog;
    }

    public void updateUI() {
        ArrayList<Route> routeList = presenter.getClaimableRoutes();
        routeAdapter = new ClaimRouteDialog.RouteAdapter(routeList, this);
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        routesRecyclerView.setAdapter(routeAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();

        routesRecyclerView = getDialog().findViewById(R.id.routes_reyclerview);

        updateUI();

        routeQuery = getDialog().findViewById(R.id.routeQuery);
        routeQuery.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                routeAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        selectedId = -1;
        updateUI();
    }

    public void onError(String message) {
        selectedId = -1;
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void routeClick(int route_position){
        selectedId = route_position;
        presenter.claimRoute(selectedId);
    }

    public void promptSelectColor(){
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Pick the color cards you want to use");
        final String[] colors = {"Red", "Black", "Blue", "Green", "Orange", "Pink", "White", "Yellow" };
        final Color[] types = presenter.getAvailableColors();
        b.setItems(colors, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.claimGrayRoute(types[which]);
                dialog.dismiss();
            }

        });

        b.show();
    }

    public void closeDialog(){
        getDialog().dismiss();
    }

    private class RouteHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout destinationListItem;
        private Route route;
        private TextView city1;
        private TextView city2;
        private TextView value;
        private Color color;

        private RouteHolder(View itemView, final ClaimRouteDialog context) {
            super(itemView);

            destinationListItem = itemView.findViewById(R.id.destination_item);
            city1 = itemView.findViewById(R.id.city1);
            city2 = itemView.findViewById(R.id.city2);
            value = itemView.findViewById(R.id.value);

            destinationListItem.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    context.routeClick(getAdapterPosition());
                }
            });



        }

        private void bindDestination(Route route) {
            this.route = route;
            city1.setText(route.getCity1().toString());
            city2.setText(route.getCity2().toString());

            String length = Integer.toString(route.getLength());
            length += " " + route.getColor().toString();

            value.setText(length);

        }
    }

    private class RouteAdapter extends RecyclerView.Adapter<ClaimRouteDialog.RouteHolder> {

        private ArrayList<Route> mRoutes;
        private ArrayList<Route> filteredRoutes;
        private ClaimRouteDialog context;

        private RouteAdapter(ArrayList<Route> routes, ClaimRouteDialog context) {
            mRoutes = routes;
            filteredRoutes = routes;
            this.context = context;
        }

        @NonNull
        @Override
        public ClaimRouteDialog.RouteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.destination_list_item, parent, false);
            return new ClaimRouteDialog.RouteHolder(view, context);
        }

        @Override
        public void onBindViewHolder(ClaimRouteDialog.RouteHolder holder, int position) {
            Route route = filteredRoutes.get(position);
            holder.bindDestination(route);
        }

        @Override
        public int getItemCount() {
            if(filteredRoutes == null) {
                return 0;
            }
            return filteredRoutes.size();
        }

        public Filter getFilter(){
            return new Filter(){
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if(charString.isEmpty()){
                        filteredRoutes = mRoutes;
                    } else {
                        ArrayList<Route> filteredList = new ArrayList<>();
                        for (Route route : mRoutes) {
                            //name match condition
                            if (route.getCity1().toString().toLowerCase().contains(charString.toLowerCase()) || route.getCity2().toString().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(route);
                            }
                        }

                        filteredRoutes = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredRoutes;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredRoutes = (ArrayList<Route>) filterResults.values;
                    presenter.setFilteredRoutes(filteredRoutes);
                    notifyDataSetChanged();
                }
            };
        }
    }


}
