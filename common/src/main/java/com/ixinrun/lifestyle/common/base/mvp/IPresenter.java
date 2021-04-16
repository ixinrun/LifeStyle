package com.ixinrun.lifestyle.common.base.mvp;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public interface IPresenter {
    /**
     * 创建时
     */
    void onAttach();

    /**
     * 销毁时
     */
    void onDetach();
}
