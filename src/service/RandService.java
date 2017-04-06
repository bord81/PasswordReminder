package service;

import java.util.ArrayList;
import java.util.List;

public class RandService {

    private static final RandService s = new RandService();
    private static String fString = "";

    private RandService() {
    }

    public static RandService getRandService() {
        return s;
    }

    private Integer getRandomN(int i) {
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
            ex.printStackTrace();
        }
        Integer out = PasswordGen.digits.get(i);
        PasswordGen.digits.clear();
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    private String getRandomC(int i) {
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
            ex.printStackTrace();
        }
        String out = PasswordGen.letters.get(i);
        PasswordGen.letters.clear();
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    public String getRandom(int choice) {
        int strSize = choice;
        List<Integer> nList = new ArrayList<>();
        List<String> sList = new ArrayList<>();

        for (int i = 0; i < strSize; i++) {
            sList.add(this.getRandomC((int) (Math.random() * 20)));
            nList.add(this.getRandomN(this.getRandomN(this.getRandomN((int) (Math.random() * 10)))));
        }

        if (sList.isEmpty()) {
            fString = nList.get(0).toString();
            for (int i = 1; i < nList.size(); i++) {
                fString = fString.concat(nList.get(i).toString());
            }
        } else {
            if (Math.random() < 0.5) {
                fString = nList.get(0).toString();
            } else {
                fString = sList.get(0);
            }
            for (int i = 1; i < sList.size(); i++) {
                if (Math.random() < 0.5) {
                    fString = fString.concat(nList.get(i).toString());
                } else {
                    fString = fString.concat(sList.get(i));
                }
            }
        }
        return fString;
    }
}
