package com.feidao.utils;

public class UserContext {
    private static final ThreadLocal<Integer> USER_CONTEXT = new ThreadLocal<>();
    public static void setCurrentUserId(Integer userId) {
        USER_CONTEXT.set(userId);
    }
    public static Integer getCurrentUserId() {
        return USER_CONTEXT.get();
    }
    public static void removeCurrentUserId() {
        USER_CONTEXT.remove();
    }
}
