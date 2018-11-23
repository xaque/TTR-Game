package cs340.game.client.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import cs340.game.R;
import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ClaimRoutePresenter;
import cs340.game.client.Presenters.DestinationsDialogPresenter;
import cs340.game.shared.Color;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Route;

public class ClaimRouteDialog extends DialogFragment {

    private ClaimRoutePresenter presenter;

    private RecyclerView routesRecyclerView;
    private ClaimRouteDialog.RouteAdapter routeAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        presenter = new ClaimRoutePresenter(this);
        //presenter.drawDestinationCards();

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setMessage("Pick the route you want to claim.")
                .setTitle(R.string.drawDestinations);

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
//                        if(isStartOfGame) {
                        dialog.dismiss();

                    }
                });
            }
        });

        return dialog;
    }

    public void updateUI() {
        ArrayList<Route> routeList = presenter.getClaimableRoutes();
        routeAdapter = new ClaimRouteDialog.RouteAdapter(routeList);
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        routesRecyclerView.setAdapter(routeAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();

        routesRecyclerView = getDialog().findViewById(R.id.routes_reyclerview);


    }

    public void onError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public int getResourceColor(Color color){
        switch(color){
            case RED:
                return R.id.red;
            case YELLOW:
                return R.id.yellow;
            case GREEN:
                return R.id.green;
            case BLUE:
                return R.id.blue;
            case BLACK:
                return R.id.black;
            case WHITE:
                return R.id.white;
            case PINK:
                return R.id.purple;
            case ORANGE:
                return R.id.orange;
            case WILD:
                return R.id.wild;
            default:
                return R.id.red;
        }
    }

    private class RouteHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout destinationListItem;
        private Route route;
        private TextView city1;
        private TextView city2;
        private TextView value;
        private Color color;

        private RouteHolder(View itemView) {
            super(itemView);

            destinationListItem = itemView.findViewById(R.id.destination_item);
            city1 = itemView.findViewById(R.id.city1);
            city2 = itemView.findViewById(R.id.city2);
            value = itemView.findViewById(R.id.value);

        }

        private void bindDestination(Route route) {
            this.route = route;
            city1.setText(route.getCity1().toString());
            city2.setText(route.getCity2().toString());
            value.setText(Integer.toString(route.getLength()));
            color = route.getColor();
            int resourceColor = getResourceColor(color);
            int textColor = ResourcesCompat.getColor(getResources(), resourceColor, null);
            value.setTextColor(textColor);

        }
    }

    private class RouteAdapter extends RecyclerView.Adapter<ClaimRouteDialog.RouteHolder> {

        private ArrayList<Route> mRoutes;

        private RouteAdapter(ArrayList<Route> routes) {
            mRoutes = routes;
        }

        @NonNull
        @Override
        public ClaimRouteDialog.RouteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.destination_list_item, parent, false);
            return new ClaimRouteDialog.RouteHolder(view);
        }

        @Override
        public void onBindViewHolder(ClaimRouteDialog.RouteHolder holder, int position) {
            Route route = mRoutes.get(position);
            holder.bindDestination(route);
        }

        @Override
        public int getItemCount() {
            if(mRoutes == null) {
                return 0;
            }
            return mRoutes.size();
        }
    }
}
