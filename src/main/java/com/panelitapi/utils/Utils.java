package com.panelitapi.utils;

import java.util.ArrayList;

public class Utils {
    private Utils() {}

    public static boolean hasUpperCaseLetters(String str){
        return str != null && str.matches("[A-Z]+");
    }

    public static String naturalizeCamelCase(String str){
        String normalized = "";
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(Character.isUpperCase(ch)){
                ch = Character.toLowerCase(ch);
                normalized += " "+ch;
            }
            else normalized += ch;
        }
        return normalized;
    }

    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
