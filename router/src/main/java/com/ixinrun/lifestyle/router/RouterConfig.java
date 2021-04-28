package com.ixinrun.lifestyle.router;

/**
 * 描述: 路由配置
 * </p>
 *
 * @author xinrun
 * @date 2021/4/7
 */
public class RouterConfig {

    /**
     * module_main
     */
    public static class ModuleMain {
        private static final String name = "/module_main";
        public static final String MainActivity = name + "/MainActivity";
    }

    /**
     * module_step
     */
    public static class ModuleRun {
        private static final String name = "/module_step";
        public static final String MainRunFrag = name + "/MainStepFrag";
    }

    /**
     * module_eat
     */
    public static class ModuleEat {
        private static final String name = "/module_eat";
        public static final String MainEatFrag = name + "/MainEatFrag";
    }

    /**
     * module_more
     */
    public static class ModuleMore {
        private static final String name = "/module_more";
        public static final String MainMoreFrag = name + "/MainMoreFrag";
    }

    /**
     * module_user
     */
    public static class ModuleUser {
        private static final String name = "/module_user";
        public static final String MainUserFrag = name + "/MainUserFrag";
    }

}
