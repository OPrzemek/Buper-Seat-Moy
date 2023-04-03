package com.oprzemek.game.mapping;

import com.oprzemek.game.main.Game;
import com.oprzemek.game.objects.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {

    private String path;
    private String line;
    private int width, height;

    private Block[][] blocks;
    private ArrayList<MovingBlock> movingBlocks;

    private ArrayList<Coin> coins;
    private int coinsAmount = 0;
    private Door door;

    private ArrayList<Spike> spikes;
    public Map (String loadPath){
        path = loadPath;

        loadMap();
    }

    public void draw(Graphics g){
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[0].length; j++){
                blocks[i][j].draw(g);
            }
        }
        for(int i = 0; i < movingBlocks.size(); i++){
            movingBlocks.get(i).draw(g);
        }
        for(int i = 0; i < coins.size(); i++){
            coins.get(i).draw(g);
        }

        door.draw(g);

        for(int i = 0; i < spikes.size(); i++){
            spikes.get(i).draw(g);
        }
    }

    public void loadMap(){
        InputStream is = this.getClass().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try{
            line = br.readLine(); //Jedna linijka na komentarz w pliku mapy
            width = Integer.parseInt(br.readLine());
            height = Integer.parseInt(br.readLine());

            coins = new ArrayList<Coin>();
            spikes = new ArrayList<Spike>();

            blocks = new Block[height][width];

            for(int y = 0; y < height; y++){
                line = br.readLine();
                String[] tokens = line.split("\\s+");

                for(int x = 0; x < width; x++){
                    if(tokens[x].equals(String.valueOf(Game.coinID))){
                        coins.add(new Coin(x * Block.blockSize,
                                y * Block.blockSize, Integer.parseInt(tokens[x])));
                    }
                    if(tokens[x].equals(String.valueOf(Game.doorID))){
                        door = new Door(x*Block.blockSize,
                                y * Block.blockSize, Integer.parseInt(tokens[x]));
                    }
                    if(tokens[x].equals(String.valueOf(Game.spikeID))){
                        spikes.add(new Spike(x * Block.blockSize,
                                y * Block.blockSize, Integer.parseInt(tokens[x])));
                    }
                    blocks[y][x] = new Block(x * Block.blockSize, y * Block.blockSize, Integer.parseInt(tokens[x]));
                }
            }

            coinsAmount = coins.size();

            line = br.readLine();
            line = br.readLine();
            int length = Integer.parseInt(line);
            movingBlocks = new ArrayList<MovingBlock>();

            for(int i = 0; i < length; i++){
                line = br.readLine();
                String[] tokens = line.split("\\s+");

                movingBlocks.add(new MovingBlock(Integer.parseInt(tokens[0]) * Block.blockSize,
                        Integer.parseInt(tokens[1]) * Block.blockSize, Integer.parseInt(tokens[2]),
                        Integer.parseInt(tokens[3]) * Block.blockSize,
                        Integer.parseInt(tokens[4]) * Block.blockSize)
                );
            }

        } catch(NumberFormatException | IOException e){
            e.printStackTrace();
        }
    }

    public Block[][] getBlocks(){
        return blocks;
    }

    public void tick(){
        for(int i = 0; i < movingBlocks.size(); i++){
            movingBlocks.get(i).tick();
        }
    }

    public ArrayList<MovingBlock> getMovingBlocks() {
        return movingBlocks;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public int getCoinsAmount() {
        return coinsAmount;
    }

    public Door getDoor() {
        return door;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
