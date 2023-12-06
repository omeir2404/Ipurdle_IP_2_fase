public class Board
{
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
		
	}
}

/*
 	*
* Board. Os objetos deste tipo representam o estado do tabuleiro de um jogo de Ipurdle. A classe deve incluir:
• public Board(int wordSize, int maxGuesses) que, assumindo que wordSize≥1 e maxGuesses≥1, cria um tabuleiro para o jogo de Ipurdle com os dados fornecidos no estado inicial (vazio)
• public  int wordLength(),  public  int maxGuesses()  e  public  int guesses()  que  permitem saber o tamanho das palavras que podem ser guardadas no tabuleiro, o número máximo de tentativas e
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