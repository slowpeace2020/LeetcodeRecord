package edu.study.csye;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderExample {
    public static void main(String[] args) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("inputFile.txt"));
            String thisLine = null;
            thisLine = bufferedReader.readLine();
            bufferedReader.close();
            System.out.println(thisLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
