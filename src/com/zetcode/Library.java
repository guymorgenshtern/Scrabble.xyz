package com.zetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Library {
    private ArrayList<String> wordMap;

    public Library() throws IOException {
        wordMap = new ArrayList<>();

        URL url = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;

        while ((line = br.readLine()) != null) {
            wordMap.add(line);
        }
    }

    public boolean isValidWord(String word) {
        return this.wordMap.contains(word);
    }

//    public static void main(String[] args) throws IOException{
//        words();
//    }
}