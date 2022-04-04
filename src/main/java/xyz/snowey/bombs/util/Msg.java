package xyz.snowey.bombs.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public static void send(CommandSender sender, String message){
        send(sender, message, "&a");
    }

    public static void send(CommandSender sender, String message, String prefix){
        sender.sendMessage(color(prefix+message));
    }

}
