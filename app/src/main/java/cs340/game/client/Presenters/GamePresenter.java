package cs340.game.client.Presenters;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DestinationsDialog;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.Color;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;

public class GamePresenter implements Observer {

    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameActivity view;
    private GameState gameState;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private int turn;

    private int[] calgary_winnipeg = {223, 34, 257, 22, 294, 19, 330, 19, 366, 26, 400, 36};
    private int[] vancouver_calgary = {96, 53, 131, 50, 168, 44};
    private int[] seattle_calgary = {95, 113, 129, 111, 163, 97, 187, 70};
    private int[] seattle_helena = {91, 133, 126, 140, 162, 148, 197, 156, 232, 164, 267, 171};
    private int[] calgary_helena = {216, 68, 239, 94, 262, 121, 285, 149};
    private int[] helena_winnipeg = {322, 149, 346, 123, 372, 97, 398, 73};
    private int[] winnipeg_sault = {457, 56,493, 63, 528, 70, 564, 78, 598, 84, 633, 91};
    private int[] winnipeg_duluth = {440, 74, 465, 99, 491, 124, 518, 149};
    private int[] helena_duluth = {327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170};
    private int[] helena_slc = {243, 262, 262, 232, 280, 201};
    private int[] helena_denver = {310, 201, 324, 234, 336, 266, 350, 298};
    private int[] helena_omaha = {335, 193, 369, 207, 401, 221, 435, 235, 468, 248};
    private int[] slc_denver1 = {254, 296, 289, 303, 324, 310};
    private int[] slc_denver2 = {252, 308, 288, 315, 322, 322};
    private int[] duluth_sault = {567, 146, 600, 131, 634, 117};
    private int[] duluth_omaha1 = {517, 198, 503, 230};
    private int[] duluth_omaha2 = {529, 202, 516, 235};
    private int[] omaha_kc1 = {511, 290};
    private int[] omaha_kc2 = {523, 285};
    private int[] denver_kc1 = {390, 330, 425, 330, 461, 325, 496, 314};
    private int[] denver_kc2 = {390, 344, 425, 343, 463, 339, 496, 328};
    private int[] denver_ok = {373, 359, 403, 380, 438, 390, 474, 394};
    private int[] kc_ok1 = {518, 336, 508, 371};
    private int[] kc_ok2 = {529, 340, 519, 376};
    private int[] phoenix_denver = {235, 448, 251, 416, 269, 387, 294, 360, 326, 341};
    private int[] ok_santafe = {378, 419, 413, 415, 449, 410};
    private int[] elpaso_ok = {370, 449, 404, 486, 436, 468, 463, 445, 487, 418};
    private int[] phoenix_santafe = {256, 459, 290, 443, 321, 429};
    private int[] phoenix_elpaso = {249, 482, 285, 493, 319, 502};
    private int[] santafe_elpaso = {346, 448, 344, 484};
    private int[] elpaso_houston = {364, 528, 398, 541, 433, 550, 469, 553, 506, 550, 540, 541};
    private int[] elpaso_dallas = {389, 512, 424, 506, 460, 500, 496, 495};
    private int[] ok_dallas1 = {510, 425, 515, 460};
    private int[] ok_dallas2 = {523, 424, 527, 458};
    private int[] dallas_houston1 = {538, 510};
    private int[] dallas_houston2 = {548, 500};
    private int[] houston_neworleans = {594, 522, 630, 516};
    private int[] dallas_littlerock = {552, 453, 573, 424};
    private int[] ok_littlerock = {533, 399, 569, 398};
    private int[] kc_stlouis1 = {550, 304, 587, 303};
    private int[] kc_stlouis2 = {551, 317, 587, 316};
    private int[] omaha_chicago = {530, 250, 558, 231, 594, 223, 628, 228};
    private int[] duluth_chicago = {556, 186, 589, 201, 624, 212};
    private int[] duluth_toronto = {561, 164, 597, 158, 632, 152, 669, 146, 704, 140, 740, 134};
    private int[] sault_montreal = {682, 83, 713, 62, 746, 48, 781, 38, 817, 34};
    private int[] neworleans_littlerock = {642, 489, 626, 457, 609, 427};
    private int[] littlerock_saintlouis = {599, 374, 608, 339};
    private int[] saintlouis_chicagogreen = {614, 283, 632, 253};
    private int[] saintlouis_chicagowhite = {625, 290, 645, 259};
    private int[] chicago_toronto = {661, 202, 689, 177, 722, 162, 754, 146};
    private int[] neworleans_miami = {696, 508, 729, 494, 765, 489, 800, 498, 830, 519, 854, 543};
    private int[] neworleans_atlantaorange = {678, 495, 694, 464, 716, 435, 740, 409};
    private int[] neworleans_atlantayellow = {668, 486, 685, 454, 707, 424, 732, 399};
    private int[] littlerock_nashville = {622, 399, 657, 390, 687, 370};
    private int[] saintlouis_nashville = {637, 335, 671, 346};
    private int[] saintlouis_pittsburgh = {637, 307, 668, 290, 699, 271, 730, 253, 761, 236};
    private int[] chicago_pittsburghblack = {681, 211, 717, 204, 753, 201};
    private int[] chicago_pittsburghorange = {685, 224, 721, 217, 757, 215};
    private int[] atlanta_miami = {861, 528, 839, 501, 816, 472, 794, 444, 771, 417};
    private int[] atlanta_chareston = {787, 398, 823, 400};
    private int[] miami_charleston = {877, 524, 862, 490, 855, 456, 851, 419};
    private int[] atlanta_raleigh1 = {779, 376, 807, 351};
    private int[] atlanta_raleigh2 = {772, 364, 799, 341};
    private int[] raleigh_charleston = {842, 345, 859, 370};
    private int[] nashville_atlanta = {727, 368};
    private int[] nashville_raleigh = {726, 335, 760, 321, 797, 318};
    private int[] nashville_pittsburgh = {701, 329, 721, 299, 748, 274, 775, 250};
    private int[] pittsburgh_raleigh = {800, 255, 808, 289};
    private int[] pittsburgh_washington = {818, 234, 850, 250};
    private int[] washington_raleigh1 = {859, 278, 835, 306};
    private int[] washington_raleigh2 = {868, 286, 845, 313};
    private int[] pittsburgh_nywhite = {810, 193, 839, 175};
    private int[] pittsburgh_nygeen = {816, 203, 845, 185};
    private int[] ny_washingtonorange = {871, 199, 874, 234};
    private int[] ny_washingtonblack = {883, 198, 885, 235};
    private int[] pittsburgh_toronto = {781, 186, 778, 149};
    private int[] toronto_montreal = {773, 98, 796, 70, 827, 50};
    private int[] montreal_boston1 = {880, 51, 907, 74};
    private int[] montreal_boston2 = {871, 62, 899, 84};
    private int[] boston_nyyellow = {905, 118, 886, 149};
    private int[] boston_nyred = {897, 154, 915, 125};
    private int[] portland_slc = {70, 171, 106, 181, 138, 197, 166, 218, 193, 243, 213, 272};
    private int[] sanfran_slcorange = {60, 345, 93, 335, 127, 323, 160, 312, 194, 301};
    private int[] sanfran_slcwhite = {64, 357, 98, 346, 130, 335, 164, 324, 198, 312};
    private int[] sanfran_lapink = {46, 385, 63, 416, 86, 445};
    private int[] sanfran_layellow = {35, 393, 53, 424, 76, 451};
    private int[] la_lasvegas = {115, 434, 142, 412};
    private int[] la__phoenix = {130, 458, 166, 456, 202, 461};
    private int[] la_elpaso = {130, 485, 162, 504, 196, 516, 231, 522, 267, 524, 303, 518};
    private int[] lasvegas_slc = {193, 392, 214, 362, 223, 327};
    private int[] vancouver_seattle1 = {57, 88};
    private int[] vancouver_seattle2 = {71, 87};
    private int[] seattle_portland1 = {45, 137};
    private int[] seattle_portland2 = {57, 141};
    private int[] portland_sanfrancisco1 = {23, 190, 18, 227, 13, 263, 15, 297, 20, 333};
    private int[] portland_sanfrancisco2 = {35, 190, 28, 227, 23, 263, 25, 297, 30, 333};
    private int[] denver_santafe = {350, 359, 348, 394};
    private int[] sault_toronto = {693, 109, 729, 116};
    private int[] denver_omaha = {376, 309, 407, 289, 441, 277, 476, 268};
    private int[] montreal_ny = {850, 70, 856, 106, 862, 140};

