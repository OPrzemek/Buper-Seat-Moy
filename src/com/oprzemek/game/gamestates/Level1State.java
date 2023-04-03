package com.oprzemek.game.gamestates;

import com.oprzemek.game.entities.Player;
import com.oprzemek.game.main.GamePanel;
import com.oprzemek.game.mapping.Map;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Level1State extends GameState{

    private Player player;
    private Map map;

    private String pointsText = "";
    private int coinsAmount;
    public Level1State(GameStateManager gsm){
        super(gsm);
    }
    public void init() {
        xOffset = -320;
        yOffset = -150;

        player = new Player(30, 30);
        map = new Map("/level1.map");

    }

    public void tick() {
        player.tick(map.getBlocks(), map.getMovingBlocks(), map.getCoins(), map.getDoor(), map.getSpikes());
        map.tick();
        if(player.isDead == true) gsm.states.push(new Level1State(gsm));
        if(player.points == map.getCoinsAmount()) map.getDoor().setOpen(true);
        if(player.isCollidingWithDoor && map.getDoor().getOpen()) gsm.states.push(new Level2State(gsm));
        pointsText = "Points: " + player.points + "/" + map.getCoinsAmount();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(20, 75, 150));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        map.draw(g);
        player.draw(g);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.YELLOW);
        g.drawString(pointsText, 50, 50);
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ESCAPE) gsm.states.push(new MenuState(gsm));
        player.keyPressed(k);
    }

    public void keyReleased(int k) {
        player.keyReleased(k);
    }
}
