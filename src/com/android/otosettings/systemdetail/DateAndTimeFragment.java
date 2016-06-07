package com.android.otosettings.systemdetail;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

import com.android.otosettings.MainActivity;
import com.android.otosettings.Utils;
import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class DateAndTimeFragment extends Fragment {

    private TextView tvCommonDialogTimeAreaTitle;
    private TextView tvtimeSetting;
    private LinearLayout llTimeSetting;
    private TextView tvTimeArea;
    private LinearLayout llTimeArea;
    private AnalogClock acClockTime;
    private TextView tvClockTime;
    private ToggleButton autoTime;
    private ToggleButton autoTimeZone;
    private Boolean autoTimeStatus =true;
    private Boolean autoTimeZoneStatus = true;
    private SimpleAdapter mTimeZoneAdapter;
    private CalendarView clCalendar;
    private Calendar c;
    private AlertDialog.Builder dialog;
    private TimeZone mSelectedTimeZone;
    private boolean calendarChoose = false;
    private int hour;
    private int minute;
    private int second;
    private Button btnPopupTitle;
    private ListView lvPopupWindowList;
    private String timeZoneData;
    private int[] btnLocation = new int[2];
    private int btnPX;
    private int btnPY;
    private int pNX;
    private int pNY;
    private int btnHeight;
    private int initPx;
    private int initPy;
    private int moveWidth;
    private int moveHeight;
    private int defaultWidth;
    private int defaultHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_date_and_time,container,false);
        initView(fragment_1_view);
        initPx = MainActivity.defaultPx;
        initPy = MainActivity.defaultPy;
        defaultWidth = MainActivity.mainWidth;
        defaultHeight = MainActivity.mainHeight;
        Log.i("dateDefaultData","----"+initPx+"---"+initPy+"----"+defaultWidth+"-----"+defaultHeight);
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
        //init make the clickable(false) effective ,must put it after the onClickListener,becase
        //in the click() default make the setClick(true)
        llTimeSetting.setClickable(false);
        llTimeArea.setClickable(false);

        return fragment_1_view;
    }

    private void initView(View view) {
        tvtimeSetting = (TextView) view.findViewById(R.id.tv_time_setting);
        llTimeSetting = (LinearLayout) view.findViewById(R.id.detail_date_and_time_timeSetting);
        tvTimeArea = (TextView) view.findViewById(R.id.tv_time_area);
        llTimeArea = (LinearLayout) view.findViewById(R.id.detail_date_and_time_timeArea);
        autoTime = (ToggleButton) view.findViewById(R.id.auto_time_toggle);
        autoTimeZone = (ToggleButton) view.findViewById(R.id.auto_timeZone_toggle);

        boolean autoTimeEnabled = getAutoState(Settings.Global.AUTO_TIME);
        boolean autoTimeZoneEnabled = getAutoState(Settings.Global.AUTO_TIME_ZONE);

        if(autoTimeEnabled ==true) {
            autoTime.setEnabled(true);
        } else {
            autoTime.setEnabled(false);
        }

        if (autoTimeZoneEnabled == true) {
            autoTimeZone.setEnabled(true);
        } else {
            autoTimeZone.setEnabled(false);
        }

        autoTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    autoTimeStatus = false;
                    llTimeSetting.setClickable(false);
                }
                else {
                    autoTimeStatus = true;
                    llTimeSetting.setClickable(true);
                }
            }
        });
        autoTimeZone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    autoTimeZoneStatus = false;
                    llTimeArea.setClickable(false);
                }
                else {
                    autoTimeZoneStatus = true;
                    llTimeArea.setClickable(true);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register for time ticks and other reasons for time change
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        getActivity().registerReceiver(mIntentReceiver, filter, null, null);

        updateTimeAndDateDisplay(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mIntentReceiver);
    }

    public void updateTimeAndDateDisplay(Context context) {
        final Calendar now = Calendar.getInstance();
        tvtimeSetting.setText(DateFormat.getLongDateFormat(context).format(now.getTime())+" "+DateFormat.getTimeFormat(getActivity()).format(now.getTime()));
        tvTimeArea.setText(getTimeZoneText(now.getTimeZone(), true));
    }

    //show time setting dialog
    private void showTimeSettingDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View timeSettingDialog = layoutInflater.inflate(R.layout.detail_date_and_time_dialog_time_setting,null);
        acClockTime = (AnalogClock) timeSettingDialog.findViewById(R.id.calendar_clock);
        tvClockTime = (TextView) timeSettingDialog.findViewById(R.id.tv_clock_time);
        clCalendar = (CalendarView) timeSettingDialog.findViewById(R.id.detail_date_and_time_dialog_calendar);

        final SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//get the current time
        String strTime = formatter.format(curDate);
        tvClockTime.setText(strTime);
        clCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                c = Calendar.getInstance();

                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendarChoose = true;
            }
        });

        //get default calendar
        if(calendarChoose == false) {
            Date defaultDate = new Date(System.currentTimeMillis());
            c = Calendar.getInstance();
            c.set(Calendar.YEAR, defaultDate.getYear());
            c.set(Calendar.MONTH, defaultDate.getMonth());
            c.set(Calendar.DAY_OF_MONTH, defaultDate.getDay());
        }

        dialog =  new AlertDialog.Builder(getActivity()).setView(timeSettingDialog);
        dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Date newDateTime = null;
                    String newTime = tvClockTime.getText().toString();
                    try {
                        newDateTime = formatter.parse(newTime);
                    }catch(ParseException e) {

                    }
                    try{
                         hour = newDateTime.getHours();
                         minute  = newDateTime.getMinutes();
                         second = newDateTime.getSeconds();
                    }catch (Exception e) {

                    }
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, minute);
                    c.set(Calendar.SECOND, second);
                    c.set(Calendar.MILLISECOND, 0);

                    long date = c.getTimeInMillis();
                    if (date / 1000 < Integer.MAX_VALUE) {
                       ((AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE)).setTime(date);
                    }
                    Intent timeChanged = new Intent(Intent.ACTION_TIME_CHANGED);
                    getActivity().sendBroadcast(timeChanged);
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

    //show the time area dialog
    private void showTimeAreaDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View timeAreaDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogTimeAreaTitle = (TextView) timeAreaDialog.findViewById(R.id.common_dialog_title);
        btnPopupTitle = (Button) timeAreaDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogTimeAreaTitle.setText("time area ");
        final TimeZone tz = TimeZone.getDefault();
        mSelectedTimeZone = tz;
        timeZoneData = mSelectedTimeZone.getID().toString();
        btnPopupTitle.setText(timeZoneData);
        mTimeZoneAdapter = ZonePicker.constructTimezoneAdapter(getActivity(), false,
                R.layout.date_time_setup_custom_list_item_2);
        //mTimeZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        btnPopupTitle.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        btnPopupTitle.getLocationOnScreen(btnLocation);
                        btnPX = btnLocation[0];
                        btnPY = btnLocation[1];
                        btnHeight  = btnPopupTitle.getHeight();
                        Log.i("observerBtnHeight","---------"+"-------"+btnPX+"---"+btnPY+"-----"+btnHeight);
