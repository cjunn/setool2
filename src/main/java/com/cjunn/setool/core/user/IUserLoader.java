package com.cjunn.setool.core.user;

public interface IUserLoader {
    IUser getUser();
    <T extends IUser> T getUser(Class<T> clz);
}
