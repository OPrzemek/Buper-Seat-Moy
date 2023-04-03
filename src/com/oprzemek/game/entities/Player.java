package com.oprzemek.game.entities;

import com.oprzemek.game.gamestates.GameState;
import com.oprzemek.game.main.GamePanel;
import com.oprzemek.game.objects.*;
import com.oprzemek.game.physics.Collision;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player{

    private boolean right = false, left = false, jumping = false, falling = false, resetJump = false, running = false;
    private boolean topCollision = false;

    private int jumpAmount = 2;
    private int currentJumpAmount = 2;
    private double x, y;
    private int width, height;

    private double acceleration = 1;
    private double moveSpeed = 3.25;
    private double jumpSpeed = 5;
    private double currentJumpSpeed = jumpSpeed;

    private double maxFallSpeed = 5;
    private double currentFallSpeed = 0.1;

    //Speed multiplier
    private double speedMultiplier = 2;

    public boolean isDead = false;

    public int points = 0;
    public boolean isCollidingWithDoor;

    public Player(int width, int height){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        this.width = width;
        this.height = height;
    }

    public void tick(Block[][] b, ArrayList<MovingBlock> mB, ArrayList<Coin> coins, Door door, ArrayList<Spike> spikes){

        int iX = (int)x; //Sparsowane zmienne na liczby całkowite
        int iY = (int)y; //

        GameState.xOffset = Math.round(GameState.xOffset * 10.0) / 10.0;
        GameState.yOffset = Math.round(GameState.yOffset * 10.0) / 10.0;

        //KOLIZJA ZWYKŁYCH BLOKÓW
        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < b[0].length; j++){
                if(b[i][j].getId() != 0){

                    //Kolizja od góry
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset + 2,
                            iY + height + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset - 3,
                            iY + height + (int)GameState.yOffset + 1), b[i][j])
                            ){
                        if(b[i][j].getId() == 2) isDead = true;
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
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset + 2,
                            iY + (int)GameState.yOffset - 2), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset - 3,
                            iY + (int)GameState.yOffset - 2), b[i][j])
                            ){
                        jumping = false;
                        falling = true;
                        //currentJumpAmount--;
                        GameState.yOffset += 3;
                    }

                    //Kolizja od lewej strony
                    if  (Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset + 2,
                            iY + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset + 2,
                            iY + height + (int)GameState.yOffset - 2), b[i][j])
                            ){
                        right = false;
                        double dx = b[i][j].getX() - width - GameState.xOffset;
                        GameState.xOffset -= (x - dx);
                    }

                    //Kolizja od prawej strony
                    if  (Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1,
                            iY + (int)GameState.yOffset + 1), b[i][j])
                            || Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1,
                            iY + height + (int)GameState.yOffset - 2), b[i][j])
                            ){
                        left = false;
                        double dx = Math.abs(b[i][j].getX()) - GameState.xOffset + Block.blockSize;
                        GameState.xOffset -= (x - dx);
                    }

                }
            }
        }

        //KOLIZJA RUSZAJĄCYCH SIĘ BLOKÓW
        for(int i = 0; i < mB.size(); i++){
            if(mB.get(i).getId() != 0){
                //Kolizja od góry
                if  (Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset + 2,
                        iY + height + (int)GameState.yOffset + 1), mB.get(i))
                        || Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset - 3,
                        iY + height + (int)GameState.yOffset + 1), mB.get(i))
                ){
                    if(mB.get(i).getId() == 2) isDead = true;
                    double dy = mB.get(i).getY() - height - GameState.yOffset;
                    GameState.yOffset -= y - dy;
                    GameState.xOffset += mB.get(i).getMove();
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
                if  (Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset + 2,
                        iY + (int)GameState.yOffset - 2), mB.get(i))
                        || Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset - 3,
                        iY + (int)GameState.yOffset - 2), mB.get(i))
                ){
                    jumping = false;
                    falling = true;
                    //currentJumpAmount--;
                    GameState.yOffset += 3;
                }

                //Kolizja od lewej strony
                if  (Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + (int)GameState.yOffset + 1), mB.get(i))
                        || Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + height + (int)GameState.yOffset - 2), mB.get(i))
                ){
                    right = false;
                    double dx = mB.get(i).getX() - width - GameState.xOffset;
                    GameState.xOffset -= (x - dx);
                }

                //Kolizja od prawej strony
                if  (Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset - 1,
                        iY + (int)GameState.yOffset + 1), mB.get(i))
                        || Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset - 1,
                        iY + height + (int)GameState.yOffset - 2), mB.get(i))
                ){
                    left = false;
                    double dx = Math.abs(mB.get(i).getX()) - GameState.xOffset + Block.blockSize;
                    GameState.xOffset -= (x - dx);
                }
            }
        }

        //KOLIZJA MONET
        for(int i = 0; i < coins.size(); i++){
            if(coins.get(i).getId() != 0){
                //Kolizja od lewej strony
                if  (Collision.playerCoin(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + (int)GameState.yOffset + 1), coins.get(i))
                        || Collision.playerCoin(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + height + (int)GameState.yOffset - 2), coins.get(i))
                ){
                    coins.remove(coins.get(i));
                    points++;
                    continue;
                }

                //Kolizja od prawej strony
                if  (Collision.playerCoin(new Point(iX + (int)GameState.xOffset - 1,
                        iY + (int)GameState.yOffset + 1), coins.get(i))
                        || Collision.playerCoin(new Point(iX + (int)GameState.xOffset - 1,
                        iY + height + (int)GameState.yOffset - 2), coins.get(i))
                ){
                    coins.remove(coins.get(i));
                    points++;
                    continue;
                }
            }
        }

        //KOLIZJA DRZWI
        //Kolizja od lewej strony
        if  (Collision.playerDoor(new Point(iX + width + (int)GameState.xOffset + 2,
                iY + (int)GameState.yOffset + 1), door)
                || Collision.playerDoor(new Point(iX + width + (int)GameState.xOffset + 2,
                iY + height + (int)GameState.yOffset - 2), door)
        ){
            isCollidingWithDoor = true;
        }
        else isCollidingWithDoor = false;

        //Kolizja od prawej strony
        if  (Collision.playerDoor(new Point(iX + (int)GameState.xOffset - 1,
                iY + (int)GameState.yOffset + 1), door)
                || Collision.playerDoor(new Point(iX + (int)GameState.xOffset - 1,
                iY + height + (int)GameState.yOffset - 2), door)
        ){
            isCollidingWithDoor = true;
        }
        else isCollidingWithDoor = false;

        //KOLIZJA KOLCÓW
        for(int i = 0; i < spikes.size(); i++){
            if(spikes.get(i).getId() != 0){
                //Kolizja od góry
                if  (Collision.playerSpike(new Point(iX + (int)GameState.xOffset + 2,
                        iY + height + (int)GameState.yOffset + 1), spikes.get(i))
                        || Collision.playerSpike(new Point(iX + width + (int)GameState.xOffset - 3,
                        iY + height + (int)GameState.yOffset + 1), spikes.get(i))
                ){
                    isDead = true;
                }
                //Kolizja od lewej strony
                if  (Collision.playerSpike(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + (int)GameState.yOffset + 1), spikes.get(i))
                        || Collision.playerSpike(new Point(iX + width + (int)GameState.xOffset + 2,
                        iY + height + (int)GameState.yOffset - 2), spikes.get(i))
                ){
                    isDead = true;
                }

                //Kolizja od prawej strony
                if  (Collision.playerSpike(new Point(iX + (int)GameState.xOffset - 1,
                        iY + (int)GameState.yOffset + 1), spikes.get(i))
                        || Collision.playerSpike(new Point(iX + (int)GameState.xOffset - 1,
                        iY + height + (int)GameState.yOffset - 2), spikes.get(i))
                ){
                    isDead = true;
                }
            }
        }

        topCollision = false;

        if(running){
            acceleration = 2;
        } else acceleration = 1;

        if(right){
            GameState.xOffset += moveSpeed * acceleration;
        }

        if(left){
            GameState.xOffset -= moveSpeed * acceleration;
        }

        if(jumping){
            if(resetJump){
                if(currentJumpAmount > 0){
                    currentJumpSpeed = jumpSpeed;
                    currentJumpAmount--;
                }
                resetJump = false;
            }
            falling = false;
            GameState.yOffset -= currentJumpSpeed * speedMultiplier;

            currentJumpSpeed -= .1 * speedMultiplier;

            if(currentJumpSpeed <= 0){
                currentJumpSpeed = jumpSpeed;
                //currentJumpAmount--;
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
        g.setColor(new Color(175,50,0));
        g.fillRect((int)x, (int)y, width, height);
        g.setColor(new Color(40,20,20));
        int sizeDiff = 9 - 3*currentJumpAmount;
        if(right == true)
            g.fillRect((int)x + sizeDiff + (6 - 2*currentJumpAmount), (int)y + sizeDiff,
                    width - sizeDiff*2, height - sizeDiff*2);
        else if(left == true)
            g.fillRect((int)x + sizeDiff - (6 - 2*currentJumpAmount), (int)y + sizeDiff,
                    width - sizeDiff*2, height - sizeDiff*2);
        else
            g.fillRect((int)x + sizeDiff, (int)y + sizeDiff,
                    width - sizeDiff*2, height - sizeDiff*2);
    }

    public void keyPressed(int k){
        if(k == KeyEvent.VK_RIGHT) right = true;
        if(k == KeyEvent.VK_LEFT) left = true;
        if(k == KeyEvent.VK_SHIFT) running = true;
        if(k == KeyEvent.VK_UP && currentJumpAmount > 0){
            jumping = true;
            resetJump = true;
        }
    }

    public void keyReleased(int k){
        if(k == KeyEvent.VK_RIGHT) right = false;
        if(k == KeyEvent.VK_LEFT) left = false;
        if(k == KeyEvent.VK_SHIFT) running = false;
    }
}