    public GamePresenter(GameActivity view) {
        this.view = view;
        gameState = gameFacade.getCurrentGame();
        currentPlayer = gameFacade.getCurrentPlayer();
        players = gameState.getPlayers();

        view.setPlayerName(currentPlayer.getName());
        setPlayers(players);

        // This will set the marker to the right position
        String currentTurn = gameState.getCurrentTurnPlayer();
        for(int i = 0; i < players.size(); i++){
            String name = players.get(i).getName();
            if(name.equals(currentTurn)){
                turn = i + 1;
            }
        }
        //turn = 1;


        gameFacade.addObserver(this);
        gameFacade.addObserverToGameState(this);
        gameFacade.addObserverToCurrentPlayer(this);
        //currentPlayer.addObserver(this);

        Bundle bundle = new Bundle();
        bundle.putBoolean("isStartOfGame", true);
        DestinationsDialog destinationsDialog = new DestinationsDialog();
        destinationsDialog.setArguments(bundle);
        destinationsDialog.show(view.getActivityContext().getSupportFragmentManager(),"Game Activity");
    }


    public void placeRoutes() {
        List<int[]> routes = new ArrayList<int[]>();
        routes.add(atlanta_chareston);
        routes.add(atlanta_miami);
        routes.add(atlanta_raleigh1);
        routes.add(atlanta_raleigh2);
        routes.add(neworleans_atlantaorange);
        routes.add(nashville_atlanta);
        routes.add(neworleans_atlantayellow);
        routes.add(vancouver_calgary);
        routes.add(vancouver_seattle1);
        routes.add(vancouver_seattle2);
        routes.add(calgary_helena);
        routes.add(calgary_winnipeg);
        routes.add(vancouver_calgary);
        routes.add(seattle_calgary);
        routes.add(seattle_helena);
        routes.add(seattle_portland1);
        routes.add(seattle_portland2);
        routes.add(slc_denver1);
        routes.add(slc_denver2);
        routes.add(helena_slc);
        routes.add(lasvegas_slc);
        routes.add(portland_slc);
        routes.add(sanfran_slcorange);
        routes.add(sanfran_slcwhite);
        routes.add(duluth_chicago);
        routes.add(duluth_omaha1);
        routes.add(duluth_omaha2);
        routes.add(duluth_sault);
        routes.add(duluth_toronto);
        routes.add(helena_duluth);
        routes.add(winnipeg_duluth);
        routes.add(winnipeg_sault);
        routes.add(calgary_winnipeg);
        routes.add(helena_winnipeg);
        routes.add(helena_denver);
        routes.add(helena_omaha);
        routes.add(kc_ok1);
        routes.add(kc_ok2);
        routes.add(kc_stlouis1);
        routes.add(kc_stlouis2);
        routes.add(denver_kc1);
        routes.add(denver_kc2);
        routes.add(omaha_kc1);
        routes.add(omaha_kc2);
        routes.add(denver_ok);
        routes.add(phoenix_denver);
        routes.add(elpaso_ok);
        routes.add(ok_santafe);
        routes.add(phoenix_santafe);
        routes.add(phoenix_elpaso);
        routes.add(santafe_elpaso);
        routes.add(elpaso_dallas);
        routes.add(elpaso_houston);
        routes.add(ok_dallas1);
        routes.add(ok_dallas2);
        routes.add(dallas_houston1);
        routes.add(dallas_houston2);
        routes.add(houston_neworleans);
        routes.add(neworleans_atlantayellow);
        routes.add(neworleans_atlantaorange);
        routes.add(neworleans_littlerock);
        routes.add(dallas_littlerock);
        routes.add(ok_littlerock);
        routes.add(omaha_chicago);
        routes.add(sault_montreal);
        routes.add(saintlouis_chicagogreen);
        routes.add(saintlouis_chicagowhite);
        routes.add(littlerock_saintlouis);
        routes.add(chicago_toronto);
        routes.add(miami_charleston);
        routes.add(neworleans_miami);
        routes.add(littlerock_nashville);
        routes.add(nashville_atlanta);
        routes.add(nashville_pittsburgh);
        routes.add(nashville_raleigh);
        routes.add(saintlouis_nashville);
        routes.add(raleigh_charleston);
        routes.add(atlanta_raleigh1);
        routes.add(atlanta_raleigh2);
        routes.add(chicago_pittsburghblack);
        routes.add(chicago_pittsburghorange);
        routes.add(saintlouis_pittsburgh);
        routes.add(pittsburgh_nygeen);
        routes.add(pittsburgh_nywhite);
        routes.add(pittsburgh_raleigh);
        routes.add(pittsburgh_washington);
        routes.add(washington_raleigh1);
        routes.add(washington_raleigh2);
        routes.add(ny_washingtonblack);
        routes.add(ny_washingtonorange);
        routes.add(boston_nyred);
        routes.add(boston_nyyellow);
        routes.add(pittsburgh_toronto);
        routes.add(toronto_montreal);
        routes.add(montreal_boston1);
        routes.add(montreal_boston2);
        routes.add(sanfran_lapink);
        routes.add(sanfran_layellow);
        routes.add(la_lasvegas);
        routes.add(la_elpaso);
        routes.add(la__phoenix);
        routes.add(portland_sanfrancisco1);
        routes.add(portland_sanfrancisco2);
        routes.add(denver_santafe);
        routes.add(denver_omaha);
        routes.add(sault_toronto);
        routes.add(montreal_ny);

        for(int[] route : routes) {
            view.placeRoute(Color.YELLOW, route);
        }


    }




