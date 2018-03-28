import java.util.*;
import java.io.*;

public class Boggle
{
	private char[][] board;
	private Player currentPlayer;
	private Player player1;
	private Player player2;
	private List<String> dictionary;
	private List<String> masterWordList;
	final int BOARD_SIZE = 4;

	public Boggle() throws IOException
	{
		dictionary = new ArrayList<String>();
		masterWordList = new ArrayList<String>();
		populateDictionary();	
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter name of player 1:");
		player1 = new Player(scan.nextLine());
		System.out.println("Enter name of player 2:");
		player2 = new Player(scan.nextLine());
		
		board = new char[BOARD_SIZE][BOARD_SIZE];
		initializeBoard();
		
		masterWordList.clear();
		findAllWords();	//Make masterWordList
		//System.out.println(masterWordList);
		
		int a = (int) ( Math.random() * 2 );
		if (a == 0)
		{
			currentPlayer = player1;
		}
		else
		{
			currentPlayer = player2;
		}
	}

	public void initializeBoard()
	{
		List<String> dice = new ArrayList<String>(Arrays.asList("AAAFRS", "AAEEEE", "AAFIRS",
				"ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
				"CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR", "DHLNOR", "DHLNOR",
				"EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "IPRRRY",
				"NOOTUW", "OOOTTU"));
		for (int row = 0; row < BOARD_SIZE; row++)
		{
			for (int col = 0; col < BOARD_SIZE; col++)
			{
				String temp = dice.remove((int)(Math.random()*dice.size()));
				
				board[row][col] = temp.charAt((int)(Math.random()*temp.length()));
			}
		}
		masterWordList.clear();
		findAllWords();	//Make masterWordList
//		System.out.println(masterWordList);
		
	}

	public void printBoard()
	{
		System.out.println(" -----------------");
		for (int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				System.out.print(" | " + board[i][j]);
			}
			System.out.println(" |");
			System.out.println(" -----------------");
		}
	}

	/**
	 * Determines whether a user-found word is on the masterWordList
	 * 
	 * @param 	word	the word to test
	 * @return			true if word is on masterWordList
	 * 					false if word does not exist on masterWordList
	 */
	public boolean checkBoardValidity(String word)
	{
		return ( masterWordList.contains(word));
	}
	
	/**
	 * Adds all English words to our List, dictionary, 
	 * from the British Dictionary text file
	 * 
	 * @throws IOException
	 */
	private void populateDictionary() throws IOException
	{
		Scanner scan = new Scanner(new File("BritishDictionary.txt"));
		
		while (scan.hasNext() )
		{
			dictionary.add(scan.next().toUpperCase());
		}
		scan.close();
	}
	
	/**
	 * Once board is created, find all possible words and stores
	 * them in List named masterWordList
	 */
	private void findAllWords() 
	{
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[x].length; y++)
			{
				List<Point> listOfPoints = new ArrayList<Point>();
				wordBuilder(x, y, "", listOfPoints);
			}
		}
	}
	
	/**
	 * Fills masterWordList with all real words that can be created from the board
	 * by recursively adding surrounding letters and checking the created prefix
	 * with the dictionary
	 * 
	 * @param 	row		the row of the current letter
	 * @param 	col		the col of the current letter
	 * @param 	word	the word/prefix of a word so far
	 */
	private void wordBuilder(int row, int col, String word, List<Point> listOfPoints)
	{
		word += board[row][col];
		listOfPoints.add(new Point(row, col));
			
		//Check to see if word is a valid word (check against your word list).
	    //If word, add to wordList
		if ( word.length() > 2 && wordBinarySearch(word) )
		{
			masterWordList.add(word);
		}
		
		/*Check word list to see if any words contain current prefix. If not,
	     then there's no point in continuing further (return). IE if AQZ isn't the 
	     start of any word at all in the list, no reason to keep adding letters, it's
	     never going to make a word.  */
		if (!prefixBinarySearch(word))
		{
			return;
		}
		else
		{
			//Otherwise recursively call this method moving left/right/up/down
			//Making sure we don't go out of bounds
			
			for(int newRow = row-1; newRow <= row+1; newRow++)
			{
				for(int newCol = col - 1; newCol <= col + 1; newCol++) 
				{
					// newRow/newCol point to an index on the board
					// this index isn't the same letter
					// this letter has not already been used to create a word
					if(row > 0 && row < board.length - 1 && col > 0 && col < board[row].length - 1 
							&& !(row == newRow && col == newCol)
							&& !isPointInList(listOfPoints, row, col + 1) )
					{
						wordBuilder(newRow, newCol, word, new ArrayList<>(listOfPoints));
					}
				}
			}
			
		}
	}
	public boolean isPointInList(List<Point> myList, int row, int col)
	{
		for (int i = 0; i< myList.size(); i++)
		{
			Point tempPoint = myList.get(i);
			if(tempPoint.getX() == row && tempPoint.getY() == col)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a word in its entirety can be found in the dictionary
	 * 
	 * @param	word	The sequence of letters to begin a word in the dictionary
	 * @return			true if word is found
	 * 					false if word is not found
	 */
	private boolean wordBinarySearch(String word) 
	{
	    int first = 0;
	    int last = dictionary.size() - 1;
	    int mid = 0;

	    while (first <= last) 
	    {
	        mid = (first + last) / 2;
	        String tempWord = dictionary.get(mid);
	        int c = tempWord.compareTo(word);
	        
	        if (c > 0) 
	        {
	        	last = mid - 1;
	        } 
	        else if (c == 0) 
	        {
	            return true;
	        } 
	        else
	        	first = mid + 1;
	    }
	    return false;
	}

	
	
	/**
	 * Searches dictionary and determines whether there exists a word whose 
	 * prefix matches the given string
	 * 
	 * @param	prefix	The sequence of letters to begin a word in the dictionary
	 * @return			true if any word matches the prefix
	 * 					false if no word matches the prefix
	 */
	private boolean prefixBinarySearch(String prefix) 
	{
	    int first = 0;
	    int last = dictionary.size() - 1;
	    int mid = 0;

	    while (first <= last) 
	    {
	        mid = (first + last) / 2;
	        String tempWord = dictionary.get(mid);
	        int c;
	        if (tempWord.startsWith(prefix) )
	        	c = 0;
	        else 
	        	c = tempWord.compareTo(prefix);
	        
	        if (c > 0) 
	        {
	        	last = mid - 1;
	        } 
	        else if (c == 0) 
	        {
	            return true;
	        } 
	        else
	        	first = mid + 1;
	    }
	    return false;
	}

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public Player getPlayer2()
	{
		return player2;
	}
	
	public void changeCurrentPlayer()
	{
		if (currentPlayer == player1)
		{
			currentPlayer = player2;
		}
		else
		{
			currentPlayer = player1;
		}
	}
}