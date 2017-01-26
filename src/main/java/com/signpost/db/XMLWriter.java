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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.bukkit.Bukkit;

/**
 *
 * @author Jan Schoneberg(Freeviv)
 */
public class XMLWriter implements Runnable {

    private final static String PATH = "plugins/FastTravel-0.2/";
    private final static String FILE_NAME = "areas.xml";
    private static final List<Area> AREA = new ArrayList<>();
    private static final List<String> NAMES = new ArrayList<>();
    private XMLInputFactory factory = XMLInputFactory.newFactory();
    private XMLEventReader reader = null;
    
    @Override
    public void run() {
        System.out.println("[FastTravel] Started XMLWriter thread");
    }
    
    public XMLWriter(){
        File tmp = new File(PATH + FILE_NAME);
        if(!tmp.exists()){
            writeAll();
        }
        try {
            reader = factory.createXMLEventReader(new FileReader(PATH + FILE_NAME));
            readFile();
        } catch (XMLStreamException | FileNotFoundException ex) {
            Logger.getLogger(StorePoints.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[FastTravel] This error may occur if this plugin is started for the first time or there are no waypoints.");
            System.out.println("[FastTravel] Please validate the XML-file bevor opening an issue!");
        }
    }
    
    private void readFile() throws XMLStreamException{
        boolean name = false;
        boolean x_pos = false;
        boolean y_pos = false;
        boolean z_pos = false;
        boolean owner = false; //TODO
        boolean player = false;
        boolean w_player = false;//TODO
        boolean dimension = false;
        boolean needsNewArea = true;
        Area a = null;
        while(reader.hasNext()){
            if(needsNewArea){
                a = new Area();
                needsNewArea = false;
            }
            if(a == null){
                throw new NullPointerException("Area was null. This should not happen!");
            }
            XMLEvent event = reader.nextEvent();
            switch(event.getEventType()){
                case XMLStreamConstants.START_ELEMENT:
                    StartElement start = event.asStartElement();
                    String seName = start.getName().toString();
                    if(seName.equalsIgnoreCase("name")){
                        name = true;
                    } else if(seName.equalsIgnoreCase("x_pos")){
                        x_pos = true;
                    } else if(seName.equalsIgnoreCase("y_pos")){
                        y_pos = true;
                    } else if(seName.equalsIgnoreCase("z_pos")){
                        z_pos = true;
                    } else if(seName.equalsIgnoreCase("owner")){
                        owner = true;
                    } else if(seName.equalsIgnoreCase("players")){
                        player = true;
                    } else if(seName.equalsIgnoreCase("wl_players")){
                        w_player = true;
                    } else if(seName.equalsIgnoreCase("dimension")){
                        dimension = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    Characters chars = event.asCharacters();
                    if(name) {
                        a.setName(chars.getData());
                        NAMES.add(chars.getData());
                        name = false;
                    }
                    if(x_pos) {
                        a.setX(Integer.parseInt(chars.getData()));
                        x_pos = false;
                    }
                    if(y_pos) {
                        a.setY(Integer.parseInt(chars.getData()));
                        y_pos = false;
                    }
                    if(z_pos) {
                        a.setZ(Integer.parseInt(chars.getData()));
                        z_pos = false;
                    }
                    if(player) {
                        String[] players = chars.getData().split(" ");
                        for(String s:players){
                            a.addPlayer(s);
                        }
                        a.playerInit();
                        player = false;
                    }
                    if(dimension) {
                        String world = chars.getData();
                        a.setWorld(Bukkit.getWorld(world));
                        dimension = false;
                    }
                    break;
            }
            if(a.isCorrect()){
                AREA.add(a);
                needsNewArea = true;
            }
        }
    }
    
    public final void writeAll() {
        String start = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +
                        "<class>";
        String end = "</class>";
        File f = new File(PATH + "out_tmp.xml");
        try {
            if(!f.exists()){
                f.createNewFile();
            }
            java.io.FileWriter fw = new java.io.FileWriter(f);
            try (BufferedWriter writer = new BufferedWriter(fw)) {
                writer.append(start);
                writer.newLine();
                for(Area a:AREA){
                    writer.append("  <area>");
                    writer.newLine();
                    writer.append("      <name>" + a.getName() + "</name>");
                    writer.newLine();
                    writer.append("      <x_pos>" + a.getX() + "</x_pos>");
                    writer.newLine();
                    writer.append("      <y_pos>" + a.getY() + "</y_pos>");
                    writer.newLine();
                    writer.append("      <z_pos>" + a.getZ() + "</z_pos>");
                    writer.newLine();
                    writer.append("      <dimension>" + a.getWorld().getName() + "</dimension>");
                    writer.newLine();
                    String all_player = new String();
                    for(String s:a.getAllPlayer()){
                        all_player += s + " ";
                    }
                    all_player = all_player.trim();
                    writer.append("      <players>" + all_player + "</players>");
                    writer.newLine();
                    writer.append("  </area>");
                    writer.newLine();
                    writer.flush();
                }
                writer.append(end);
                writer.flush();
                fw.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StorePoints.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StorePoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        File old_file = new File(PATH + FILE_NAME);
        f.renameTo(old_file);
    }
    
    public List<String> getAllNames(){
        return NAMES;
    }
    
    public void addName(String name){
        NAMES.add(name);
    }
    
    public void addArea(Area a){
        AREA.add(a);
    }
    
    public Area getAreaByName(String name){
        for(Area a:AREA){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }
    
    public boolean removeArea(Area a){
        if(!AREA.contains(a)){
            return false;
        }
        boolean b = AREA.remove(a);
        NAMES.remove(a.getName());
        writeAll();
        return b;
    }
    
    public List<Area> getAllAreas(){
        return AREA;
    }
    
}
