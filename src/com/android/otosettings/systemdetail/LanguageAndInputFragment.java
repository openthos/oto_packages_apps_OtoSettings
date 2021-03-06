package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import com.android.otosettings.MainActivity;
import com.android.otosettings.Utils;
import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class LanguageAndInputFragment extends Fragment {

    private TextView tvLanguage;
    private LinearLayout llLanguage;
    private TextView tvCommonDialogLanguageTitle;
    private TextView tvTpyeWriting;
    private LinearLayout llTypeWriting;
    private TextView tvCommonDialogTypeWritingTitle;
    private Button btnPopupTitle;
    private ListView lvPopupWindowList;
    private String languageData;
    private String typeWritingData;
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
    private Locale localeChoose;
    private String DEFAULT_INPUT_METHOD;
    private String SOU_GOU = "com.sohu.inputmethod.sogou";
    private String BAI_DU = "com.baidu.input";
    private String GOOGLE = "com.google.android.inputmethod.pinyin";
    private String TENCENT = "com.tencent.qqpinyin";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_language,container,false);

        tvLanguage = (TextView) fragment_1_view.findViewById(R.id.language_info);
        llLanguage = (LinearLayout) fragment_1_view.findViewById(R.id.detail_language_languageInfo);
        tvTpyeWriting = (TextView) fragment_1_view.findViewById(R.id.type_writing_info);
        llTypeWriting = (LinearLayout) fragment_1_view.findViewById(R.id.detail_language_type_writing);
        initPx = MainActivity.defaultPx;
        initPy = MainActivity.defaultPy;
        defaultWidth = MainActivity.mainWidth;
        defaultHeight = MainActivity.mainHeight;
        Log.i("languageDefaultData","----"+initPx+"---"+initPy+"----"+defaultWidth+"-----"+defaultHeight);
        llLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        llTypeWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeWritingDialog();
            }
        });

        return fragment_1_view;
    }

    //show language dialog
    public void showLanguageDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View languageDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogLanguageTitle = (TextView) languageDialog.findViewById(R.id.common_dialog_title);
        btnPopupTitle = (Button) languageDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogLanguageTitle.setText("language ");
        btnPopupTitle.setText("simple Chinese");
        final ArrayAdapter<LocalePicker.LocaleInfo> languageAdapter =
                      LocalePicker.constructAdapter(getActivity(),R.layout.item_text_info,R.id.text1);
        final List<LocalePicker.LocaleInfo>  locale = LocalePicker.locales;
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
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnPopupTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupWindowView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_popup_window_list, null);
                lvPopupWindowList =(ListView)popupWindowView.findViewById(R.id.lv_popup_list);
                final PopupWindow pw=new PopupWindow(popupWindowView, 150,180);
                lvPopupWindowList.setAdapter(languageAdapter);
                pw.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.dialog_backgroud));
                pw.setOutsideTouchable(true);
                getInitPopupPosition();
                pw.showAtLocation(languageDialog, Gravity.BOTTOM, btnPX - pNX, pNY-btnPY-btnHeight);
                lvPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        languageData = locale.get(position).toString();
                        localeChoose = locale.get(position).getLocale();
                        Log.i("localeChoose",localeChoose.toString());
                        updateDialogView(languageData);
                        pw.dismiss();
                    }
                });
            }
        });
        new AlertDialog.Builder(getActivity()).setView(languageDialog)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            IActivityManager am = ActivityManagerNative.getDefault();
                            Configuration config = am.getConfiguration();
                            config.locale = localeChoose;
                            // indicate this isn't some passing default - the user wants this remembered
                            config.userSetLocale = true;
                            am.updateConfiguration(config);
                            // Trigger the dirty bit for the Settings Provider.
                            BackupManager.dataChanged("com.android.providers.settings");
                        } catch (RemoteException e) {
                            // Intentionally left blank
                        }
                        getActivity().finish();
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

    //show typeWriting dialog
    public void showTypeWritingDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View typeWritingDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogTypeWritingTitle = (TextView) typeWritingDialog.findViewById(R.id.common_dialog_title);
        btnPopupTitle = (Button) typeWritingDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogTypeWritingTitle.setText("typeWriting ");
        btnPopupTitle.setText("input method");
        InputMethodManager imm = (InputMethodManager) getActivity()
                                                        .getSystemService(getActivity().INPUT_METHOD_SERVICE);
        final List<InputMethodInfo> methodList = imm.getInputMethodList();
        final String[] typeWritingList =new String[methodList.size()];
        for(int i=0;i<methodList.size();i++) {
            typeWritingList[i] = methodList.get(i).loadLabel(getActivity().getPackageManager()).toString();
        }
        final ArrayAdapter<String> typeWritingAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,typeWritingList);
        //typeWritingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                lvPopupWindowList.setAdapter(typeWritingAdapter);
                pw.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.dialog_backgroud));
                pw.setOutsideTouchable(true);
                getInitPopupPosition();
                pw.showAtLocation(typeWritingDialog, Gravity.BOTTOM, btnPX - pNX, pNY-btnPY-btnHeight);
                lvPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        typeWritingData = typeWritingList[position];
                        updateDialogView(typeWritingData);
                        String inputPackageName = methodList.get(position).getPackageName();
                        if(SOU_GOU.equals(inputPackageName)) {
                            DEFAULT_INPUT_METHOD = "com.sohu.inputmethod.sogou/.SogouIME";
                        } else if(GOOGLE.equals(inputPackageName)) {
                            DEFAULT_INPUT_METHOD = "com.google.android.inputmethod.pinyin/.PinyinIME";
                        } else if(BAI_DU.equals(inputPackageName)) {
                            DEFAULT_INPUT_METHOD = "com.baidu.input/.ImeService";
                        } else if(TENCENT.equals(inputPackageName)) {
                            DEFAULT_INPUT_METHOD = "com.tencent.qqpinyin/.QQPYInputMethodService";
                        } else {
                            DEFAULT_INPUT_METHOD = "com.android.inputmethod.latin/.LatinIME";
                        }
                        pw.dismiss();
                    }
                });
            }
        });
        new AlertDialog.Builder(getActivity()).setView(typeWritingDialog)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Settings.Secure.putString(getActivity().getContentResolver(),
                                Settings.Secure.DEFAULT_INPUT_METHOD,
                                DEFAULT_INPUT_METHOD);
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

}
