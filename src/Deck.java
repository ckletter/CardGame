import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public void setCards(ArrayList<Card> cards)
    {
        this.cards = cards;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public void setCardsLeft(int cardsLeft)
    {
        this.cardsLeft = cardsLeft;
    }
    public Deck(String[] ranks, String[] suits, int[] points)
    {
        cards = new ArrayList<Card>();
        for (int i = 0; i < suits.length; i++)
        {
            for (int j = 0; j < ranks.length; j++)
            {
                Card c = new Card(ranks[j], suits[i], points[j]);
                cards.add(c);
            }
        }
        cardsLeft = 52;
        shuffle();
    }
    public boolean isEmpty()
    {
        return (cardsLeft == 0);
    }
    public Card deal()
    {
        cardsLeft--;
        return cards.get(cardsLeft);
    }
    public void shuffle()
    {
        for (int i = cardsLeft - 1; i >= 0; i--)
        {
            int random = (int)(Math.random() * 52);
            swap(i, random);
        }
    }
    public void swap(int index1, int index2)
    {
        Card c = cards.get(index1);
        cards.set(index1, cards.get(index2));
        cards.set(index2, c);
    }
}
