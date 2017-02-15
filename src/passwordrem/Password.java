package passwordrem;

/*
The purpose of this program is to test the basic Java functions, like IO, databases and such.
Don't use it for sensitive data!

It is a password reminder for websites.

User can store website' URL, login and password.
This data is stored then in a local text file and user can add, display or remove saved entries.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class Password {
//main variables initialization
    public static int length;
    public static String[] head = {"URL: ", "Login: ", "Password: "};
    public static char[] c;
    public static char[] n;
    public static int firstD;
    public static int lastD;
//main method welcomes user and handles display of initial menu
    public static void main(String[] args) {
        boolean m = true;
        System.out.println("Welcome to Password Reminder!");
        while (m) {
            String in;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nPlease make a choice:\nr - read passwords\nw - write a new password\nd - delete entry by id\nx - exit program\n");
            while (true) {
                in = sc.nextLine();
                if (in.equals("r") || in.equals("w") || in.equals("d") || in.equals("x")) {
                    break;
                }
                System.out.println("Please make correct choice (r/w/d/x):\n");
            }
            switch (in) {
                case "r":
                    fRead();
                    break;
                case "w":
                    fWrite();
                    break;
                case "d":
                    deleteEntry();
                    break;
                case "x":
                    m = false;
            }
        }
    }
//method for adding new entries to the file
    static void fWrite() {
        String[] inp = new String[3];
        Scanner sc = new Scanner(System.in);
        System.out.println("Entered data should contain only letters and numbers.");
        while (true) {
            boolean ok = true;
            for (int i = 0; i < 3; i++) {
                System.out.println("\nPlease enter " + head[i] + ":");
                inp[i] = sc.nextLine();
                char[] ch = new char[inp[i].length()];
                for (int j = 0; j < inp[i].length(); j++) {
                    ch[j] = inp[i].charAt(j);
                    if (Character.getNumericValue(ch[j]) == -1) {
                        ok = false;
                    }
                }
            }
            if (ok) {
                break;
            } else {
                System.out.println("Please re-enter using numbers and letters only, no special symbols.");
            }
        }
        getFile();
        try (Writer out = new FileWriter("/Users/test1/Documents/passwords.txt");) {
            if (length == 0) {
                out.append('?').append(inp[0]).append('&').append(inp[1]).append('&').append(inp[2]).append('§');
            } else {
                for (int i = 0; i < c.length; i++) {
                    if (c[i] == '§') {
                        c[i] = '?';
                    }
                }
                out.write(c);
                out.append(inp[0]).append('&').append(inp[1]).append('&').append(inp[2]).append('§');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//method for displaying the stored passwords
    static void fRead() {
        byte first = 0x0;
        int i = 0;
        int j = 1;
        String out = "";
        getFile();
        System.out.println("\nYour data are:\n");
        while (i < length) {
            if (c[i] == '§') {
                break;
            }
            if (c[i] == '?') {
                i++;
                out = out.concat(head[0]);
                while (c[i] != '&') {
                    if (Character.getNumericValue(c[i]) != -1) {
                        out = out.concat(String.valueOf(c[i]));
                    }
                    i++;
                }
                System.out.println("ID:" + j);
                j++;
                System.out.println(out);
                out = "";
                first = 0x1;
            } else if (c[i] == '&') {
                switch (first) {
                    case 0x1:
                        i++;
                        out = out.concat(head[1]);
                        while (c[i] != '&') {
                            if (Character.getNumericValue(c[i]) != -1) {
                                out = out.concat(String.valueOf(c[i]));
                            }
                            i++;
                        }
                        System.out.println(out);
                        out = "";
                        first = 0x0;
                        break;
                    case 0x0:
                        i++;
                        out = out.concat(head[2]);
                        while (c[i] != '?' && c[i] != '§' && c[i] != '&') {
                            if (Character.getNumericValue(c[i]) != -1) {
                                out = out.concat(String.valueOf(c[i]));
                            }
                            i++;
                        }
                        System.out.println(out + "\n");
                        out = "";
                        first = 0x1;
                        break;
                }
            }
        }
    }
//method to delete single entries
    static void deleteEntry() {
        int i = 0;
        int j = 0;
        Integer id = null;
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of entry ID or press 'r' to view existing entries:");
        while (true) {
            s = sc.nextLine();
            if (s.equals("r")) {
                try {
                    fRead();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            try {
                id = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers only or 'r' for entries list.");
            }
            if (id != null) {
                break;
            }
        }
        getFile();
        while (i < length) {
            if (c[i] == '§') {
                break;
            }
            if (c[i] == '?') {
                j++;
            }
            if (id.equals(j)) {
                //deletion code
                int d = 1;
                i++;
                firstD = i;
                while (i < length) {
                    if (c[i] == '?' || c[i] == '§') {
                        lastD = i;
                        break;
                    }
                    i++;
                    d++;
                }
                n = new char[length - d];
                int fn = 0;
                for (int v = 0; v < firstD; v++) {
                    n[v] = c[v];
                    fn = v;
                }
                for (int v = fn; v < n.length; v++) {
                    n[v] = c[lastD];
                    lastD++;
                }
                try (Writer out = new FileWriter("/Users/test1/Documents/passwords.txt");) {
                    out.write(n);
                } catch (Exception e) {
                    e.printStackTrace();;
                }
                break;
            }
            i++;
        }
        if (!id.equals(j)) {
            System.out.println("Please look at your passwords list again and choose right id to delete.");
        }
    }
//method for reading the passwords file and returning its contents
    static void getFile() {
        try (Reader rd = new FileReader("/Users/test1/Documents/passwords.txt");) {
            File f = new File("/Users/test1/Documents/passwords.txt");
            length = (int) f.length();
            c = new char[length];
            rd.read(c);
        } catch (FileNotFoundException e) {
            System.out.println("Passwords file not found in directory - enter values into new file.");
            File f = new File("/Users/test1/Documents/passwords.txt");
            try {f.createNewFile();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}