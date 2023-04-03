package com.oprzemek.game.gamestates;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    //Manager stanu gry, pozwala na ładowanie i przejście pomiędzy wybranymi stanami gry
    public Stack<GameState> states; //Stos zawierający stany gry
    public GameStateManager(){ //Domyślny konstruktor
        states = new Stack<GameState>(); //Deklaracja stosu stanów gry
        states.push(new MenuState(this)); //Przy rozpoczęciu procesu, domyślny stan to Menu
    }

    //Obecnie nieużywana
    public void tick(){
        states.peek().tick();
    }

    //Rysowanie stanu gry, który jest najwyżej na stosie
    public void draw(Graphics g){
        states.peek().draw(g);
    }
    //Podobnie jak wyżej...
    public void keyPressed(int k){
        states.peek().keyPressed(k);
    }

    public void keyReleased(int k){
        states.peek().keyReleased(k);
    }
}
