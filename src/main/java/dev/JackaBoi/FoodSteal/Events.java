package dev.JackaBoi.FoodSteal;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Events implements Listener {

    private Main plugin;

    public Events(Main instance){
        Bukkit.getPluginManager().registerEvents(this,instance);
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!plugin.getConfig().getConfigurationSection("Players").contains(String.valueOf(e.getPlayer().getUniqueId()))){
            plugin.getConfig().set("Players."+e.getPlayer().getUniqueId()+".MaxFoodLevel", 20);
            plugin.saveConfig();
        }
        if(plugin.getConfig().getInt("Players."+e.getPlayer().getUniqueId()+".MaxFoodLevel")<e.getPlayer().getFoodLevel()){
            e.getPlayer().setFoodLevel(plugin.getConfig().getInt("Players."+e.getPlayer().getUniqueId()+".MaxFoodLevel"));
        }
    }
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        if(e.getFoodLevel()>plugin.getConfig().getInt("Players."+e.getEntity().getUniqueId()+".MaxFoodLevel")){
            e.getEntity().setFoodLevel(plugin.getConfig().getInt("Players."+e.getEntity().getUniqueId()+".MaxFoodLevel"));
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getEntity().isDead()){
            if(plugin.getConfig().getInt("Players."+e.getEntity().getUniqueId()+".MaxFoodLevel")!=0){
                plugin.getConfig().set("Players."+e.getEntity().getUniqueId()+".MaxFoodLevel", plugin.getConfig().getInt("Players."+e.getEntity().getUniqueId()+".MaxFoodLevel")-1);
                plugin.saveConfig();
            }
            if (e.getEntity().getKiller()!=null) {
                if(plugin.getConfig().getInt("Players."+e.getEntity().getKiller().getUniqueId()+".MaxFoodLevel")<20) {
                    plugin.getConfig().set("Players." + e.getEntity().getKiller().getUniqueId() + ".MaxFoodLevel", plugin.getConfig().getInt("Players." + e.getEntity().getKiller().getUniqueId() + ".MaxFoodLevel") + 1);
                    plugin.saveConfig();
                }
            }
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                e.getPlayer().setFoodLevel(plugin.getConfig().getInt("Players."+e.getPlayer().getUniqueId()+".MaxFoodLevel"));
            }
        }, 3L);
    }

}
