package com.android.otosettings.applicationdetial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.otosettings.R;
import com.android.otosettings.bean.ApplicationInfo;
import com.android.otosettings.listviewadapts.ApplicationInfoAdapter;

import java.util.ArrayList;

/**
 * Created by zhu on 2016/5/6.
 */
public class OpenSatrtFragment extends Fragment {
    private ListView ls_open_inform;
    private ArrayList<ApplicationInfo> settingAppInfos = new ArrayList<ApplicationInfo>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setInfo();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_open_start_manager,container,false);
        ls_open_inform = (ListView) fragment_1_view.findViewById(R.id.ls_open_start_application);
        ls_open_inform.setAdapter(new ApplicationInfoAdapter(settingAppInfos, inflater,"open_start"));


        return fragment_1_view;
    }

    //show data
    public void setInfo(){
        settingAppInfos.clear();
        int i=0;
        while(i<10){
            ApplicationInfo information = new ApplicationInfo();
            information.setAppName("application" + i);
            information.setAppStatus("on");
            information.setAppIcon(R.drawable.ic_launcher);

            settingAppInfos.add(information); 
            i++;
        }
    }
}
