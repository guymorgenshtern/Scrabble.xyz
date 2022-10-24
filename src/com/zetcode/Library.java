package com.zetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Library {
    public static void words() throws IOException{
        ArrayList<String> wordMap = new ArrayList<>();

        var url = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
        var br = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;

        while ((line = br.readLine()) != null) {
            wordMap.add(line);
        }
        System.out.println(wordMap);
    }

    public static void main(String[] args) throws IOException{
        words();
    }
}