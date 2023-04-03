package com.oprzemek.game.main;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static final int coinID = 16;
    public static final int doorID = 17;
    public static final int spikeID = 19;
    //Główna klasa gry, rozpoczyna okno gry oraz panel gry
    public static void main(String[] args) {
        System.out.println("Siema");
        JFrame jframe = new JFrame("Buper Seat Moy");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLayout(new BorderLayout());
        jframe.add(new GamePanel(), BorderLayout.CENTER);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}