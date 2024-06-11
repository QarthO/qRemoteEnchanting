package gg.quartzdev.qremoteenchanting.listeners;

import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.storage.ConfigPath;
import gg.quartzdev.qremoteenchanting.storage.YMLconfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    YMLconfig config;
    Material remoteMaterial;

    public RightClickListener(){
        config = RemoteEnchantingAPI.getConfig();
        remoteMaterial = config.get(ConfigPath.REMOTE_MATERIAL, Material.GHAST_TEAR);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

//        Makes sure the player is right-clicking an item
        if(event.getAction().isLeftClick() || event.getHand() == EquipmentSlot.OFF_HAND || event.getItem() == null) return;

        final ItemStack heldItem = event.getItem();
//        Held item is the remote
        if(heldItem.getType() != config.get(ConfigPath.REMOTE_MATERIAL, Material.GHAST_TEAR)) return;

    }

}
