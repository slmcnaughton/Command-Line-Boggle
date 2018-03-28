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
			for (int j = 0; j < game.getPlayers().length; j++)
			{
				System.out.println("Press enter to begin " + game.getCurrentPlayer().getName() + "'s turn");
				scan.nextLine();
				
				long startTime = System.currentTimeMillis();
				long elapsedTime = 0L;
				
				game.printBoard();
				System.out.println();
				System.out.println(game.getCurrentPlayer().getName() + 
						", type in all of the words you see! Remember, they must connect!");
				while (elapsedTime < 60*1000) 
				{					
					String word = scan.nextLine().toUpperCase();
					if (game.checkBoardValidity(word))
					{
						game.getCurrentPlayer().getWords().add(word);
					}
				    elapsedTime = (new Date()).getTime() - startTime;
				}
				System.out.println(game.getCurrentPlayer().getName() + "'s turn is now over!");
				game.changeCurrentPlayer();
			}
			System.out.println("Round " + i + " is now over." );
			
			//Print out each player's lists
			for(int j = 0; j < game.getPlayers().length; j++)
			{
				System.out.println(game.getPlayers()[j].getName() + " words: " + game.getPlayers()[j].getWords());
			}
			System.out.println();
			
			//Eliminate duplicates
			game.crossReference();
			
			//Print out edited Lists
			for(int j = 0; j < game.getPlayers().length; j++)
			{
				System.out.println(game.getPlayers()[j].getName() + "'s edited list: " + game.getPlayers()[j].getWords());
			}
			System.out.println();
			
			//Print out round/cumulative scores
			//Print out edited Lists
			for(int j = 0; j < game.getPlayers().length; j++)
			{
				System.out.println(game.getPlayers()[j]);
			}
			System.out.println();
			
			game.initializeBoard();
		}
		game.printWinner();
	}
	

}
