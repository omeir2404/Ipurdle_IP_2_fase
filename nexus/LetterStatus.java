public enum LetterStatus {
    INEXISTENT(1),
    WRONG_POS(2),
    CORRECT_POS(3);

    private final int value;
    private LetterStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}