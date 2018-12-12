package app.utils;

import java.util.ArrayList;

public class Extractor {
    public static ArrayList<String> getFIO(String input){
        ArrayList <String> result = new ArrayList<>();
        String current ="";
        for (int i =0; i<= input.length()-1; i++){
            if (input.charAt(i)==' '){
                result.add(current);
                current="";
                continue;
            }
            current+=input.charAt(i);
        }
        result.add(current);
        return result;
    }

    public static ArrayList<String> getPass(String input){
        return null;
    }
}
