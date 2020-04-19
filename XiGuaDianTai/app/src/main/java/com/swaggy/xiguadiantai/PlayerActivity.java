package com.swaggy.xiguadiantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.swaggy.xiguadiantai.adapters.PlayerTrackPageAdapter;
import com.swaggy.xiguadiantai.interfaces.IPlayerCallback;
import com.swaggy.xiguadiantai.presenters.PlayPresenter;
import com.swaggy.xiguadiantai.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements IPlayerCallback, ViewPager.OnPageChangeListener {

    private static final String TAG = "PlayerActivity";
    private ImageView mControlBtn;
    private PlayPresenter mPlayPresenter;

    //时间格式化
    private SimpleDateFormat mMinFormat = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:mm:ss");
    private TextView mTotalDuration;
    private TextView mCurrentPosition;
    private SeekBar mDurationBar;
    private int mCurrentProgress = 0;
    private boolean mIsUserTouchProgress = false;
    private ImageView mPlayNextBtn;
    private ImageView mPlayPreBtn;
    private TextView mTrackTitleTv;
    private String mTrackTitleText;
    private ViewPager mTrackPageView;
    private PlayerTrackPageAdapter mPlayerTrackPageAdapter;
    private boolean mIsUserSlidePager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mPlayPresenter = PlayPresenter.getPlayPresenter();
        //注册回调
        mPlayPresenter.registerViewCallback(this);
        initView();
        //在界面初始化完成之后才去获取数据
        mPlayPresenter.getPlayList();
        initEvent();
        startPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源，取消回调的注册
        if (mPlayPresenter != null) {
            mPlayPresenter.unRegisterViewCallback(this);
            mPlayPresenter = null;
        }
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        if (mPlayPresenter != null) {
            mPlayPresenter.play();
        }
    }

    /**
     * 设置控件的事件
     */
    private void initEvent() {
        //播放与暂停的切换
        mControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后判断是否正在播放，然后根据状态切换
                if (mPlayPresenter.isPlay()) {
                    mPlayPresenter.pause();
                }else {
                    mPlayPresenter.play();
                }
            }
        });

        //拖动进度条，改变播放进度
        mDurationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if (isFromUser) {
                    mCurrentProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgress = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgress = false;
                mPlayPresenter.seekTo(mCurrentProgress);
            }
        });

        /**
         * 播放上一首
         */
        mPlayPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayPresenter.playPre();
            }
        });

        /**
         * 播放下一首
         */
        mPlayNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayPresenter.playNext();
            }
        });

        /**
         * 给歌曲的图片设置滑动监听，实现滑动切歌的效果
         */
        mTrackPageView.addOnPageChangeListener(this);

        mTrackPageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        mIsUserSlidePager = true;
                        break;
                }
                return false;
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mControlBtn = findViewById(R.id.play_or_pause_btn);
        mTotalDuration = findViewById(R.id.track_duration);
        mCurrentPosition = findViewById(R.id.current_position);
        mDurationBar = findViewById(R.id.track_seek_bar);
        mPlayNextBtn = findViewById(R.id.play_next);
        mPlayPreBtn = findViewById(R.id.play_pre);
        mTrackTitleTv = findViewById(R.id.track_title);
        if (!TextUtils.isEmpty(mTrackTitleText)) {
            mTrackTitleTv.setText(mTrackTitleText);
        }
        mTrackPageView = this.findViewById(R.id.track_pager_view);
        //创建适配器
        mPlayerTrackPageAdapter = new PlayerTrackPageAdapter();
        //设置适配器
        mTrackPageView.setAdapter(mPlayerTrackPageAdapter);
    }

    /**
     * 开始播放，修改UI
     */
    @Override
    public void onPlayStart() {
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.mipmap.stop_press);
        }
    }

    @Override
    public void onPlayPause() {
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.mipmap.play_press);
        }
    }

    @Override
    public void onPlayStop() {
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.mipmap.play_press);
        }
    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void nextPlay(Track track) {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> list) {
        //LogUtil.d(TAG,"list---->"+list.size());
        //把数据设置到适配器里
        if (mPlayerTrackPageAdapter != null) {
            mPlayerTrackPageAdapter.setData(list);
        }
    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

    }

    /**
     * 更新播放进度
     * @param currentDuration
     * @param total
     */
    @Override
    public void onProgressChange(int currentDuration, int total) {
        mDurationBar.setMax(total);
        String totalDuration;
        String currentPosition;
        if (total > 1000 * 60 * 60) {
            totalDuration = mHourFormat.format(total);
            currentPosition = mHourFormat.format(currentDuration);
        }else {
            totalDuration = mMinFormat.format(total);
            currentPosition = mMinFormat.format(currentDuration);
        }

        if (mTotalDuration != null) {
            mTotalDuration.setText(totalDuration);
        }
        if (mCurrentPosition != null) {
            mCurrentPosition.setText(currentPosition);
        }
        //更新进度
        if (!mIsUserTouchProgress) {
            mDurationBar.setProgress(currentDuration);
        }

    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdLoaded() {

    }

    @Override
    public void onTrackUpdate(Track track,int playIndex) {
        this.mTrackTitleText = track.getTrackTitle();
        if (mTrackTitleTv != null) {
            mTrackTitleTv.setText(mTrackTitleText);
        }
        //当点击按钮切歌的时候，也要切换对应的图片
        if (mTrackPageView != null) {
            mTrackPageView.setCurrentItem(playIndex,true);
        }
    }
//====================专辑图片滑动切歌要实现的方法开始
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 当页面被选中的时候，就播放当前的歌曲
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        LogUtil.d(TAG,"position---->"+position);
        if (mPlayPresenter != null && mIsUserSlidePager) {
            mPlayPresenter.playByIndex(position);
        }
        mIsUserSlidePager = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
