package gg.quartzdev.qtemplateplugin.storage;

public enum ConfigPath {


    CHECK_UPDATES("check-updates");

    private final String path;
    ConfigPath(String path){
        this.path = path;
    }

    public String get(){
        return path;
    }
}
