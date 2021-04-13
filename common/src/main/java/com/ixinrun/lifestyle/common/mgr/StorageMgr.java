package com.ixinrun.lifestyle.common.mgr;

import com.ixinrun.base.utils.SPUtil;

/**
 * 描述: 本地存储管理器
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/13
 */
public class StorageMgr {

    /**
     * 全局存储
     */
    static class Global {
        /**
         * LS_GLOBAL 全局表
         * FIRST_LAUNCH 第一次启动
         * VOICE 语音播报开关
         */
        private static final String LS_GLOBAL = "LS_GLOBAL";
        public static final String FIRST_LAUNCH = "FIRST_LAUNCH";
        public static final String VOICE = "VOICE";

        /**
         * 设置 Global
         *
         * @param key   key，such as “StorageMgr.Global.FIRST_LAUNCH”
         * @param value value
         */
        public static void setGlobal(String key, Object value) {
            SPUtil.applyPut(LS_GLOBAL, key, value);
        }
    }

    /**
     * 用户相关存储
     */
    static class User {
        /**
         * LS_USER 用户表
         * USER_INFO 用户信息
         */
        private static final String LS_USER = "LS_USER";
        public static final String USER_INFO = "USER_INFO";

        /**
         * 设置 User
         *
         * @param key   key，such as “StorageMgr.User.USER_INFO”
         * @param value value
         */
        public static boolean setStoreUser(String key, Object value) {
            SPUtil.applyPut(LS_USER, key, value);
            return true;
        }
    }

}
