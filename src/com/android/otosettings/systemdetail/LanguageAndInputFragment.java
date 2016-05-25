package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.otosettings.R;

/**
 * Created by zhu on 2016/5/6.
 */
public class LanguageAndInputFragment extends Fragment {

    private Spinner spCommonDialogSpinner;
    private TextView tvLanguage;
    private LinearLayout llLanguage;
    private TextView tvCommonDialogLanguageTitle;
    private TextView tvTpyeWriting;
    private LinearLayout llTypeWriting;
    private TextView tvCommonDialogTypeWritingTitle;

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
        View languageDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogLanguageTitle = (TextView) languageDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) languageDialog.findViewById(R.id.common_dialog_spinner);
        tvCommonDialogLanguageTitle.setText("language ");
        String[] languageList =new String[] {"simple chinese","complex chinese","complex chinese(Hokong)","English"};
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,languageList);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCommonDialogSpinner.setAdapter(languageAdapter);
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
        View typeWritingDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogTypeWritingTitle = (TextView) typeWritingDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) typeWritingDialog.findViewById(R.id.common_dialog_spinner);
        tvCommonDialogTypeWritingTitle.setText("typeWriting ");
        String[] typeWritingList =new String[] {"google inputWriting","sougou inputWriting","microsoft inputWriting","qqPinYin inputWriting"};
        ArrayAdapter<String> typeWritingAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,typeWritingList);
        typeWritingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCommonDialogSpinner.setAdapter(typeWritingAdapter);
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

}
