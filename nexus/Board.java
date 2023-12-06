public class Board
{
	private int wordSize;
	private int maxGuesses;
	private int currentGuess;
	private String guesses[];
	private Clue[] clue;

	/**
	 * 
	 * @param wordSize
	 * @param maxGuesses
	 * @requires wordSize >= 1 
	 * @requires maxGuesses >= 1
	 * 
	 */
	public Board(int wordSize, int maxGuesses)
	{
// cria um tabuleiro para o jogo de Ipurdle com os dados fornecidos no estado inicial (vazio)
		this.wordSize = wordSize;
		this.maxGuesses = maxGuesses;
		this.currentGuess = 0;
		this.guesses = new String[maxGuesses];
		this.clue = new Clue[maxGuesses];
	}

	public int wordLength()
	{
// permite saber o tamanho das palavras que podem ser guardadas no tabuleiro
		return this.wordSize;
	
	}

	public int maxGuesses()
	{
// permite saber o número máximo de tentativas
		return this.maxGuesses;
	}

	public int guesses()
	{
// permite saber quantas já foram realizadas (que variam entre 0 e maxGuesses())
		return this.currentGuess;
	}

	public void insertGuessAndClue(String guess, Clue clue)
	{
// regista palavra e pista fornecidas, assumindo que guess.length()==clue.length()==wordLength() e guesses()< maxGuesses()
			this.guesses[this.currentGuess] = guess;
			this.clue[this.currentGuess] = clue;
			this.currentGuess++;
	}

	public String toString()
	{
// dá uma representação textual do estado do tabuleiro como se ilustra abaixo:
		String result = "";
		for (int i = 0; i < this.currentGuess; i++)
		{
			result += "+---------------+\n";
			result += "| " + this.guesses[i] + " | " + this.clue[i] + " |\n";
		}
		result += "+---------------+\n";
		return result;
	}
}

/*
 	*
* Board. Os objetos deste tipo representam o estado do tabuleiro de um jogo de Ipurdle. A classe deve incluir:
• public Board(int wordSize, int maxGuesses) que, assumindo que wordSize≥1 e maxGuesses≥1, cria um tabuleiro para o jogo de Ipurdle com os dados fornecidos no estado inicial (vazio)
• public  int wordLength(),  public  int maxGuesses()  e  public  int guesses()  que  permitem saber o tamanho das palavras que podem ser guardadas no tabuleiro, 
o número máximo de tentativas e
quantas já foram realizadas (que variam entre 0 e maxGuesses()), respetivamente
• public void insertGuessAndClue(String guess, Clue clue) que regista palavra e pista fornecidas,
assumindo que guess.length()==clue.length()==wordLength() e guesses()< maxGuesses()
• public String toString() que dá uma representação textual do estado do tabuleiro como se ilustra abaixo:

	+---------------+
	| WHILE | ____* |
	+---------------+
	| FIELD | __o__ |
	+---------------+
	| ABOVE | ***** |
	+---------------+
 */