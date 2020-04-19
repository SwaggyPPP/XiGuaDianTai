package com.swaggy.xiguadiantai.presenters;

import com.swaggy.xiguadiantai.interfaces.IAlbumDetailPresenter;
import com.swaggy.xiguadiantai.interfaces.IAlbumDetailViewCallback;
import com.swaggy.xiguadiantai.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {
    private String TAG = "AlbumDetailPresenter";
    private List<IAlbumDetailViewCallback> mCallbacks = new ArrayList<>();
    private Album mTargetAlbum = null;

    private AlbumDetailPresenter(){}

    private static AlbumDetailPresenter sInstance = null;

    public static AlbumDetailPresenter getInstance(){
        if (sInstance == null) {
            synchronized (AlbumDetailPresenter.class){
                if (sInstance==null) {
                    sInstance = new AlbumDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    /**
     * 根据专辑的ID和页面去获取该专辑的详情
     * @param albumId  专辑ID
     * @param page  页码
     */
    @Override
    public void getAlbumDetail(int albumId, int page) {
        Map<String,String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID,albumId+"");
        map.put(DTransferConstants.SORT,"asc");
        map.put(DTransferConstants.PAGE,page+"");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                if (trackList != null) {
                    List<Track> tracks = trackList.getTracks();
                    LogUtil.d(TAG,"tracks size-->"+tracks.size());
                    handleAlbumDetailResult(tracks);
                }
            }
            @Override
            public void onError(int errorCode, String errorMsg) {
                LogUtil.d(TAG,"errorCode-->"+errorCode);
                LogUtil.d(TAG,"errorMsg-->"+errorMsg);
                handleError(errorCode,errorMsg);
            }
        });
    }

    private void handleError(int errorCode, String errorMsg) {
        for (IAlbumDetailViewCallback mCallback : mCallbacks) {
            mCallback.onNetworkError(errorCode,errorMsg);
        }
    }

    private void handleAlbumDetailResult(List<Track> tracks) {
        for (IAlbumDetailViewCallback mCallback : mCallbacks) {
            mCallback.onDetailListLoaded(tracks);
        }
    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)) {
            mCallbacks.add(detailViewCallback);
            if (mTargetAlbum != null) {
                detailViewCallback.onAlbumLoaded(mTargetAlbum);
            }
        }
    }

    @Override
    public void unregisterViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        mCallbacks.remove(detailViewCallback);
    }

    public void setTargetAlbum(Album targetAlbum){
        this.mTargetAlbum = targetAlbum;
    }
}
