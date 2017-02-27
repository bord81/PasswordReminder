package passwordrem;

/*
The purpose of this program is to test the basic Java functions, like IO, databases and such.
Don't use it for sensitive data!

It is a password reminder for websites.

User can store website' URL, login and password.
This data is stored then in a local text file and user can add, display or remove saved entries.
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Password {
    
//main variables initialization
    public static int length;
    public static String[] head = {"URL: ", "Login: ", "Password: "};
    public static String ser = "/Users/test1/Documents/path.ser";
    public static String filepath = "/Users/test1/Documents/passwords.txt";
    public static char[] c;
    public static char[] n;
    public static int firstD;
    public static int lastD;
    public static List<Integer> digits = new LinkedList<>();
    public static List<String> letters = new LinkedList<>();
    public static String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static volatile boolean stop = false;
    public static String fString;
    
    public static synchronized void add(Integer i) {
        digits.add(i);
    }

    public static synchronized void add(String s) {
        letters.add(s);
    }
    
    static class Path implements Serializable{
        public String filepath = "/Users/test1/Documents/passwords.txt";
    }
    
//main method welcomes user and handles display of initial menu
    public static void main(String[] args) {
        FileSys fsys= new FileSys();
        fsys.fSer(true);
        boolean m = true;
        System.out.println("Welcome to Password Reminder!");
        while (m) {
            String in;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nPlease make a choice:\nr - read passwords\nw - write a new password\nd - delete entry by id\nf - enter new filename/location\nx - exit program\n");
            while (true) {
                in = sc.nextLine();
                if (in.equals("r") || in.equals("w") || in.equals("d") || in.equals("x")||in.equals("f")) {
                    break;
                }
                System.out.println("Please make correct choice (r/w/d/x):\n");
            }
            switch (in) {
                case "r":
                    fsys.fRead();
                    break;
                case "w":
                    fsys.fWrite();
                    break;
                case "d":
                    fsys.deleteEntry();
                    break;
                case "f":
                    fsys.fPath();
                    break;
                case "x":
                    m = false;
            }
        }
    }
}
