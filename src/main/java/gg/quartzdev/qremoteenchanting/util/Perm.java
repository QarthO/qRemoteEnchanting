package gg.quartzdev.qremoteenchanting.util;

public enum Perm {

    GROUP_PLAYER("q.group.player"),
    GROUP_MOD("q.group.mod"),
    GROUP_ADMIN("q.group.admin"),

//    open an enchanter via command
    ENCHANTER_USE("q.enchanter.use"),
    ENCHANTER_USE_OTHERS("q.enchanter.use.others"),
    ENCHANTER_USE_DEFAULT("q.enchanter.use.default"),

//    open an enchanter via a remote item
    ENCHANTER_REMOTE_USE("q.enchanter.remote.use"),

//    set an enchanter
    ENCHANTER_SET("q.enchanter.set"),
    ENCHANTER_SET_DEFAULT("q.enchanter.set.default"),
    ENCHANTER_SET_OTHERS("q.enchanter.set.others"),

//    link an enchanter to a remote item
    ENCHANTER_LINK("q.enchanter.link"),
    ENCHANTER_UNLINK("q.enchanter.unlink"),

//    link an enchanter to a remote item bypassing the item type requirement
    ENCHANTER_LINK_BYPASS("q.enchanter.link.itembypass"),

//    delete an enchanter (ones that are accessed via command)
    ENCHANTER_DELETE("q.enchanter.delete"),
    ENCHANTER_DELETE_DEFAULT("q.enchanter.delete.default"),
    ENCHANTER_DELETE_OTHERS("q.enchanter.delete.others"),

//    reload config/data files
    ENCHANTER_RELOAD("q.enchanter.reload");

    private final String PERMISSION_NODE;

    Perm(String permissionNode){
        this.PERMISSION_NODE = permissionNode;
    }

    public String get(){
        return PERMISSION_NODE;
    }

    @Override
    public String toString(){
        return get();
    }
}
