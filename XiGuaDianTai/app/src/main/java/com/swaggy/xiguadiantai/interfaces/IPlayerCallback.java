package com.swaggy.xiguadiantai.interfaces;


import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerCallback {

    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPause();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 播放出错
     */
    void onPlayError();

    /**
     * 下一首播放
     */
    void nextPlay(Track track);

    /**
     * 播放上一首
     */
    void onPrePlay(Track track);

    /**
     * 列表加载完成
     * @param list
     */
    void onListLoaded(List<Track> list);

    /**
     * 播放模式改变
     * @param playMode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode playMode);

    /**
     * 进度条发生改变了
     * @param currentProgress
     * @param total
     */
    void onProgressChange(int currentProgress,int total);

    /**
     * 广告正在加载
     */
    void onAdLoading();

    /**
     * 广告加载完成了
     */
    void onAdLoaded();

    /**
     * 更新当前节目
     */
    void onTrackUpdate(Track track,int playIndex);


}
