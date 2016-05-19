package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.otosettings.netdetail.EthernetFragment;
import com.android.otosettings.netdetail.ProxyFragment;
import com.android.otosettings.netdetail.VPNFragment;
import com.android.otosettings.netdetail.WirelessFragment;
import com.android.otosettings.systemdetail.AboutThisComputerFragment;
import com.android.otosettings.systemdetail.CloudServiceFragment;
import com.android.otosettings.systemdetail.DateAndTimeFragment;
import com.android.otosettings.systemdetail.DisplayFragment;
import com.android.otosettings.systemdetail.EnergeSaveFragment;
import com.android.otosettings.systemdetail.LanguageAndInputFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class SystemFragment extends Fragment {
    private RadioGroup systemGroup = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_system, container, false);

        systemGroup = (RadioGroup)view.findViewById(R.id.left_system_group);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_display_sys);
        radioButton.setChecked(true);
        addRightFragment(new DisplayFragment(), "sys_display");
        MainActivity.tag_right = "sys_display";

        systemGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_display_sys:
                        addRightFragment(new DisplayFragment(), "sys_display");
                        MainActivity.tag_right = "sys_display";
                        break;
                    case R.id.left_setting_nsave_energe:
                        addRightFragment(new EnergeSaveFragment(), "sys_save");
                        MainActivity.tag_right = "sys_save";
                        break;
                    case R.id.left_setting_date_time:
                        addRightFragment(new DateAndTimeFragment(), "sys_date");
                        MainActivity.tag_right = "sys_date";
                        break;
                    case R.id.left_setting_input_language:
                        addRightFragment(new LanguageAndInputFragment(), "sys_input");
                        MainActivity.tag_right = "sys_input";
                        break;
                    case R.id.left_setting_cloud_service:
                        addRightFragment(new CloudServiceFragment(), "sys_cloud");
                        MainActivity.tag_right = "sys_cloud";
                        break;
                    case R.id.left_setting_about:
                        addRightFragment(new AboutThisComputerFragment(), "sys_about");
                        MainActivity.tag_right = "sys_about";
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
