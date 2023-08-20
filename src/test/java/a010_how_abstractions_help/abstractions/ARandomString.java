package a010_how_abstractions_help.abstractions;

import java.util.Random;

class ARandomString {
    private final String space;
    private final int maxWords;
    private final int wordMaxLen;
    private final int wordMinLen;
    private final Random rnd;

    final String validChars = "abcdefghijklmnopqrtsuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ARandomString(String separator) {
        this.space = separator;
        this.maxWords = 4;
        this.wordMaxLen = 7;
        this.wordMinLen = 3;
        rnd = new Random();
    }

    public String generate() {

        int wordsToGenerate = rnd.nextInt(maxWords) + 1;
        StringBuilder sentence = new StringBuilder();
        String spacer = "";

        while (wordsToGenerate > 0) {

            sentence.append(spacer);

            int wordLength = rnd.nextInt((wordMaxLen - wordMinLen) + 1) + wordMinLen;

            while (wordLength > 0) {

                int charIndex = rnd.nextInt(validChars.length());
                char charToAdd = validChars.charAt(charIndex);
                sentence.append(charToAdd);
                wordLength--;
            }

            wordsToGenerate--;
            spacer = space;
        }

        return sentence.toString();
    }

}
