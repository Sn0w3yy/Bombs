package xyz.snowey.bombs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import xyz.snowey.bombs.Bombs;
import xyz.snowey.bombs.util.Eco;
import xyz.snowey.bombs.util.GUI;
import xyz.snowey.bombs.util.Msg;

public class GUIClose implements Listener {

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        GUI gui = Bombs.getInstance().getActivePlayers().get(player.getUniqueId());
        if(e.getView().getTitle().equalsIgnoreCase("Bombs")){
            if(!gui.getGameRunning()){
                player.sendTitle(Msg.color("&4You Lost."), Msg.color("$" + gui.getBet() + "."), 1, 20, 1);
            }else if(gui.getDiamondCounter() == 0){
                Eco.giveMoney(player, gui.getBet());
                player.sendTitle(Msg.color("&bRefunded."), Msg.color("$" + gui.getBet() + " Refunded."), 1, 20, 1);
            }else{
                Eco.giveMoney(player, gui.getCashout());
                player.sendTitle(Msg.color("&aCashed Out."), Msg.color("$" + gui.getCashout() + " Cashed Out."), 1, 20, 1);
            }
            gui.resetDiamondCounter();
            Bombs.getInstance().getActivePlayers().remove(player.getUniqueId());
        }
    }
}
