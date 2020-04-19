package com.swaggy.xiguadiantai.interfaces;

public interface IAlbumDetailPresenter {
    /**
     * 下拉刷新
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     */
    void getAlbumDetail(int albumId,int page);

    /**
     * 注册UI通知接口
     * @param detailViewCallback
     */
    void registerViewCallback(IAlbumDetailViewCallback detailViewCallback);

    /**
     * 取消注册UI通知接口
     * @param detailViewCallback
     */
    void unregisterViewCallback(IAlbumDetailViewCallback detailViewCallback);
}
