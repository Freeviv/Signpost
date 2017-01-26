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

import com.signpost.commands.Com_sp_add;
import com.signpost.db.StorePoints;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Mainclass of this Plugin
 * @author Jan Schoneberg(Freeviv)
 */
public class Signpost extends JavaPlugin {

    /**
     * Fired when plugin is first enabled
     */
    @Override
    public void onEnable() {
        //this.getCommand("ft_loc").setExecutor(new Com_ft_loc());
        Com_sp_add sp = new Com_sp_add();
        sp.setPlugin(this);
        this.getCommand("sp_add").setExecutor(sp);
        EventListener el = new EventListener();
        el.setPlugin(this);
        getServer().getPluginManager().registerEvents(el, this);
    }
    /**
     * Fired when plugin is disabled
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
    
}
