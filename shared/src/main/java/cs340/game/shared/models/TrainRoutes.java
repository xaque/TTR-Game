package cs340.game.shared.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainRoutes {
    private Map<String, List<Integer>> routes;

    private static TrainRoutes instance;

    private TrainRoutes() {
        this.routes = new HashMap<>();
        this.routes.put("calgary_winnipeg", Arrays.asList(223, 34, 257, 22, 294, 19, 330, 19, 366, 26, 400, 36));
        this.routes.put("vancouver_calgary", Arrays.asList(96, 53, 131, 50, 168, 44));
        this.routes.put("seattle_calgary", Arrays.asList(95, 113, 129, 111, 163, 97, 187, 70));
        this.routes.put("seattle_helena", Arrays.asList(91, 133, 126, 140, 162, 148, 197, 156, 232, 164, 267, 171));
        this.routes.put("calgary_helena", Arrays.asList(216, 68, 239, 94, 262, 121, 285, 149));
        this.routes.put("helena_winnipeg", Arrays.asList(322, 149, 346, 123, 372, 97, 398, 73));
        this.routes.put("winnipeg_sault", Arrays.asList(457, 56,493, 63, 528, 70, 564, 78, 598, 84, 633, 91));
        this.routes.put("winnipeg_duluth", Arrays.asList(440, 74, 465, 99, 491, 124, 518, 149));
        this.routes.put("helena_duluth", Arrays.asList(327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170));
        this.routes.put("helena_slc", Arrays.asList(243, 262, 262, 232, 280, 201));
        this.routes.put("helena_denver", Arrays.asList(310, 201, 324, 234, 336, 266, 350, 298));
        this.routes.put("helena_omaha", Arrays.asList(335, 193, 369, 207, 401, 221, 435, 235, 468, 248));
        this.routes.put("slc_denver1", Arrays.asList(254, 296, 289, 303, 324, 310));
        this.routes.put("slc_denver2", Arrays.asList(252, 308, 288, 315, 322, 322));
        this.routes.put("duluth_sault", Arrays.asList(567, 146, 600, 131, 634, 117));
        this.routes.put("duluth_omaha1", Arrays.asList(517, 198, 503, 230));
        this.routes.put("duluth_omaha2", Arrays.asList(529, 202, 516, 235));
        this.routes.put("omaha_kc1", Arrays.asList(511, 290));
        this.routes.put("omaha_kc2", Arrays.asList(523, 285));
        this.routes.put("denver_kc1", Arrays.asList(390, 330, 425, 330, 461, 325, 496, 314));
        this.routes.put("denver_kc2", Arrays.asList(390, 344, 425, 343, 463, 339, 496, 328));
        this.routes.put("denver_ok", Arrays.asList(373, 359, 403, 380, 438, 390, 474, 394));
        this.routes.put("kc_ok1", Arrays.asList(518, 336, 508, 371));
        this.routes.put("kc_ok2", Arrays.asList(529, 340, 519, 376));
        this.routes.put("phoenix_denver", Arrays.asList(235, 448, 251, 416, 269, 387, 294, 360, 326, 341));
        this.routes.put("ok_santafe", Arrays.asList(378, 419, 413, 415, 449, 410));
        this.routes.put("elpaso_ok", Arrays.asList(370, 449, 404, 486, 436, 468, 463, 445, 487, 418));
        this.routes.put("phoenix_santafe", Arrays.asList(256, 459, 290, 443, 321, 429));
        this.routes.put("phoenix_elpaso", Arrays.asList(249, 482, 285, 493, 319, 502));
        this.routes.put("santafe_elpaso", Arrays.asList(346, 448, 344, 484));
        this.routes.put("elpaso_houston", Arrays.asList(364, 528, 398, 541, 433, 550, 469, 553, 506, 550, 540, 541));
        this.routes.put("elpaso_dallas", Arrays.asList(389, 512, 424, 506, 460, 500, 496, 495));
        this.routes.put("ok_dallas1", Arrays.asList(510, 425, 515, 460));
        this.routes.put("ok_dallas2", Arrays.asList(523, 424, 527, 458));
        this.routes.put("dallas_houston1", Arrays.asList(538, 510));
        this.routes.put("dallas_houston2", Arrays.asList(548, 500));
        this.routes.put("houston_neworleans", Arrays.asList(594, 522, 630, 516));
        this.routes.put("dallas_littlerock", Arrays.asList(552, 453, 573, 424));
        this.routes.put("ok_littlerock", Arrays.asList(533, 399, 569, 398));
        this.routes.put("kc_stlouis1", Arrays.asList(550, 304, 587, 303));
        this.routes.put("kc_stlouis2", Arrays.asList(551, 317, 587, 316));
        this.routes.put("omaha_chicago", Arrays.asList(530, 250, 558, 231, 594, 223, 628, 228));
        this.routes.put("duluth_chicago", Arrays.asList(556, 186, 589, 201, 624, 212));
        this.routes.put("duluth_toronto", Arrays.asList(561, 164, 597, 158, 632, 152, 669, 146, 704, 140, 740, 134));
        this.routes.put("sault_montreal", Arrays.asList(682, 83, 713, 62, 746, 48, 781, 38, 817, 34));
        this.routes.put("neworleans_littlerock", Arrays.asList(642, 489, 626, 457, 609, 427));
        this.routes.put("littlerock_saintlouis", Arrays.asList(599, 374, 608, 339));
        this.routes.put("saintlouis_chicagogreen", Arrays.asList(614, 283, 632, 253));
        this.routes.put("saintlouis_chicagowhite", Arrays.asList(625, 290, 645, 259));
        this.routes.put("chicago_toronto", Arrays.asList(661, 202, 689, 177, 722, 162, 754, 146));
        this.routes.put("neworleans_miami", Arrays.asList(696, 508, 729, 494, 765, 489, 800, 498, 830, 519, 854, 543));
        this.routes.put("neworleans_atlantaorange", Arrays.asList(678, 495, 694, 464, 716, 435, 740, 409));
        this.routes.put("neworleans_atlantayellow", Arrays.asList(668, 486, 685, 454, 707, 424, 732, 399));
        this.routes.put("littlerock_nashville", Arrays.asList(622, 399, 657, 390, 687, 370));
        this.routes.put("saintlouis_nashville", Arrays.asList(637, 335, 671, 346));
        this.routes.put("saintlouis_pittsburgh", Arrays.asList(637, 307, 668, 290, 699, 271, 730, 253, 761, 236));
        this.routes.put("chicago_pittsburghblack", Arrays.asList(681, 211, 717, 204, 753, 201));
        this.routes.put("chicago_pittsburghorange", Arrays.asList(685, 224, 721, 217, 757, 215));
        this.routes.put("atlanta_miami", Arrays.asList(861, 528, 839, 501, 816, 472, 794, 444, 771, 417));
        this.routes.put("atlanta_chareston", Arrays.asList(787, 398, 823, 400));
        this.routes.put("miami_charleston", Arrays.asList(877, 524, 862, 490, 855, 456, 851, 419));
        this.routes.put("atlanta_raleigh1", Arrays.asList(779, 376, 807, 351));
        this.routes.put("atlanta_raleigh2", Arrays.asList(772, 364, 799, 341));
        this.routes.put("raleigh_charleston", Arrays.asList(842, 345, 859, 370));
        this.routes.put("nashville_atlanta", Arrays.asList(727, 368));
        this.routes.put("nashville_raleigh", Arrays.asList(726, 335, 760, 321, 797, 318));
        this.routes.put("nashville_pittsburgh", Arrays.asList(701, 329, 721, 299, 748, 274, 775, 250));
        this.routes.put("pittsburgh_raleigh", Arrays.asList(800, 255, 808, 289));
        this.routes.put("pittsburgh_washington", Arrays.asList(818, 234, 850, 250));
        this.routes.put("washington_raleigh1", Arrays.asList(859, 278, 835, 306));
        this.routes.put("washington_raleigh2", Arrays.asList(868, 286, 845, 313));
        this.routes.put("pittsburgh_nywhite", Arrays.asList(810, 193, 839, 175));
        this.routes.put("pittsburgh_nygeen", Arrays.asList(816, 203, 845, 185));
        this.routes.put("ny_washingtonorange", Arrays.asList(871, 199, 874, 234));
        this.routes.put("ny_washingtonblack", Arrays.asList(883, 198, 885, 235));
        this.routes.put("pittsburgh_toronto", Arrays.asList(781, 186, 778, 149));
        this.routes.put("toronto_montreal", Arrays.asList(773, 98, 796, 70, 827, 50));
        this.routes.put("montreal_boston1", Arrays.asList(880, 51, 907, 74));
        this.routes.put("montreal_boston2", Arrays.asList(871, 62, 899, 84));
        this.routes.put("boston_nyyellow", Arrays.asList(905, 118, 886, 149));
        this.routes.put("boston_nyred", Arrays.asList(897, 154, 915, 125));
        this.routes.put("portland_slc", Arrays.asList(70, 171, 106, 181, 138, 197, 166, 218, 193, 243, 213, 272));
        this.routes.put("sanfran_slcorange", Arrays.asList(60, 345, 93, 335, 127, 323, 160, 312, 194, 301));
        this.routes.put("sanfran_slcwhite", Arrays.asList(64, 357, 98, 346, 130, 335, 164, 324, 198, 312));
        this.routes.put("sanfran_lapink", Arrays.asList(46, 385, 63, 416, 86, 445));
        this.routes.put("sanfran_layellow", Arrays.asList(35, 393, 53, 424, 76, 451));
        this.routes.put("la_lasvegas", Arrays.asList(115, 434, 142, 412));
        this.routes.put("la__phoenix", Arrays.asList(130, 458, 166, 456, 202, 461));
        this.routes.put("la_elpaso", Arrays.asList(130, 485, 162, 504, 196, 516, 231, 522, 267, 524, 303, 518));
        this.routes.put("lasvegas_slc", Arrays.asList(193, 392, 214, 362, 223, 327));
        this.routes.put("vancouver_seattle1", Arrays.asList(57, 88));
        this.routes.put("vancouver_seattle2", Arrays.asList(71, 87));
        this.routes.put("seattle_portland1", Arrays.asList(45, 137));
        this.routes.put("seattle_portland2", Arrays.asList(57, 141));
        this.routes.put("portland_sanfrancisco1", Arrays.asList(23, 190, 18, 227, 13, 263, 15, 297, 20, 333));
        this.routes.put("portland_sanfrancisco2", Arrays.asList(35, 190, 28, 227, 23, 263, 25, 297, 30, 333));
        this.routes.put("denver_santafe", Arrays.asList(350, 359, 348, 394));
        this.routes.put("sault_toronto", Arrays.asList(693, 109, 729, 116));
        this.routes.put("denver_omaha", Arrays.asList(376, 309, 407, 289, 441, 277, 476, 268));
        this.routes.put("montreal_ny", Arrays.asList(850, 70, 856, 106, 862, 140));
    }

    public static Map<String, List<Integer>> getRoutes() {
        if(instance == null) {
            instance = new TrainRoutes();
        }
        return instance.routes;
    }
}
