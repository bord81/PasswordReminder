package fstor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Table implements Serializable {

    private String master;
    private byte[] salt;
    private List<Entry> passwords = new LinkedList<>();

    public Table(String master, byte[] salt, List<Entry> passwords) {
        this.master = master;
        this.salt = salt;
        this.passwords = passwords;
    }

    public String getMaster() {
        return master;
    }

    public byte[] getSalt() {
        return salt;
    }

    public List<Entry> getPasswords() {
        return passwords;
    }

}
