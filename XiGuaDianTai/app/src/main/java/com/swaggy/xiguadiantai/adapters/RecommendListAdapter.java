package com.swaggy.xiguadiantai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.swaggy.xiguadiantai.R;
import com.swaggy.xiguadiantai.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐界面的适配器
 */
public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

    private List<Album> mData = new ArrayList<>();
    private String TAG = "RecommendListAdapter";
    private OnRecommendItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //开始设置数据
        holder.itemView.setTag(position);
        //为每个条目设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    int clickPosition = (int) v.getTag();
                    mItemClickListener.onItemClick(clickPosition,mData.get(clickPosition));
                }
                LogUtil.d(TAG,"第"+v.getTag()+"个条目被点击了");
            }
        });
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //绑定数据
    public void setData(List<Album> albumlist) {
        if (mData != null) {
            mData.clear();
            mData.addAll(albumlist);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到各个控件，并设置数据
            //封面
            ImageView albumCoverIv = itemView.findViewById(R.id.album_cover);
            //标题
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            //描述
            TextView albumDescrTv = itemView.findViewById(R.id.album_description_tv);
            //播放量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            //专辑内容
            TextView albumContentCountTv = itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDescrTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount() + "");
            albumContentCountTv.setText(album.getIncludeTrackCount() + "");

            Picasso.with(itemView.getContext()).load(album.getCoverUrlLarge()).into(albumCoverIv);
        }
    }

    public void setOnRecommendItemClickListener(OnRecommendItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface OnRecommendItemClickListener{
        void onItemClick(int position, Album album);
    }

}
