package com.swaggy.xiguadiantai.utils;

import com.swaggy.xiguadiantai.base.BaseFragment;
import com.swaggy.xiguadiantai.fragments.HistoryFragment;
import com.swaggy.xiguadiantai.fragments.RecommendFragment;
import com.swaggy.xiguadiantai.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment创造器
 */
public class FragmentCreator {

    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;
    //页面总数
    public final static int PAGE_COUNT = 3;

    //缓存已经创建的Fragment
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    /**
     * 根据适配器传进来的参数，来创建对应的Fragment
     * @param index  Fragment 对应的位置
     * @return 创建好的Fragment
     */
    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment = sCache.get(index);
        //先判断缓存的集合中有没有想要的Fragment，有就直接用，没有才重新创建
        if (baseFragment != null) {
            return baseFragment;
        }
        switch (index){
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
        }
        sCache.put(index,baseFragment);
        return baseFragment;
    }

}
