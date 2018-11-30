package cs340.game.server.DB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<DestinationCard> cards;

    /**
     * Constructor for a DestinationCardDeck. Initializes all cards by reading info from
     * server/DestinationCardSetupText.txt.
     * @pre None
     * @post deck initialized with all cards, size 30
     * @exception cs340.game.shared.ServerException thrown if text file cannot be accessed
     */
    public DestinationCardDeck() {
        cards = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("server/DestinationCardSetupText.txt");
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
            shuffle();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Cannot find the DestinationCardSetupText file to initialize DestinationCardDeck.");
        }
        catch(IOException ex) {
            System.out.println("Cannot read from DestinationCardSetupText file.");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Shuffles the deck by randomizing the order of the cards
     * @pre None
     * @post None
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Draws cards from the deck - 3 if possible, but if the deck is too small, draws the remaining
     * cards.
     * @return a list of DestinationCards - length of 3 or less
     * @pre None
     * @post None
     */
    public ArrayList<DestinationCard> drawCards() {
        int numberOfDrawnCards;
        if(getSize() >= 3) {
            numberOfDrawnCards = 3;
        }
        else {
            numberOfDrawnCards = getSize();
        }
        //TODO contingency if deck is empty? (size = 0)
        ArrayList<DestinationCard> drawnCards = new ArrayList<>();
        for(int i = 0; i < numberOfDrawnCards; i++) {
            if(getSize() == 0){
                return drawnCards;
            }
            DestinationCard drawnCard = this.cards.remove(0);
            drawnCards.add(drawnCard);
        }
        return drawnCards;
    }

    /**
     * Puts the returned/discarded cards back in the deck. Increases deck size accordingly
     * @param returnedCards
     */
    public void returnCards(ArrayList<DestinationCard> returnedCards) {
        if(returnedCards != null) {
            this.cards.addAll(returnedCards);
            shuffle();
        }
    }

    public int getSize() {
        return this.cards.size();
    }
}