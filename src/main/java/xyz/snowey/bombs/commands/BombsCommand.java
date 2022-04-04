package xyz.snowey.bombs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.snowey.bombs.util.Eco;
import xyz.snowey.bombs.util.GUI;
import xyz.snowey.bombs.util.Msg;

public class BombsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Msg.send(sender, "&cOnly players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        int bet = Integer.parseInt(args[0]);
        GUI gui = new GUI(player, bet);
        if(args.length != 1 || bet > 1000000 || bet < 1000){
            Msg.send(sender, "&cEnter a bet value using: /bombs <1000-1000000>");
            return true;
        }
        if((int)(Math.round(Eco.getMoney(player))) < bet){
            Msg.send(player, "&cInsufficient Funds.");
            return true;
        }
        Eco.takeMoney(player, bet);

        player.openInventory(gui.createGUI());

        return true;
    }
}
