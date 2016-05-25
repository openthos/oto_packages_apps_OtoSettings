package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.otosettings.applicationdetial.NotifyManagerFragment;
import com.android.otosettings.applicationdetial.OpenSatrtFragment;
import com.android.otosettings.netdetail.EthernetFragment;
import com.android.otosettings.netdetail.ProxyFragment;
import com.android.otosettings.netdetail.VPNFragment;
import com.android.otosettings.netdetail.WirelessFragment;
import com.android.otosettings.safedetail.FireWallFragment;
import com.android.otosettings.sbdetail.PrinterFragment;
import com.android.otosettings.systemdetail.DisplayFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class NormalFragment extends Fragment {
    private RadioGroup normalGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_normal,container,false);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_inform);
        radioButton.setChecked(true);
        // default
        addRightFragment(new NotifyManagerFragment(),"normal_inform");
        MainActivity.tag_right= "normal_inform";
        normalGroup = (RadioGroup)view.findViewById(R.id.left_normal_group);
        normalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_inform:
                        addRightFragment(new NotifyManagerFragment(),"normal_inform");
                        MainActivity.tag_right= "normal_inform";
                        break;
                    case R.id.left_setting_reboot:
                        addRightFragment(new OpenSatrtFragment(),"normal_open_start");
                        MainActivity.tag_right= "normal_open_start";
                        break;
                    case R.id.left_setting_wireless_net:
                        addRightFragment(new WirelessFragment(),"normal_wireless");
                        MainActivity.tag_right= "normal_wireless";
                        break;
                    case R.id.left_setting_printer:
                        addRightFragment(new PrinterFragment(),"normal_printer");
                        MainActivity.tag_right= "normal_printer";
                        break;
                    case R.id.left_setting_display:
                        addRightFragment(new DisplayFragment(),"normal_display");
                        MainActivity.tag_right= "normal_display";
                        break;
                    case R.id.left_setting_firewall_net:
                        addRightFragment(new FireWallFragment(),"normal_fire_wall");
                        MainActivity.tag_right= "normal_fire_wall";
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
