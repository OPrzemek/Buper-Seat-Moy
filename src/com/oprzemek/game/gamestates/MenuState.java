package com.oprzemek.game.gamestates;

import com.oprzemek.game.main.GamePanel;
import com.oprzemek.game.resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
    //Stan Menu Głównego
    private String[] options = {"Play", "Help", "Quit"}; //Lista opcji do wyboru
    private int currentSelection = 0; //Obecnie wybrana opcja

    //Do managera przypisywany jest stan gry z klasy wyżej
    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g) {
        g.setColor(new Color(35, 35, 200));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        for(int i = 0; i < options.length; i++){
            if(i == currentSelection)
                g.setColor(Color.GREEN);
            else g.setColor(Color.BLACK);

            g.setFont(new Font("Arial", Font.PLAIN, 48));
            g.drawString(options[i], GamePanel.WIDTH / 2 - 50, 250 + i * 100);
        }

        g.drawImage(Images.blocks[19],GamePanel.WIDTH/2 - 150,50, 300, 150, null);
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_DOWN){
            currentSelection++;
            if(currentSelection >= options.length)
                currentSelection = 0;
        } else if (k == KeyEvent.VK_UP){
            currentSelection--;
            if(currentSelection < 0)
                currentSelection = options.length - 1;
        }

        if(k == KeyEvent.VK_ENTER){
            if(currentSelection == 0){
                //Play State
                gsm.states.push(new Level1State(gsm));
            } else if(currentSelection == 1){
                //Help State
                gsm.states.push(new HelpState(gsm));
            } else if(currentSelection == 2){
                //Exit State
                System.exit(0);
            }
        }
    }

    public void keyReleased(int k) {

    }
}
