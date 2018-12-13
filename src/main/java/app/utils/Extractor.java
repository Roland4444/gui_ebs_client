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
        String seria="";
        String number="";
        ArrayList arr=new ArrayList();
        for (int i=0; i<input.length();i++){
            if (i<4){
                seria+=input.charAt(i);
                continue;
            }
            number+=input.charAt(i);
        }
        arr.add(seria);
        arr.add(number);
        return arr;
    }
}
