package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.otosettings.sbdetail.BluetoothFragment;
import com.android.otosettings.sbdetail.MouseFragment;
import com.android.otosettings.sbdetail.PrinterFragment;

/**
 * Created by zhu on 2016/5/4.
 */
public class SBFragment  extends Fragment {
    private RadioGroup sbGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_sb,container,false);
        sbGroup = (RadioGroup)view.findViewById(R.id.left_sb_group);
        RadioButton radioButton = (RadioButton)view.findViewById(R.id.left_setting_printer_sb);
        radioButton.setChecked(true);
        addRightFragment(new PrinterFragment(), "sb_printer");
        MainActivity.tag_right = "sb_printer";

        sbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.left_setting_printer_sb:
                        addRightFragment(new PrinterFragment(), "sb_printer");
                        MainActivity.tag_right = "sb_printer";
                        break;
                    case R.id.left_setting_blue:
                        addRightFragment(new BluetoothFragment(), "sb_bluetooth");
                        MainActivity.tag_right = "sb_bluetooth";
                        break;
                    case R.id.left_setting_mouse:
                        addRightFragment(new MouseFragment(), "sb_mouse");
                        MainActivity.tag_right = "sb_mouse";
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
