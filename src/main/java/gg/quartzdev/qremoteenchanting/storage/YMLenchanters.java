package gg.quartzdev.qremoteenchanting.storage;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.storage.ConfigOption;
import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class YMLenchanters extends QConfiguration {

    public YMLenchanters(JavaPlugin plugin, String fileName, boolean useSchema) {
        super(plugin, fileName, useSchema);
        loadAllData();
        initializeAll();
    }

    @Override
    public void loadAllData() {

    }

    @Override
    public void saveAllData() {

    }

    public void initializeAll(){
        setup(ConfigPath.DEFAULT_ENCHANTER, new Location(null, 0, 0, 0), () -> yamlConfiguration.getLocation(ConfigPath.DEFAULT_ENCHANTER.get()));
    }

    public <T> void setup(ConfigPath path, T defaultValue, Supplier<T> loader){
        configOptions.put(path.get(), new ConfigOption<>(path.get(), yamlConfiguration, loader));
    }

    public <T> T get(ConfigPath key, T defaultValue){
            ConfigOption<T> option = getConfigOption(key, defaultValue);
            return option != null ? option.get() : defaultValue;
    }

    public <T> void set(ConfigPath key, T value){
        ConfigOption<T> option = getConfigOption(key, value);
        if(option == null){
            QLogger.error("Could not set config option " + key.get() + " to " + value);
            return;
        }
        option.setValue(value);
        option.save();
        save();
    }

    @SuppressWarnings("unchecked")
    private @Nullable <T> ConfigOption<T> getConfigOption(ConfigPath key, T value){
        try{
            return (ConfigOption<T>) configOptions.get(key.get());
        } catch (ClassCastException ignored){
            return null;
        }
    }

    public void setDefaultLocation(Location location){
        set(ConfigPath.DEFAULT_ENCHANTER, location);
    }

    public Location getDefaultLocation(){
        return get(ConfigPath.DEFAULT_ENCHANTER, null);
    }
}
