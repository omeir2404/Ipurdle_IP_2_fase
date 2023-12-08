public class IpurdleGame
{
	private String[] validWords;
	private boolean[] validWordsStatus;
	private int wordSize;
	private int maxGuesses;
	private int playedGuesses = 0;
	public Clue clue;
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

	public int guesses()
	{
		return this.playedGuesses;
	}

	public int maxGuesses()
	{
		return this.maxGuesses;
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
		if (this.clue != null) {
			// System.out.println("is max " + this.clue.isMax());
			if (this.clue.isMax())
			    return true;
		}
		return false;
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
			// System.out.println("word" + word +"word.charAt(i) = " + word.charAt(i) + "| i: " + i);
			if (word.charAt(i) == letter)
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
	public Clue clueForGuessAndWord(String guess, String word)
	{
		Clue clue;
		LetterStatus[] elements = new LetterStatus[guess.length()];
	
		for(int count = 0; count < this.wordSize; count++)
		{
			if (guess.charAt(count) == word.charAt(count))
				elements[count] = LetterStatus.CORRECT_POS;
			else if (countOccurrences(guess.charAt(count), word, this.wordSize) > 0)
			{
				if (countOccurrences(guess.charAt(count), word, this.wordSize) == 1)
				{
					if (countOccurrences(guess.charAt(count), guess, count) > 0)
						elements[count] = LetterStatus.INEXISTENT;
					else
						elements[count] = LetterStatus.WRONG_POS;
				}
				else
				{
					elements[count] = LetterStatus.WRONG_POS;
				}
			}
			else
{			System.out.println("countOccurrences(guess.charAt(count), word, this.wordSize) = " + countOccurrences(guess.charAt(count), word, this.wordSize));
				elements[count] = LetterStatus.INEXISTENT;}
		}
		clue = new Clue(elements);
		return clue;
	}

	// public static LetterStatus[] elementsFromIntClue(int clue, int wordSize)
	// {
	// 	LetterStatus[] elements = new LetterStatus[wordSize];
	// 	for (int i = 0; i < wordSize; i++)
	// 	{
	// 		switch (clue % 10)
	// 		{
	// 			case 1:
	// 				elements[i] = LetterStatus.INEXISTENT;
	// 				break;
	// 			case 2:
	// 				elements[i] = LetterStatus.WRONG_POS;
	// 				break;
	// 			case 3:
	// 				elements[i] = LetterStatus.CORRECT_POS;
	// 				break;
	// 		}
	// 		clue /= 10;
	// 	}
	// 	return (elements);
	// }

	public	boolean compareClues(Clue clue1, Clue clue2)
	{
		for (int i = 0; i < clue1.length(); i++)
		{
			if (clue1.letterStatus()[i] != clue2.letterStatus()[i])
				return (false);
		}
		return (true);
	}

	public int howManyWordsWithClue(String[] validWords, Clue clue, String guess)
	{
		int count = 0;
		for(int i = 0; i < validWords.length; i++)
		{
			if (validWords[i].length() == guess.length())
			{
				if (compareClues(clueForGuessAndWord(guess, validWords[i]), clue))
					count++;
			}
		}
		return count;
	}

	public Clue minClue(int wordSize)
	{
		LetterStatus[] minClue = new LetterStatus[wordSize];
		// System.out.println("minClue wordSize = " + wordSize);
		for (int i = 0; i < wordSize; i++)
			minClue[i] = LetterStatus.INEXISTENT;
		return (new Clue(minClue));
	}

	public Clue maxClue(int wordSize)
	{
		LetterStatus[] maxClue = new LetterStatus[wordSize];
		for (int i = 0; i < wordSize; i++)
			maxClue[i] = LetterStatus.CORRECT_POS;
		return (new Clue(maxClue));
	}

	public boolean oneValidWord(String[] validWords, boolean[] validWordsStatus , String guess)
	{
		int count = 0;
		boolean isGuess = false;
		for (int i = 0; i < validWords.length; i++)
		{
			if (validWordsStatus[i])
			{
				if (validWords[i].equals(guess))
					isGuess = true;
				count++;
			}
		}
		return (count == 1 && isGuess == true);
	}

	public Clue betterClueForGuess(String[] validWords , String guess)
	{
		Clue bestClue = minClue(guess.length());
		int bestCount = -1;
		Clue clue = minClue(guess.length());
		int count = 0;


		if (oneValidWord(validWords, validWordsStatus, guess))
		{
			System.out.println("IM THE MAXXXXX");
			return (maxClue(guess.length()));
		}
		for (int i = 0; i < validWords.length; i++)
		{
			if (validWords[i].length() == guess.length())
			{
				clue = clueForGuessAndWord(guess, validWords[i]);
				count = howManyWordsWithClue(validWords, clue, guess);
				if (count > bestCount)
				{
					bestCount = count;
					bestClue = clue;
				}
			}
		}
		return bestClue;
	}

    // public static int playGuess(DictionaryIP dictionary, String guess){
    //     int clue =  betterClueForGuess(dictionary, guess);
	// 	for(int i = 0; i < dictionary.lenght(); i++)
	// 	{
	// 		if (clueForGuessAndWord(guess, dictionary.getWord(i)) != clue)
	// 			dictionary.selectForRemove(i);
	// 	}
    //     dictionary.removeSelected();
    //     return clue;
    // }

	public Clue playGuess(String guess)
	{
		this.guess = guess;
		Clue clue = betterClueForGuess(validWords, guess);
		for (int i = 0; i < validWords.length ; i++)
		{
			if (validWords[i].length() == guess.length())
			{
				if (compareClues(clueForGuessAndWord(guess, validWords[i]), clue))
					validWordsStatus[i] = false;
			}
		}
		this.clue = clue;
		this.clue.length();
		// System.out.println();
		// for (LetterStatus element : this.clue.letterStatus()) {
		// 	if (element != null) {
		// 		System.out.println(element);
		// 	} else {
		// 		System.out.println("Clue is null");
		// 	}
		// }
		// this.clue.showElements();
		// this.clue.showElementsAsClue();
		// System.out.println("clue = " + clue.toString());
		this.playedGuesses++;
		// System.out.println(this.clue.toString());
		return this.clue;
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