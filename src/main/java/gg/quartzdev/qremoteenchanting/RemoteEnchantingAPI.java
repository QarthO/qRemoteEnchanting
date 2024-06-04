package gg.quartzdev.qremoteenchanting;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.metrics.bukkit.Metrics;
import gg.quartzdev.qremoteenchanting.commands.CMD;
import gg.quartzdev.qremoteenchanting.commands.CMDreload;
import gg.quartzdev.qremoteenchanting.commands.CMDset;
import gg.quartzdev.qremoteenchanting.listeners.RightClickListener;
import gg.quartzdev.qremoteenchanting.listeners.UpdateCheckerListener;
import gg.quartzdev.qremoteenchanting.storage.ConfigPath;
import gg.quartzdev.qremoteenchanting.storage.YMLconfig;
import gg.quartzdev.qremoteenchanting.util.Messages;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class RemoteEnchantingAPI implements QPluginAPI {
    private final String CONSOLE_PREFIX = "<white>[<red>q<aqua>RemoteEnchanting<white>]";
    private final String CHAT_PREFIX = "<aqua>RemoteEnchanting <bold><gray>>></bold>";
    private final String MODRINTH_SLUG = "qremoteenchanting";
    private final String MODRINTH_LOADER = "paper";

    private static RemoteEnchantingAPI apiInstance;
    private static QRemoteEnchanting pluginInstance;
    private static Messages messages;
    private static QCommandMap commandMap;
    private static gg.quartzdev.metrics.bukkit.Metrics metrics;
    private static YMLconfig config;

    private static EnchanterManager enchanterManager;

    public static QRemoteEnchanting getPlugin(){
        return pluginInstance;
    }

    public static YMLconfig getConfig(){
        return config;
    }

    private RemoteEnchantingAPI(){
    }

    private RemoteEnchantingAPI(QRemoteEnchanting plugin, int bStatsPluginId){

//        Used to get plugin instance in other classes
        pluginInstance = plugin;

//        Initializes custom logger
        QLogger.init(pluginInstance.getComponentLogger());

//        Loads custom messages defined in messages.yml
        setupMessages();

//        Sets up bStats metrics
        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

//        Sets up config.yml
        setupConfig();
        setupEnchanterManager();

//        Initializes bukkit event listeners
        registerListeners();

//        Registers all commands
        registerCommands();
    }

    @SuppressWarnings("SameParameterValue")
    protected static void enable(QRemoteEnchanting plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new RemoteEnchantingAPI(plugin, bStatsPluginId);
    }

    protected static void disable(){

//        Logs plugin is being disabled
        QLogger.info(GenericMessages.PLUGIN_DISABLE);

//        Clears instances
        apiInstance = null;
        pluginInstance = null;
        config = null;
        if(commandMap != null){
            commandMap.unregisterAll();
            commandMap = null;
        }
        if(metrics != null){
            metrics.shutdown();
            metrics = null;
        }

//        Stops async tasks
//        ...
    }

    @SuppressWarnings("UnstableApiUsage")
    public static String getVersion(){
        return pluginInstance.getPluginMeta().getVersion();
    }

    public static String getName(){
        return pluginInstance.getName();
    }

    public void setupMetrics(int pluginId){
        metrics = new Metrics(pluginInstance, pluginId);
    }

    public void registerCommands(){
        commandMap = new QCommandMap();
        new QPerm("qenchant.");
        String label = "qremoteenchanting";
        List<String> aliases = config.get(ConfigPath.ALIASES, List.of("enchanter"));
        QLogger.info("<prefix> Aliases: " + Arrays.toString(aliases.toArray()));
        commandMap.create(label, new CMD("", QPerm.GROUP_PLAYER), aliases);
        commandMap.addSubCommand(label, new CMDreload("reload", QPerm.GROUP_ADMIN));
        commandMap.addSubCommand(label, new CMDset("set", QPerm.GROUP_ADMIN));
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new RightClickListener(), pluginInstance);
        if(config.get(ConfigPath.CHECK_UPDATES, true)){
            setupUpdater(MODRINTH_SLUG, MODRINTH_LOADER);
        }
    }

    public void setupEnchanterManager(){
        enchanterManager = new EnchanterManager();
    }
    public static EnchanterManager getEnchanterManager(){
        return enchanterManager;
    }

    public void setupConfig(){
        config = new YMLconfig(pluginInstance, "config.yml");
    }
    public void setupMessages(){
        messages = new Messages(CONSOLE_PREFIX, CHAT_PREFIX);
    }
    public static void loadCustomMessages(){
        messages.reload();
    }

    public void setupUpdater(String slug, String loader){
        Bukkit.getPluginManager().registerEvents(new UpdateCheckerListener(slug, loader), pluginInstance);
    }

}
