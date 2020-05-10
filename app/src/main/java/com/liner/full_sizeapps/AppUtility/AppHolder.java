package com.liner.full_sizeapps.AppUtility;

import java.io.Serializable;

public class AppHolder implements Serializable {
    private String appName;
    private String appPackageName;
    private String appSourceDir;
    private boolean isSelected;

    public AppHolder(String appName, String appPackageName, String appSourceDir, boolean isSelected) {
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.appSourceDir = appSourceDir;
        this.isSelected = isSelected;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "AppHolder{" +
                "appName='" + appName + '\'' +
                ", appPackageName='" + appPackageName + '\'' +
                ", appSourceDir='" + appSourceDir + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
