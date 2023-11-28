public enum LetterStatus {
    INEXISTENT(false),
    WRONG_POS(false),
    CORRECT_POS(false);

    private final boolean value;
    private LetterStatus(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}