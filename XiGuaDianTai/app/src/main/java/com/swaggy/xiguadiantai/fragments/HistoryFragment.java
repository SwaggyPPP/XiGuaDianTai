package com.swaggy.xiguadiantai.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swaggy.xiguadiantai.R;
import com.swaggy.xiguadiantai.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_history, container,false);
        return rootView;
    }
}
