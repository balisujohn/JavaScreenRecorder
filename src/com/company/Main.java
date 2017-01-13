package com.company;
import javax.swing.*;


public class Main {


    public static void main(String[] args) {
        Interface i = new Interface();
        i.setVisible(true);
        i.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        i.setSize(350, 100);
        i.setLocationRelativeTo(null);
    }


}
