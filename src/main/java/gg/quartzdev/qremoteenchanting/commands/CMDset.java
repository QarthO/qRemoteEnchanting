package gg.quartzdev.qremoteenchanting.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.util.Messages;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDset extends QCommand {

    public CMDset(String commandName, String permissionGroup) {
        super("q.enchanter.set", "q.group.admin");
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player player)){
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND.get());
            return false;
        }
        Block block = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);
        if(block == null ||block.getType() != Material.ENCHANTING_TABLE){
            Sender.message(sender, Messages.ERROR_NOT_LOOKING_AT_ENCHANTER.get());
            return false;
        }
        RemoteEnchantingAPI.getEnchanterManager().setDefaultEnchanter(block.getLocation());


        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        return null;
    }
}
