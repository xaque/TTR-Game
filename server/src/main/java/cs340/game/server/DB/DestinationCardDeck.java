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

/**
 * The deck of cards is contained in a list. Index 0 of the list contains the top card of the deck.
 * Cards will drawn from the beginning of the list and discarded to the end of the list.
 */
public class DestinationCardDeck {
    private List<DestinationCard> cards;
    private int size;


    public DestinationCardDeck() {
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
            shuffle(5);
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
            while (this.cards.size() != 0) {
                int index = rand.nextInt(this.cards.size());
                DestinationCard removedCard = this.cards.remove(index);
                shuffledList.add(removedCard);
            }
            this.cards = shuffledList;
            shuffledList.clear();
        }
    }

    public List<DestinationCard> drawCards() {
        int numberOfDrawnCards;
        if(this.size >= 3) {
            numberOfDrawnCards = 3;
        }
        else {
            numberOfDrawnCards = this.size;
        }
        //TODO contingency if deck is empty? (size = 0)
        List<DestinationCard> drawnCards = new ArrayList<>();
        for(int i = 0; i < numberOfDrawnCards; i++) {
            DestinationCard drawnCard = this.cards.remove(0);
            this.size -= 1;
            drawnCards.add(drawnCard);
        }
        return drawnCards;
    }

    public void returnCards(List<DestinationCard> returnedCards) {
        this.cards.addAll(returnedCards);
        this.size += returnedCards.size();
    }

    public int getSize() {
        return this.size;
    }
}