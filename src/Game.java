import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player p;
    private Deck d;
    private Board b;
    private int deckIndex;
    private int mode;
    public Game()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("What is your name?");
        p = new Player(s.nextLine());
        b = new Board();
        deckIndex = 0;
    }
    public void printInstructions()
    {
        System.out.println("\nLet's play solitaire!\n\nYour goal as a player is to transfer cards from their starting pos"
                + "ition to the card foundation piles on the board. If you do this and observe all the solitaire \ngame rules, you win"
    + " the game. \n\nHere are the rules of playing solitaire. You can only move cards from one pile to another in your hand while obser"
                + "ving the specific solitaire rules. \nFor instance, one can move a 6 on top of a 7 or a Queen on top of"
                + " a King. Moving a card from one pile to the next frees up the next face-down card. \nOnce a pile becom"
                + "es empty, you can move a King to that empty pile. You should fill fou"
                + "ndations on the board in ascending order and ensure that they match \nthe appropriate suit. E.g., two of hearts can"
                + "only go on top of ace of hearts, followed by three of hearts. Stockpile cards can only be used when "
                + "a player runs \nout of moves. You can add stockpile cards to your hand or the board.\n" +
                "\n");
        System.out.println("Are you ready to play? (press enter)");
    }
    public void playGame()
    {
        Scanner s = new Scanner(System.in);
        printInstructions();
        s.nextLine();
        chooseGameMode();
        createDeck();
        createHand();
        do {
            printBoard();
            printHand();
            printDeck();
            userTurn();
        }
        while (!b.checkWin(mode));
        congratulateUser();
    }
    public void printHand()
    {
        for (int i = 0; i < 7; i++)
        {
            if (p.getHand()[i].isEmpty())
            {
                System.out.print((i + 1) + ".\t*pile is empty*\t\t");
            }
            else
            {
                System.out.print((i + 1) + ".\t" + p.getHand()[i].get(0) + "\t\t");
            }
        }
        System.out.println("\n");
    }
    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
    public void createHand()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < i + 1; j++)
            {
                p.getHand()[i].add(d.deal());
            }
        }
    }
    public void userTurn()
    {
        Scanner s = new Scanner(System.in);
        String move;
        do
        {
            System.out.println("Would you like to transfer a card, play a card, or flip the deck? (transfer/play/flip)");
            move = s.nextLine();
        }
        while (!move.equals("play") && !move.equals("transfer") && !move.equals("flip"));
        if (move.equals("play"))
        {
            int choice;
            do
            {
                System.out.println("What card would you like to play? (1/2/3/4/5/6/7/8)");
                choice = s.nextInt();
                s.nextLine();
            }
            while (choice < 1 || choice > 8);
            boolean valid = false;
            if (choice == 8)
            {
                playFromDeck();
            }
            else if (valid = b.addCard(p.getHand()[choice - 1].get(0)))
            {
                p.getHand()[choice - 1].remove(0);
            }
            else
            {
                printHand();
                System.out.println("You can't play that card out.");
            }
        }
        else if (move.equals("flip"))
        {
            flipDeck();
        }
        else
        {
            transferCard();
        }
    }
    public void printBoard()
    {
        System.out.println("\n\n");
        int emptyPiles = 0;
        for (int i = 0; i < 4; i++)
        {
            ArrayList<Card>[] board = b.getBoard();
            if (board[i].isEmpty())
            {
                emptyPiles++;
                continue;
            }
            else
            {
                System.out.print(board[i].get(board[i].size() - 1) + "\t\t");
            }
        }
        if (emptyPiles == 4)
        {
            System.out.println("*the board is currently empty*");
        }
        System.out.println("\n\n");
    }
    public void printDeck()
    {
        if (deckIndex == 0)
        {
            return;
        }
        System.out.println("Your Deck:\n\n8.\t" + getFromDeck() + "\n\n");
    }
    public void flipDeck()
    {
        if (deckIndex == d.getCardsLeft() - 1)
        {
            deckIndex = 0;
        }
        deckIndex += 3;
        if ((deckIndex > d.getCardsLeft() - 1))
        {
            deckIndex = d.getCardsLeft() - 1;
        }
    }
    public void playFromDeck()
    {
        Scanner s = new Scanner(System.in);
        String choice;
        do
        {
            System.out.println("Would you like to add the card to the board or to your hand? (board/hand)");
            choice = s.nextLine();
        }
        while (!choice.equals("board") && !choice.equals("hand"));
        if (choice.equals("hand")) {
            int pile;
            do {
                System.out.println("What pile would you like to add to? (1/2/3/4/5/6/7)");
                pile = s.nextInt();
                s.nextLine();
            }
            while (pile < 1 || pile > 7);
            if (checkValidPile((pile - 1), getFromDeck()))
            {
                System.out.println("\nCard successfully added to your hand.");
            }
            else
            {
                System.out.println("\nUnable to add card to your hand (value must be one less than card in your hand).");
            }
        }
        else
        {
            if (b.addCard(getFromDeck()))
            {
                d.setCardsLeft((d.getCardsLeft() - 1));
                d.getCards().remove(deckIndex);
                System.out.println("\nCard successfully added to the board.");
            }
            else
            {
                System.out.println("\nUnable to add card to the board.");
            }
        }
    }
    public boolean checkValidPile(int index, Card c)
    {
        if (p.getHand()[index].isEmpty()) {
            if (c.getRank().equals("K")) {
                p.getHand()[index].add(0, c);
                d.getCards().remove(deckIndex);
                d.setCardsLeft((d.getCardsLeft() - 1));
            }
        }
        else if ((p.getHand()[index].get(0).getPoint() == c.getPoint() + 1))
        {
            p.getHand()[index].add(0, c);
            d.getCards().remove(deckIndex);
            d.setCardsLeft((d.getCardsLeft() - 1));
            return true;
        }
        return false;
    }
    public Card getFromDeck()
    {
        return d.getCards().get(deckIndex);
    }
    public void transferCard()
    {
        Scanner s = new Scanner(System.in);
        int choice;
        do
        {
            System.out.println("What pile would you like to transfer your card from? (1/2/3/4/5/6/7)");
            choice = s.nextInt();
            s.nextLine();
        }
        while (choice < 1 || choice > 7);
        int choice2;
        do
        {
            System.out.println("What pile would you like to transfer your card to? (1/2/3/4/5/6/7)");
            choice2 = s.nextInt();
            s.nextLine();
        }
        while (choice2 < 1 || choice2 > 7);
        if (p.getHand()[choice - 1].isEmpty())
        {
            System.out.println("Cannot transfer card from empty pile.");
            return;
        }
        if (p.getHand()[choice2 - 1].isEmpty())
        {
            if (p.getHand()[choice - 1].get(0).getRank().equals("K"))
            {
                p.getHand()[choice2 - 1].add(0, p.getHand()[choice - 1].get(0));
                p.getHand()[choice - 1].remove(0);
                System.out.println("\nCard successfully transferred.");
            }
            else
            {
                System.out.println("Cannot successfully transfer card (must be rank K).");
            }
        }
        else if (p.getHand()[choice - 1].get(0).getPoint() + 1 == p.getHand()[choice2 - 1].get(0).getPoint())
        {
            p.getHand()[choice2 - 1].add(0, p.getHand()[choice - 1].get(0));
            p.getHand()[choice - 1].remove(0);
            System.out.println("\nCard successfully transferred.");
        }
        else
        {
            System.out.println("\nUnable to successfully transfer card (value must be one less).");
        }
    }
    public void congratulateUser()
    {
        System.out.println("\nYou won the game, " + p.getName() + "! You filled up all of the foundations on the board.");
    }
    public void chooseGameMode()
    {
        Scanner s = new Scanner(System.in);
        String choice;
        do
        {
            System.out.println("Would you like to play on easy or hard mode? (easy/hard)");
            choice = s.nextLine();
        }
        while (!choice.equals("easy") && !choice.equals("hard"));
        if (choice.equals("easy"))
        {
            mode = 0;
        }
        else
        {
            mode = 1;
        }
    }
    public void createDeck()
    {
        if (mode == 0)
        {
            String[] ranks = {"A", "2", "3", "4"};
            String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
            int[] points = {1, 2, 3, 4};
            d = new Deck(ranks, suits, points);
        }
        else
        {
            String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
            int[] points = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
            d = new Deck(ranks, suits, points);
        }
    }
}