//                        mainView.getViewTreeObserver()
//                                .removeGlobalOnLayoutListener(this);
                    }
                });
        btnPopupTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupWindowView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_popup_window_list, null);
                lvPopupWindowList =(ListView)popupWindowView.findViewById(R.id.lv_popup_list);
                final PopupWindow pw=new PopupWindow(popupWindowView, 150,180);
                lvPopupWindowList.setAdapter(mTimeZoneAdapter);
                pw.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.dialog_backgroud));
                pw.setOutsideTouchable(true);
                getInitPopupPosition();
                pw.showAtLocation(timeAreaDialog, Gravity.BOTTOM, btnPX - pNX, pNY-btnPY-btnHeight);
                lvPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final TimeZone tz = ZonePicker.obtainTimeZoneFromItem(parent.getItemAtPosition(position));
                        mSelectedTimeZone = tz;
                        final AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        alarm.setTimeZone(tz.getID());
                        timeZoneData = mSelectedTimeZone.getID().toString();
                        updateDialogView(timeZoneData);
                        pw.dismiss();
                    }
                });
            }
        });

         new AlertDialog.Builder(getActivity()).setView(timeAreaDialog)
            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                     Intent timeZoneChanged = new Intent(Intent.ACTION_TIME_CHANGED);
                     getActivity().sendBroadcast(timeZoneChanged);
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

    //get the init popup position
    private void getInitPopupPosition() {
        moveWidth = MainActivity.px - initPx;
        moveHeight = MainActivity.py - initPy;
        pNX = (int)(defaultWidth/Utils.WIDTH_RATE + moveWidth);
        pNY = (int)(defaultHeight/Utils.HEIGHT_RATE + moveHeight);
        Log.i("middleData","------"+moveWidth+"---"+moveHeight+"---"+pNX+"---"+pNY);
    }

    //update button data
    public void updateDialogView(String data) {
        btnPopupTitle.setText(data);
    }

    private boolean getAutoState(String name) {
        try {
            return Settings.Global.getInt(getActivity().getContentResolver(), name) > 0;
        } catch (Settings.SettingNotFoundException snfe) {
            return false;
        }
    }

    public static String getTimeZoneText(TimeZone tz, boolean includeName) {
        Date now = new Date();

        // Use SimpleDateFormat to format the GMT+00:00 string.
        SimpleDateFormat gmtFormatter = new SimpleDateFormat("ZZZZ");
        gmtFormatter.setTimeZone(tz);
        String gmtString = gmtFormatter.format(now);

        // Ensure that the "GMT+" stays with the "00:00" even if the digits are RTL.
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        Locale l = Locale.getDefault();
        boolean isRtl = TextUtils.getLayoutDirectionFromLocale(l) == View.LAYOUT_DIRECTION_RTL;
        gmtString = bidiFormatter.unicodeWrap(gmtString,
                isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);

        if (!includeName) {
            return gmtString;
        }

        // Optionally append the time zone name.
        SimpleDateFormat zoneNameFormatter = new SimpleDateFormat("zzzz");
        zoneNameFormatter.setTimeZone(tz);
        String zoneNameString = zoneNameFormatter.format(now);

        // We don't use punctuation here to avoid having to worry about localizing that too!
        return gmtString + " " + zoneNameString;
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Activity activity = getActivity();
            if (activity != null) {
                updateTimeAndDateDisplay(activity);
            }
        }
    };
}
