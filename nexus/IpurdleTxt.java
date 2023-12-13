import java.util.Scanner;
public class IpurdleTxt {
	public static void main(String[] args)
	{
		
		IpurdleGame game = new IpurdleGame(5,6);
		Scanner scr = new Scanner(System.in);
		boolean error = false;
		
		while(!game.isOver())
		{
			System.out.println("Introduza uma palavra de 5 letras:");
			String word = scr.nextLine().toUpperCase();
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
		
		scr.close();
	}

}


/*IpurdleTxt. Classe com um método main que deverá criar uma partida de Ipurdle com palavras de 5 
letras e 6 tentativas. O método deve suportar a interação com o jogador: obter uma palavra do utilizador 
que seja válida, jogar essa palavra e imprimir o estado do jogo; repetir isto enquanto o jogo não terminar. */