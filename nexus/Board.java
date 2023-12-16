/**
 * Represents the state of the board for the game of Ipurdle.
 * 
 * @authors
 *   Name: Omeir Haroon, Student Number: 61810
 *   Name: Matilde Brandão, Student Number: 61814
 */


public class Board
{
	int wordSize;
	int maxGuesses;
	int currentGuess;
	String[] guesses;
	Clue[] clue;
	/**
	 * Creates a board for the game of Ipurdle with the provided initial state (empty).
	 * 
	 * @param wordSize
	 *   The size of the words that can be stored on the board.
	 * @param maxGuesses
	 *   The maximum number of guesses.
	 * @requires
	 *   wordSize >= 1
	 * @requires
	 *   maxGuesses >= 1
	 */
	public Board(int wordSize, int maxGuesses)
	{
		this.wordSize = wordSize;
		this.maxGuesses = maxGuesses;
		this.currentGuess = 0;
		this.guesses = new String[maxGuesses];
		this.clue = new Clue[maxGuesses];
	}

	/**
	 * Returns the size of the words that can be stored on the board.
	 * 
	 * @return
	 *   The size of the words.
	 */
	public int wordLength()
	{
		return this.wordSize;
	}

	/**
	 * Returns the maximum number of guesses.
	 * 
	 * @return
	 *   The maximum number of guesses.
	 */
	public int maxGuesses()
	{
		return this.maxGuesses;
	}

	/**
	 * Returns the number of guesses that have been made.
	 * 
	 * @return
	 *   The number of guesses.
	 */
	public int guesses()
	{
		return this.currentGuess;
	}

	/**
	 * Inserts the provided guess and clue, assuming that guess.length() == clue.length() == wordLength() and guesses() < maxGuesses().
	 * 
	 * @param guess
	 *   The guess to be inserted.
	 * @param clue
	 *   The clue to be inserted.
	 */
	public void insertGuessAndClue(String guess, Clue clue)
	{
		this.guesses[this.currentGuess] = guess;
		this.clue[this.currentGuess] = clue;
		this.currentGuess++;
	}

	/**
	 * Returns a textual representation of the board's state.
	 * 
	 * @return
	 *   The textual representation of the board's state.
	 */
	public String toString()
	{
		String result = "";
		for (int i = 0; i < this.currentGuess; i++)
		{
			result += "+---------------+\n";
			result += "| " + this.guesses[i] + " | " + this.clue[i].toString() + " |\n";
		}
		result += "+---------------+\n";
		return result;
	}
}
