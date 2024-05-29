package gg.quartzdev.qtemplateplugin;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.metrics.bukkit.Metrics;
import gg.quartzdev.qtemplateplugin.commands.CMD;
import gg.quartzdev.qtemplateplugin.commands.CMDreload;
import gg.quartzdev.qtemplateplugin.listeners.ExampleListener;
import gg.quartzdev.qtemplateplugin.listeners.UpdateCheckerListener;
import gg.quartzdev.qtemplateplugin.storage.ConfigPath;
import gg.quartzdev.qtemplateplugin.storage.YMLconfig;
import gg.quartzdev.qtemplateplugin.util.Messages;
import org.bukkit.Bukkit;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class TemplateAPI implements QPluginAPI {
    private final String CONSOLE_PREFIX = "<white>[<red>q<aqua>TemplatePlugin<white>]";
    private final String CHAT_PREFIX = "<red>q<aqua>TemplatePlugin <bold><gray>>></bold>";
    private final String MODRINTH_SLUG = "qtemplate";
    private final String MODRINTH_LOADER = "paper";

    private static TemplateAPI apiInstance;
    private static QTemplatePlugin pluginInstance;
    private static Messages messages;
    private static QCommandMap commandMap;
    private static gg.quartzdev.metrics.bukkit.Metrics metrics;
    private static YMLconfig config;

    public static QTemplatePlugin getPlugin(){
        return pluginInstance;
    }

    public static YMLconfig getConfig(){
        return config;
    }

    private TemplateAPI(){
    }

    private TemplateAPI(QTemplatePlugin plugin, int bStatsPluginId){

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

//        Initializes bukkit event listeners
        registerListeners();

//        Registers all commands
        registerCommands();
    }

    @SuppressWarnings("SameParameterValue")
    protected static void enable(QTemplatePlugin plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new TemplateAPI(plugin, bStatsPluginId);
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
        String label = "qclaimblocks";
        commandMap.create(label, new CMD("", QPerm.GROUP_PLAYER), List.of("claimblocks", "cb"));
        commandMap.addSubCommand(label, new CMDreload("reload", QPerm.GROUP_ADMIN));
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new ExampleListener(), pluginInstance);
        if(config.get(ConfigPath.CHECK_UPDATES, true)){
            setupUpdater(MODRINTH_SLUG, MODRINTH_LOADER);
        }
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
