package com.xxl.hello.main.ui.main.adapter;

/**
 * 视频封面实体类
 *
 * @author xxl.
 * @date 2026/3/24.
 */
public class VideoCoverEntity {

    private String id;
    private int coverResId;
    private String title;
    private String subtitle;
    private boolean isSelected;

    public VideoCoverEntity(String id, int coverResId, String title, String subtitle) {
        this.id = id;
        this.coverResId = coverResId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
