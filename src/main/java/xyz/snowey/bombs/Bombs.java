package xyz.snowey.bombs;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.snowey.bombs.commands.BombsCommand;
import xyz.snowey.bombs.events.GUIClick;
import xyz.snowey.bombs.events.GUIClose;
import xyz.snowey.bombs.util.GUI;
import xyz.snowey.bombs.util.Msg;

import java.util.HashMap;
import java.util.UUID;

public final class Bombs extends JavaPlugin {

    private static Bombs instance;
    private static Economy econ;
    @Getter public HashMap<UUID, GUI> activePlayers = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        Msg.console("&bBombs Enabled.");

        getCommand("bombs").setExecutor(new BombsCommand());
        getServer().getPluginManager().registerEvents(new GUIClick(), this);
        getServer().getPluginManager().registerEvents(new GUIClose(), this);

        if (!setupEconomy() ) {
            Msg.console("&4Bombs disabled due no Vault dependency not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        Msg.console("&cBombs Disabled.");
    }

    public static Bombs getInstance(){
        return instance;
    }

    public static Economy getEconomy(){
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
