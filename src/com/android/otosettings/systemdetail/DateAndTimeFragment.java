package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class DateAndTimeFragment extends Fragment {

    private TextView tvCommonDialogTimeAreaTitle;
    private Spinner spCommonDialogSpinner;
    private TextView tvtimeSetting;
    private Button btnConfirm;
    private Button btnCancel;
    private LinearLayout llTimeSetting;
    private TextView tvTimeArea;
    private LinearLayout llTimeArea;
    private AnalogClock acClockTime;
    private TextView tvClockTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_date_and_time,container,false);

        tvtimeSetting = (TextView) fragment_1_view.findViewById(R.id.tv_time_setting);
        llTimeSetting = (LinearLayout) fragment_1_view.findViewById(R.id.detail_date_and_time_timeSetting);
        tvTimeArea = (TextView) fragment_1_view.findViewById(R.id.tv_time_area);
        llTimeArea = (LinearLayout) fragment_1_view.findViewById(R.id.detail_date_and_time_timeArea);

        llTimeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeSettingDialog();
            }
        });

        llTimeArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeAreaDialog();
            }
        });


        return fragment_1_view;
    }

    //show time setting dialog
    private void showTimeSettingDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View timeSettingDialog = layoutInflater.inflate(R.layout.detail_date_and_time_dialog_time_setting,null);
        btnConfirm = (Button) timeSettingDialog.findViewById(R.id.btn_dialog_calendar_confirm);
        btnCancel = (Button) timeSettingDialog.findViewById(R.id.btn_dialog_calendar_cancel);
        acClockTime = (AnalogClock) timeSettingDialog.findViewById(R.id.calendar_clock);
        tvClockTime = (TextView) timeSettingDialog.findViewById(R.id.tv_clock_time);

        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//get the current time
        String strTime = formatter.format(curDate);
        tvClockTime.setText(strTime);
        new AlertDialog.Builder(getActivity()).setView(timeSettingDialog).create().show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //show the time area dialog
    private void showTimeAreaDialog(){

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View timeAreaDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogTimeAreaTitle = (TextView) timeAreaDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) timeAreaDialog.findViewById(R.id.common_dialog_spinner);
        tvCommonDialogTimeAreaTitle.setText("time area ");
        String[] timeAreaList =new String[] {"east 1","east 2","east 3","east 4"};
        ArrayAdapter<String> timeAreaAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,timeAreaList);
        timeAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCommonDialogSpinner.setAdapter(timeAreaAdapter);
        new AlertDialog.Builder(getActivity()).setView(timeAreaDialog)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Log.e(TAG, "confirm" + which);
                    }
                })
                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Log.e(TAG, "cancel" + which);
                    }
                }).create().show();
    }
}
