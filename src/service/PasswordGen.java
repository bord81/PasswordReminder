package service;

import java.util.LinkedList;
import java.util.List;

public class PasswordGen {
    
    public static List<Integer> digits = new LinkedList<>();
    public static List<String> letters = new LinkedList<>();
    public static String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    
     public static synchronized void add(Integer i) {
        digits.add(i);
    }

    public static synchronized void add(String s) {
        letters.add(s);
    }
}
