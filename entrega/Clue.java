/**
 * The Clue class represents a clue in a word puzzle game.
 * 
 * @author Name: Omeir Haroon, Student Number: 61810
 * @author Name: Matilde Brandão, Student Number: 61814
 */

/**
 * The Clue class represents a clue in a word puzzle game.
 * It stores the elements of the clue, the order number, and the word size.
 */
public class Clue {

	private LetterStatus[] elements;
	public int orderNumber;
	private int wordSize;

	/**
	 * Constructs a Clue object with the given elements.
	 *
	 * @param elements the elements of the clue
	 * @requires elements != null
	 */
	public Clue(LetterStatus[] elements) {
		int orderNumber = 0;
		this.elements = new LetterStatus[elements.length];
		this.wordSize = elements.length;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == LetterStatus.CORRECT_POS)
				orderNumber += (Math.pow(3, wordSize - i));
			else if (elements[i] == LetterStatus.WRONG_POS)
				orderNumber += (2 * Math.pow(3, wordSize - i));
		}
		this.orderNumber = orderNumber + 1;
		for (int i = 0; i < elements.length; i++)
			this.elements[i] = elements[i];
	}

	/**
	 * Constructs a Clue object with the given order number and word size.
	 *
	 * @param orderNumber the order number of the clue
	 * @param wordSize    the word size of the clue
	 */
	public Clue(int orderNumber, int wordSize) {
		this.orderNumber = orderNumber;
		this.wordSize = wordSize;
		this.elements = new LetterStatus[wordSize];
		orderNumber--;
		for (wordSize--; wordSize >= 0; wordSize--) {
			this.elements[wordSize] = LetterStatus.values()[orderNumber % 3];
			orderNumber /= 3;
		}
	}

	/**
	 * Displays the elements of the clue.
	 */
	public void showElements() {
		for (int i = 0; i < elements.length; i++)
			System.out.print(elements[i] + "  | ");
		System.out.println();
	}

	/**
	 * Displays the elements of the clue as a clue representation.
	 */
	public void showElementsAsClue() {
		for (int i = 0; i < elements.length; i++) {
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

	/**
	 * Returns the letter status elements of the clue.
	 *
	 * @return the letter status elements
	 */
	public LetterStatus[] letterStatus() {
		return this.elements;
	}

	/**
	 * Returns the order number of the clue.
	 *
	 * @return the order number
	 */
	public int orderNumber() {
		return this.orderNumber;
	}

	/**
	 * Returns the word size of the clue.
	 *
	 * @return the word size
	 */
	public int length() {
		return this.wordSize;
	}

	/**
	 * Returns a string representation of the clue.
	 *
	 * @return a string representation of the clue
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			switch (elements[i]) {
				case CORRECT_POS:
					sb.append(StringColouring.toColoredString("*", StringColouring.GREEN));
					break;
				case WRONG_POS:
					sb.append(StringColouring.toColoredString("o", StringColouring.YELLOW));
					break;
				case INEXISTENT:
					sb.append(StringColouring.toColoredString("_", StringColouring.RED));
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * Checks if the clue is the maximum possible clue.
	 *
	 * @return true if the clue is the maximum, false otherwise
	 */
	public boolean isMax() {
		for (int i = 0; i < this.elements.length; i++) {
			if (this.elements[i] == LetterStatus.INEXISTENT || this.elements[i] == LetterStatus.WRONG_POS)
				return false;
		}
		return true;
	}
}

