package com.swaggy.xiguadiantai.interfaces;

/**
 * 一个接口，主要用于控制RecommendFragment的逻辑
 *    界面主动发起的动作
 */
public interface IRecommendPresenter {

    /**
     * 获取推荐内容（绑定要显示的数据）
     */
    void getRecommendList();

    /**
     * 下拉刷新
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 注册UI的回调实现类
     */
    void registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消UI的回调注册
     */
    void unRegisterViewCallback(IRecommendViewCallback callback);
}
