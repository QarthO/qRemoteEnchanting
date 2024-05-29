package gg.quartzdev.qtemplateplugin.listeners;

import gg.quartzdev.lib.qlibpaper.UpdateChecker;
import gg.quartzdev.qtemplateplugin.TemplateAPI;
import gg.quartzdev.qtemplateplugin.storage.ConfigPath;
import gg.quartzdev.qtemplateplugin.storage.YMLconfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class UpdateCheckerListener implements Listener {

    UpdateChecker updateChecker;
    YMLconfig config;
    public UpdateCheckerListener(String slug, String loader){
        updateChecker = new UpdateChecker(slug, loader);
        config = TemplateAPI.getConfig();
        if(config.get(ConfigPath.CHECK_UPDATES, true)) {
            checkForUpdates(null);
        }
    }

    public void checkForUpdates(Player player){
        updateChecker.checkForUpdatesAsync(TemplateAPI.getPlugin(), TemplateAPI.getVersion(), player);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        if(!config.get(ConfigPath.CHECK_UPDATES, true)){
            return;
        }
        if(!event.getPlayer().hasPermission("qclaimblocks.admin.updates")){
            return;
        }
        checkForUpdates(event.getPlayer());
    }
}

