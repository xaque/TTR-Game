package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs340.game.shared.Color;
import cs340.game.shared.models.TrainCard;

/**
 * Created by Stephen on 10/31/2018.
 */

public class TrainCardDeck {
    private List<TrainCard> cards;
    private List<TrainCard> faceUpCards;
    private List<TrainCard> discardPile;
    private int size;

    public TrainCardDeck() {
        for(int i = 0; i < 12; i++) {
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

    public void shuffle(int howManyTimes) {
        Random rand = new Random();
        List<TrainCard> shuffledList = new ArrayList<>();
        for(int i = 0; i < howManyTimes; i++) {
            while (this.cards.size() != 0) {
                int index = rand.nextInt(this.cards.size());
                TrainCard removedCard = this.cards.remove(index);
                shuffledList.add(removedCard);
            }
            this.cards = shuffledList;
            shuffledList.clear();
        }
    }

    public List<TrainCard> drawStartingCards() {
        List<TrainCard> drawnCards = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            TrainCard drawnCard = this.cards.remove(0);
            this.size -= 1;
            drawnCards.add(drawnCard);
        }
        return drawnCards;
    }

    public TrainCard drawCardFromDeck() {
        TrainCard drawnCard = this.cards.remove(0);
        this.size -= 1;
        if(this.size == 0) {
            cards = discardPile;
            shuffle(5);
        }
        return drawnCard;
    }

    public TrainCard drawFaceUpCard(int position) {
        TrainCard replacementCard = drawCardFromDeck();
        TrainCard drawnCard = faceUpCards.get(position);
        faceUpCards.set(position, replacementCard);
        return drawnCard;
    }

    public void discardCards(List<TrainCard> cardsToDiscard) {
        discardPile.addAll(cardsToDiscard);
    }
}
