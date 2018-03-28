import java.util.*;
public class Player
{
	private String name;
	private int points;
	private ArrayList<String> words;

	public Player() // the default constructor
	{
		name = "Player 1";
		points = 0;
		words = new ArrayList<String>();
	}

	public Player(String name)
	{
		this.name = name;
	}
	
	public int getPoints() // getter
	{
		return points;
	}

	public String getName()
	{
		return name;
	}

	public void addPoints(String word) // setter
	{
		points += word.length();
	}

	public String toString()
	{
		return name + " has " + points + " points!";
	}
}
