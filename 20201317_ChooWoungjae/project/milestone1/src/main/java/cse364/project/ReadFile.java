package cse364.project;

import java.io.*;
import java.util.*;

public class ReadFile {
    public List<List<String>> readDAT(String path){
        List<List<String>> csvList = new ArrayList<List<String>>();
        File file = new File(path);
        System.out.println("success read\n");
        BufferedReader buffer = null;
        String line = "";

        try {
            buffer = new BufferedReader(new FileReader(file));
            while ((line = buffer.readLine()) != null) {
                List<String> ParseList = new ArrayList<String>();
                String[] LineParse = line.split("::");
                ParseList = Arrays.asList(LineParse);
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
        return csvList;
    }
}
