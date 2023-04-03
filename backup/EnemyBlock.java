package com.oprzemek.game.objects;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.resources.Images;

import java.awt.*;

public class EnemyBlock extends Block {
    public static final int blockSize = 70;
    private int id;
    public EnemyBlock(int x, int y, int id) {
        super(x, y, id);
    }

    public void tick(){}

    public void draw(Graphics g){
        g.setColor(Color.RED);
        if(id != 0){
            g.drawImage(Images.blocks[id - 1], x - (int) GameState.xOffset, y - (int)GameState.yOffset,
                    width, height, null);
        }
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
