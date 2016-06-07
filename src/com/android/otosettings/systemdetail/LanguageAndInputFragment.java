package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

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
        final String[] languageList =new String[] {"simple chinese","complex chinese","complex chinese(Hokong)","English"};
        final ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,languageList);
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
                        languageData = languageList[position];
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

    //show typeWriting dialog
    public void showTypeWritingDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View typeWritingDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogTypeWritingTitle = (TextView) typeWritingDialog.findViewById(R.id.common_dialog_title);
        btnPopupTitle = (Button) typeWritingDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogTypeWritingTitle.setText("typeWriting ");
        final String[] typeWritingList =new String[] {"google inputWriting","sougou inputWriting","microsoft inputWriting","qqPinYin inputWriting"};
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
                        pw.dismiss();
                    }
                });
            }
        });
        new AlertDialog.Builder(getActivity()).setView(typeWritingDialog)
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
