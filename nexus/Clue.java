public class Clue {

	private LetterStatus[]	elements;
	private int 			orderNumber;
	private int 			wordSize;
		/**
		 * @param elements
		 * @requires {@code elements != null}
		 */
		public Clue(LetterStatus[] elements)
		{
			this.elements = new LetterStatus[elements.length];
			for( int i = 0; i < elements.length; i++ )
				this.elements[i] = elements[i];
			this.wordSize = elements.length;
		}

		public Clue(int orderNumber, int wordSize)
		{
			this.orderNumber = orderNumber;
			this.wordSize = wordSize;
			this.elements = new LetterStatus[wordSize];
			orderNumber--;
			System.out.println("wordSize = " + wordSize + " orderNumber = " + orderNumber);

			for (wordSize--; wordSize >= 0; wordSize--)
			{
				// System.out.println("|orderNumber = " + orderNumber + "| (orderNumber % 3) = " + (orderNumber % 3) + "| (orderNumber % 3 + 1) == clue ==" + ((orderNumber % 3) + 1));
				this.elements[wordSize] = LetterStatus.values()[orderNumber % 3];
				orderNumber /= 3;
			}
			// showElements();
			// showElementsAsClue();b
		}

	public void showElements()
	{
		for (int i = 0; i < elements.length; i++)
			System.out.println(elements[i]);
	}

	public void showElementsAsClue()
	{
		for (int i = 0; i < elements.length; i++)
		{
			switch (elements[i]) {
				case CORRECT_POS:
					System.out.print("3"); 
					break;
				case WRONG_POS:
					System.out.print("2");
					break;
				case INEXISTENT:
					System.out.print("1");
					break;
			}
		}
		System.out.println();
	}

	public LetterStatus[] letterStatus()
	{
		return (this.elements);
	}

	public int orderNumber()
	{
		return (this.orderNumber);
	}

	public int length()
	{
		return (this.wordSize);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length; i++)
		{
			switch (elements[i]) {
				case CORRECT_POS:
					sb.append("*"); 
					break;
				case WRONG_POS:
					sb.append("o");
					break;
				case INEXISTENT:
					sb.append("_");
					break;
			}
		}
		return (sb.toString());
	}

	public boolean isMax()
	{
		for (int i = 0; i < elements.length; i++)
		{
			if (elements[i] == LetterStatus.INEXISTENT || elements[i] == LetterStatus.WRONG_POS)
				return (false);
		}
		return (true);
	}

	public static void main(String[] args) {

	}
}

/*
 * Clue. Os objetos deste tipo representam pistas para palavras de um determinado tamanho. A classe deve 
incluir os seguintes construtores e métodos públicos: 
• public Clue(LetterStatus[] elements), que assumindo que elements!=null, constrói uma pista com os elementos dados 
• public  Clue(int  orderNumber,  int  wordSize), que assumindo que  wordSize>0  e 1 ≤
orderNumber≤3wordSize, constrói uma pista para uma palavra de tamanho wordSize e com o número de 
ordem orderNumber 
• public int length(), que dá o tamanho da pista 
• public int orderNumber(), que dá o número de ordem de uma pista 
• public LetterStatus[] letterStatus(), que dá um vetor com os elementos da pista 
• public boolean isMax() que indica se a pista é a maior entre todas as pistas do seu tamanho 
• public  String  toString()  que  dá  uma  representação  textual  de  uma  lista  onde  são  usados  os símbolos *, o e _ para representar letra na posição correta, letra na posição errada e letra inexistente
 */