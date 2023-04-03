package com.oprzemek.game.objects;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.main.Game;
import com.oprzemek.game.resources.Images;

import java.awt.*;

public class Block extends Rectangle {

    public static final int blockSize = 70;
    private int id;

    public Block(int x, int y, int id){
        if(id != Game.coinID && id != Game.doorID && id != Game.spikeID)
            setBounds(x, y, blockSize, blockSize);
        this.id = id;
    }

    public void tick(){}

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        if(id != 0 && id != Game.coinID && id != Game.doorID && id != Game.spikeID){
            g.drawImage(Images.blocks[id - 1],x - (int)GameState.xOffset, y - (int)GameState.yOffset,
                    width, height, null);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
