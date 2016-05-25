package com.android.otosettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends FragmentActivity {
    //topbutton
    private RadioGroup rgsetting = null;
    //record the left part
    private  String tag_left = null;
    //record the right part
    public  static   String tag_right = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //activity restruct :all fragments miss and the mark also clear
        tag_left = null;
        tag_right = null;

        //default situaiton normal
        NormalFragment normalFragment = new NormalFragment();
        addLeftFragment(normalFragment, "normal");
        rgsetting = (RadioGroup)findViewById(R.id.setting_group);
        RadioButton  radioButton= (RadioButton)findViewById(R.id.setting_normal);
        radioButton.setChecked(true);
        rgsetting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.setting_normal:
                        NormalFragment normalFragment = new NormalFragment();
                        addLeftFragment(normalFragment, "normal");
                        break;
                    case R.id.setting_net:
                        NetFragment netFragment = new NetFragment();
                        Log.e("############","01");
                        addLeftFragment(netFragment, "net");
                        break;
                    case R.id.setting_progress:
                        ProgressFragment progressFragment = new ProgressFragment();
                        addLeftFragment(progressFragment, "progress");
                        break;
                    case R.id.setting_system:
                        SystemFragment systemFragment = new SystemFragment();
                        addLeftFragment(systemFragment, "system");
                        break;
                    case R.id.setting_safe:
                        SafeFragment safeFragment = new SafeFragment();
                        addLeftFragment(safeFragment, "safe");
                        break;
                    case R.id.setting_sb:
                        SBFragment sbFragment = new SBFragment();
                        addLeftFragment(sbFragment, "sb");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *left add Fragment
     *
     * @param fragment
     *@param _tag
     */
    private void addLeftFragment(Fragment fragment, String _tag) {
        if (fragment.isAdded()) {
        } else {
            if (tag_left!=null)  removeFragment(tag_left);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.left_frame, fragment,_tag);
            transaction.commit();
            this.tag_left = _tag;
            Log.e("############","03");
        }
    }
    /**
     * delete Fragment
     *
     * @param _tag
     */
    private void removeFragment(String _tag) {
        Log.e("############","02");
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(_tag);
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
//    /**
//     * right add Fragment
//     * @param
//     * */
//    public void addRightFragment(){
//
//    }
}
