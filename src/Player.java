import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card>[] hand;
    int points;
    public Player(String name)
    {
        this.name = name;
        points = 0;
        hand = new ArrayList[7];
        for (int i = 0; i < 7; i++)
        {
            hand[i] = new ArrayList<Card>();
        }
    }
    public String getName()
    {
        return name;
    }

    public ArrayList<Card>[] getHand()
    {
        return hand;
    }

    public int getPoints() {
        return points;
    }
    public void addPoints(int points)
    {
        this.points += points;
    }
    public String toString()
    {
        return name + " has " + points + " points\n" + name + "'s cards: " + hand;
    }
}
