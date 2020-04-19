package com.swaggy.xiguadiantai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.swaggy.xiguadiantai.R;
import com.swaggy.xiguadiantai.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放器界面图片的适配器
 */
public class PlayerTrackPageAdapter extends PagerAdapter {

    private List<Track> mData = new ArrayList<>();

    private String TAG = "PlayerTrackPageAdapter";

    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_pager, container, false);
        container.addView(itemView);
        //找到图片空间
        ImageView item = itemView.findViewById(R.id.track_pager_item);
        //设置图片
        Track track = mData.get(position);
        String coverUrlLarge = track.getCoverUrlSmall();
        LogUtil.d(TAG,"专辑图片信息："+coverUrlLarge);
        if (item != null) {
            Picasso.with(container.getContext()).load(coverUrlLarge).into(item);
        }
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<Track> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
