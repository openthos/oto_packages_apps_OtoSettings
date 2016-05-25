package com.android.otosettings.listviewadapts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.otosettings.R;
import com.android.otosettings.bean.ApplicationInfo;

import java.util.ArrayList;

/**
 * Created by zhu on 2016/5/8.
 */
public class ApplicationInfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private  View[] item_views ;
    /**
     * mark which type adpater
    * "notify_manager" notify manager
     * "open_start" open start manager
     *"cloud_user" user resource sync
     *"cloud_app"  application resource sync
     * */
    private String flag;

    public ApplicationInfoAdapter(ArrayList<ApplicationInfo> mlists, LayoutInflater _inflater, String _flag){
        inflater = _inflater;
        item_views = new View[mlists.size()];
        flag = _flag;

        for (int i=0; i<item_views.length;i++)
        {
            final ApplicationInfo info=mlists.get(i);
            View view = inflater.inflate(R.layout.item_app_info, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.list_item_img);
            TextView nameText = (TextView)view.findViewById(R.id.list_item_name);
            final TextView statusText = (TextView)view.findViewById(R.id.list_item_status);
            ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.list_item_toggle);
            imageView.setImageResource(info.getAppIcon());
            nameText.setText(info.getAppName());
            statusText.setText(info.getAppStatus());
            if (info.getAppStatus().equals("on")){
                toggleButton.setChecked(true);
            }else toggleButton.setChecked(false);
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        statusText.setText("on");
                    }
                    else{
                        statusText.setText("forbidden");
                    }
                }
            });

            item_views[i] = view;
        }
    }

    @Override
    public int getCount() {
        return  item_views.length;
    }

    @Override
    public Object getItem(int position) {
        return item_views[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return item_views[position];

    }
}
