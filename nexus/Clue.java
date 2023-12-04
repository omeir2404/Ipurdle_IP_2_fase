public class Clue {

    private LetterStatus[] elements;
    private int clue;
    /**
     * @param elements
     * @requires {@code elements != null}
     */
    public Clue(LetterStatus[] elements)
    {
        int clue = 0;
        for( int i = 0; i < elements.length; i++ )
            clue = (clue*10) + elements[i].getValue();
        this.clue = clue;
        this.elements = elements;
    }

    public int length()
    {
        int size = 0;
        int temp = this.clue;
        while (temp > 0)
        {
            temp /= 10;
            size++;
        }
        return (size);
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
    public static void main(String[] args)
    {
        // 12332
        LetterStatus[] elements = {LetterStatus.INEXISTENT, LetterStatus.WRONG_POS, LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS, LetterStatus.WRONG_POS};
        Clue clue = new Clue(elements);
        System.out.println("Testing toString() wtih clue 12332: "+ clue.toString());
        System.out.println("Testing length() wtih clue 12332: "+ clue.length());

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