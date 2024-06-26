package gg.quartzdev.qremoteenchanting.util;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qremoteenchanting.RemoteEnchantingAPI;
import gg.quartzdev.qremoteenchanting.storage.YMLmessages;
import org.jetbrains.annotations.Nullable;

public class Messages extends GenericMessages {

    private static Messages INSTANCE;
    YMLmessages messagesFile;

    //    WITHDRAW CLAIMBLOCKS
    public static QMessage ERROR_NOT_LOOKING_AT_ENCHANTER = new QMessage(
            "<prefix> <red>Error: You must be looking at an enchanter");
    public static QMessage ENCHANTER_OPEN = new QMessage(
            "<prefix> <green>Opening...");
    public static QMessage ENCHANTER_SET_DEFAULT = new QMessage(
            "<prefix> <green>Default location has been set");
    public static QMessage ENCHANTER_SET_PERSONAL = new QMessage(
            "<prefix> <green><player> enchanter location has been set");
    public static QMessage SYNTAX_RELOAD = new QMessage(
            "<prefix> <red>Syntax: /<label> reload <file>");
    public static QMessage SYNTAX_SET = new QMessage(
            "<prefix> <red>Syntax: /<label> set");
    public static QMessage ERROR_UNKNOWN_FILE_RELOAD = new QMessage(
            "<prefix> <red>Unknown file to reload: <yellow><file>");

    public Messages(String consolePrefix, String chatPrefix){
        super(consolePrefix, chatPrefix);
        messagesFile = new YMLmessages(RemoteEnchantingAPI.getPlugin(), "messages.yml");
    }

    /**
     * Reloads the messages file
     */
    public void reload(){
        messagesFile.reload();
    }

    /**
     * uses reflection to get the {@link QMessage} object from the class
     * @param key the name of the field to get
     * @return the {@link QMessage} or {@link null} if it doesn't exist
     */
    public static @Nullable QMessage getCustomMessage(String key) {
        try {
            return (QMessage) Messages.class.getField(key).get(QMessage.class);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            return null;
        }
    }
}
