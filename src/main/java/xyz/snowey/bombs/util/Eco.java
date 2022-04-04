package xyz.snowey.bombs.util;

import org.bukkit.entity.Player;
import xyz.snowey.bombs.Bombs;

public class Eco {

    public static void takeMoney(Player player, int amount){
        Bombs.getEconomy().withdrawPlayer(player, amount);
    }

    public static void giveMoney(Player player, int amount){
        Bombs.getEconomy().depositPlayer(player, amount);
    }

    public static double getMoney(Player player){
        return Bombs.getEconomy().getBalance(player);
    }
}
