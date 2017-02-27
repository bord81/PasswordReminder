package passwordrem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Th extends Thread {

    private Integer digit;
    boolean b;

    @Override
    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Th.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (digit != null) {
            Password.add(digit);
        } else {
            Password.add(Password.abc[(int) System.currentTimeMillis() % 52]);

        }
    }

    public Th(Integer digit) {
        this.digit = digit;
    }

    public Th(boolean b) {
        this.b = b;
    }
}
