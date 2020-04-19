package com.swaggy.xiguadiantai.presenters;

import com.swaggy.xiguadiantai.interfaces.IRecommendPresenter;
import com.swaggy.xiguadiantai.interfaces.IRecommendViewCallback;
import com.swaggy.xiguadiantai.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Recommend的逻辑层（获取数据）
 */
public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";
    private static int RECOMMEND_COUNT = 50;
    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter(){}

    private static RecommendPresenter sInstance = null;

    /**
     * 获取单例对象
     */
    public static RecommendPresenter getInstance(){
        if (sInstance==null) {
            synchronized (RecommendPresenter.class){
                if (sInstance==null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取推荐内容（猜你喜欢）
     */
    @Override
    public void getRecommendList() {
        updateLoading();
        Map<String,String> map = new HashMap<>();
        //一页要显示多少条的数据
        map.put(DTransferConstants.LIKE_COUNT,RECOMMEND_COUNT+"");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                //数据获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        //LogUtil.d(TAG,"size-->"+albumList.size());
                        //拿到数据后，将数据显示到页面上
                        //upRecommendUI(albumList);
                        handlerRecommendResult(albumList);
                    }
                }
            }
            @Override
            public void onError(int i, String s) {
                //数据获取失败
                LogUtil.d(TAG,"error code-->"+i);
                LogUtil.d(TAG,"error msg-->"+s);
                handlerError();
            }
        });
    }

    private void handlerError() {
        //通知UI更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onNetworkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI更新
        if (albumList != null) {
            if (albumList.size()==0) {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onEmpty();
                }
            }else {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }
    }

    private void updateLoading(){
        for (IRecommendViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null && !mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(mCallbacks);
        }
    }
}
