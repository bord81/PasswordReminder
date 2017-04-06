package service;

import fstor.Entry;
import fstor.Table;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.LinkedList;

public class FileMan {

    private final static FileMan f = new FileMan();
    private File currentf;
    private String intpass;

    private FileMan() {
    }

    public static FileMan getFileMan() {
        return f;
    }

    private String encode64(String s) {
        Encoder enc = Base64.getUrlEncoder();
        String enc_s = enc.encodeToString(s.getBytes());
        return enc_s;
    }

    private String decode64(String s) {
        Decoder dec = Base64.getUrlDecoder();
        String dec_s = new String(dec.decode(s));
        return dec_s;
    }

    private String getSHA128(String pass, byte[] salt) {
        String genPass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(pass.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            genPass = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return genPass;
    }

    private byte[] getSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return salt;
    }

    public void decodeTable(String intpass) {
        this.intpass = intpass;
    }

    public void decodePreCheck(Entry e) throws IllegalArgumentException {
        Entry check = new Entry(decode64(encode64(e.getUrl())), decode64(encode64(e.getLogin())), decode64(encode64(e.getPass())));
    }

    public Table decodeTable(Table table) throws IllegalArgumentException {
        Table newTable = new Table(table.getMaster(), table.getSalt(), new LinkedList<>());
        String check_pass = getSHA128(intpass, newTable.getSalt());
        boolean check = check_pass.equals(newTable.getMaster());
        if (check_pass.equals(newTable.getMaster())) {
            table.getPasswords().forEach((t) -> {
                if (t.getUrl().length() > 1 && t.getLogin().length() > 1 && t.getPass().length() > 1) {
                    Entry entry = new Entry(decode64(t.getUrl()), decode64(t.getLogin()), decode64(t.getPass()));
                    newTable.getPasswords().add(entry);
                }
            });
        }
        if (check) {
            return newTable;
        } else {
            return null;
        }
    }

    public Table getTable(File file) {
        Table table = null;
        FileInputStream filestr = null;
        ObjectInputStream in = null;
        try {
            filestr = new FileInputStream(file);
            in = new ObjectInputStream(filestr);
            table = (Table) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                filestr.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return table;
    }

    public void saveTable(File file) {
        this.currentf = file;
    }

    public boolean saveTable(Table table) {
        boolean isSuccess = false;
        if (table.getSalt() == null) {
            String codePass;
            byte[] newSalt = getSalt();
            codePass = getSHA128(table.getMaster(), newSalt);
            Table newTable = new Table(codePass, newSalt, table.getPasswords());
            FileOutputStream filestr = null;
            ObjectOutputStream os = null;
            try {
                filestr = new FileOutputStream(currentf);
                os = new ObjectOutputStream(filestr);
                os.writeObject(newTable);
                isSuccess = true;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    filestr.close();
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            Table encTable = new Table(table.getMaster(), table.getSalt(), new LinkedList<>());
            table.getPasswords().forEach((t) -> {
                Entry entry = new Entry(encode64(t.getUrl()), encode64(t.getLogin()), encode64(t.getPass()));
                encTable.getPasswords().add(entry);
            });
            FileOutputStream filestr = null;
            ObjectOutputStream os = null;
            try {
                filestr = new FileOutputStream(currentf);
                os = new ObjectOutputStream(filestr);
                os.writeObject(encTable);
                isSuccess = true;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    filestr.close();
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return isSuccess;
    }
}
