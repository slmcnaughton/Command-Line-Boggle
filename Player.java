import java.util.*;
public class Player
{
	private String name;
	private int points;
	private int roundScore;
	private HashSet<String> words;

	public Player() // the default constructor
	{
		name = "Player 1";
		points = 0;
		words = new HashSet<String>();
	}

	public Player(String name)
	{
		this.name = name;
		points = 0;
		words = new HashSet<String>();
	}
	

	public String getName()
	{
		return name;
	}

	public HashSet<String> getWords()
	{
		return words;
	}

	
	public int getRoundScore()
	{
		return roundScore;
	}

	public void setRoundScore(int roundScore)
	{
		this.roundScore = roundScore;
	}

	public int getPoints() // getter
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}

	
	public String toString()
	{
		return name + " scored " + roundScore + " points and now has " + points + " points!";
	}
}
