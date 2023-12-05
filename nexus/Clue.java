public class Clue {

    private LetterStatus[] elements;
    private int clue;
    private int  orderNumber;
    private int wordSize;
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
        copyElements(this.elements , elements);
        System.out.println("this.elements: " + this.elements);
    }


    void    copyElements(LetterStatus[] arr, LetterStatus[] arr2)
    {
        for (int i = 0; i < arr.length; i++)
            arr2[i] = arr[i];
        return ;
    }

    private int nextClue(int clue)
    {
        int howManyThree = 0;
        // int nextclue = 0;
        if (clue % 10 == 1 || clue % 10 == 2)
            return (clue + 1);
        while (clue % 10 == 3)
        {
            howManyThree++;
            clue /= 10;
        }
        clue++;
        for (int i = 0; i < howManyThree; i++)
            clue = (clue * 10) + 1;
        return (clue);
    }

    private int minClue(int wordSize)
    {
        int clue = 0;
        for (int i = 0; i < wordSize; i++)
            clue = (clue * 10) + LetterStatus.INEXISTENT.getValue();
        return (clue);
    }

    public  Clue(int  orderNumber,  int  wordSize)
    {
        int temp_clue = minClue(wordSize);
        this.orderNumber = orderNumber;
        // System.out.println("orderNumber: " + orderNumber + " wordSize: " + wordSize);
        for (int i = 0; i < orderNumber; i++)
            temp_clue = nextClue(temp_clue);
        this.clue = temp_clue;
        // System.out.println("clue: " + clue);
        this.elements = new LetterStatus[wordSize];
        for (int i = 0; i < wordSize; i++)
        {
            // System.out.println("temp_clue: " + temp_clue + " temp_clue % 10: " + temp_clue % 10);
            switch (temp_clue % 10) {
                case 1:
                    this.elements[i] = LetterStatus.INEXISTENT;
                    temp_clue /= 10;
                    break;
                case 2:
                    this.elements[i] = LetterStatus.WRONG_POS;
                    temp_clue /= 10;
                    break;
                case 3:
                    this.elements[i] = LetterStatus.CORRECT_POS;
                    temp_clue /= 10;
                    break;
            }
        }
    }

    public int orderNumber()
    {
        return (this.orderNumber);
    }

    public LetterStatus[] letterStatus()
    {
        return (this.elements);
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

    public boolean isMax()
    {
        int temp = this.clue;
        while (temp > 0)
        {
            if (temp % 10 != 3)
                return (false);
            temp /= 10;
        }
        return (true);
    }
    public static void main(String[] args)
    {
        // 12332
        LetterStatus[] elements = {LetterStatus.INEXISTENT, LetterStatus.WRONG_POS, LetterStatus.CORRECT_POS, LetterStatus.CORRECT_POS, LetterStatus.WRONG_POS};
        Clue clue = new Clue(elements);
        System.out.println("Testing toString() wtih clue 12332: "+ clue.toString());
        System.out.println("Testing length() wtih clue 12332: "+ clue.length());
        System.out.println("Testing isMax() wtih clue 12332: "+ clue.isMax());
        System.out.println("Testing nextClue() wtih clue 12332: "+ clue.nextClue(12332));
        System.out.println("Testing nextClue() wtih clue 12333: "+ clue.nextClue(12333));
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