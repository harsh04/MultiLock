package com.webconsole.harshmathur.multilock.model;

/**
 * Created by Harsh Mathur on 04-09-2015.
 */
public class NavDrawerItem  {
    private boolean showNotify;
    private String title;
    private int iconID;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int iconID) {
        this.showNotify = showNotify;
        this.title = title;
        this.iconID = iconID;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }
    public int getIcon() {
        return iconID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setIcon(int iconID) {
        this.iconID = iconID;
    }
}
