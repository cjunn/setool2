package com.cjunn.setool.core.user;

/**
 * @ClassName @{NAME}
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:24
 * @Version
 */
public class AnonymousUser implements IUser {
    @Override
    public String getUserId() {
        return "0";
    }

    @Override
    public String getUserCode() {
        return "0";
    }

    @Override
    public String getUserName() {
        return "匿名用戶";
    }

    @Override
    public String getDeptId() {
        return "0";
    }

    @Override
    public String getDeptCode() {
        return "0";
    }

    @Override
    public String getDeptName() {
        return "0";
    }
}
