package dev.JackaBoi.FoodSteal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin implements CommandExecutor {

    private final Logger log = Bukkit.getLogger();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(!getConfig().getBoolean("RemoveSelfPromo")) log.info("[FS] >> YT: JackaBoiGamez"); log.info("[FS] >> Remove this self promo in the config");
        log.info("[FS] >> Loading...");
        new Events(this);
        log.info("[FS] >> Loaded!");
    }

    @Override
    public void onDisable() {
        log.info("[FS] >> Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.isOp()){
            if(args.length<2) return false;
            if(Integer.parseInt(args[1])<0||Integer.parseInt(args[1])>20) return false;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if(target!=null){
                getConfig().set("Players."+target.getUniqueId()+".MaxFoodLevel", Integer.parseInt(args[1]));
                if(target.isOnline()){
                    Player t = Bukkit.getPlayer(target.getUniqueId());
                    if(getConfig().getInt("Players."+t.getUniqueId()+".MaxFoodLevel")<t.getFoodLevel()){
                        t.setFoodLevel(getConfig().getInt("Players."+t.getUniqueId()+".MaxFoodLevel"));
                    }
                }
            }else sender.sendMessage("Player Does Not Exist!"); return false;
        }else sender.sendMessage(Objects.requireNonNull(cmd.getPermissionMessage())); return true;
    }
}
