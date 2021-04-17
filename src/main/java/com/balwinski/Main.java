package com.balwinski;

import com.balwinski.services.TextFileReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TextFileReader textFileReader = new TextFileReader();
        List<String> lines = textFileReader.readLogic();

        for (String l : lines) {
            System.out.println(l);
        }
    }
}
