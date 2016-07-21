package com.wavemaker.chatpp.chatclient.io;

import com.wavemaker.chatpp.chatclient.exceptions.AppFileNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Created by sainihala on 14/7/16.
 */
public class InputReader {

    public BufferedReader createConsoleReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public BufferedReader createFileReader(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException fileNotFound) {
            throw new AppFileNotFoundException("File NotFound");
        }
        return br;
    }
}