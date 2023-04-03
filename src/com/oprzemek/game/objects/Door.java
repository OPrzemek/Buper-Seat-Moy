package com.oprzemek.game.objects;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.main.Game;
import com.oprzemek.game.resources.Images;

import java.awt.*;

public class Door extends Rectangle{
    private static final int doorSize = 70;
    private int id;
    private boolean isOpen = false;
    public Door(int x, int y, int id){
        setBounds(x, y, doorSize, doorSize);
        this.id = id;
    }


    public void tick(){}

    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        if(id == Game.doorID && isOpen == false){
            g.drawImage(Images.blocks[id - 1],x - (int) GameState.xOffset, y - (int)GameState.yOffset,
                    width, height, null);
        }
        else{
            g.drawImage(Images.blocks[id],x - (int) GameState.xOffset, y - (int)GameState.yOffset,
                    width, height, null);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
    public boolean getOpen(){
        return isOpen;
    }
}
