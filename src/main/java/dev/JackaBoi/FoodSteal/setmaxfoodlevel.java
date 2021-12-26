package dev.JackaBoi.FoodSteal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class setmaxfoodlevel implements CommandExecutor {

    private Main plugin;

    public setmaxfoodlevel(Main instance){
        instance.getCommand("setmaxfoodlevel").setExecutor(this);
        plugin=instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length < 2) return false;
            if (Integer.parseInt(args[1]) < 0 || Integer.parseInt(args[1]) > 20) return false;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target.hasPlayedBefore()) {
                plugin.getConfig().set("Players." + target.getUniqueId() + ".MaxFoodLevel", Integer.parseInt(args[1]));
                plugin.saveConfig();
                Player t = Bukkit.getPlayer(target.getUniqueId());
                if (t!=null){
                    if(t.getFoodLevel()>plugin.getConfig().getInt("Players."+t.getUniqueId()+".MaxFoodLevel")){
                        t.setFoodLevel(plugin.getConfig().getInt("Players."+t.getUniqueId()+".MaxFoodLevel"));
                    }
                }
                return true;
            } else {
                sender.sendMessage("Player Does Not Exist!");
                return false;
            }
        } else{
            sender.sendMessage(Objects.requireNonNull(cmd.getPermissionMessage()));
            return true;}
    }
}
