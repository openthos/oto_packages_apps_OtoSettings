package com.android.otosettings.safedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class FireWallFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_fire_wall,container,false);



        return fragment_1_view;
    }
}