package gg.quartzdev.qremoteenchanting.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CMDreload extends QCommand {
    List<String> reloadableFiles = List.of("config", "messages");
    public CMDreload(String commandName, String permissionGroup) {
        super("q.enchanter.reload", "q.group.admin");
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        switch (args.length) {
            case 1:
                reloadConfig(sender);
                reloadMessages(sender);
                return true;
            case 2:
                switch(args[1]){
                    case "config":
                        reloadConfig(sender);
                        return true;
                    case "messages":
                        reloadMessages(sender);
                        return true;
                    default:
                        Sender.message(sender, "<red>Unknown file to reload: <yellow>" + args[1] + "</red>");
                        return false;
                }
            default:
                Sender.message(sender, Messages.SYNTAX_RELOAD
                        .parse("label", label)
                        .get());
                return false;
        }
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return reloadableFiles;
        }
        return null;
    }

    public void reloadConfig(CommandSender sender){
        RemoteEnchantingAPI.getConfig().reload();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "config"));
    }
    public void reloadMessages(CommandSender sender){
        RemoteEnchantingAPI.loadCustomMessages();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "messages"));
    }
}
