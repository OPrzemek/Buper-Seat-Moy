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

        hitBox.x = iX + (int)GameState.xOffset;
        hitBox.y = iY + (int)GameState.yOffset;

        boolean canMoveHere = true;

        double xMove = 0;
        double yMove = 0;

        if(right){
            xMove = moveSpeed;
        }

        if(left){
            xMove = -moveSpeed;
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


        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < b[0].length; j++){
                if(b[i][j].getId() != 0){
                    //Kolizja
                    if(hitBox.intersects(b[i][j])){
                        System.out.println("Nie moze sie ruszac");
                        //canMoveHere = false;
                        i = j = 1000000;
                        break;
                    }
                    else{
                        System.out.println("Moze sie ruszac");
                        //canMoveHere = true;
                        if(right){
                            GameState.xOffset += xMove;
                            hitBox.x += xMove;
                        }
                        if(left){
                            GameState.xOffset += xMove;
                        }
                        i = j = 1000000;
                        break;
                    }
                }
            }
        }

        topCollision = false;

        canMoveHere = true;
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
