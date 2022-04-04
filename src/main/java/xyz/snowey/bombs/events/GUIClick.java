package xyz.snowey.bombs.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.snowey.bombs.Bombs;
import xyz.snowey.bombs.util.GUI;

public class GUIClick implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUI gui = Bombs.getInstance().getActivePlayers().get(player.getUniqueId());
        if(e.getClickedInventory() == null) {
            return;
        }
        if(e.getView().getTitle().equalsIgnoreCase("Bombs")){
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if(Bombs.getInstance().getActivePlayers().get(player.getUniqueId()) != null){
                if (e.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)) {
                    gui.reveal();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bombs.getInstance(), new Runnable() {
                        public void run(){
                            gui.closeGUI();
                        }
                    }, 10L);
                }
                if (e.getCurrentItem().getType().equals(Material.CHEST)) {
                    gui.clickedChest(e.getSlot());
                }
            }
        }
    }
}
