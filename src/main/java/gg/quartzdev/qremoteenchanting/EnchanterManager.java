package gg.quartzdev.qremoteenchanting;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qremoteenchanting.storage.ConfigPath;
import gg.quartzdev.qremoteenchanting.storage.YMLenchanters;
import org.bukkit.Location;

public class EnchanterManager {

    YMLenchanters enchanters;

    public EnchanterManager(){
        enchanters = new YMLenchanters(RemoteEnchantingAPI.getPlugin(),"enchanters.yml", false);
    }

    public void setDefaultEnchanter(Location location){
        enchanters.set(ConfigPath.DEFAULT_ENCHANTER, location);
        QLogger.info("<prefix> Setting default enchanter");
    }

    public Location getDefaultEnchanterLocation(){
        return enchanters.get(ConfigPath.DEFAULT_ENCHANTER, null);
    }

    public void reload(){
        enchanters.reload();
    }
}
