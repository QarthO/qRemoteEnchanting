package gg.quartzdev.qremoteenchanting.util;

import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public class PDC {

    private static final NamespacedKey markKey = new NamespacedKey(RemoteEnchantingAPI.getPlugin(), "mark");
    private static final NamespacedKey remoteKey = new NamespacedKey(RemoteEnchantingAPI.getPlugin(), "remote");

    /**
     * Marks an {@link ItemMeta}. Note: You will need to update the {@link ItemStack} for the mark to work
     * @param itemMeta the {@link ItemMeta} to mark
     */
    public static void mark(ItemMeta itemMeta){
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(markKey, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Marks an item
     * @param itemStack the {@link ItemStack} to mark
     */
    public static void mark(ItemStack itemStack){
        if(!itemStack.hasItemMeta()){
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        mark(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     *  Checks if an {@link ItemMeta} is marked
     * @param itemMeta where to check
     * @return true if the {@link ItemMeta} is marked, false if not
     */
    public static boolean isMarked(ItemMeta itemMeta){
        return itemMeta.getPersistentDataContainer().has(markKey);
    }
    /**
     *  Checks if an item is marked
     * @param item where to check
     * @return true if the {@link Item} is marked, false if not
     */
    public static boolean isMarked(Item item){
        if(!item.getItemStack().hasItemMeta()) return false;
        return isMarked(item.getItemStack().getItemMeta());
    }

    public static @Nullable Location getEnchanterFromRemote(ItemStack itemStack, Material remoteMaterial){
        if(itemStack.getType() != remoteMaterial) return null;
        final PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();
        if(pdc.has(remoteKey)){

        }
        return null;
    }

}
