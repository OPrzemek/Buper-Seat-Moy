package com.oprzemek.game.gamestates;

import java.awt.*;

public abstract class GameState {
    //Abstrakcyjna klasa dla poszczególnych stanów gry, takich jak Menu, Pauza, czy Gra
    protected GameStateManager gsm; //Obiekt managera stanu gry
    public static double xOffset, yOffset;
    public GameState(GameStateManager gsm){ //Domyślny konstruktor stanu gry
        this.gsm = gsm;

        xOffset = 0;
        yOffset = 0;
        init();
    }

    public abstract void init(); //Inicjalizacja stanu gry, wykonywana tylko raz
    public abstract void tick(); //Przemieszczenie obiektów
    public abstract void draw(Graphics g); //Rysowanie okna
    public abstract void keyPressed(int k); //Czy kliknięto przycisk
    public abstract void keyReleased(int k); //Czy puszczono przycisk
}
