package com.idwxy.blogdemo.util;

public class Util {
    private static ThreadLocal<Integer> currentUidContainer = new ThreadLocal<>();

    // 用户存放当前用户的 uid，可提供在项目中使用
    public static void setCurrentUid(int uid) {
        currentUidContainer.set(uid);
    }

    // 获取当前登入用户的 uid
    public static int getCurrentUid() {
        return currentUidContainer.get();
    }
}
