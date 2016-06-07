package com.android.otosettings.systemdetail;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.otosettings.MainActivity;
import com.android.otosettings.Utils;
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
    private Handler mHandler;
    private Configuration mCurConfig = new Configuration();
    private Button btnPopupTitle;
    private ListView lvPopupWindowList;
    private String projectData;
    private String fontSizeData;
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

        View fragment_1_view = inflater.inflate(R.layout.detail_display,container,false);

        tvLightCount = (TextView) fragment_1_view.findViewById(R.id.light_count);
        llLight = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_light);
        tvProjectInfo = (TextView) fragment_1_view.findViewById(R.id.projectInfo);
        llProject = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_project);
        tvFontSize = (TextView) fragment_1_view.findViewById(R.id.fontSize);
        llFontSize = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_fontSize);
        tvWallPaperInfo = (TextView) fragment_1_view.findViewById(R.id.wallPaperInfo);
        llWallPager = (LinearLayout) fragment_1_view.findViewById(R.id.detail_display_wallPaper);
        initPx = MainActivity.defaultPx;
        initPy = MainActivity.defaultPy;
        defaultWidth = MainActivity.mainWidth;
        defaultHeight = MainActivity.mainHeight;
        Log.i("displayDefaultData","----"+initPx+"---"+initPy+"----"+defaultWidth+"-----"+defaultHeight);
        Log.i("DisplayFragment","-------------------------------------------------------------init------displaycontent");
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:Bundle b = msg.getData();
                        tvLightCount.setText(String.valueOf(b.get("tmpInt")));
                        break;
                    case 1:Bundle size = msg.getData();
                        tvFontSize.setText(size.get("fontSize").toString());
                        Log.i("DisplayFragment","---------------------------------------------------------------handler----fontsizeMsg");
                    default:
                        break;

                }
            }
        };

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
        sbLight.setMax(255);
        int normal = android.provider.Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,255);
        sbLight.setProgress(normal);

        sbLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // get current progress
                int tmpInt = seekBar.getProgress();
                Message msg = Message.obtain();
                msg.what = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("tmpInt",tmpInt);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
                // when tempInt below 80 make the light 80 in case of can not seeing
                if (tmpInt < 80) {
                    tmpInt = 80;
                }
                // change the light according to the progress
                Settings.System.putInt(getActivity().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, tmpInt);
                tmpInt = Settings.System.getInt(getActivity().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, -1);
                WindowManager.LayoutParams wl = getActivity().getWindow().getAttributes();

                float tmpFloat = (float) tmpInt / 255;
                if (tmpFloat > 0 && tmpFloat <= 1) {
                    wl.screenBrightness = tmpFloat;
                }
                getActivity().getWindow().setAttributes(wl);
            }
        });
        new AlertDialog.Builder(getActivity()).setView(lightDialog).create().show();
    }

    //show the project dialog
    private void showProjectDialog(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View projectDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogProjectTitle = (TextView) projectDialog.findViewById(R.id.common_dialog_title);
        spCommonDialogSpinner = (Spinner) projectDialog.findViewById(R.id.common_dialog_spinner);
        btnPopupTitle = (Button) projectDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogProjectTitle.setText("projectSetting");
        btnPopupTitle.setText("project");
        final String[] projectList =new String[] {"project","just Computer"};
        projectData = projectList[0];
        final ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,projectList);
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
                final View popupWindowView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_popup_window_list, null);
                lvPopupWindowList =(ListView)popupWindowView.findViewById(R.id.lv_popup_list);
                final PopupWindow pw=new PopupWindow(popupWindowView, 150,180);
                btnPopupTitle.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                popupWindowView.getLocationOnScreen(btnLocation);
                                Log.i("observerPopup","----x=="+btnLocation[0]+"----y=="+btnLocation[1]);
//                              mainView.getViewTreeObserver()
//                                .removeGlobalOnLayoutListener(this);
                            }
                        });
                lvPopupWindowList.setAdapter(projectAdapter);
                pw.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.dialog_backgroud));
                pw.setOutsideTouchable(true);
                getInitPopupPosition();
                int width = btnPX - pNX;
                int height =pNY-btnPY-btnHeight;
                Log.i("moveDistance","----"+width+"----"+height);
                pw.showAtLocation(btnPopupTitle,Gravity.BOTTOM, width, height);
                lvPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        projectData = projectList[position];
                        updateDialogView(projectData);
                        pw.dismiss();
                    }
                });
            }
        });
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
        final Message msg = Message.obtain();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View fontSizeDialog = layoutInflater
                .inflate(R.layout.common_detail_dialog_spinner, null);
        tvCommonDialogFontSizeTitle = (TextView) fontSizeDialog.findViewById(R.id.common_dialog_title);
        btnPopupTitle = (Button) fontSizeDialog.findViewById(R.id.btn_popup_title);
        tvCommonDialogFontSizeTitle.setText("system font size ");
        btnPopupTitle.setText("normal");
        final String[] fontSizeList =new String[] {"small","normal","big","bigger"};
        fontSizeData = fontSizeList[0];
        final ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_text_info,fontSizeList);
        //fontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                final View popupWindowView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_popup_window_list, null);
                lvPopupWindowList =(ListView)popupWindowView.findViewById(R.id.lv_popup_list);
                final PopupWindow pw=new PopupWindow(popupWindowView, 150,180);
                lvPopupWindowList.setAdapter(fontSizeAdapter);
                pw.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.dialog_backgroud));
                pw.setOutsideTouchable(true);
                getInitPopupPosition();
                pw.showAtLocation(fontSizeDialog, Gravity.BOTTOM, btnPX - pNX, pNY-btnPY-btnHeight);
                lvPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        msg.what =1;
                        Bundle bundle = new Bundle();
                        if (position == 0) {
                            mCurConfig.fontScale = .75f;
                            bundle.putString("fontSize","small");
                        } else if (position == 2) {
                            mCurConfig.fontScale = 1.0f;
                            bundle.putString("fontSize","normal");
                        } else if(position == 3) {
                            mCurConfig.fontScale = 1.25f;
                            bundle.putString("fontSize","big");
                        } else {
                            mCurConfig.fontScale = 1.5f;
                            bundle.putString("fontSize","bigger");
                        }
                        msg.setData(bundle);

                        fontSizeData = fontSizeList[position];
                        updateDialogView(fontSizeData);
                        pw.dismiss();
                    }
                });
            }
        });
           new AlertDialog.Builder(getActivity()).setView(fontSizeDialog)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ActivityManagerNative.getDefault().updatePersistentConfiguration(mCurConfig);
                            mHandler.sendMessage(msg);
                            Log.i("DispalyFragment","========================================================click======fontsize");
                        } catch (RemoteException e) {
                        }
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

    //update button data
    public void updateDialogView(String data) {
        btnPopupTitle.setText(data);
    }

    //get the init popup position
    private void getInitPopupPosition() {
        moveWidth = MainActivity.px - initPx;
        moveHeight = MainActivity.py - initPy;
        pNX = (int)(defaultWidth/Utils.WIDTH_RATE + moveWidth);
        pNY = (int)(defaultHeight/Utils.HEIGHT_RATE + moveHeight);
        Log.i("middleData","------"+moveWidth+"---"+moveHeight+"---"+pNX+"---"+pNY);
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
