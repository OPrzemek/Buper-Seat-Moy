package com.oprzemek.game.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {
    public static BufferedImage[] blocks;

    public Images(){
        blocks = new BufferedImage[20];
        try {
            blocks[0] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Castle/castle.png"));
            blocks[1] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Castle/castleCenter.png"));
            blocks[2] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Castle/castleLeft.png"));
            blocks[3] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Castle/castleMid.png"));
            blocks[4] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Castle/castleRight.png"));
            blocks[5] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Grass/grass.png"));
            blocks[6] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Grass/grassCenter.png"));
            blocks[7] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Grass/grassLeft.png"));
            blocks[8] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Grass/grassMid.png"));
            blocks[9] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Grass/grassRight.png"));
            blocks[10] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Stone/stone.png"));
            blocks[11] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Stone/stoneCenter.png"));
            blocks[12] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Stone/stoneLeft.png"));
            blocks[13] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Stone/stoneMid.png"));
            blocks[14] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Stone/stoneRight.png"));
            blocks[15] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Other/coin.png"));
            blocks[16] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Other/doorClosed.png"));
            blocks[17] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Other/doorOpen.png"));
            blocks[18] = ImageIO.read(getClass().getResourceAsStream("/Blocks/Other/spikes.png"));
            blocks[19] = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
