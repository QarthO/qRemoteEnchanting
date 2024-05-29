package gg.quartzdev.qtemplateplugin.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.qtemplateplugin.TemplateAPI;
import gg.quartzdev.qtemplateplugin.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CMDreload extends QCommand {
    List<String> reloadableFiles = List.of("config", "messages", "transactions");
    public CMDreload(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        Sender.message(sender, "<blue>Args[<yellow>" + args.length + "<blue>] <gray>- <red>" + Arrays.toString(args));
//        reload all configs
        reloadMessages(sender);
        return true;
//        if (args.length == 1) {
//            reloadConfig(sender);
//            reloadMessages(sender);
//            reloadTransactions(sender);
//            return true;
//        }
//        return switch (args[1]) {
//            case "config" -> {
//                reloadConfig(sender);
//                yield true;
//            }
//            case "messages" -> {
//                reloadMessages(sender);
//                yield true;
//            }
//            case "transactions" -> {
//                reloadTransactions(sender);
//                yield true;
//            }
//            default -> {
//                Sender.message(sender, "<red>Unknown file to reload: <yellow>" + args[1] + "</red>");
//                yield false;
//            }
//        };
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return reloadableFiles;
        }
        return null;
    }

    public void reloadConfig(CommandSender sender){
        TemplateAPI.getConfig().reload();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "config"));
    }
    public void reloadMessages(CommandSender sender){
        TemplateAPI.loadCustomMessages();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "messages"));
    }
}
