package cs340.game.server.DB;

import java.util.ArrayList;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.ServerException;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;

/**
 * Created by Stephen on 11/11/2018.
 */

public class GameRoutesDatabase {
    private ArrayList<Route> unclaimedRoutes;
    private ArrayList<Route> claimedRoutes;

    public GameRoutesDatabase() {
        unclaimedRoutes = new ArrayList<>();
        claimedRoutes = new ArrayList<>();
        int[] calgary_winnipeg = {223, 34, 257, 22, 294, 19, 330, 19, 366, 26, 400, 36};
        unclaimedRoutes.add(new Route(City.CALGARY, City.WINNIPEG, Color.WHITE, 6, false, calgary_winnipeg));
        int[] vancouver_calgary = {96, 53, 131, 50, 168, 44};
        unclaimedRoutes.add(new Route(City.VANCOUVER, City.CALGARY, Color.WILD, 3, false, vancouver_calgary));
        int[] seattle_calgary = {95, 113, 129, 111, 163, 97, 187, 70};
        unclaimedRoutes.add(new Route(City.SEATTLE, City.CALGARY, Color.WILD, 4, false, seattle_calgary));
        int[] seattle_helena = {91, 133, 126, 140, 162, 148, 197, 156, 232, 164, 267, 171};
        unclaimedRoutes.add(new Route(City.SEATTLE, City.HELENA, Color.YELLOW, 6, false, seattle_helena));
        int[] calgary_helena = {216, 68, 239, 94, 262, 121, 285, 149};
        unclaimedRoutes.add(new Route(City.CALGARY, City.HELENA, Color.WILD, 4, false, calgary_helena));
        int[] helena_winnipeg = {322, 149, 346, 123, 372, 97, 398, 73};
        unclaimedRoutes.add(new Route(City.HELENA, City.WINNIPEG, Color.BLUE, 4,false, helena_winnipeg));
        int[] winnipeg_sault = {457, 56, 493, 63, 528, 70, 564, 78, 598, 84, 633, 91};
        unclaimedRoutes.add(new Route(City.WINNIPEG, City.SAULT_ST_MARIE, Color.WILD, 6, false, winnipeg_sault));
        int[] winnipeg_duluth = {440, 74, 465, 99, 491, 124, 518, 149};
        unclaimedRoutes.add(new Route(City.WINNIPEG, City.DULUTH, Color.BLACK, 4, false, winnipeg_duluth));
        int[] helena_duluth = {327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170};
        unclaimedRoutes.add(new Route(City.HELENA, City.DULUTH, Color.ORANGE, 6, false, helena_duluth));
        int[] helena_slc = {243, 262, 262, 232, 280, 201};
        unclaimedRoutes.add(new Route(City.HELENA, City.SALT_LAKE_CITY, Color.PINK, 3, false, helena_slc));
        int[] helena_denver = {310, 201, 324, 234, 336, 266, 350, 298};
        unclaimedRoutes.add(new Route(City.HELENA, City.DENVER, Color.GREEN, 4, false, helena_denver));
        int[] helena_omaha = {335, 193, 369, 207, 401, 221, 435, 235, 468, 248};
        unclaimedRoutes.add(new Route(City.HELENA, City.OMAHA, Color.RED, 5, false, helena_omaha));
        int[] slc_denver1 = {254, 296, 289, 303, 324, 310};
        unclaimedRoutes.add(new Route(City.SALT_LAKE_CITY, City.DENVER, Color.RED, 3, true, slc_denver1));
        int[] slc_denver2 = {252, 308, 288, 315, 322, 322};
        unclaimedRoutes.add(new Route(City.SALT_LAKE_CITY, City.DENVER, Color.YELLOW, 3, true, slc_denver2));
        int[] duluth_sault = {567, 146, 600, 131, 634, 117};
        unclaimedRoutes.add(new Route(City.DULUTH, City.SAULT_ST_MARIE, Color.WILD, 3,false, duluth_sault));
        int[] duluth_omaha1 = {517, 198, 503, 230};
        unclaimedRoutes.add(new Route(City.DULUTH, City.OMAHA, Color.WILD, 2, true, duluth_omaha1));
        int[] duluth_omaha2 = {529, 202, 516, 235};
        unclaimedRoutes.add(new Route(City.DULUTH, City.OMAHA, Color.WILD, 2, true, duluth_omaha2));
        int[] omaha_kc1 = {511, 290};
        unclaimedRoutes.add(new Route(City.OMAHA, City.KANSAS_CITY, Color.WILD, 1, true, omaha_kc1));
        int[] omaha_kc2 = {523, 285};
        unclaimedRoutes.add(new Route(City.OMAHA, City.KANSAS_CITY, Color.WILD, 1, true, omaha_kc2));
        int[] denver_kc1 = {390, 330, 425, 330, 461, 325, 496, 314};
        unclaimedRoutes.add(new Route(City.DENVER, City.KANSAS_CITY, Color.BLACK, 4, true, denver_kc1));
        int[] denver_kc2 = {390, 344, 425, 343, 463, 339, 496, 328};
        unclaimedRoutes.add(new Route(City.DENVER, City.KANSAS_CITY, Color.ORANGE, 4,true, denver_kc2));
        int[] denver_ok = {373, 359, 403, 380, 438, 390, 474, 394};
        unclaimedRoutes.add(new Route(City.DENVER, City.OKLAHOMA_CITY, Color.RED, 4, false, denver_ok));
        int[] kc_ok1 = {518, 336, 508, 371};
        unclaimedRoutes.add(new Route(City.KANSAS_CITY, City.OKLAHOMA_CITY, Color.WILD, 2, true, kc_ok1));
        int[] kc_ok2 = {529, 340, 519, 376};
        unclaimedRoutes.add(new Route(City.KANSAS_CITY, City.OKLAHOMA_CITY, Color.WILD, 2, true, kc_ok2));
        int[] phoenix_denver = {235, 448, 251, 416, 269, 387, 294, 360, 326, 341};
        unclaimedRoutes.add(new Route(City.PHOENIX, City.DENVER, Color.WHITE, 5, false, phoenix_denver));
        int[] ok_santafe = {378, 419, 413, 415, 449, 410};
        unclaimedRoutes.add(new Route(City.OKLAHOMA_CITY, City.SANTA_FE, Color.BLUE, 3, false, ok_santafe));
        int[] elpaso_ok = {370, 449, 404, 486, 436, 468, 463, 445, 487, 418};
        unclaimedRoutes.add(new Route(City.EL_PASO, City.OKLAHOMA_CITY, Color.YELLOW, 5, false, elpaso_ok));
        int[] phoenix_santafe = {256, 459, 290, 443, 321, 429};
        unclaimedRoutes.add(new Route(City.PHOENIX, City.SANTA_FE, Color.WILD, 3,false, phoenix_santafe));
        int[] phoenix_elpaso = {249, 482, 285, 493, 319, 502};
        unclaimedRoutes.add(new Route(City.PHOENIX, City.EL_PASO, Color.WILD, 3,false, phoenix_elpaso));
        int[] santafe_elpaso = {346, 448, 344, 484};
        unclaimedRoutes.add(new Route(City.SANTA_FE, City.EL_PASO, Color.WILD, 2,false, santafe_elpaso));
        int[] elpaso_houston = {364, 528, 398, 541, 433, 550, 469, 553, 506, 550, 540, 541};
        unclaimedRoutes.add(new Route(City.EL_PASO, City.HOUSTON, Color.GREEN, 6, false, elpaso_houston));
        int[] elpaso_dallas = {389, 512, 424, 506, 460, 500, 496, 495};
        unclaimedRoutes.add(new Route(City.EL_PASO, City.DALLAS, Color.RED, 4, false, elpaso_dallas));
        int[] ok_dallas1 = {510, 425, 515, 460};
        unclaimedRoutes.add(new Route(City.OKLAHOMA_CITY, City.DALLAS, Color.WILD, 2, true, ok_dallas1));
        int[] ok_dallas2 = {523, 424, 527, 458};
        unclaimedRoutes.add(new Route(City.OKLAHOMA_CITY, City.DALLAS, Color.WILD, 2, true, ok_dallas2));
        int[] dallas_houston1 = {538, 510};
        unclaimedRoutes.add(new Route(City.DALLAS, City.HOUSTON, Color.WILD, 1, true, dallas_houston1));
        int[] dallas_houston2 = {548, 500};
        unclaimedRoutes.add(new Route(City.DALLAS, City.HOUSTON, Color.WILD, 1, true, dallas_houston2));
        int[] houston_neworleans = {594, 522, 630, 516};
        unclaimedRoutes.add(new Route(City.HOUSTON, City.NEW_ORLEANS, Color.WILD, 2,false, houston_neworleans));
        int[] dallas_littlerock = {552, 453, 573, 424};
        unclaimedRoutes.add(new Route(City.DALLAS, City.LITTLE_ROCK, Color.WILD, 2, false, dallas_littlerock));
        int[] ok_littlerock = {533, 399, 569, 398};
        unclaimedRoutes.add(new Route(City.OKLAHOMA_CITY, City.LITTLE_ROCK, Color.WILD, 2, false, ok_littlerock));
        int[] kc_stlouis1 = {550, 304, 587, 303};
        unclaimedRoutes.add(new Route(City.KANSAS_CITY, City.SAINT_LOUIS, Color.BLUE, 2, true, kc_stlouis1));
        int[] kc_stlouis2 = {551, 317, 587, 316};
        unclaimedRoutes.add(new Route(City.KANSAS_CITY, City.SAINT_LOUIS, Color.PINK, 2, true, kc_stlouis2));
        int[] omaha_chicago = {530, 250, 558, 231, 594, 223, 628, 228};
        unclaimedRoutes.add(new Route(City.OMAHA, City.CHICAGO, Color.BLUE, 4,false, omaha_chicago));
        int[] duluth_chicago = {556, 186, 589, 201, 624, 212};
        unclaimedRoutes.add(new Route(City.DULUTH, City.CHICAGO, Color.RED, 3, false, duluth_chicago));
        int[] duluth_toronto = {561, 164, 597, 158, 632, 152, 669, 146, 704, 140, 740, 134};
        unclaimedRoutes.add(new Route(City.DULUTH, City.TORONTO, Color.PINK, 6, false, duluth_toronto));
        int[] sault_montreal = {682, 83, 713, 62, 746, 48, 781, 38, 817, 34};
        unclaimedRoutes.add(new Route(City.SAULT_ST_MARIE, City.MONTREAL, Color.BLACK, 5, false, sault_montreal));
        int[] neworleans_littlerock = {642, 489, 626, 457, 609, 427};
        unclaimedRoutes.add(new Route(City.NEW_ORLEANS, City.LITTLE_ROCK, Color.GREEN, 3, false, neworleans_littlerock));
        int[] littlerock_saintlouis = {599, 374, 608, 339};
        unclaimedRoutes.add(new Route(City.LITTLE_ROCK, City.SAINT_LOUIS, Color.WILD, 2, false, littlerock_saintlouis));
        int[] saintlouis_chicagogreen = {614, 283, 632, 253};
        unclaimedRoutes.add(new Route(City.SAINT_LOUIS, City.CHICAGO, Color.GREEN, 2,true, saintlouis_chicagogreen));
        int[] saintlouis_chicagowhite = {625, 290, 645, 259};
        unclaimedRoutes.add(new Route(City.SAINT_LOUIS, City.CHICAGO, Color.WHITE, 2,true, saintlouis_chicagowhite));
        int[] chicago_toronto = {661, 202, 689, 177, 722, 162, 754, 146};
        unclaimedRoutes.add(new Route(City.CHICAGO, City.TORONTO, Color.WHITE, 4, false, chicago_toronto));
        int[] neworleans_miami = {696, 508, 729, 494, 765, 489, 800, 498, 830, 519, 854, 543};
        unclaimedRoutes.add(new Route(City.NEW_ORLEANS, City.MIAMI, Color.RED, 6, false, neworleans_miami));
        int[] neworleans_atlantaorange = {678, 495, 694, 464, 716, 435, 740, 409};
        unclaimedRoutes.add(new Route(City.NEW_ORLEANS, City.ATLANTA, Color.ORANGE, 4,true, neworleans_atlantaorange));
        int[] neworleans_atlantayellow = {668, 486, 685, 454, 707, 424, 732, 399};
        unclaimedRoutes.add(new Route(City.NEW_ORLEANS, City.ATLANTA, Color.YELLOW, 4, true, neworleans_atlantayellow));
        int[] littlerock_nashville = {622, 399, 657, 390, 687, 370};
        unclaimedRoutes.add(new Route(City.LITTLE_ROCK, City.NASHVILLE, Color.WHITE, 3, false, littlerock_nashville));
        int[] saintlouis_nashville = {637, 335, 671, 346};
        unclaimedRoutes.add(new Route(City.SAINT_LOUIS, City.NASHVILLE, Color.WILD, 2, false, saintlouis_nashville));
        int[] saintlouis_pittsburgh = {637, 307, 668, 290, 699, 271, 730, 253, 761, 236};
        unclaimedRoutes.add(new Route(City.SAINT_LOUIS, City.PITTSBURGH, Color.GREEN, 5, false, saintlouis_pittsburgh));
        int[] chicago_pittsburghblack = {681, 211, 717, 204, 753, 201};
        unclaimedRoutes.add(new Route(City.CHICAGO, City.PITTSBURGH, Color.BLACK, 3, true, chicago_pittsburghblack));
        int[] chicago_pittsburghorange = {685, 224, 721, 217, 757, 215};
        unclaimedRoutes.add(new Route(City.CHICAGO, City.PITTSBURGH, Color.ORANGE, 3, true, chicago_pittsburghorange));
        int[] atlanta_miami = {861, 528, 839, 501, 816, 472, 794, 444, 771, 417};
        unclaimedRoutes.add(new Route(City.ATLANTA, City.MIAMI, Color.BLUE, 5, false, atlanta_miami));
        int[] atlanta_chareston = {787, 398, 823, 400};
        unclaimedRoutes.add(new Route(City.ATLANTA, City.CHARLESTON, Color.WILD, 2, false, atlanta_chareston));
        int[] miami_charleston = {877, 524, 862, 490, 855, 456, 851, 419};
        unclaimedRoutes.add(new Route(City.MIAMI, City.CHARLESTON, Color.PINK, 4, false, miami_charleston));
        int[] atlanta_raleigh1 = {779, 376, 807, 351};
        unclaimedRoutes.add(new Route(City.ATLANTA, City.RALEIGH, Color.WILD, 2, true, atlanta_raleigh1));
        int[] atlanta_raleigh2 = {772, 364, 799, 341};
        unclaimedRoutes.add(new Route(City.ATLANTA, City.RALEIGH, Color.WILD, 2, true, atlanta_raleigh2));
        int[] raleigh_charleston = {842, 345, 859, 370};
        unclaimedRoutes.add(new Route(City.RALEIGH, City.CHARLESTON, Color.WILD, 2, false, raleigh_charleston));
        int[] nashville_atlanta = {727, 368};
        unclaimedRoutes.add(new Route(City.NASHVILLE, City.ATLANTA, Color.WILD, 1, false, nashville_atlanta));
        int[] nashville_raleigh = {726, 335, 760, 321, 797, 318};
        unclaimedRoutes.add(new Route(City.NASHVILLE, City.RALEIGH, Color.BLACK, 3, false, nashville_raleigh));
        int[] nashville_pittsburgh = {701, 329, 721, 299, 748, 274, 775, 250};
        unclaimedRoutes.add(new Route(City.NASHVILLE, City.PITTSBURGH, Color.YELLOW, 4, false, nashville_pittsburgh));
        int[] pittsburgh_raleigh = {800, 255, 808, 289};
        unclaimedRoutes.add(new Route(City.PITTSBURGH, City.RALEIGH, Color.WILD, 2, false, pittsburgh_raleigh));
        int[] pittsburgh_washington = {818, 234, 850, 250};
        unclaimedRoutes.add(new Route(City.PITTSBURGH, City.WASHINGTON, Color.WILD, 2, false, pittsburgh_washington));
        int[] washington_raleigh1 = {859, 278, 835, 306};
        unclaimedRoutes.add(new Route(City.WASHINGTON, City.RALEIGH, Color.WILD, 2, true, washington_raleigh1));
        int[] washington_raleigh2 = {868, 286, 845, 313};
        unclaimedRoutes.add(new Route(City.WASHINGTON, City.RALEIGH, Color.WILD, 2, true, washington_raleigh2));
        int[] pittsburgh_nywhite = {810, 193, 839, 175};
        unclaimedRoutes.add(new Route(City.PITTSBURGH, City.NEW_YORK, Color.WHITE, 2, true, pittsburgh_nywhite));
        int[] pittsburgh_nygeen = {816, 203, 845, 185};
        unclaimedRoutes.add(new Route(City.PITTSBURGH, City.NEW_YORK, Color.GREEN, 2, true, pittsburgh_nygeen));
        int[] ny_washingtonorange = {871, 199, 874, 234};
        unclaimedRoutes.add(new Route(City.NEW_YORK, City.WASHINGTON, Color.ORANGE, 2, true, ny_washingtonorange));
        int[] ny_washingtonblack = {883, 198, 885, 235};
        unclaimedRoutes.add(new Route(City.NEW_YORK, City.WASHINGTON, Color.BLACK, 2, true, ny_washingtonblack));
        int[] pittsburgh_toronto = {781, 186, 778, 149};
        unclaimedRoutes.add(new Route(City.PITTSBURGH, City.TORONTO, Color.WILD, 2, false, pittsburgh_toronto));
        int[] toronto_montreal = {773, 98, 796, 70, 827, 50};
        unclaimedRoutes.add(new Route(City.TORONTO, City.MONTREAL, Color.WILD, 3, false, toronto_montreal));
        int[] montreal_boston1 = {880, 51, 907, 74};
        unclaimedRoutes.add(new Route(City.MONTREAL, City.BOSTON, Color.WILD, 2, true, montreal_boston1));
        int[] montreal_boston2 = {871, 62, 899, 84};
        unclaimedRoutes.add(new Route(City.MONTREAL, City.BOSTON, Color.WILD, 2, true, montreal_boston2));
        int[] boston_nyyellow = {905, 118, 886, 149};
        unclaimedRoutes.add(new Route(City.BOSTON, City.NEW_YORK, Color.YELLOW, 2, true, boston_nyyellow));
        int[] boston_nyred = {897, 154, 915, 125};
        unclaimedRoutes.add(new Route(City.BOSTON, City.NEW_YORK, Color.RED, 2, true, boston_nyred));
        int[] portland_slc = {70, 171, 106, 181, 138, 197, 166, 218, 193, 243, 213, 272};
        unclaimedRoutes.add(new Route(City.PORTLAND, City.SALT_LAKE_CITY, Color.BLUE, 6, false, portland_slc));
        int[] sanfran_slcorange = {60, 345, 93, 335, 127, 323, 160, 312, 194, 301};
        unclaimedRoutes.add(new Route(City.SAN_FRANCISCO, City.SALT_LAKE_CITY, Color.ORANGE, 5, true, sanfran_slcorange));
        int[] sanfran_slcwhite = {64, 357, 98, 346, 130, 335, 164, 324, 198, 312};
        unclaimedRoutes.add(new Route(City.SAN_FRANCISCO, City.SALT_LAKE_CITY, Color.WHITE, 5, true, sanfran_slcwhite));
        int[] sanfran_lapink = {46, 385, 63, 416, 86, 445};
        unclaimedRoutes.add(new Route(City.SAN_FRANCISCO, City.LOS_ANGELES, Color.PINK, 3, true, sanfran_lapink));
        int[] sanfran_layellow = {35, 393, 53, 424, 76, 451};
        unclaimedRoutes.add(new Route(City.SAN_FRANCISCO, City.LOS_ANGELES, Color.YELLOW, 3, true, sanfran_layellow));
        int[] la_lasvegas = {115, 434, 142, 412};
        unclaimedRoutes.add(new Route(City.LOS_ANGELES, City.LAS_VEGAS, Color.WILD, 2, false, la_lasvegas));
        int[] la_phoenix = {130, 458, 166, 456, 202, 461};
        unclaimedRoutes.add(new Route(City.LOS_ANGELES, City.PHOENIX, Color.WILD, 3, false, la_phoenix));
        int[] la_elpaso = {130, 485, 162, 504, 196, 516, 231, 522, 267, 524, 303, 518};
        unclaimedRoutes.add(new Route(City.LOS_ANGELES, City.EL_PASO, Color.BLACK, 6, false, la_elpaso));
        int[] lasvegas_slc = {193, 392, 214, 362, 223, 327};
        unclaimedRoutes.add(new Route(City.LAS_VEGAS, City.SALT_LAKE_CITY, Color.ORANGE, 3, false, lasvegas_slc));
        int[] vancouver_seattle1 = {57, 88};
        unclaimedRoutes.add(new Route(City.VANCOUVER, City.SEATTLE, Color.WILD, 1, true, vancouver_seattle1));
        int[] vancouver_seattle2 = {71, 87};
        unclaimedRoutes.add(new Route(City.VANCOUVER, City.SEATTLE, Color.WILD, 1, true, vancouver_seattle2));
        int[] seattle_portland1 = {45, 137};
        unclaimedRoutes.add(new Route(City.SEATTLE, City.PORTLAND, Color.WILD, 1, true, seattle_portland1));
        int[] seattle_portland2 = {57, 141};
        unclaimedRoutes.add(new Route(City.SEATTLE, City.PORTLAND, Color.WILD, 1, true, seattle_portland2));
        int[] portland_sanfrancisco1 = {23, 190, 18, 227, 13, 263, 15, 297, 20, 333};
        unclaimedRoutes.add(new Route(City.PORTLAND, City.SAN_FRANCISCO, Color.GREEN, 5, true, portland_sanfrancisco1));
        int[] portland_sanfrancisco2 = {35, 190, 28, 227, 23, 263, 25, 297, 30, 333};
        unclaimedRoutes.add(new Route(City.PORTLAND, City.SAN_FRANCISCO, Color.PINK, 5, true, portland_sanfrancisco2));
        int[] denver_santafe = {350, 359, 348, 394};
        unclaimedRoutes.add(new Route(City.DENVER, City.SANTA_FE, Color.WILD, 2, false, denver_santafe));
        int[] sault_toronto = {693, 109, 729, 116};
        unclaimedRoutes.add(new Route(City.SAULT_ST_MARIE, City.TORONTO, Color.WILD, 2, false, sault_toronto));
        int[] denver_omaha = {376, 309, 407, 289, 441, 277, 476, 268};
        unclaimedRoutes.add(new Route(City.DENVER, City.OMAHA, Color.PINK, 4, false, denver_omaha));
        int[] montreal_ny = {850, 70, 856, 106, 862, 140};
        unclaimedRoutes.add(new Route(City.MONTREAL, City.NEW_YORK, Color.BLUE, 3, false, montreal_ny));
    }

