package com.swaggy.xiguadiantai.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * 逻辑层回调通知界面更新
 */
public interface IRecommendViewCallback {

    /**
     * 获取推荐内容的结果
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoading();

    /**
     * 加载更多
     */
    void onLoadMore(List<Album> result);

    /**
     * 下拉刷新
     */
    void onRefreshMore(List<Album> result);
}
