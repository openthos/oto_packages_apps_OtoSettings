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
import com.android.otosettings.safedetail.AccountManagerFragment;
import com.android.otosettings.safedetail.FireWallFragment;
import com.android.otosettings.safedetail.SeparaterFModeFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class SafeFragment extends Fragment {
    private RadioGroup safeGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_safe,container,false);
        safeGroup = (RadioGroup)view.findViewById(R.id.left_safe_group);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_account);
        radioButton.setChecked(true);
        addRightFragment(new AccountManagerFragment(), "safe_account");
        MainActivity.tag_right = "safe_account";

        safeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_account:
                        addRightFragment(new AccountManagerFragment(), "safe_account");
                        MainActivity.tag_right = "safe_account";
                        break;
                    case R.id.left_setting_firewall_safe:
                        addRightFragment(new FireWallFragment(), "safe_fire_wall");
                        MainActivity.tag_right = "safe_fire_wall";
                        break;
                    case R.id.left_setting_separate_mode:
                        addRightFragment(new SeparaterFModeFragment(), "safe_separater");
                        MainActivity.tag_right = "safe_separater";
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
