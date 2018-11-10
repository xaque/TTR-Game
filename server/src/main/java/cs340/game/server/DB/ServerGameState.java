package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;

public class ServerGameState {
    private GameState gameState;
    private DestinationCardDeck destinationCardDeck;
    private TrainCardDeck trainCardDeck;

    public ServerGameState(String name, ArrayList<User> users) {
        //Create Players from Users
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            String authToken = users.get(i).getAuthToken();
            Player player = new Player(username, authToken);
            players.add(player);

        }

        //Initialize GameState and decks
        this.gameState = new GameState(name, players);
        this.destinationCardDeck = new DestinationCardDeck();
        this.trainCardDeck = new TrainCardDeck();
        for(Player player : players) {
            ArrayList<DestinationCard> drawnDestinationCards = destinationCardDeck.drawCards();
            player.addDestinationCards(drawnDestinationCards);
            String actionMessage = player.getName() + " drew " + Integer.toString(drawnDestinationCards.size()) + " Destination cards.";
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);

            ArrayList<TrainCard> drawnTrainCards = trainCardDeck.drawStartingCards();
            for(TrainCard card : drawnTrainCards) {
                player.addTrainCard(card);
            }
            actionMessage = player.getName() + " drew " + Integer.toString(drawnTrainCards.size()) + " Train cards.";
            action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);

            // Initialize Routes
            createRoutes();
        }

        this.trainCardDeck.initializeFaceUpCards();
        updateDeckSizes();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Player> getPlayers() {
        return this.gameState.getPlayers();
    }

    public ArrayList<DestinationCard> drawDestinationCards(String username) {
        ArrayList<DestinationCard> drawnCards = this.destinationCardDeck.drawCards();
        ArrayList<Player> players = this.gameState.getPlayers();
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equals(username)) {
                players.get(i).addDestinationCards(drawnCards);
                break;
            }
        }
        return drawnCards;
    }

    public void returnDestinationCards(ArrayList<DestinationCard> cards, String username) {
        ArrayList<Player> players = this.gameState.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(username)) {
                players.get(i).removeDestinationCards(cards);
                break;
            }
        }
        this.destinationCardDeck.returnCards(cards);
    }

    public TrainCard drawTrainCardFromDeck(String username) {
        TrainCard drawnCard = this.trainCardDeck.drawCardFromDeck();
        ArrayList<Player> players = this.gameState.getPlayers();
        for(Player player: players) {
            if(player.getName().equals(username)) {
                player.addTrainCard(drawnCard);
                break;
            }
        }
        return drawnCard;
    }

    public TrainCard drawTrainCardFaceUp(int position, String username) {
        TrainCard drawnCard = this.trainCardDeck.drawFaceUpCard(position);
        ArrayList<Player> players = this.gameState.getPlayers();
        for(Player player: players) {
            if(player.getName().equals(username)) {
                player.addTrainCard(drawnCard);
                break;
            }
        }
        return drawnCard;
    }

    public void discardTrainCards(ArrayList<TrainCard> cardsToDiscard, String username) {
        ArrayList<Player> players = this.gameState.getPlayers();
        ArrayList<TrainCard> discardedCards = new ArrayList<>();
        try {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getName().equals(username)) {
                    for (int j = 0; j < cardsToDiscard.size(); j++) {
                        players.get(i).removeTrainCard(cardsToDiscard.get(j).getColor());
                        discardedCards.add(cardsToDiscard.get(j));
                    }
                    break;
                }
            }
            this.trainCardDeck.discardCards(discardedCards);
        }
        catch(Exception ex) {
            ServerException exception = new ServerException(ex.getMessage());
            return;
        }
    }

    public void addGameCommand(GameHistoryAction action) {
        this.gameState.addHistoryAction(action);
    }

    public void updateDeckSizes() {
        gameState.setDestinationTicketDeckSize(destinationCardDeck.getSize());
        gameState.setTrainCardDeckSize(trainCardDeck.getSize());
        gameState.setFaceUpCards(trainCardDeck.getFaceUpCards());
    }

    // TODO Change colors of routes
    private void createRoutes(){
        List<Route> routes = gameState.getRoutes();
        int[] calgary_winnipeg = {223, 34, 257, 22, 294, 19, 330, 19, 366, 26, 400, 36};
        routes.add(new Route(City.CALGARY, City.WINNIPEG, Color.BLACK, 6, calgary_winnipeg));
        int[] vancouver_calgary = {96, 53, 131, 50, 168, 44};
        routes.add(new Route(City.VANCOUVER, City.CALGARY, Color.BLACK, 3, vancouver_calgary));
        int[] seattle_calgary = {95, 113, 129, 111, 163, 97, 187, 70};
        routes.add(new Route(City.SEATTLE, City.CALGARY, Color.BLACK, 4, seattle_calgary));
        int[] seattle_helena = {91, 133, 126, 140, 162, 148, 197, 156, 232, 164, 267, 171};
        routes.add(new Route(City.SEATTLE, City.HELENA, Color.BLACK, 6, seattle_helena));
        int[] calgary_helena = {216, 68, 239, 94, 262, 121, 285, 149};
        routes.add(new Route(City.CALGARY, City.HELENA, Color.BLACK, 4, calgary_helena));
        int[] helena_winnipeg = {322, 149, 346, 123, 372, 97, 398, 73};
        routes.add(new Route(City.HELENA, City.WINNIPEG, Color.BLACK, 4, helena_winnipeg));
        int[] winnipeg_sault = {457, 56, 493, 63, 528, 70, 564, 78, 598, 84, 633, 91};
        routes.add(new Route(City.WINNIPEG, City.SAULT_ST_MARIE, Color.BLACK, 6, winnipeg_sault));
        int[] winnipeg_duluth = {440, 74, 465, 99, 491, 124, 518, 149};
        routes.add(new Route(City.WINNIPEG, City.DULUTH, Color.BLACK, 4, winnipeg_duluth));
        int[] helena_duluth = {327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170};
        routes.add(new Route(City.HELENA, City.DULUTH, Color.BLACK, 6, helena_duluth));
        int[] helena_slc = {243, 262, 262, 232, 280, 201};
        routes.add(new Route(City.HELENA, City.SALT_LAKE_CITY, Color.BLACK, 3, helena_slc));
        int[] helena_denver = {310, 201, 324, 234, 336, 266, 350, 298};
        routes.add(new Route(City.HELENA, City.DENVER, Color.BLACK, 4, helena_denver));
        int[] helena_omaha = {335, 193, 369, 207, 401, 221, 435, 235, 468, 248};
        routes.add(new Route(City.HELENA, City.OMAHA, Color.BLACK, 5, helena_omaha));
        int[] slc_denver1 = {254, 296, 289, 303, 324, 310};
        routes.add(new Route(City.SALT_LAKE_CITY, City.DENVER, Color.BLACK, 3, slc_denver1));
        int[] slc_denver2 = {252, 308, 288, 315, 322, 322};
        routes.add(new Route(City.SALT_LAKE_CITY, City.DENVER, Color.BLACK, 3, slc_denver2));
        int[] duluth_sault = {567, 146, 600, 131, 634, 117};
        routes.add(new Route(City.DULUTH, City.SAULT_ST_MARIE, Color.BLACK, 3, duluth_sault));
        int[] duluth_omaha1 = {517, 198, 503, 230};
        routes.add(new Route(City.DULUTH, City.OMAHA, Color.BLACK, 2, duluth_omaha1));
        int[] duluth_omaha2 = {529, 202, 516, 235};
        routes.add(new Route(City.DULUTH, City.OMAHA, Color.BLACK, 2, duluth_omaha2));
        int[] omaha_kc1 = {511, 290};
        routes.add(new Route(City.OMAHA, City.KANSAS_CITY, Color.BLACK, 1, omaha_kc1));
        int[] omaha_kc2 = {523, 285};
        routes.add(new Route(City.OMAHA, City.KANSAS_CITY, Color.BLACK, 1, omaha_kc2));
        int[] denver_kc1 = {390, 330, 425, 330, 461, 325, 496, 314};
        routes.add(new Route(City.DENVER, City.KANSAS_CITY, Color.BLACK, 4, denver_kc1));
        int[] denver_kc2 = {390, 344, 425, 343, 463, 339, 496, 328};
        routes.add(new Route(City.DENVER, City.KANSAS_CITY, Color.BLACK, 4, denver_kc2));
        int[] denver_ok = {373, 359, 403, 380, 438, 390, 474, 394};
        routes.add(new Route(City.DENVER, City.OKLAHOMA_CITY, Color.BLACK, 4, denver_ok));
        int[] kc_ok1 = {518, 336, 508, 371};
        routes.add(new Route(City.KANSAS_CITY, City.OKLAHOMA_CITY, Color.BLACK, 2, kc_ok1));
        int[] kc_ok2 = {529, 340, 519, 376};
        routes.add(new Route(City.KANSAS_CITY, City.OKLAHOMA_CITY, Color.BLACK, 2, kc_ok2));
        int[] phoenix_denver = {235, 448, 251, 416, 269, 387, 294, 360, 326, 341};
        routes.add(new Route(City.PHOENIX, City.DENVER, Color.BLACK, 5, phoenix_denver));
        int[] ok_santafe = {378, 419, 413, 415, 449, 410};
        routes.add(new Route(City.OKLAHOMA_CITY, City.SANTA_FE, Color.BLACK, 3, ok_santafe));
        int[] elpaso_ok = {370, 449, 404, 486, 436, 468, 463, 445, 487, 418};
        routes.add(new Route(City.EL_PASO, City.OKLAHOMA_CITY, Color.BLACK, 5, elpaso_ok));
        int[] phoenix_santafe = {256, 459, 290, 443, 321, 429};
        routes.add(new Route(City.PHOENIX, City.SANTA_FE, Color.BLACK, 3, phoenix_santafe));
        int[] phoenix_elpaso = {249, 482, 285, 493, 319, 502};
        routes.add(new Route(City.PHOENIX, City.EL_PASO, Color.BLACK, 3, phoenix_elpaso));
        int[] santafe_elpaso = {346, 448, 344, 484};
        routes.add(new Route(City.SANTA_FE, City.EL_PASO, Color.BLACK, 2, santafe_elpaso));
        int[] elpaso_houston = {364, 528, 398, 541, 433, 550, 469, 553, 506, 550, 540, 541};
        routes.add(new Route(City.EL_PASO, City.HOUSTON, Color.BLACK, 6, elpaso_houston));
        int[] elpaso_dallas = {389, 512, 424, 506, 460, 500, 496, 495};
        routes.add(new Route(City.EL_PASO, City.DALLAS, Color.BLACK, 4, elpaso_dallas));
        int[] ok_dallas1 = {510, 425, 515, 460};
        routes.add(new Route(City.OKLAHOMA_CITY, City.DALLAS, Color.BLACK, 2, ok_dallas1));
        int[] ok_dallas2 = {523, 424, 527, 458};
        routes.add(new Route(City.OKLAHOMA_CITY, City.DALLAS, Color.BLACK, 2, ok_dallas2));
        int[] dallas_houston1 = {538, 510};
        routes.add(new Route(City.DALLAS, City.HOUSTON, Color.BLACK, 1, dallas_houston1));
        int[] dallas_houston2 = {548, 500};
        routes.add(new Route(City.DALLAS, City.HOUSTON, Color.BLACK, 1, dallas_houston2));
        int[] houston_neworleans = {594, 522, 630, 516};
        routes.add(new Route(City.HOUSTON, City.NEW_ORLEANS, Color.BLACK, 2, houston_neworleans));
        int[] dallas_littlerock = {552, 453, 573, 424};
        routes.add(new Route(City.DALLAS, City.LITTLE_ROCK, Color.BLACK, 2, dallas_littlerock));
        int[] ok_littlerock = {533, 399, 569, 398};
        routes.add(new Route(City.OKLAHOMA_CITY, City.LITTLE_ROCK, Color.BLACK, 2, ok_littlerock));
        int[] kc_stlouis1 = {550, 304, 587, 303};
        routes.add(new Route(City.KANSAS_CITY, City.SAINT_LOUIS, Color.BLACK, 2, kc_stlouis1));
        int[] kc_stlouis2 = {551, 317, 587, 316};
        routes.add(new Route(City.KANSAS_CITY, City.SAINT_LOUIS, Color.BLACK, 2, kc_stlouis2));
        int[] omaha_chicago = {530, 250, 558, 231, 594, 223, 628, 228};
        routes.add(new Route(City.OMAHA, City.CHICAGO, Color.BLACK, 4, omaha_chicago));
        int[] duluth_chicago = {556, 186, 589, 201, 624, 212};
        routes.add(new Route(City.DULUTH, City.CHICAGO, Color.BLACK, 3, duluth_chicago));
        int[] duluth_toronto = {561, 164, 597, 158, 632, 152, 669, 146, 704, 140, 740, 134};
        routes.add(new Route(City.DULUTH, City.TORONTO, Color.BLACK, 6, duluth_toronto));
        int[] sault_montreal = {682, 83, 713, 62, 746, 48, 781, 38, 817, 34};
        routes.add(new Route(City.SAULT_ST_MARIE, City.MONTREAL, Color.BLACK, 5, sault_montreal));
        int[] neworleans_littlerock = {642, 489, 626, 457, 609, 427};
        routes.add(new Route(City.NEW_ORLEANS, City.LITTLE_ROCK, Color.BLACK, 3, neworleans_littlerock));
        int[] littlerock_saintlouis = {599, 374, 608, 339};
        routes.add(new Route(City.LITTLE_ROCK, City.SAINT_LOUIS, Color.BLACK, 2, littlerock_saintlouis));
        int[] saintlouis_chicagogreen = {614, 283, 632, 253};
        routes.add(new Route(City.SAINT_LOUIS, City.CHICAGO, Color.GREEN, 2, saintlouis_chicagogreen));
        int[] saintlouis_chicagowhite = {625, 290, 645, 259};
        routes.add(new Route(City.SAINT_LOUIS, City.CHICAGO, Color.WHITE, 2, saintlouis_chicagowhite));
        int[] chicago_toronto = {661, 202, 689, 177, 722, 162, 754, 146};
        routes.add(new Route(City.CHICAGO, City.TORONTO, Color.BLACK, 4, chicago_toronto));
        int[] neworleans_miami = {696, 508, 729, 494, 765, 489, 800, 498, 830, 519, 854, 543};
        routes.add(new Route(City.NEW_ORLEANS, City.MIAMI, Color.BLACK, 6, neworleans_miami));
        int[] neworleans_atlantaorange = {678, 495, 694, 464, 716, 435, 740, 409};
        routes.add(new Route(City.NEW_ORLEANS, City.ATLANTA, Color.ORANGE, 4, neworleans_atlantaorange));
        int[] neworleans_atlantayellow = {668, 486, 685, 454, 707, 424, 732, 399};
        routes.add(new Route(City.NEW_ORLEANS, City.ATLANTA, Color.YELLOW, 4, neworleans_atlantayellow));
        int[] littlerock_nashville = {622, 399, 657, 390, 687, 370};
        routes.add(new Route(City.LITTLE_ROCK, City.NASHVILLE, Color.BLACK, 3, littlerock_nashville));
        int[] saintlouis_nashville = {637, 335, 671, 346};
        routes.add(new Route(City.SAINT_LOUIS, City.NASHVILLE, Color.BLACK, 2, saintlouis_nashville));
        int[] saintlouis_pittsburgh = {637, 307, 668, 290, 699, 271, 730, 253, 761, 236};
        routes.add(new Route(City.SAINT_LOUIS, City.PITTSBURGH, Color.BLACK, 5, saintlouis_pittsburgh));
        int[] chicago_pittsburghblack = {681, 211, 717, 204, 753, 201};
        routes.add(new Route(City.CHICAGO, City.PITTSBURGH, Color.BLACK, 3, chicago_pittsburghblack));
        int[] chicago_pittsburghorange = {685, 224, 721, 217, 757, 215};
        routes.add(new Route(City.CHICAGO, City.PITTSBURGH, Color.ORANGE, 3, chicago_pittsburghorange));
        int[] atlanta_miami = {861, 528, 839, 501, 816, 472, 794, 444, 771, 417};
        routes.add(new Route(City.ATLANTA, City.MIAMI, Color.BLACK, 5, atlanta_miami));
        int[] atlanta_chareston = {787, 398, 823, 400};
        routes.add(new Route(City.ATLANTA, City.CHARLESTON, Color.BLACK, 2, atlanta_chareston));
        int[] miami_charleston = {877, 524, 862, 490, 855, 456, 851, 419};
        routes.add(new Route(City.MIAMI, City.CHARLESTON, Color.BLACK, 4, miami_charleston));
        int[] atlanta_raleigh1 = {779, 376, 807, 351};
        routes.add(new Route(City.ATLANTA, City.RALEIGH, Color.BLACK, 2, atlanta_raleigh1));
        int[] atlanta_raleigh2 = {772, 364, 799, 341};
        routes.add(new Route(City.ATLANTA, City.RALEIGH, Color.BLACK, 2, atlanta_raleigh2));
        int[] raleigh_charleston = {842, 345, 859, 370};
        routes.add(new Route(City.RALEIGH, City.CHARLESTON, Color.BLACK, 2, raleigh_charleston));
        int[] nashville_atlanta = {727, 368};
        routes.add(new Route(City.NASHVILLE, City.ATLANTA, Color.BLACK, 1, nashville_atlanta));
        int[] nashville_raleigh = {726, 335, 760, 321, 797, 318};
        routes.add(new Route(City.NASHVILLE, City.RALEIGH, Color.BLACK, 3, nashville_raleigh));
        int[] nashville_pittsburgh = {701, 329, 721, 299, 748, 274, 775, 250};
        routes.add(new Route(City.NASHVILLE, City.PITTSBURGH, Color.BLACK, 4, nashville_pittsburgh));
        int[] pittsburgh_raleigh = {800, 255, 808, 289};
        routes.add(new Route(City.PITTSBURGH, City.RALEIGH, Color.BLACK, 2, pittsburgh_raleigh));
        int[] pittsburgh_washington = {818, 234, 850, 250};
        routes.add(new Route(City.PITTSBURGH, City.WASHINGTON, Color.BLACK, 2, pittsburgh_washington));
        int[] washington_raleigh1 = {859, 278, 835, 306};
        routes.add(new Route(City.WASHINGTON, City.RALEIGH, Color.BLACK, 2, washington_raleigh1));
        int[] washington_raleigh2 = {868, 286, 845, 313};
        routes.add(new Route(City.WASHINGTON, City.RALEIGH, Color.BLACK, 2, washington_raleigh2));
        int[] pittsburgh_nywhite = {810, 193, 839, 175};
        routes.add(new Route(City.PITTSBURGH, City.NEW_YORK, Color.WHITE, 2, pittsburgh_nywhite));
        int[] pittsburgh_nygeen = {816, 203, 845, 185};
        routes.add(new Route(City.PITTSBURGH, City.NEW_YORK, Color.GREEN, 2, pittsburgh_nygeen));
        int[] ny_washingtonorange = {871, 199, 874, 234};
        routes.add(new Route(City.NEW_YORK, City.WASHINGTON, Color.ORANGE, 2, ny_washingtonorange));
        int[] ny_washingtonblack = {883, 198, 885, 235};
        routes.add(new Route(City.NEW_YORK, City.WASHINGTON, Color.BLACK, 2, ny_washingtonblack));
        int[] pittsburgh_toronto = {781, 186, 778, 149};
        routes.add(new Route(City.PITTSBURGH, City.TORONTO, Color.BLACK, 2, pittsburgh_toronto));
        int[] toronto_montreal = {773, 98, 796, 70, 827, 50};
        routes.add(new Route(City.TORONTO, City.MONTREAL, Color.BLACK, 3, toronto_montreal));
        int[] montreal_boston1 = {880, 51, 907, 74};
        routes.add(new Route(City.MONTREAL, City.BOSTON, Color.BLACK, 2, montreal_boston1));
        int[] montreal_boston2 = {871, 62, 899, 84};
        routes.add(new Route(City.MONTREAL, City.BOSTON, Color.BLACK, 2, montreal_boston2));
        int[] boston_nyyellow = {905, 118, 886, 149};
        routes.add(new Route(City.BOSTON, City.NEW_YORK, Color.YELLOW, 2, boston_nyyellow));
        int[] boston_nyred = {897, 154, 915, 125};
        routes.add(new Route(City.BOSTON, City.NEW_YORK, Color.RED, 2, boston_nyred));
        int[] portland_slc = {70, 171, 106, 181, 138, 197, 166, 218, 193, 243, 213, 272};
        routes.add(new Route(City.PORTLAND, City.SALT_LAKE_CITY, Color.BLACK, 6, portland_slc));
        int[] sanfran_slcorange = {60, 345, 93, 335, 127, 323, 160, 312, 194, 301};
        routes.add(new Route(City.SAN_FRANCISCO, City.SALT_LAKE_CITY, Color.ORANGE, 5, sanfran_slcorange));
        int[] sanfran_slcwhite = {64, 357, 98, 346, 130, 335, 164, 324, 198, 312};
        routes.add(new Route(City.SAN_FRANCISCO, City.SALT_LAKE_CITY, Color.WHITE, 5, sanfran_slcwhite));
        int[] sanfran_lapink = {46, 385, 63, 416, 86, 445};
        routes.add(new Route(City.SAN_FRANCISCO, City.LOS_ANGELES, Color.PINK, 3, sanfran_lapink));
        int[] sanfran_layellow = {35, 393, 53, 424, 76, 451};
        routes.add(new Route(City.SAN_FRANCISCO, City.LOS_ANGELES, Color.YELLOW, 3, sanfran_layellow));
        int[] la_lasvegas = {115, 434, 142, 412};
        routes.add(new Route(City.LOS_ANGELES, City.LAS_VEGAS, Color.BLACK, 2, la_lasvegas));
        int[] la_phoenix = {130, 458, 166, 456, 202, 461};
        routes.add(new Route(City.LOS_ANGELES, City.PHOENIX, Color.BLACK, 3, la_phoenix));
        int[] la_elpaso = {130, 485, 162, 504, 196, 516, 231, 522, 267, 524, 303, 518};
        routes.add(new Route(City.LOS_ANGELES, City.EL_PASO, Color.BLACK, 6, la_elpaso));
        int[] lasvegas_slc = {193, 392, 214, 362, 223, 327};
        routes.add(new Route(City.LAS_VEGAS, City.SALT_LAKE_CITY, Color.BLACK, 3, lasvegas_slc));
        int[] vancouver_seattle1 = {57, 88};
        routes.add(new Route(City.VANCOUVER, City.SEATTLE, Color.BLACK, 1, vancouver_seattle1));
        int[] vancouver_seattle2 = {71, 87};
        routes.add(new Route(City.VANCOUVER, City.SEATTLE, Color.BLACK, 1, vancouver_seattle2));
        int[] seattle_portland1 = {45, 137};
        routes.add(new Route(City.SEATTLE, City.PORTLAND, Color.BLACK, 1, seattle_portland1));
        int[] seattle_portland2 = {57, 141};
        routes.add(new Route(City.SEATTLE, City.PORTLAND, Color.BLACK, 1, seattle_portland2));
        int[] portland_sanfrancisco1 = {23, 190, 18, 227, 13, 263, 15, 297, 20, 333};
        routes.add(new Route(City.PORTLAND, City.SAN_FRANCISCO, Color.BLACK, 5, portland_sanfrancisco1));
        int[] portland_sanfrancisco2 = {35, 190, 28, 227, 23, 263, 25, 297, 30, 333};
        routes.add(new Route(City.PORTLAND, City.SAN_FRANCISCO, Color.BLACK, 5, portland_sanfrancisco2));
        int[] denver_santafe = {350, 359, 348, 394};
        routes.add(new Route(City.DENVER, City.SANTA_FE, Color.BLACK, 2, denver_santafe));
        int[] sault_toronto = {693, 109, 729, 116};
        routes.add(new Route(City.SAULT_ST_MARIE, City.TORONTO, Color.BLACK, 2, sault_toronto));
        int[] denver_omaha = {376, 309, 407, 289, 441, 277, 476, 268};
        routes.add(new Route(City.DENVER, City.OMAHA, Color.BLACK, 4, denver_omaha));
        int[] montreal_ny = {850, 70, 856, 106, 862, 140};
        routes.add(new Route(City.MONTREAL, City.NEW_YORK, Color.BLACK, 3, montreal_ny));
    }
}
