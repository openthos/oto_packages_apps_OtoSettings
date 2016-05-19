package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.otosettings.applicationdetial.ApplicationManagerFragment;
import com.android.otosettings.applicationdetial.NotifyManagerFragment;
import com.android.otosettings.applicationdetial.OpenSatrtFragment;
import com.android.otosettings.applicationdetial.RunningModeFragment;
import com.android.otosettings.netdetail.EthernetFragment;
import com.android.otosettings.netdetail.ProxyFragment;
import com.android.otosettings.netdetail.VPNFragment;
import com.android.otosettings.netdetail.WirelessFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class ProgressFragment extends Fragment {
    private RadioGroup appGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_progress,container,false);

        appGroup = (RadioGroup)view.findViewById(R.id.left_app_group);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_reboot_p);
        radioButton.setChecked(true);
        addRightFragment(new OpenSatrtFragment(), "app_open_start");
        MainActivity.tag_right = "app_open_start";

        appGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_reboot_p:
                        addRightFragment(new OpenSatrtFragment(), "app_open_start");
                        MainActivity.tag_right = "app_open_start";
                        break;
                    case R.id.left_setting_inform_sb:
                        addRightFragment(new NotifyManagerFragment(), "app_notify");
                        MainActivity.tag_right = "app_notify";
                        break;
                    case R.id.left_setting_progress_manager:
                        addRightFragment(new ApplicationManagerFragment(), "app_progress");
                        MainActivity.tag_right = "app_progress";
                        break;
                    case R.id.left_setting_run_mode:
                        addRightFragment(new RunningModeFragment(), "app_run_mode");
                        MainActivity.tag_right = "app_run_mode";
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    /**
     * left add Fragment
     *
     * @param fragment
     *@param _tag
     */
    private void addRightFragment(Fragment fragment, String _tag) {
        if (fragment.isAdded()) {
        } else {
            if (MainActivity.tag_right!=null)  removeFragment(MainActivity.tag_right);
            android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.right_frame, fragment,_tag);
            transaction.commit();
            MainActivity.tag_right = _tag;
        }
    }
    /**
     * delete Fragment
     *
     * @param _tag
     */
    private void removeFragment(String _tag) {
        android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(_tag);
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
}
