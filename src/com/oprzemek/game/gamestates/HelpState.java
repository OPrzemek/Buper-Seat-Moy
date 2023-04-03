package com.oprzemek.game.gamestates;

import com.oprzemek.game.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HelpState extends GameState{
    //Stan Menu Głównego
    private String backText = "Back";
    private String[] helpLines = {"How to play:",
            "Arrow Left - Move Left",
            "Arrow Right - Move Right",
            "Arrow Up - Jump",
            "Shift - Run",
            "Esc - Quit to Menu",
            "Press Jump twice to double jump"};

    public HelpState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g) {
        g.setColor(new Color(75, 75, 200));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.GREEN);
        int fontSize = 48;
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString(backText, GamePanel.WIDTH / 2 - fontSize*(backText.length()/4), GamePanel.HEIGHT - 50);
        g.setColor(Color.BLACK);
        for(int i = 0; i < helpLines.length; i++){
            g.setFont(new Font("Arial", Font.PLAIN, 36));
            g.drawString(helpLines[i], 50, 100 + i*50);
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            gsm.states.push(new MenuState(gsm));
        }
    }

    public void keyReleased(int k) {

    }
}
