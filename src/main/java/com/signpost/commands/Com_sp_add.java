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
package com.signpost.commands;

import com.signpost.Signpost;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * CommandExecutor for the /ft command
 * @author Jan Schoneberg(Freeviv)
 */
public class Com_sp_add implements CommandExecutor{
    

    private Plugin plugin;
    
    public void setPlugin(Plugin pl)
    {
        plugin = pl;
    }
    
    /**
     * Implements the Command "/sp_add" thich allows the player to travel to different
     * places on the server
     * @param sender CommandSender (should be a human player)
     * @param com Command
     * @param arg0
     * @param arg1 List of arguments, only first entry will be recognized
     * @return true if the player was teleported else false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command com, String arg0, String[] arg1) {
        Player p;
        if(sender instanceof Player) {
            p = (Player)sender;
            System.out.println("Test123");
            p.setMetadata("sp_add", new FixedMetadataValue(plugin, true));
        } else {
            return false;
        }
        return true;
    }
}
