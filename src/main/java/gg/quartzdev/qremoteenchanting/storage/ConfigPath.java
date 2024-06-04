package gg.quartzdev.qremoteenchanting.storage;

public enum ConfigPath {


//    Config
    CHECK_UPDATES("check-updates"),
    REMOTE_MATERIAL("remote.material"),
    ALIASES("aliases"),

//    Enchanters
    DEFAULT_ENCHANTER("default"),
    ENCHANTERS("enchanters");

    private final String path;
    ConfigPath(String path){
        this.path = path;
    }

    public String get(){
        return path;
    }
}
