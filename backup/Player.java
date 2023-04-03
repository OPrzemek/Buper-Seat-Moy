package com.oprzemek.game.entities;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.main.Game;
import com.oprzemek.game.main.GamePanel;
import com.oprzemek.game.objects.Block;
import com.oprzemek.game.physics.Collision;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Console;

public class Player{

    private boolean right = false, left = false, jumping = false, falling = false;
    private boolean topCollision = false;

    private int jumpAmount = 2;
    private int currentJumpAmount = 2;
    private double x, y;
    private int width, height;

    private double moveSpeed = 3.25;
    private double jumpSpeed = 5;
    private double currentJumpSpeed = jumpSpeed;

    private double maxFallSpeed = 5;
    private double currentFallSpeed = 0.1;

    //Speed multiplier
    private double speedMultiplier = 2;

    Rectangle hitBox;
    public Player(int width, int height){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle((int)x,(int)y, width, height);
    }

    public void tick(Block[][] b){

        int iX = (int)x; //Sparsowane zmienne na liczby całkowite
        int iY = (int)y; //

        hitBox.x = (int)x + (int)GameState.xOffset;
        hitBox.y = (int)y + (int)GameState.yOffset;
        System.out.println("hitbox XY: " + hitBox.x + " " + hitBox.y);

        System.out.println(GameState.xOffset + " / " + GameState.yOffset);

        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < b[0].length; j++){
                if(b[i][j].getId() != 0){

                    //Poprzednia kolizja
                    //
                    //Kolizja od góry
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset + 2,
                            iY + height + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset - 1,
                            iY + height + (int)GameState.yOffset + 1), b[i][j])
                    ){
                        double dy = b[i][j].getY() - height - GameState.yOffset;
                        GameState.yOffset -= y - dy;
                        falling = false;
                        topCollision = true;
                        currentJumpAmount = jumpAmount;
                    } else {
                        if(!topCollision && !jumping){
                            currentJumpSpeed = jumpSpeed;
                            falling = true;
                        }
                    }

                    //Kolizja od dołu
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset + 1,
                            iY + (int)GameState.yOffset - 2), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset,
                            iY + (int)GameState.yOffset - 2), b[i][j])
                    ){
                        jumping = false;
                        falling = true;
                        currentJumpAmount--;
                        GameState.yOffset += 4;
                    }

                    //Kolizja od lewej strony
                    if  (Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset + 1,
                            iY + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset + 1,
                            iY + height + (int)GameState.yOffset - 1), b[i][j])
                            ){
                        right = false;
                        double dx = b[i][j].getX() - width - GameState.xOffset;
                        //double dx = Math.abs(GameState.xOffset) - b[i][j].getX() - width;
                        System.out.println("Lewa kolizja - offset:" + GameState.xOffset + " getX: " + b[i][j].getX() + " width: " + width);
                        //System.out.println("dx po kolizji od lewej: " + dx + "x: " + x + " getx: " + b[i][j].getX());
                        GameState.xOffset -= (x - dx);
                    }

                    //Kolizja od prawej strony
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1,
                            iY + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1,
                            iY + height + (int)GameState.yOffset - 1), b[i][j])
                            ){
                        left = false;
                        double dx = Math.abs(b[i][j].getX()) - GameState.xOffset + Block.blockSize;
                        //System.out.println("dx po kolizji od prawej: " + dx + " x: " + x + " getx: " + b[i][j].getX());
                        System.out.println("Prawa kolizja - offset:" + GameState.xOffset + " getX: " + b[i][j].getX() + " blockSize: " + Block.blockSize);
                        GameState.xOffset -= (x - dx);
                    }

                    /*if(hitBox.intersects(b[i][j])){
                        double dy = b[i][j].getY() - height - GameState.yOffset;
                        System.out.println("hitbox: " + hitBox.getBounds());
                        System.out.println("Gorna kolizja - offset:" + GameState.xOffset + " getX: " + b[i][j].getX() + " height: " + height);
                        GameState.yOffset -= y - dy;
                        falling = false;
                        topCollision = true;
                        currentJumpAmount = jumpAmount;
                    } else {
                        if(!topCollision && !jumping){
                            currentJumpSpeed = jumpSpeed;
                            falling = true;
                        }
                    }*/

                }
            }
        }

        topCollision = false;

        if(right){
            GameState.xOffset += moveSpeed;
        }

        if(left){
            GameState.xOffset -= moveSpeed;
        }

        if(jumping){
            falling = false;
            GameState.yOffset -= currentJumpSpeed * speedMultiplier;

            currentJumpSpeed -= .1 * speedMultiplier;

            if(currentJumpSpeed <= 0){
                currentJumpSpeed = jumpSpeed;
                currentJumpAmount--;
                jumping = false;
                falling = true;
            }
        }

        if(falling){
            GameState.yOffset += currentFallSpeed;
            y = 300;
            if(currentFallSpeed < maxFallSpeed){
                currentFallSpeed += .1 * speedMultiplier;
            }
        }

        if(!falling){
            currentFallSpeed = .1 * speedMultiplier;
        }
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 150, 0));
        g.fillRect((int)x, (int)y, width, height);
    }

    public void keyPressed(int k){
        if(k == KeyEvent.VK_RIGHT) right = true;
        if(k == KeyEvent.VK_LEFT) left = true;
        if(k == KeyEvent.VK_UP && currentJumpAmount > 0) jumping = true;
    }

    public void keyReleased(int k){
        if(k == KeyEvent.VK_RIGHT) right = false;
        if(k == KeyEvent.VK_LEFT) left = false;
    }
}
