package cse364.project;

import java.io.*;
import java.util.*;

public class ReadFile {
    public static List<String> splitByDelimiter(String input, String delimiter) {
        List<String> result = new ArrayList<>();
        int start = 0;
        int end = input.indexOf(delimiter);
        while (end != -1) {
            result.add(input.substring(start, end));
            start = end + delimiter.length();
            end = input.indexOf(delimiter, start);
        }
        if (start <= input.length()) {
            result.add(input.substring(start));
        }
        return result;
    }
    public List<List<String>> readDAT(String path){
        List<List<String>> csvList = new ArrayList<List<String>>();
        File file = new File(path);

        BufferedReader buffer = null;
        String line = "";

        try {
            buffer = new BufferedReader(new FileReader(file));
            while ((line = buffer.readLine()) != null) {
                List<String> ParseList = splitByDelimiter(line, "::");
                csvList.add(ParseList);
            }
        } catch(FileNotFoundException ex){
            ex.getMessage();
        } catch(IOException ex){
            ex.getMessage();
        } finally{
            try{
                if(buffer != null){
                    buffer.close();
                }
            } catch(IOException ex){
                ex.getMessage();
            }
        }
        System.out.println("success read\n");
        return csvList;
    }
}
