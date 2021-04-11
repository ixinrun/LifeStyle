package com.ixinrun.lifestyle.common.widget;


import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 事件总线
 * 基于LiveData，通过SingleLiveData对MutableLiveData的包装处理，解决粘性事件
 * </p>
 *
 * @author ixinrun
 * @date 2021/3/31
 */
public final class LiveDataBus {
    private final Map<String, SingleLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public <T> SingleLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new SingleLiveData<>());
        }
        return (SingleLiveData<T>) bus.get(key);
    }

    public SingleLiveData<Object> with(String key) {
        return with(key, Object.class);
    }
}
