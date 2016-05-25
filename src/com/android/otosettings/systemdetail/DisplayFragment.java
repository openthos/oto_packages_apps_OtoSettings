package com.android.otosettings.systemdetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.otosettings.R;
/**
 * Created by zhu on 2016/5/6.
 */
public class DisplayFragment extends Fragment {

    private TextView tvCommonDialogLightTitle;
    private Spinner spCommonDialogSpinner;
    private TextView tvLightCount;
    private LinearLayout llLight;
    private SeekBar sbLight;
    private TextView tvProjectInfo;
    private TextView tvCommonDialogProjectTitle;
    private LinearLayout llProject;
    private TextView tvFontSize;
    private TextView tvCommonDialogFontSizeTitle;
    private LinearLayout llFontSize;
    private TextView tvWallPaperInfo;
    private LinearLayout llWallPager;
    private GridView gvWallPaper;
    private Button btnUserDefined;
    private Button btnConfirm;
    private Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragment_1_view = inflater.inflate(R.layout.detail_display,container,false);

        tvLightCount = (TextView) fragment_1_view.findViewById(R.id.light_count);
        llLight = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_light);
        tvProjectInfo = (TextView) fragment_1_view.findViewById(R.id.projectInfo);
        llProject = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_project);
        tvFontSize = (TextView) fragment_1_view.findViewById(R.id.fontSize);
        llFontSize = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_fontSize);
        tvWallPaperInfo = (TextView) fragment_1_view.findViewById(R.id.wallPaperInfo);
        llWallPager = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_wallPaper);


        llLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLightDialog();
            }
        });

        llProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProjectDialog();
            }
        });

        llFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontSizeDialog();
            }
        });

        llWallPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWallPaperDialog();
            }
        });

        return fragment_1_view;
    }

    //show the light dialog
    private void showLightDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View lightDialog = layoutInflater
                .inflate(R.layout.detail_dialog_light_seekbar, null);
        sbLight = (SeekBar) lightDialog.findViewById(R.id.seekbar_light);
        new AlertDialog.Builder(getActivity()).setView(lightDialog)
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

    //show the project dialog
    private void showProjectDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View projectDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogProjectTitle = (TextView) projectDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) projectDialog.findViewById(R.id.common_dialog_spinner);
        tvCommonDialogProjectTitle.setText("projectSetting");
        String[] projectList =new String[] {"project","just Computer"};
        ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCommonDialogSpinner.setAdapter(projectAdapter);
        new AlertDialog.Builder(getActivity()).setView(projectDialog)
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


    //show the font size dialog
    private void showFontSizeDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View fontSizeDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogFontSizeTitle = (TextView) fontSizeDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) fontSizeDialog.findViewById(R.id.common_dialog_spinner);
        tvCommonDialogFontSizeTitle.setText("system font size ");
        String[] fontSizeList =new String[] {"small","normal","big","bigger"};
        ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,fontSizeList);
        fontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCommonDialogSpinner.setAdapter(fontSizeAdapter);
        new AlertDialog.Builder(getActivity()).setView(fontSizeDialog)
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

    //show the wall paper dialog
    private void showWallPaperDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View wallPaperDialog = layoutInflater
                .inflate(R.layout.detail_dialog_wallpaper, null);
        gvWallPaper = (GridView) wallPaperDialog.findViewById(R.id.gvWallPaper);
        btnUserDefined = (Button) wallPaperDialog.findViewById(R.id.btn_user_defined);
        btnConfirm = (Button) wallPaperDialog.findViewById(R.id.btn_wallPager_confirm);
        btnCancel = (Button) wallPaperDialog.findViewById(R.id.btn_wallPager_cancel);

        String[] wallPaperList =new String[] {"system wall pager01","system wall pager02","system wall pager03","system wall pager04",};
        int[] wallPaperIcon = new int[]{R.drawable.wall_paper_01,R.drawable.wall_paper_01,R.drawable.wall_paper_01,R.drawable.wall_paper_01,};

        MyGridViewAdapter wallPaperAdapter = new MyGridViewAdapter(getActivity(),wallPaperList,wallPaperIcon);
        gvWallPaper.setAdapter(wallPaperAdapter);

        new AlertDialog.Builder(getActivity()).setView(wallPaperDialog).create().show();

    }

    //wallpaper adapter
    private class MyGridViewAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private Context mContext;
        private String[] mTitles;
        private int[] mIcons;
        public MyGridViewAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        public MyGridViewAdapter(Context context, String[] titles, int [] icons){
            this.mInflater = LayoutInflater.from(context);
            this.mContext = context;
            this.mTitles = titles;
            this.mIcons = icons;
        }
        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public String getItem(int position) {
            return mTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.detail_dialog_wallpaper_item,null);
                viewHolder = new GViewHolder();

                viewHolder.img =(ImageView)convertView.findViewById(R.id.iv_wallpaper_image);
                viewHolder.title = (TextView)convertView.findViewById(R.id.tv_wallpaper_title);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (GViewHolder)convertView.getTag();
            }

            viewHolder.title.setText(mTitles[position]);
            viewHolder.img.setImageResource(mIcons[position]);
            return convertView;
        }
    }

    static class GViewHolder{
       public TextView title;
       public ImageView img;
    }
}
