package passwordrem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;
import static passwordrem.Password.c;
import static passwordrem.Password.filepath;
import static passwordrem.Password.firstD;
import static passwordrem.Password.head;
import static passwordrem.Password.lastD;
import static passwordrem.Password.length;
import static passwordrem.Password.n;
import static passwordrem.Password.ser;

public class FileSys {
    private String[] inp=new String[3];

    //set password file name & location
    void fPath() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter or paste a filename (.txt) with full path for storing passwords:");
        filepath = sc.nextLine();
        fSer(false);
    }

    //method for writing ser file
   void fSer(boolean acc) {
        Password.Path p = new Password.Path();
        if (acc) {
            try {
                File f = new File(ser);
                if (f.exists()) {
                    FileInputStream fread = new FileInputStream(ser);
                    ObjectInputStream fr = new ObjectInputStream(fread);
                    p = (Password.Path) fr.readObject();
                    filepath = p.filepath;
                    fr.close();
                    fread.close();
                }
            } catch (Exception ex) {
                System.out.println("Initializer error: it is advised to enter new path/filename for passwords file" + ex);
            }
        } else {
            try {
                p.filepath = filepath;
                FileOutputStream fwrite = new FileOutputStream(ser);
                ObjectOutputStream fw = new ObjectOutputStream(fwrite);
                fw.writeObject(p);
                fw.close();
                fwrite.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
//helper method for fWrite
  private void fWHelp(int start, int end){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Entered data should contain only letters and numbers.");
        while (true) {
            boolean ok = true;
            for (int i = start; i < end; i++) {
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
        
  }  
    
//method for adding new entries to the file
    void fWrite() {
        
        Scanner sc = new Scanner(System.in);
        fWHelp(0, 2);
        System.out.println("Do you want to auto-generate a password and use it in your entry? Press 'g' to generate a new password or any key to continue");
        String pc = sc.nextLine();
        if (pc.equals("g")) {
           
                Service s = new Service();
                s.ask();
                inp[2]=Password.fString;
               
        }else {
    fWHelp(2, 3);
        }
        getFile();
        try (Writer out = new FileWriter(filepath);) {
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
    void fRead() {
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
        c = new char[1];
        c[0] = '§';
    }

//method to delete single entries
    void deleteEntry() {
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
                try (Writer out = new FileWriter(filepath);) {
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
   private void getFile() {
        try (Reader rd = new FileReader(filepath);) {
            File f = new File(filepath);
            length = (int) f.length();
            c = new char[length];
            rd.read(c);
            rd.close();
        } catch (FileNotFoundException e) {
            System.out.println("Passwords file not found in directory - new one will be created.");
            File f = new File(filepath);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
