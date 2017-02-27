package passwordrem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    public Integer getRandomN(int i) {
        Th t0 = new Th(0);
        Th t1 = new Th(1);
        Th t2 = new Th(2);
        Th t3 = new Th(3);
        Th t4 = new Th(4);
        Th t5 = new Th(5);
        Th t6 = new Th(6);
        Th t7 = new Th(7);
        Th t8 = new Th(8);
        Th t9 = new Th(9);
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer out = Password.digits.get(i);
        Password.digits.clear();
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public String getRandomC(int i) {
        Th t0 = new Th(true);
        Th t1 = new Th(true);
        Th t2 = new Th(true);
        Th t3 = new Th(true);
        Th t4 = new Th(true);
        Th t5 = new Th(true);
        Th t6 = new Th(true);
        Th t7 = new Th(true);
        Th t8 = new Th(true);
        Th t9 = new Th(true);
        Th t10 = new Th(true);
        Th t11 = new Th(true);
        Th t12 = new Th(true);
        Th t13 = new Th(true);
        Th t14 = new Th(true);
        Th t15 = new Th(true);
        Th t16 = new Th(true);
        Th t17 = new Th(true);
        Th t18 = new Th(true);
        Th t19 = new Th(true);
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
        t19.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        String out = Password.letters.get(i);
        Password.letters.clear();
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public void ask() {
        String str;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Plese make a choice:\nn - for numbers password (fast)");
            System.out.println("l - for letters and numbers password (slow)");
            System.out.println("x - for exit\n");
            str = sc.next();
            if (str.equals("n") || str.equals("l") || str.equals("x")) {
                break;
            } else {
                System.out.println("Please make a correct choice.\n");
            }
        }
        switch (str) {
            case "n":
                this.getRandom(str);
                break;
            case "l":
                this.getRandom(str);
                break;
            case "x":
                break;
        }

    }

    public void getRandom(String s) {
        int strSize = 0;
        List<Integer> nList = new ArrayList<>();
        List<String> sList = new ArrayList<>();
        
        while (true) {
            boolean state = true;
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the length of the password:");
            String str = sc.next();
            try {
                strSize = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
                System.out.println("Plese enter only numbers!\n");
                state = false;
            } finally {
                if (state) {
                    break;
                }
            }

        }
        System.out.println("\nYour password is: ");
        Wait w = new Wait();
        w.start();
        if (s.equals("n")) {
            for (int i = 0; i < strSize; i++) {
                nList.add(this.getRandomN(this.getRandomN(this.getRandomN((int) (Math.random() * 10)))));
            }
        } else {
            for (int i = 0; i < strSize; i++) {
                sList.add(this.getRandomC((int) (Math.random() * 20)));
                nList.add(this.getRandomN(this.getRandomN(this.getRandomN((int) (Math.random() * 10)))));
            }
        }
        Password.stop = true;
        if (sList.isEmpty()) {
            Password.fString = nList.get(0).toString();
            for (int i = 1; i < nList.size(); i++) {
                Password.fString = Password.fString.concat(nList.get(i).toString());
            }
        } else {
            if (Math.random() < 0.5) {
                Password.fString = nList.get(0).toString();
            } else {
                Password.fString = sList.get(0);
            }
            for (int i = 1; i < sList.size(); i++) {
                if (Math.random() < 0.5) {
                   Password.fString = Password.fString.concat(nList.get(i).toString());
                } else {
                    Password.fString = Password.fString.concat(sList.get(i));
                }
            }
        }
        System.out.print(Password.fString+"\n");
    }
}
