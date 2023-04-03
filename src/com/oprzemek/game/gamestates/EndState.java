package com.oprzemek.game.gamestates;

import com.oprzemek.game.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EndState extends GameState{
    //Stan Menu Głównego
    private String winText = "You won!";
    private String[] options = {"Menu", "Quit"}; //Lista opcji do wyboru
    private int currentSelection = 0; //Obecnie wybrana opcja

    //Do managera przypisywany jest stan gry z klasy wyżej
    public EndState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g) {
        g.setColor(new Color(25, 25, 200));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 72));
        g.drawString(winText, GamePanel.WIDTH / 2 - 150, GamePanel.HEIGHT / 2);
        for(int i = 0; i < options.length; i++){
            if(i == currentSelection)
                g.setColor(Color.GREEN);
            else g.setColor(Color.BLACK);

            g.setFont(new Font("Arial", Font.PLAIN, 48));
            g.drawString(options[i], 250 + i * 200, GamePanel.HEIGHT - 50);
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT){
            currentSelection++;
            if(currentSelection >= options.length)
                currentSelection = 0;
        } else if (k == KeyEvent.VK_RIGHT){
            currentSelection--;
            if(currentSelection < 0)
                currentSelection = options.length - 1;
        }

        if(k == KeyEvent.VK_ENTER){
            if(currentSelection == 0){
                //Menu
                gsm.states.push(new MenuState(gsm));
            } else if(currentSelection == 1){
                //Quit
                System.exit(0);
            }
        }
    }

    public void keyReleased(int k) {

    }
}
