package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class AboutThisComputerFragment extends Fragment {

    private LinearLayout llUpgrade;
    private LinearLayout llReset;
    private LinearLayout llUpgradeNewVersion;
    private Button btnReset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_own_inform,container,false);

        llUpgrade = (LinearLayout) fragment_1_view.findViewById(R.id.detail_own_info_upgrade);
        llReset = (LinearLayout) fragment_1_view.findViewById(R.id.detail_own_info_reset);

        llUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpgradeDialog();
            }
        });

        llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetDialog();
            }
        });

        return fragment_1_view;
    }

    //show the system upgrade dialog
    private void showUpgradeDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View upgradeDialog = layoutInflater
                .inflate(R.layout.detail_own_inform_dialog_upgrade, null);
        llUpgradeNewVersion = (LinearLayout)  upgradeDialog.findViewById(R.id.detail_own_info_upgrade_version);

        new AlertDialog.Builder(getActivity()).setView(upgradeDialog).create().show();

        llUpgradeNewVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.create();
                dialog.setTitle("system upgrade");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
            }
        });

    }

    //show the system reset dialog
    private void showResetDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View ResetDialog = layoutInflater
                .inflate(R.layout.detail_own_inform_dialog_reset, null);
        btnReset = (Button)  ResetDialog.findViewById(R.id.btn_detail_own_info_dialog_reset);

        new AlertDialog.Builder(getActivity()).setView(ResetDialog).create().show();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.create();
                dialog.setTitle("the system will recover default and clear all data,should go?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
            }
        });

    }

}
