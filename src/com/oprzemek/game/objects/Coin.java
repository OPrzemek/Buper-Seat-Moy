package com.oprzemek.game.objects;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.resources.Images;

import java.awt.*;

public class Coin extends Rectangle {
    private static final int coinSize = 70;
    private int id;
    public Rectangle hitBox;
    public Coin(int x, int y, int id){
        setBounds(x, y, coinSize, coinSize);
        hitBox = new Rectangle();
        hitBox.setBounds(x+15, y+15, 35, 35);
        this.id = id;
    }

    
    public void tick(){}
    
    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.drawImage(Images.blocks[id - 1],x - (int) GameState.xOffset, y - (int)GameState.yOffset,
                width, height, null);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
