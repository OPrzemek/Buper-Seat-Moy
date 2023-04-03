package com.oprzemek.game.main;

import com.oprzemek.game.gamestates.GameStateManager;
import com.oprzemek.game.resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    //Panel gry, zawiera metody związane z działaniem gry i jej wyglądem
    public static final int WIDTH = 800; //Szerokość okna gry
    public static final int HEIGHT = 600; //Wysokość okna gry

    private Thread thread; //Główny proces gry
    private boolean isRunning = false; //Czy proces jest włączony

    private int FPS = 60; //Szybkość odświeżania okna
    private long targetTime = 1000 / FPS;

    private GameStateManager gsm;

    //Domyślny konstruktor panelu gry
    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Ustawienie rozmiaru okna

        addKeyListener(this);
        setFocusable(true);

        new Images();

        start(); //Rozpoczęcie procesu
    }

    private void start(){ //Metoda rozpoczynania procesu, wykonywana tylko raz
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){ //Metoda działania procesu, wykonywana FPS(60) razy na sekundę
        long start, elapsed, wait;

        gsm = new GameStateManager();

        while (isRunning){ //Game loop
            start = System.nanoTime();

            tick();

            //Sprawdzanie ile czasu minęło od ostatniej klatki gry.
            //Jeżeli poszło szybciej niż FPS(60) razy na sekundę, poczekaj (try..catch).
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;

            if(wait <= 0)
                wait = 5;

            try{
                Thread.sleep(wait);
            } catch (Exception e){
                e.printStackTrace();
            }

            repaint();
        }
    }

    public void tick(){
        gsm.tick();
    }

    public void paintComponent(Graphics g){ //Rysowanie okna
        super.paintComponent(g);

        g.clearRect(0, 0, WIDTH, HEIGHT);

        gsm.draw(g);
    }
    //Sprawdzanie ,czy kliknięto przycisk
    public void keyPressed(KeyEvent e){
        gsm.keyPressed(e.getKeyCode());
    }
    //Sprawdzanie ,czy puszczono przycisk
    public void keyReleased(KeyEvent e){
        gsm.keyReleased(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e){}
}
