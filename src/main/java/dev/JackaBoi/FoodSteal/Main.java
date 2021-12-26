package dev.JackaBoi.FoodSteal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin implements CommandExecutor {

    private final Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (!getConfig().getBoolean("RemoveSelfPromo")) log.info("[FS] >> YT: JackaBoiGamez");
        log.info("[FS] >> Remove this self promo in the config");
        log.info("[FS] >> Loading...");
        new setmaxfoodlevel(this);
        new Events(this);
        log.info("[FS] >> Loaded!");
    }

    @Override
    public void onDisable() {
        log.info("[FS] >> Disabled!");
    }
}
