package com.zetcode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Has all allowable words for the validation checking
 * @author Alexander Hum 101180821
 * @edited Guy Morgenshtern 101151430
 */
public class Library {
    /** An array list for the words */
    private ArrayList<String> acceptableWords;

    /**
     * Creates a Library with all acceptable words
     * @throws IOException
     * @author Alexander Hum 101180821
     */
    public Library() throws IOException {
        acceptableWords = new ArrayList<>();
        URL url = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
        // Used ZetCode when accessing the web page for the words
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String word;
        while ((word = br.readLine()) != null) {
            acceptableWords.add(word);
        }
    }

    /**
     * checks if the word is valid
     * @param word String representing the word
     * @return True if the word is valid
     */
    public boolean isValidWord(String word) {
        return this.acceptableWords.contains(word);
    }
}