    //************UPDATE UI FUNCTIONS**********************

    public void setPlayers(ArrayList<Player> players) {
        for(int i=1; i <= 5; i++) {
            if(i <= players.size()){
                setPlayer(i, players.get(i-1));
            }
            else {
                view.hidePlayer(i);
            }
        }
    }

    public void setPlayer(int playerNumber, Player player){
        String name = player.getName();
        //String color = player.getColor;

        switch(playerNumber) {
            case 1:
                view.setPlayer1(name);
                break;
            case 2:
                view.setPlayer2(name);
                break;
            case 3:
                view.setPlayer3(name);
                break;
            case 4:
                view.setPlayer4(name);
                break;
            case 5:
                view.setPlayer5(name);
                break;
        }
    }

    public void updatePoints(int points){
        view.updatePoints(Integer.toString(points));
    }

    public void updateTrains(int trainsLeft) {
        view.updateTrainsLeft(Integer.toString(trainsLeft));
    }

    public void updateDestinationsLeft(int destinations){
        view.updateDestinationsLeft(Integer.toString(destinations));
    }

    public void nextTurn(){
        if( turn < players.size()) {
            turn++;
        } else {
            turn = 1;
        }
        view.changeTurn(turn);
    }

    public void leaveGame() {
        //facade.LeaveGame()
        onLeaveGameResponse(true);
    }

