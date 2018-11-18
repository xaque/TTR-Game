package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs340.game.shared.Color;
import cs340.game.shared.models.TrainCard;

/**
 * Created by Stephen on 10/31/2018.
 */

/**
 * The deck of cards is contained in a list. Index 0 of the list contains the top card of the deck.
 * Cards will drawn from the beginning of the list and discarded to the discard pile in this class.
 */
public class TrainCardDeck {
    private ArrayList<TrainCard> cards;
    private ArrayList<TrainCard> faceUpCards;
    private ArrayList<TrainCard> discardPile;
    private int size;

    /**
     * Constructor for a TrainCardDeck. Initialization hard coded to have the correct number of each
     * color of train card in the deck. Shuffles deck to randomize order at game start
     * @pre None
     * @post deck initialized with all cards, size 110
     */
    public TrainCardDeck() {
        cards = new ArrayList<>();
        faceUpCards = new ArrayList<>();
        discardPile = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            cards.add(new TrainCard(Color.RED));
            cards.add(new TrainCard(Color.YELLOW));
            cards.add(new TrainCard(Color.GREEN));
            cards.add(new TrainCard(Color.BLUE));
            cards.add(new TrainCard(Color.BLACK));
            cards.add(new TrainCard(Color.WHITE));
            cards.add(new TrainCard(Color.PINK));
            cards.add(new TrainCard(Color.ORANGE));
            cards.add(new TrainCard(Color.WILD));
        }
        cards.add(new TrainCard(Color.WILD));
        cards.add(new TrainCard(Color.WILD));
        size = 110;
        shuffle(5);
    }

    /**
     * Shuffles the deck by randomizing the order of the cards
     * @param howManyTimes number of times to shuffle the deck
     * @pre None
     * @post deck is same size with the same number of each color of TrainCard
     */
    public void shuffle(int howManyTimes) {
        Random rand = new Random();
        ArrayList<TrainCard> shuffledList = new ArrayList<>();
        for(int i = 0; i < howManyTimes; i++) {
            while (this.cards.size() != 0) {
                int index = rand.nextInt(this.cards.size());
                TrainCard removedCard = this.cards.remove(index);
                shuffledList.add(removedCard);
            }
            //this.cards = shuffledList;
            for(int j = 0; j < shuffledList.size(); j++){
                this.cards.add(shuffledList.get(j));
            }
            shuffledList.clear();
        }
    }

    /**
     * Creates the list of 5 face up cards whose colors are visible to the players. Drawn from the
     * top of the deck.
     * @pre Deck must be initialized with the constructor
     * @post this.faceUpCards has 5 cards and drawFaceUpCard is now available to call
     */
    public void resetFaceUpCards() {
        discardCards(this.faceUpCards);
        for(int i = 0; i < 5; i++) {
            if(this.cards.size() == 0){
                return;
            }
            this.faceUpCards.add(drawCardFromDeck());
        }
    }

    /**
     * Draws 4 cards for a player to start the game. Called as part of StartGameCommand
     * @return a list of 4 TrainCards drawn from the top of the deck
     * @pre A player is in a game that is starting
     * @post The top 4 cards have been removed from the deck
     */
    public ArrayList<TrainCard> drawStartingCards() {
        ArrayList<TrainCard> drawnCards = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            if(this.cards.size() == 0){
                return drawnCards;
            }
            TrainCard drawnCard = this.cards.remove(0);
            this.size -= 1;
            drawnCards.add(drawnCard);
        }
        return drawnCards;
    }

    /**
     * Draws a card from the top of the deck. If this makes the deck empty, make the discard pile
     * into the new deck and shuffle it.
     * @return the TrainCard at the top of the deck
     * @pre None
     * @post if deck is size 1 when this is called, deck is replaced with shuffled discard pile
     */
    public TrainCard drawCardFromDeck() {
        TrainCard drawnCard = this.cards.remove(0);
        this.size -= 1;
        if(this.size == 0) {
            cards = discardPile;
            this.size = discardPile.size();
            discardPile = null;
            shuffle(5);
        }
        return drawnCard;
    }

    /**
     * Removes the faceUp card at position position. Replaces this card with the top card of the
     * deck.
     * @param position the location of the face up card to draw
     * @return the TrainCard from the given position
     * @pre deck has been initialized by calling the constructor
     * @post deck is 1 smaller and replaced by the discard pile if this makes the deck empty
     */
    public TrainCard drawFaceUpCard(int position) {
        TrainCard replacementCard = drawCardFromDeck();
        TrainCard drawnCard = faceUpCards.get(position);
        faceUpCards.set(position, replacementCard);
        return drawnCard;
    }

    public boolean checkFaceUpLocomotives() {
        boolean cardsReset = false;
        boolean tooManyLocomotives;
        do {
            int faceUpLocomotiveCount = 0;
            for (TrainCard card : this.faceUpCards) {
                if (card.getColor() == Color.WILD) {
                    faceUpLocomotiveCount++;
                }
            }
            if (faceUpLocomotiveCount >= 3) {
                tooManyLocomotives = true;
                resetFaceUpCards();
                cardsReset = true;
            }
            else {
                tooManyLocomotives = false;
            }
        }while(tooManyLocomotives);
        return cardsReset;
    }

    /**
     * Discards the cards sent by a player meant to claim a route. Discard pile gets these cards.
     * @param cardsToDiscard list of cards the player is discarding
     * @pre None
     * @post Discard pile grows by the number of cards sent to the function
     */
    public void discardCards(ArrayList<TrainCard> cardsToDiscard) {
        discardPile.addAll(cardsToDiscard);
    }

    /**
     * Returns the size of the deck.
     * @return the size of the deck of remaining cards
     * @pre None
     * @post None
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the list of face up cards
     * @return the list of face up cards
     * @pre None
     * @post None
     */
    public ArrayList<TrainCard> getFaceUpCards() {
        return this.faceUpCards;
    }
}
