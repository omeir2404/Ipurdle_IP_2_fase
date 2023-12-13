/**
 * 
 */
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Scanner;

public class aliceIpurdleGame {
    private String[] dicionario;
    private String[] dicionarioTamanho; //array with the words that have the right length
    private boolean[] dicionarioValido; //array of booleans to represent the words that can be played
    private int numeroPalavras;
    private Board board;
    private Clue clue;
    private boolean over;

    /**
     * creats the starting point of the game
     * @param wordSize
     * @param maxGuesses
     * @requires {@code wordSize>=1 && maxGuesses>=1}
     */
    public aliceIpurdleGame(int wordSize, int maxGuesses) {
        this.over = false;
        this.board = new Board(wordSize, maxGuesses);
        String ficheiro = "Dicionario.txt";
        int numero = 0;
        try{
            Scanner num = new Scanner(new File (ficheiro));
            Scanner palavras = new Scanner(new File (ficheiro));
            while(num.hasNextLine()){ //checks the number of words that the file has
                num.nextLine();
                numero ++;
            }
            dicionario = new String[numero];
            for(int i = 0; i < numero ; i++){ //puts the words that are in the file to the array
                if(palavras.hasNextLine()){
                dicionario[i] = palavras.nextLine();
                }
            }
            num.close();
            palavras.close();
        } catch (FileNotFoundException e) {
            System.err.println("File" + ficheiro + "has not been found or could not be open");
        }
        for(int j = 0; j < dicionario.length; j++){ //sees how many words have the right length
            if(this.board.wordLength() == dicionario[j].length()){
                numeroPalavras++;
            }
        }
        dicionarioTamanho = new String[numeroPalavras];
        int posição = 0;
        for(int a = 0 ; a < dicionario.length; a++){ // puts the words that have the rigth length in a new array
            if(this.board.wordLength() == dicionario[a].length()){
                dicionarioTamanho[posição] = dicionario[a];
                posição++;
            }
        }
        dicionarioValido = new boolean[dicionarioTamanho.length];
        Arrays.fill(dicionarioValido, true);
    }
    /**
     * 
     * @return word size
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
     * @requires {@code guess!=null}
     */
    public boolean isValid(String guess) {
        if (guess.length() == this.board.wordLength()) {
            for (int acc = 0; acc < dicionarioTamanho.length; acc++) {
                if (guess.equals(dicionarioTamanho[acc])) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Checks to see if the player has reached the maximum number
     * of tries or if the player guessed the right word to see  
     * if the game is over or not
     * @return if the game is over or not
     */
    public boolean isOver() {
        return over;
    }
    /**
     * Considering the guess and the word, this method 
     * calculates the clue based on the enumerate LetterStatus
     * @param guess
     * @param word
     * @return clue
     * @requires {@code guess.length() == word.length()}
     */
    private Clue clueForGuessAndWord(String guess, String word) {
        int size = wordLength();
        LetterStatus[] clue = new LetterStatus[size];
        for (int i = 0; i < size; i++) { 
            if (guess.charAt(i) == word.charAt(i)){ 
                clue[i] = LetterStatus.CORRECT_POS;
            }else{
                clue[i] = LetterStatus.INEXISTENT;
            }
        }
        for (int i = 0; i < size; i++) {
            if (clue[i] != LetterStatus.CORRECT_POS && numCharInWord(word, guess.charAt(i)) > 0) {
                int acc = 0;
                for (int j = 0; j < size; j++) {
                    if (guess.charAt(i) == guess.charAt(j) && (clue[j] == LetterStatus.CORRECT_POS || clue[j] == LetterStatus.WRONG_POS))
                        acc += 1;
                }
                if (acc < numCharInWord(word, guess.charAt(i)))
                    clue[i] = LetterStatus.WRONG_POS;
            }
        }
            Clue clueClue = new Clue(clue);
            return clueClue;
    }
    /**
     * Checks how many equal characthers there are in the word
     * 
     * @param word
     * @param a
     * @return the number of equal characthers in a word
     * @requires {@code word.length() > 0}
     */
    private int numCharInWord (String word, char a) {
        int acc = 0;
        int i = 0;
        while (i < word.length() && acc < 2) {
            if (word.charAt(i) == a) 
                acc++;
            i++;
        }
        return acc;
    }
    /**
     * Checks what guess fits the largest number of words 
     * @param guess
     * @return guess 
     * @requires isValid(guess) and !isOver
     */
    public Clue playGuess(String guess) {
        Clue bestClue = betterClueForGuess(guess);
        for(int j = 0; j < dicionarioTamanho.length; j++){
            if(dicionarioValido[j]){
                Clue clue = clueForGuessAndWord(guess, dicionarioTamanho[j]);
                if(clue.orderNumber != bestClue.orderNumber){ 
                    dicionarioValido[j] = false;
                }
            }
        }
        this.board.insertGuessAndClue(guess, bestClue);
        if(maxGuesses() == guesses() || bestClue.isMax()){
            over = true;
        }
        return bestClue;
    }
    /**
     * 
     * @param clue
     * @param guess
     * @return
     */
    private int countValidWords(Clue clue, String guess) {
        int numeroPalavras = 0;
        for(int i = 0; i < dicionarioTamanho.length; i++){ 
            String palavraDicionario = dicionarioTamanho[i];
            Clue clueDicionario = clueForGuessAndWord(guess, palavraDicionario);
            if(clue.orderNumber() == clueDicionario.orderNumber() && dicionarioValido[i]){ 
                numeroPalavras ++;
            }
        }
        return numeroPalavras;
    }
    /**
     * 
     * @param guess
     * @return bestClue
     */
    private Clue betterClueForGuess(String guess){
        int size = this.board.wordLength();
        Clue betterClue = new Clue ((int)Math.pow(3, size),size);
        Clue clue = new Clue(1,size);
        int bestWords = 0;
        int numeroPalavras;
        for(int acc = 0; acc < dicionarioTamanho.length; acc++){
            if(dicionarioValido[acc] &&  dicionarioTamanho[acc] != guess){
                clue = clueForGuessAndWord(guess, dicionarioTamanho[acc]);
                numeroPalavras = countValidWords(clue, guess);
                int orderNumberClue = clue.orderNumber();
                int orderNumberBetterClue = betterClue.orderNumber();
                if (bestWords  <  numeroPalavras || numeroPalavras == bestWords && orderNumberBetterClue > orderNumberClue){ 
                    bestWords = numeroPalavras;
                    betterClue = clue;
                }
            }
        }
        return betterClue;
    }

        
    /**
     * Gives the textual representation of the board 
     * in the current guess
     * 
     */
    public String toString() {
        String linha1 ="Ipurdle with words of" + this.board.wordLength() +" letters";
        String linha2 = "Remaining guesses:" + (this.board.maxGuesses()-this.board.guesses());
        String linhas12 = linha1.concat(linha2);
        String tudo = linhas12.concat(this.board.toString());
        return tudo;
    }
}