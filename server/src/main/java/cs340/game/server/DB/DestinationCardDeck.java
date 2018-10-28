package cs340.game.server.DB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs340.game.shared.City;
import cs340.game.shared.models.DestinationCard;

/**
 * Created by Stephen on 10/26/2018.
 */

public class DestinationCardDeck {
    private List<DestinationCard> cards;
    private int size;

    private static DestinationCardDeck instance;

    public static DestinationCardDeck getInstance() {
        if(instance == null) {
            instance = new DestinationCardDeck();
        }
        return instance;
    }

    private DestinationCardDeck() {
        try {
            FileReader fileReader = new FileReader("DestinationCardSetupText.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] strArray = line.split(",");
                City city1 = City.valueOf(strArray[0].toUpperCase());
                City city2 = City.valueOf(strArray[1].toUpperCase());
                int points = Integer.parseInt(strArray[2]);
                DestinationCard card = new DestinationCard(city1, city2, points);
                this.cards.add(card);
            }
            this.size = 30; // initial deck has 30 cards
        }
        catch(FileNotFoundException ex) {
            System.out.println("Cannot find the DestinationCardSetupText file to initialize DestinationCardDeck.");
        }
        catch(IOException ex) {
            System.out.println("Cannot read from DestinationCardSetupText file.");
        }
    }

    public void shuffle(int howManyTimes) {
        Random rand = new Random();
        List<DestinationCard> shuffledList = new ArrayList<>();
        for(int i = 0; i < howManyTimes; i++) {
            while (cards.size() != 0) {
                int index = rand.nextInt(cards.size());
                DestinationCard removedCard = cards.remove(index);
                shuffledList.add(removedCard);
            }
            cards = shuffledList;
            shuffledList.clear();
        }
    }
}