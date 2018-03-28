import java.util.*;
import java.io.*;

public class BoggleRunner
{
	public static void main(String[] args) throws IOException
	{
		Boggle game = new Boggle();
		Scanner scan = new Scanner(System.in);

		System.out.println();
		
		for (int i = 1; i <= 3; i++)	//number of rounds
		{
			for (int j = 0; j < 2; j++)	//2 players
			{
				System.out.println("Press enter to begin the turn");
				scan.nextLine();
				
				long startTime = System.currentTimeMillis();
				long elapsedTime = 0L;
				
				game.printBoard();
				System.out.println();
				System.out.println("It's now " + game.getCurrentPlayer().getName() + "'s turn!");
				System.out.println();
				System.out.println("Type in all of the words you see! Remember, they must connect!");
				while (elapsedTime < 20*1000) 
				{					
					String word = scan.nextLine().toUpperCase();
					if (game.checkBoardValidity(word) == true)
					{
						game.getCurrentPlayer().addPoints(word);
					}
					if (game.checkBoardValidity(word) == false)
					{
						//System.out.println("Input was invalid!");
					}
				    elapsedTime = (new Date()).getTime() - startTime;
				}
				System.out.println(game.getCurrentPlayer().getName() + "'s turn is now over!");
				game.changeCurrentPlayer();
			}
			System.out.println("Round " + i + " is now over." );
			System.out.println(game.getPlayer1());
			System.out.println(game.getPlayer2());
			game.initializeBoard();
		}
	}

}
