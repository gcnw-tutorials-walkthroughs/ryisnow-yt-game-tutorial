package main;

import javax.swing.*;
//
//REVIEWED 13APR2022
//
public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel ();
        window.add(gamePanel);
//DEFINE - window size
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
//START - game in window
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
