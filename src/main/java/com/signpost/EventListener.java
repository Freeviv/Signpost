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
package com.signpost;

import com.signpost.db.Area;
import com.signpost.db.StorePoints;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * EventListener
 * @author Jan Schoneberg(Freeviv)
 */
public class EventListener implements Listener{
    
    private static List<Area> area = StorePoints.getInstance().getAllAreas();
    
    private Plugin plugin;
    
    public void setPlugin(Plugin pl){
        plugin = pl;
    }
    
    /**
     * Eventlistener for Playerjoining
     * @param event 
     */
    @EventHandler
     public void onPlayerJoin(PlayerJoinEvent event)
     {
     }
     
     /**
      * Eventlistener for player movement. Is required to register area discoveries.
      * @param event 
      */
    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event) {
    }
    
    /**
     * Inventory click listener. Needed for the inventory gui
     * @param event 
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Only check db if it was an right click on a block
        if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
            return;
        }
        // Check cklicked block 
        if(!(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN))
        {
            return;
        }
        Sign sign = (Sign) e.getClickedBlock().getState();
        String name = sign.getLine(0);
        Player p = e.getPlayer();
        if(p.getMetadata("sp_add").get(0).asBoolean())
        {
            p.setMetadata("sp_add", new FixedMetadataValue(plugin, false));
            if(name != null && !name.trim().isEmpty())
            {
                StorePoints.getInstance().addArea(name, p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
                p.sendMessage("Added new Signpost \"" + name + "\" at X: " + p.getLocation().getBlockX() + " Y: " + p.getLocation().getBlockY() + " Z: " + p.getLocation().getBlockZ());
            }
        }else {
            Area a = StorePoints.getInstance().getAreaByName(name);
            if(a != null)
            if(!a.getAllPlayer().contains(e.getPlayer().getUniqueId().toString()))
            {
                a.addPlayer(e.getPlayer().getUniqueId().toString());
            }
        }
        //p.setMetadata("sp_add", MetadataValue);
    }
}
