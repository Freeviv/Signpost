/*
 * Copyright (C) 2016  Jan Schoneberg
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version
 * 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.signpost.db;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;

/**
 *
 * @author Jan Schoneberg(Freeviv)
 */
public class Area {
    /**
     * Name of the area. Will be used for all fining methodes
     */
    private String name;
    
    /**
     * x value of the spawning point.
     */
    private int x = Integer.MAX_VALUE;
    
    /**
     * y value of the spawning point.
     */
    private int y = Integer.MAX_VALUE;
    
    /**
     * z value of the spawning point.
     */
    private int z = Integer.MAX_VALUE;
    
    
    /**
     * List of players who have already discovered the area.
     */
    private List<String> player;
    
    /**
     * Helper attribute for object serilization.
     */
    private boolean playerInit = false;
    
    private World dimension;
    
    /**
     * Contructor
     */
    public Area(){
        player = new ArrayList<>();
    }
    
    /**
     * Setter for the area name.
     * @param name Name to be given to the area
     * @return true, if the name was successfully given else false
     */
    public boolean setName(String name){
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        this.name = name;
        return true;
    }
    
    /**
     * Setter for the x value
     * @param x 
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**
     * Setter for the y value
     * @param y 
     */
    public void setY(int y){
        this.y = y;
    }
    
    /**
     * Setter for the z value
     * @param z 
     */
    public void setZ(int z){
        this.z = z;
    }
    
    public void setWorld(World w){
        dimension = w;
    }

    
    /**
     * Adds a player to the area
     * @param uuid the uuid of the player
     * @return true if the player was added else false
     */
    public boolean addPlayer(String uuid){
        if(uuid == null || uuid.trim().isEmpty()){
            return false;
        }
        if(player.contains(uuid)){
            return true;
        }
        player.add(uuid);
        return true;
    }
    
    /**
     * Removes a player from the area
     * @param uuid the uuid of the player
     * @return true if player was remove else false
     */
    public boolean removePlayer(String uuid) {
        if(uuid == null || uuid.trim().isEmpty()){
            return false;
        }
        if(player.contains(uuid)){
            player.remove(uuid);
            return true;
        }
        return false;
    }
    
    public String getName(){
        return name;
    }
    
    /**
     * Getter for the x value of this area.
     * @return x
     */
    public int getX(){
        return x;
    }
    
    /**
     * Getter for the y value of this area.
     * @return y
     */
    public int getY(){
        return y;
    }
    
    /**
     * Getter for the z value of this area.
     * @return z
     */
    public int getZ(){
        return z;
    }
    
    /**
     * Sets playerinit to true
     */
    public void playerInit(){
        playerInit = true;
    }
    
    /**
     * Function to validate the area instance
     * @return True if all position values are changed and playerinit is true
     */
    public boolean isCorrect(){
        return (x != Integer.MAX_VALUE) && (y != Integer.MAX_VALUE) && (z != Integer.MAX_VALUE) && (name != null) && playerInit;
    }
    
    /**
     * Getter for all Players who have already discovered the area.
     * @return all Players who have already discovered the area.
     */
    public List<String> getAllPlayer(){
        return player;
    }
    
    public World getWorld(){
        return dimension;
    }
    
}