    public void claimRoute(Route routeToClaim, String username) throws ServerException{
        boolean routeIsUnclaimed = false;
        int routeIndex;
        for(routeIndex = 0; routeIndex < unclaimedRoutes.size(); routeIndex++) {
            if(unclaimedRoutes.get(routeIndex).connectsToCity(routeToClaim.getCity1()) &&
                    unclaimedRoutes.get(routeIndex).connectsToCity(routeToClaim.getCity2()) &&
                    unclaimedRoutes.get(routeIndex).getColor().equals(routeToClaim.getColor())) {
                routeIsUnclaimed = true;
                break;
            }
        }
        if(routeIsUnclaimed) {
            Route claimedRoute = unclaimedRoutes.remove(routeIndex);
            claimedRoute.setPlayerOnRoute(username);
            claimedRoutes.add(claimedRoute);
        }
        else {
            throw new ServerException("This route has already been claimed.");
        }
    }

    public void claimDoubleRoute(Route claimedRoute) {
        int routeIndex;
        for(routeIndex = 0; routeIndex < unclaimedRoutes.size(); routeIndex++) {
            if(unclaimedRoutes.get(routeIndex).connectsToCity(claimedRoute.getCity1()) &&
                    unclaimedRoutes.get(routeIndex).connectsToCity(claimedRoute.getCity2())) {
                Route claimedDoubleRoute = unclaimedRoutes.remove(routeIndex);
                claimedDoubleRoute.setPlayerOnRoute("DOUBLE_ROUTE");
                claimedRoutes.add(claimedDoubleRoute);
            }
        }
    }