    private void onLeaveGameResponse(boolean isLeaveSuccess) {
        view.onLeaveGameResponse(isLeaveSuccess);
    }

    public void onGameEnd(){
        view.onGameEnd();
    }

    public boolean isPlayersTurn(){
        return gameFacade.isCurrentPlayersTurn();
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        view.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                updatePoints(currentPlayer.getPoints());
                updateTrains(currentPlayer.getTrainTokens());
                updateDestinationsLeft(gameState.getDestinationTicketDeckSize());

//                if(gameState.newRouteExists()){
//                    view.placeRoute(gameState.getNewRouteOwner(), gameState.getNewRoute());
//                }
                for(int i = 0; i < gameState.getRoutes().size(); i++){
                    Route route = gameState.getRoutes().get(i);
                    if(route.isClaimed()){
                        String playerName = route.getPlayerOnRoute();
                        view.placeRoute(getPlayerColor(playerName), route.getCoordinates());
                    }
                }

                if(gameState.hasTurnChanged()){
                    nextTurn();
                }

                if(gameState.isGameOver()){
                    onGameEnd();
                    System.out.println("GAME OVER");
                }
            }
        });
    }

    private Color getPlayerColor(String playerName){
        ArrayList<Player> players = gameState.getPlayers();
        int num = players.indexOf(playerName);
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLACK, Color.GREEN, Color.BLUE};
        return colors[num];
    }
}
