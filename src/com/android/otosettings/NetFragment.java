package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.otosettings.applicationdetial.NotifyManagerFragment;
import com.android.otosettings.netdetail.EthernetFragment;
import com.android.otosettings.netdetail.ProxyFragment;
import com.android.otosettings.netdetail.VPNFragment;
import com.android.otosettings.netdetail.WirelessFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class NetFragment extends Fragment {
    private RadioGroup netGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_net,container,false);
        netGroup = (RadioGroup)view.findViewById(R.id.left_net_group);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_wireless);
        radioButton.setChecked(true);
        addRightFragment(new WirelessFragment(), "net_wireless");
        MainActivity.tag_right = "net_wireless";

        netGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_wireless:
                        addRightFragment(new WirelessFragment(), "net_wireless");
                        MainActivity.tag_right = "net_wireless";
                        break;
                    case R.id.left_setting_ethernet:
                        addRightFragment(new EthernetFragment(), "net_ethernet");
                        MainActivity.tag_right = "net_ethernet";
                        break;
                    case R.id.left_setting_vpn:
                        addRightFragment(new VPNFragment(), "net_vpn");
                        MainActivity.tag_right = "net_vpn";
                        break;
                    case R.id.left_setting_proxy:
                        addRightFragment(new ProxyFragment(), "net_proxy");
                        MainActivity.tag_right = "net_proxy";
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
