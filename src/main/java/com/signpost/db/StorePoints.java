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

import java.util.List;
import org.bukkit.World;

/**
 *
 * @author Jan Schoneberg(Freeviv)
 */
public class StorePoints {

    private static StorePoints instance;
    private final XMLWriter writer;
    
    private StorePoints(){
        writer = new XMLWriter();
        Thread t = new Thread(writer);
        t.start();
    }
    
    public static StorePoints getInstance(){
        if(instance == null){
            instance = new StorePoints();
        }
        return instance;
    }
    
    public boolean addArea(String name,World w, int x,int y, int z){
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        Area a = new Area();
        a.setName(name);
        writer.addName(name);
        a.setX(x);
        a.setY(y);
        a.setZ(z);
        a.setWorld(w);
        writer.addArea(a);
        writer.writeAll();
        return true;
    }
    
    public List<String> getAllNames(){
        return writer.getAllNames();
    }
    
    /**
     * Returns the area with the given name or null
     * @param name Name of the area
     * @return the area or null if this area does not exists
     */
    public Area getAreaByName(String name){
        return writer.getAreaByName(name);
    }
    
    public boolean removeArea(Area a){
        return writer.removeArea(a);
    }
    
    public boolean removeAreaByName(String name){
        return removeArea(getAreaByName(name));
    }
    
    public List<Area> getAllAreas(){
        return writer.getAllAreas();
    }
    
    public void writeAll(){
        writer.writeAll();
    }
}