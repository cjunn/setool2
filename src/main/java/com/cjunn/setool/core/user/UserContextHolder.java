package com.cjunn.setool.core.user;

public class UserContextHolder {
    private static final IUser anonymousUser=new AnonymousUser();
    private static IUserLoader iUserLoader;
    public UserContextHolder(IUserLoader iUserLoader){
        this.iUserLoader=iUserLoader;
    }
    public static IUser getUser(){
        if(iUserLoader==null||iUserLoader.getUser()==null){
            return anonymousUser;
        }
        return iUserLoader.getUser();
    }
    public static <T extends IUser> T getUser(Class<T> clz){
        if(iUserLoader==null){
            return null;
        }
        return iUserLoader.getUser(clz);
    }
}