    public void determineCompletedDestinationCards(ArrayList<Player> players) {
        ArrayList<DestinationCard> cards;
        ArrayList<Route> playerClaimedRoutes = new ArrayList<>();
        boolean routeCompleted;
        City terminus1;
        City terminus2;
        for(Player player : players) {
            for(Route route : claimedRoutes) {
                if(route.getPlayerOnRoute().equals(player.getName())) {
                    playerClaimedRoutes.add(route);
                }
            }
            cards = player.getDestinationCards();
            for(DestinationCard card : cards) {
                terminus1 = card.getCity1();
                terminus2 = card.getCity2();
                routeCompleted = continueDetermineCompletedDestinationCards(terminus1, terminus2, playerClaimedRoutes);
                if(routeCompleted) {
                    player.addDestinationCardRoutePoints(card.getPointValue());
                }
                else {
                    player.subtractDestinationCardRoutePoints(card.getPointValue());
                }
            }
        }
    }

    public boolean continueDetermineCompletedDestinationCards(City connector, City endTerminus, ArrayList<Route> remainingRoutes) {
        City newConnector = null;
        boolean success;
        Route route;
        for(int i = 0; i < remainingRoutes.size(); i++) {
            route = remainingRoutes.get(i);
            if(route.getCity1().equals(connector)) {
                newConnector = route.getCity2();
            }
            else if(route.getCity2().equals(connector)) {
                newConnector = route.getCity1();
            }

            if(newConnector != null) {
                if(newConnector.equals(endTerminus)) {
                    return true;
                }
                else {
                    remainingRoutes.remove(route);
                    success = continueDetermineCompletedDestinationCards(newConnector, endTerminus, remainingRoutes);
                    remainingRoutes.add(i, route);
                }

                if(success) {
                    return true;
                }
                else {
                    newConnector = null;
                }
            }
        }
        return false;
    }

