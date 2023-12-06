public class IpurdleGame
{
	private String[] validWords;
	private boolean[] validWordsStatus;
	private int wordSize;
	private int maxGuesses;
	private int playedGuesses = 0;
	private Clue clue;
	private Board board;
	private String guess;

    public IpurdleGame(int wordSize, int maxGuesses)
    {
        this.wordSize = wordSize;
        this.maxGuesses = maxGuesses;
        this.validWords = new String[] { "JAVA", "LOOP", "EXIT", "TRUE", "LONG", "THIS", "BREAK", "WHILE", "GRADE",
            "PUPIL", "FIELD", "BASIC", "ABORT", "ABOVE", "FALSE", "FLOAT", "SHORT", "CLASS", "FINAL", "STATIC",
            "METHOD", "STRING", "RETURN", "RANDOM", "EQUALS", "OBJECT", "FUNCTION", "VARIABLE", "INTEGER",
            "SCANNER" };
        this.validWordsStatus = new boolean[this.validWords.length];
        for (int i = 0; i < this.validWordsStatus.length; i++)
            this.validWordsStatus[i] = true;
        this.board = new Board(this.wordSize, this.maxGuesses);
    }

	int wordLength()
	{
		return this.wordSize;
	}

	public boolean isValid(String guess)
	{
		if (guess.length() != this.wordSize)
			return false;
		for (int i = 0; i < this.validWords.length; i++)
		{
			if (this.validWords[i].equals(guess))
				return true;
		}
		return false;
	}

	public boolean isOver()
	{
		if (this.playedGuesses == this.maxGuesses)
			return true;
		for (int i = 0; i < this.validWordsStatus.length; i++)
		{
			this.board.insertGuessAndClue(this.playedGuesses, new Clue(clueForGuessAndWord(this.validWords[i], this.validWords[i])));
		}
		return true;
	}

	/**
	 * 
	 * @param letter
	 * @param word
	 * @param stop
	 * @requires {@code word != NULL}
	 * @requires {@code stop} <= {@code word.length()}
	 * @requires {@code letter != NULL}
	 * @ensures {@code countOccurrences} is the number of occurrences of {@code letter} in {@code word} up to the position {@code stop} of word
	 * @return
	 */
	public static int countOccurrences(char letter, String word, int stop)
	{
		int count = 0;
		for(int i = 0; i < stop; i++)
		{
			if (word.charAt(i) == letter)// sempre que a letra do word de indice i for igual a letra adiciona 1 ao count
				count++;
		}
		return (count);
	}

	/**
	 * @param {@code guess}
	 * @param {@code word}
	 * @requires {@code guess} and {@code word} have the same length
	 * @ensures {@code clue} is a valid clue for {@code guess} when comparing to {@code word}
	 * @return the clue for {@code guess} when comparing to {@code word}
	 */
    public static int clueForGuessAndWord(String guess, String word)
	{
		int clue = 0;
		for(int count = 0; count < guess.length(); count++)
		{
			clue *= 10;//como o clue é um inteiro, para adicionar um digito no final do clue temos de multiplicar por 10
			if (guess.charAt(count) == word.charAt(count))
				clue += 3;//se a letra de indice count do guess for igual a letra de indice count do word adiciona 3 ao clue
			else if (countOccurrences(guess.charAt(count), word, word.length()) > 0)// se a letra de indice count da palavra esta no word
			{
				if (countOccurrences(guess.charAt(count), word, word.length()) == 1) // se a letra de indice count da palavra so aparece uma vez no word
				{
					if (countOccurrences(guess.charAt(count), guess, count) > 0)
						clue += 1; // se foi encontrado antes (na posicao certa ou nao) adiciona 1 ao clue
					else
						clue += 2; //se for a primeira vez que ocorre adiciona 2 ao clue
				}
				else
					clue += 2;// se houver mais do que uma letra igual no word adiciona 2 ao clue
			}
			else
				clue += 1;// ultimo caso adiciona 1 ao clue
		}
		return (clue);
	}

		public Clue playGuess(String guess)
		{
			if (this.playedGuesses == this.maxGuesses)
				return null;
			if (!isValid(guess))
				return null;
			int clue = 0;
			for (int i = 0; i < this.validWords.length; i++)
			{
				if (this.validWords[i].equals(guess))
				{
					this.validWordsStatus[i] = false;
					break;
				}
			}
			for (int i = 0; i < this.validWords.length; i++)
			{
				if (this.validWordsStatus[i])
				{
					clue = clueForGuessAndWord(guess, this.validWords[i]);
					this.board.insertGuessAndClue(guess, new Clue(clue));
					this.playedGuesses++;
					return new Clue(clue);
				}
			}
			return null;
		}

}


/*
 * IpurdleGame.  Os  objetos  deste  tipo  representam  uma  partida  do  jogo  de  Ipurdle  com  um  dicionário  de 
palavras fixado pela equipa docente de IP e enumeradas abaixo. 
"JAVA", "LOOP", "EXIT", "TRUE","LONG", "THIS", 
"BREAK","WHILE","GRADE","PUPIL", "FIELD", "BASIC", "ABORT", 
"ABOVE","FALSE","FLOAT","SHORT","CLASS","FINAL", 
"STATIC","METHOD","STRING","RETURN","RANDOM","EQUALS","OBJECT","FUNCTION",
"VARIABLE","INTEGER","SCANNER"  
A classe deve usar: 
• ----------um vetor para representar as palavras válidas de uma partida, ou seja, as que podem ser jogadas (que nunca mudam) 
• ----------um vetor de Booleanos para representar as palavras que ainda podem ser a palavra a descobrir (que vão mudando à medida que vão sendo feitas jogadas). 
A classe deve incluir: 
• ----------public  IpurdleGame(int  wordSize,  int  maxGuesses) que, assumindo wordSize≥1  e maxGuesses≥1, cria uma partida do jogo Ipurdle com os dados fornecidos no estado inicial  
• ----------public int wordLength(), public int maxGuesses() e public int guesses() que permitem saber o tamanho das palavras que podem ser jogadas, o número máximo de tentativas e quantas já foram realizadas, respetivamente 
• ----------public boolean isValid(String guess) que, assumindo que guess!=null, indica se a palavra é válida, ou seja, tem o tamanho certo e pertence ao dicionário 
• ----------public boolean isOver() que indica se a partida já terminou, ou seja, a palavra foi descoberta ou foram esgotadas as tentativas  
• ----------private Clue clueForGuessAndWord(String guess, String word) que, assumindo guess.length()==word.length(), retorna a pista a dar a guess se a palavra a adivinhar for word 
• public Clue playGuess(String guess) que, assumindo que isValid(guess) e !isOver(), faz a jogada  (com tudo o que isso implica) devolvendo a pista para guess que serve para mais palavras. 
Importante: este método deve percorrer o vetor das palavras válidas apenas duas vezes! 
• public String toString() que dá uma representação textual do estado da partida como ilustrado  
Ipurdle with words of 5 letters.  
Remaining guesses: 4 
+---------------+ 
| WHILE | ____* | 
+---------------+ 
| FIELD | __o__ | 
+---------------+ 
 */