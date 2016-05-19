package com.android.otosettings.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by libing on 16-5-3.
 */
public class ApplicationInfo {
    public String appName;
    public int appIcon;
    public String appStatus;

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

}
