package ar.edu.itba.ss.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static String readInputStream(String path) throws IOException {
        StringBuilder res = new StringBuilder();
        try (BufferedReader br
                = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                res.append(line).append('\n');
            }
        }
        return res.toString();
    }
}
