package gg.quartzdev.qtemplateplugin;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class QTemplatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {


//        Lets a class be serializable for easing saving of an object
//        ConfigurationSerialization.registerClass(PUT_CLASS_HERE.class);

//        Enables the plugin
        TemplateAPI.enable(this, -1);

    }

    @Override
    public void onDisable() {
        TemplateAPI.disable();
    }
}