package com.zetcode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * A Library to store all valid words for validity checking.
 * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
 */
public class Library {

    /** An ArrayList of Strings to store all valid words. */
    private ArrayList<String> validWords;

    /**
     * Creates a Library with all valid words.
     * @throws IOException If an I/O error occurs.
     * @author Alexander Hum 101180821
     */
    public Library() throws IOException {
        validWords = new ArrayList<>();

        URL url = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String word;

        while ((word = br.readLine()) != null) {
            validWords.add(word);
        }
    }

    /**
     * @param word A String representing the word to be checked.
     * @return True, if the specified word is valid. False, if not.
     * @author Alexander Hum 101180821
     */
    public boolean isValidWord(String word) {
        return this.validWords.contains(word);
    }
}