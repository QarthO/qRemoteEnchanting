package gg.quartzdev.qremoteenchanting.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.util.Messages;
import gg.quartzdev.qremoteenchanting.util.Perm;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.rmi.Remote;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CMDset extends QCommand {

    public CMDset() {
        super(Perm.ENCHANTER_SET.get(), Perm.GROUP_ADMIN.get());
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {

//        Player only command
        if(!(sender instanceof Player player)){
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND.get());
            return false;
        }

//        Get enchanter block (only checks 5 blocks - ignores fluids)
        Block enchanter = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);
        if(enchanter == null ||enchanter.getType() != Material.ENCHANTING_TABLE){
            Sender.message(sender, Messages.ERROR_NOT_LOOKING_AT_ENCHANTER.get());
            return false;
        }

        if(args.length > 3){
            sendSyntax(sender, label);
            return false;
        }

//        /<label> set      <player>
//        /<args>  args[0]  args[1]
        return switch (args.length) {
            case 1 -> {
                setEnchanterLocation(player, enchanter.getLocation());
                yield true;
            }
            case 2 -> {
                switch (args[1].toLowerCase()){
                    case "default" -> {
                        RemoteEnchantingAPI.enchanters().setDefaultLocation(enchanter.getLocation());
                        Sender.message(sender, Messages.ENCHANTER_SET_DEFAULT.get());
                        yield true;
                    }
                    case "player" -> {
                        Sender.message(sender, "<prefix> <red> no worky yet");
                        yield true;
                    }
                    default -> {
                        sendSyntax(sender, label);
                        yield false;
                    }
                }
            }
            case 3 -> {
                if(!args[1].equalsIgnoreCase("player") || !sender.hasPermission(Perm.ENCHANTER_SET_OTHERS.get())){
                    sendSyntax(sender, label);
                    yield false;
                }
                Sender.message(sender, "<prefix> <red> no worky yet");
                yield true;
            }
            default -> {
                Sender.message(sender, "");
                yield false;
            }
        };
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        return switch (args.length) {
            case 2 -> {
                final Collection<String> completions = new HashSet<>();
                if(sender.hasPermission(Perm.ENCHANTER_SET_OTHERS.get()))
                    completions.add("player");
                if(sender.hasPermission(Perm.ENCHANTER_SET_DEFAULT.get()))
                    completions.add("default");
                yield completions;
            }
            case 3 -> {
                if(args[1].equalsIgnoreCase("player") && sender.hasPermission(Perm.ENCHANTER_SET_DEFAULT.get()))
                    yield Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
//                maybe add default per permission?
                yield null;
            }
            default -> {
                yield null;
            }
        };
    }

    public void setEnchanterLocation(OfflinePlayer player, Location location){
        RemoteEnchantingAPI.enchanters().setLocation(player.getUniqueId(), location);
    }

    private void sendSyntax(CommandSender sender, String label){
        String syntax = Messages.SYNTAX_SET.get().replace("<label>", label);
        final boolean canOthers = sender.hasPermission(Perm.ENCHANTER_SET_OTHERS.get());
        final boolean canDefault = sender.hasPermission(Perm.ENCHANTER_SET_DEFAULT.get());
        if(canDefault && canOthers){
            syntax += " [default|player]";
        } else if(canDefault){
            syntax += " [default]";
        } else if(canOthers){
            syntax += " [player]";
        }
        Sender.message(sender, syntax);
    }
}
