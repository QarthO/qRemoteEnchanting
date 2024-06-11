package gg.quartzdev.qremoteenchanting;

import org.bukkit.plugin.java.JavaPlugin;

public final class QRemoteEnchanting extends JavaPlugin {

    @Override
    public void onEnable() {

//        Enables the plugin
        RemoteEnchantingAPI.enable(this, -1);
    }

    @Override
    public void onDisable() {

//        Disables the plugin
        RemoteEnchantingAPI.disable();
    }
}