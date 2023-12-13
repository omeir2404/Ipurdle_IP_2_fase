
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Scanner;

public class IpurdleGame {
    private String[] Dictionary;
    public String[] sizedDictionaryStrings;
    public boolean[] validWords;
    public Board board;
    private boolean over;
    
    
public IpurdleGame(int wordSize, int maxGuesses) {
    this.over = false;
    this.board = new Board(wordSize, maxGuesses);
    String file = "Dicionario.txt";
    try {//o try / catch teve de ser utilizado para usar o Scaner com ficheiro
        Scanner scanner = new Scanner(new File(file));// tenta abrir o ficheiro, se algo der errado vai ao catch (o ficheiro pode n existir ou n ser possivel abrir etc)
        List<String> dictionaryList = new ArrayList<>();//e criada uma lista de strings em vez de um array de strings porque nao sabemos o tamanho do ficheiro
        while (scanner.hasNextLine()) {
            dictionaryList.add(scanner.nextLine());// vai colocar cada linha do ficheiro na lista de strings, no nosso caso cada linha representa uma palavra
        }
        scanner.close();
        Dictionary = dictionaryList.toArray(new String[0]);// passar a lista com as palavras para um array de strings
    } catch (FileNotFoundException e) {
        System.err.println("File " + file + " has not been found or could not be opened");
    }
    sizedDictionaryStrings = Arrays.stream(Dictionary)
            .filter(word -> word.length() == this.board.wordLength())
            .toArray(String[]::new);// igual ao de cima mas agora em vez de contar coloca num array de strings
    validWords = new boolean[sizedDictionaryStrings.length];
    Arrays.fill(validWords, true);
}
    /**
     * 
     * @return tamanho da palavra
     */
    public int wordLength() {
        return this.board.wordLength();
    }
    /**
     * 
     * @return maximum number of tries
     */
    public int maxGuesses() {
        return this.board.maxGuesses();
    }
    /**
     *   
     * @return current number of tries
     */
    public int guesses() {
        return this.board.guesses();
    }
    /**
     * Checks to see if the length of the word and the guess is equal
     * and if the guess is in the array of the dictionary of possible words
     * @param guess
     * @return if the guess is valid or not
     * @requires
     */

	public boolean isValid(String guess)
	{

		if (guess.length() != this.board.wordLength())
			return false;
		for (int i = 0; i < this.Dictionary.length; i++)
			if (this.Dictionary[i].equals(guess))
				return true;
		return false;
	}
    /**
     * Checks to see if the player has reached the maximum number
     * of tries or if the player guessed the right word to see  
     * if the game is over or not
     * @return if the game is over or not
     * @requires
     */
    public boolean isOver() {
        return over;
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
	
		for(int count = 0; count < word.length(); count++)
		{
			if (guess.charAt(count) == word.charAt(count))
				elements[count] = LetterStatus.CORRECT_POS;
			else if (countOccurrences(guess.charAt(count), word, word.length()) > 0)
			{
				if (countOccurrences(guess.charAt(count), word, word.length()) == 1)
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
				elements[count] = LetterStatus.INEXISTENT;
        }
		clue = new Clue(elements);
		return clue;
	}

    /**
     * Checks what guess fits the largest number of words 
     * @param guess
     * @return guess 
     * @requires isValid(guess) and !isOver
     */
    public Clue playGuess(String guess) {
        int size = this.board.wordLength();
        Clue bestClue = new Clue ((int)Math.pow(3, size),size);
        Clue clue = new Clue(1,size);
        int bestWords = 0;
        int numeroPalavras;

        for(int i = 0; i < sizedDictionaryStrings.length; i++){
            if(validWords[i] &&  sizedDictionaryStrings[i] != guess && sizedDictionaryStrings[i] != null){
                clue = clueForGuessAndWord(guess, sizedDictionaryStrings[i]);
                numeroPalavras = countValidWords(clue, guess, i, bestClue);
                int orderNumberClue = clue.orderNumber();
                int orderNumberBetterClue = bestClue.orderNumber();
                if (bestWords  <=  numeroPalavras && orderNumberClue < orderNumberBetterClue){ 
                    bestWords = numeroPalavras;
                    bestClue = clue;
                }
            }
        }
    
        this.board.insertGuessAndClue(guess, bestClue);
        if(maxGuesses() == guesses() || bestClue.isMax()){
            over = true;
        }
        return bestClue;
    }
    
    private int countValidWords(Clue clue, String guess, int index, Clue bestClue) {
        int numeroPalavras = 0;
        for (int i = 0; i < sizedDictionaryStrings.length; i++) {
            if (validWords[i] && clue.orderNumber() == clueForGuessAndWord(guess, sizedDictionaryStrings[i]).orderNumber()) {
                numeroPalavras++;
            }
        }
        if(clue.orderNumber() != bestClue.orderNumber()){ 
            validWords[index] = false;
        }
        return numeroPalavras;
    }
        
    /**
     * Gives the textual representation of the board 
     * in the current guess
     * 
     */
    public String toString() {
        return "Ipurdle with words of " + this.board.wordLength() + " letters\n" +
               "Remaining guesses: " + (this.board.maxGuesses() - this.board.guesses()) + "\n" +
               this.board.toString();
    }
}