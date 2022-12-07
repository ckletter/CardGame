import java.util.ArrayList;

public class Board {
    private ArrayList<Card>[] board;
    public Board() {
        board = new ArrayList[4];
        for (int i = 0; i < 4; i++)
        {
            board[i] = new ArrayList<Card>();
        }
    }
    public int determinePile(Card c)
    {
        String suit = c.getSuit();
        if (suit.equals("Spades"))
        {
            return 0;
        }
        else if (suit.equals("Clubs"))
        {
            return 1;
        }
        else if (suit.equals("Diamonds"))
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }
    public boolean addCard(Card c)
    {
        int index = determinePile(c);
        if (board[index].isEmpty())
        {
            if (c.getRank().equals("A"))
            {
                board[index].add(c);
                return true;
            }
        }
        else if (board[index].get(board[index].size() - 1).getPoint() == c.getPoint() - 1)
        {
            board[index].add(c);
            return true;
        }
        return false;
    }

    public ArrayList<Card>[] getBoard() {
        return board;
    }
    public boolean checkWin(int mode) {
        if (mode == 1) {
            for (int i = 0; i < 4; i++) {
                if (board[i].size() == 13) {
                    for (int j = 0; j < 13; j++) {
                        if (board[i].get(j).getPoint() != (j + 1)) {
                            return false;
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            for (int i = 0; i < 4; i++) {
                if (board[i].size() == 4) {
                    for (int j = 0; j < 4; j++) {
                        if (board[i].get(j).getPoint() != (j + 1)) {
                            return false;
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
            return true;
        }
    }
}
