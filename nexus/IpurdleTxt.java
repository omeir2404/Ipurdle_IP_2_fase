/**
 * Handles the interaction with the user and the representation of the game state.
 * 
 * @authors
 *   Name: Omeir Haroon, Student Number: 61810
 *   Name: Matilde Brandão, Student Number: 61814
 */

import java.util.Scanner;
public class IpurdleTxt {
	/**
	 * 
	 * @param args
	 * @ensures a new game is initialized with a board of the specified word size and maximum number of guesses. The dictionary is loaded from a text file and filtered to only include words of the specified size. An array to keep track of valid words is initialized.
	 * @requires {@code wordSize > 0} and {@code maxGuesses > 0}. 
	 */
	public static void main(String[] args)
	{
		int wordsize = 5;
		int maxGuesses = 6;
		if (args.length > 0)
		{
			wordsize = Integer.parseInt(args[0]);
			if(args.length > 1)
				maxGuesses = Integer.parseInt(args[1]);
		}
		System.out.println("tamanho da palavra: " + wordsize + " numero de tentativas: " + maxGuesses);
		IpurdleGame game = new IpurdleGame(wordsize,maxGuesses);
		Scanner sc = new Scanner(System.in);
		boolean error = false;
		
		while(!game.isOver())
		{
			System.out.println("Introduza uma palavra de 5 letras:");
			String word = sc.nextLine().toUpperCase();
			if(word.length() != 5){
				System.out.println("A palavra deve ter 5 letras! Por favor tente novamente.");
				error = true;
				continue;
			}
			if (!game.isValid(word)){
				System.out.println("A palavra deve ser composta apenas por letras, e tem de ser uma palavra do Dicionario!! Por favor tente novamente.");
				error = true;
				continue;
			}
			if (!error)
			{
				game.playGuess(word);
				System.out.println(game.toString());
			}
			error = false;
		}
		
		sc.close();
	}

}


/*IpurdleTxt. Classe com um método main que deverá criar uma partida de Ipurdle com palavras de 5 
letras e 6 tentativas. O método deve suportar a interação com o jogador: obter uma palavra do utilizador 
que seja válida, jogar essa palavra e imprimir o estado do jogo; repetir isto enquanto o jogo não terminar. */