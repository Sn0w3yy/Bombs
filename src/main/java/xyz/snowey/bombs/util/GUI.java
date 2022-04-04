package xyz.snowey.bombs.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.snowey.bombs.Bombs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GUI {

    private Inventory bombsGUI;
    private final int bet;
    private final Player player;
    private Boolean gameRunning = false;
    @Getter private int diamondCounter = 0;
    private final ArrayList<String> delayedPlayers = new ArrayList<>();
    private final int [] bombsArray = new int[25];
    private final int [] valuesArray = new int[]{11,12,13,14,15,20,21,22,23,24,29,30,31,32,33,38,39,40,41,42,47,48,49,50,51};
    private final double [] payoutArray = new double[]{1.18, 1.50, 1.91, 2.48, 3.25, 4.34, 5.89, 8.15, 11.55, 16.8, 25.21, 39.21, 63.72, 109.25, 200.29, 400.58, 901.31, 2400, 8410, 50470};

    public GUI(Player player, int bet){
        this.player = player;
        this.bet = bet;
    }

    public Inventory createGUI() {
        bombsGUI = Bukkit.createInventory(player,54, "Bombs");
        gameRunning = true;
        Bombs.getInstance().getActivePlayers().put(player.getUniqueId(), this);

        ItemCreator cashoutButton = new ItemCreator(Material.DIAMOND_BLOCK);
        cashoutButton.setName("&aClick To Cash Out:");
        cashoutButton.addLore("&f$"+bet);


        bombsGUI.setItem(4, cashoutButton.getItemStack());
        createChests();
        fillGUI();
        fillArray();
        return bombsGUI;
    }

    public void createChests(){
        for(int o = 0; o < 5; o++){
            for(int i = 11; i < 16; i ++){
                ItemCreator chest = new ItemCreator(Material.CHEST);
                chest.setName("&fClick a chest to open.");
                bombsGUI.setItem(i+(o*9), chest.getItemStack());
            }
        }
    }

    public void fillGUI(){
        for(int i = 0; i < bombsGUI.getContents().length; i++) {
            ItemStack item = bombsGUI.getItem(i);
            if(item == null){
                ItemCreator emptySpace = new ItemCreator(Material.LEGACY_STAINED_GLASS_PANE, (byte) (new Random().nextInt(16)));
                emptySpace.setName(" ");
                bombsGUI.setItem(i, emptySpace.getItemStack());
            }
        }
    }

    public void fillArray() {
        ArrayList<Integer> bombsLocation = new ArrayList<>();
        HashMap<Integer, Boolean> hashMap = new HashMap<>();

        while (bombsLocation.size() < 5){
            int lol = new Random().nextInt(25);
            hashMap.putIfAbsent(lol, true);
            bombsLocation.add(lol);
        }

        Arrays.fill(bombsArray, 0);

        for (int i = 0; i < 5; i++) {
            bombsArray[bombsLocation.get(i)] = 1;
        }
    }

    public void clickedChest(int slot){
        for(int i = 0; i < bombsArray.length; i++){
            if(valuesArray[i] == slot){
                if(bombsArray[i] == 0){
                    foundDiamond(slot);
                } else if(bombsArray[i] == 1){
                    foundBomb(slot);
                }
            }
        }
    }

    public void foundDiamond(int slot){
        diamondCounter++;
        ItemCreator diamond = new ItemCreator(Material.DIAMOND);
        diamond.setName("&bDiamond");
        diamond.addLore("&fx" + payoutArray[diamondCounter-1]);
        bombsGUI.setItem(slot, diamond.getItemStack());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        ItemCreator cashoutButton = new ItemCreator(Material.DIAMOND_BLOCK);
        cashoutButton.setName("&aClick To Cash Out:");
        cashoutButton.addLore("&f$"+getCashout());
        bombsGUI.setItem(4, cashoutButton.getItemStack());
    }

    public void foundBomb(int slot){
        ItemCreator bomb = new ItemCreator(Material.TNT);
        bomb.setName("&4Bomb!");
        bombsGUI.setItem(slot, bomb.getItemStack());
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        gameRunning = false;
        reveal();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bombs.getInstance(), this::closeGUI, 10L);
    }

    public void reveal(){
        for(int i = 0; i < bombsArray.length; i++){
            if(bombsArray[i] == 0){
                ItemCreator diamond = new ItemCreator(Material.DIAMOND);
                diamond.setName("&bDiamond");
                bombsGUI.setItem(valuesArray[i], diamond.getItemStack());
            }else if(bombsArray[i] == 1){
                ItemCreator bomb = new ItemCreator(Material.TNT);
                bomb.setName("&4Bomb!");
                bombsGUI.setItem(valuesArray[i], bomb.getItemStack());
            }
        }
    }

    public void closeGUI(){
        player.closeInventory();
    }

    public int getBet(){
        return bet;
    }

    public boolean getGameRunning(){
        return gameRunning;
    }

    public int getCashout(){
        return (int) Math.round(bet*payoutArray[diamondCounter-1]);
    }

    public void resetDiamondCounter(){
        diamondCounter = 0;
    }
}
