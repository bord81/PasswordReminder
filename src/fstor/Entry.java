package fstor;

import java.io.Serializable;


public class Entry implements Serializable {
    
    private String url;
    private String login;
    private String pass;

    public Entry(String url, String login, String pass) {
        this.url = url;
        this.login = login;
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