    public ArrayList<String> calculateLongestTrackPlayerNames(ArrayList<Player> players) {
        ArrayList<String> longestTrackPlayerNames = new ArrayList<>();
        int longestTrackLength = 0;
        ArrayList<Route> playerRoutes = new ArrayList<>();
        ArrayList<Route> routeWorkingList = new ArrayList<>();
        int trackLength;
        for(Player player : players) {
            playerRoutes.clear();
            for(Route route : claimedRoutes) {
                if(route.getPlayerOnRoute().equals(player.getName())) {
                    playerRoutes.add(route);
                }
            }
            for(Route route : playerRoutes) {
                routeWorkingList.add(route);
            }
            for(int i = 0; i < playerRoutes.size(); i++) {
                Route startingRoute = playerRoutes.get(i);
                //City terminus = startingRoute.getCity1();
                City connector = startingRoute.getCity2();
                trackLength = startingRoute.getLength();
                routeWorkingList.remove(startingRoute);
                trackLength += getContinuedTrackLength(connector, routeWorkingList);
                if(trackLength > longestTrackLength) {
                    longestTrackPlayerNames.clear();
                    longestTrackPlayerNames.add(player.getName());
                    longestTrackLength = trackLength;
                }
                else if(trackLength == longestTrackLength) {
                    if(!longestTrackPlayerNames.contains(player.getName())) {
                        longestTrackPlayerNames.add(player.getName());
                    }
                }

                //terminus = startingRoute.getCity2();
                connector = startingRoute.getCity1();
                trackLength = startingRoute.getLength();
                trackLength += getContinuedTrackLength(connector, routeWorkingList);
                if(trackLength > longestTrackLength) {
                    longestTrackPlayerNames.clear();
                    longestTrackPlayerNames.add(player.getName());
                    longestTrackLength = trackLength;
                }
                else if(trackLength == longestTrackLength) {
                    if(!longestTrackPlayerNames.contains(player.getName())) {
                        longestTrackPlayerNames.add(player.getName());
                    }
                }
                routeWorkingList.add(i, startingRoute);
            }
        }
        return longestTrackPlayerNames;
    }

    public int getContinuedTrackLength(City connector, ArrayList<Route> remainingRoutes) {
        Route connectingRoute;
        City newConnector;
        int trackLength;
        int maxTrackLength = 0;
        for(int i = 0; i < remainingRoutes.size(); i++) {
            connectingRoute = null;
            newConnector = null;
            if(remainingRoutes.get(i).getCity1().equals(connector)) {
                connectingRoute = remainingRoutes.get(i);
                newConnector = connectingRoute.getCity2();
            }
            else if(remainingRoutes.get(i).getCity2().equals(connector)) {
                connectingRoute = remainingRoutes.get(i);
                newConnector = connectingRoute.getCity1();
            }
            if(connectingRoute != null) {
                trackLength = connectingRoute.getLength();
                remainingRoutes.remove(connectingRoute);
                trackLength += getContinuedTrackLength(newConnector, remainingRoutes);
                remainingRoutes.add(i, connectingRoute);
                if(trackLength > maxTrackLength) {
                    maxTrackLength = trackLength;
                }
            }
        }
        return maxTrackLength;
    }

}
