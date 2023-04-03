package com.oprzemek.game.physics;

import com.oprzemek.game.objects.*;

import java.awt.*;

public class Collision {

    public static boolean playerBlock(Point p, Block b){
        return b.contains(p);
    }

    public static boolean playerMovingBlock(Point p, MovingBlock b){
        return b.contains(p);
    }

    public static boolean playerCoin(Point p, Coin c){ return c.hitBox.contains(p); }

    public static boolean playerDoor(Point p, Door d){ return d.contains(p); }

    public static boolean playerSpike(Point p, Spike s){ return s.hitBox.contains(p); }
}
