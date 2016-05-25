package com.android.otosettings.systemdetail;

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
public class CloudServiceFragment extends Fragment {
    //the list of user resource sync
    private ListView ls_user_inform;
    private ArrayList<ApplicationInfo> userInfos = new ArrayList<ApplicationInfo>();
    //the list of application resource sync
    private ListView ls_app_inform;
    private ArrayList<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setInfo();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_cloud_service,container,false);
        ls_user_inform = (ListView) fragment_1_view.findViewById(R.id.ls_user_info);
        ls_app_inform = (ListView) fragment_1_view.findViewById(R.id.ls_app_info);
        ls_user_inform.setAdapter(new ApplicationInfoAdapter(userInfos, inflater,"cloud_user"));
        ls_app_inform.setAdapter(new ApplicationInfoAdapter(appInfos, inflater,"cloud_app"));


        return fragment_1_view;
    }

    //show data
    public void setInfo(){
        appInfos.clear();
        userInfos.clear();

        ApplicationInfo information1 = new ApplicationInfo();
        information1.setAppName("contact");
        information1.setAppStatus("on");
        information1.setAppIcon(R.drawable.ic_launcher);
        userInfos.add(information1); //contact info
        ApplicationInfo information2 = new ApplicationInfo();
        information2.setAppName("calendar");
        information2.setAppStatus("on");
        information2.setAppIcon(R.drawable.ic_launcher);
        userInfos.add(information2); //calendar info
        ApplicationInfo information3= new ApplicationInfo();
        information3.setAppName("email");
        information3.setAppStatus("on");
        information3.setAppIcon(R.drawable.ic_launcher);
        userInfos.add(information3); //email info
        ApplicationInfo information4 = new ApplicationInfo();
        information4.setAppName("memo");
        information4.setAppStatus("on");
        information4.setAppIcon(R.drawable.ic_launcher);
        userInfos.add(information4); //memo info
        ApplicationInfo information5 = new ApplicationInfo();
        information5.setAppName("photo");
        information5.setAppStatus("on");
        information5.setAppIcon(R.drawable.ic_launcher);
        userInfos.add(information5); //photo info

        int i=0;
        while(i<20){
            ApplicationInfo information = new ApplicationInfo();
            information.setAppName("application" + i);
            information.setAppStatus("on");
            information.setAppIcon(R.drawable.ic_launcher);

            appInfos.add(information); //application info
            i++;
        }
    }
}
