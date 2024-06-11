package gg.quartzdev.qremoteenchanting.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.util.Messages;
import gg.quartzdev.qremoteenchanting.util.Perm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD extends QCommand {
    public CMD() {
        super(Perm.ENCHANTER_USE.get(), Perm.GROUP_PLAYER.get());
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player player)){
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND);
            return false;
        }
        Sender.message(player, Messages.ENCHANTER_OPEN);
        player.openEnchanting(RemoteEnchantingAPI.enchanters().getDefaultLocation(), true);
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
