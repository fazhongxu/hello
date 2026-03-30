package com.xxl.hello.widget.ui.view.share2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareContent implements Serializable {
    public enum Type {
        TEXT, IMAGE, VIDEO, LINK
    }
    
    private Type type;
    private String title;
    private String content;
    private String url;
    private List<String> imageUrls;
    private String videoUrl;
    private String thumbnailPath;
    
    private ShareContent() {}
    
    public static class Builder {
        private ShareContent content = new ShareContent();
        
        public Builder setType(Type type) {
            content.type = type;
            return this;
        }
        
        public Builder setTitle(String title) {
            content.title = title;
            return this;
        }
        
        public Builder setContent(String content) {
            this.content.content = content;
            return this;
        }
        
        public Builder setUrl(String url) {
            content.url = url;
            return this;
        }
        
        public Builder setImageUrl(String imageUrl) {
            List<String> list = new ArrayList<>();
            list.add(imageUrl);
            content.imageUrls = list;
            return this;
        }
        
        public Builder setImageUrls(List<String> imageUrls) {
            content.imageUrls = imageUrls;
            return this;
        }
        
        public Builder setImageUrls(String... imageUrls) {
            List<String> list = new ArrayList<>();
            for (String url : imageUrls) {
                list.add(url);
            }
            content.imageUrls = list;
            return this;
        }
        
        public Builder setVideoUrl(String videoUrl) {
            content.videoUrl = videoUrl;
            return this;
        }
        
        public Builder setThumbnailPath(String thumbnailPath) {
            content.thumbnailPath = thumbnailPath;
            return this;
        }
        
        public ShareContent build() {
            return content;
        }
    }
    
    public Type getType() { return type; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getUrl() { return url; }
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getThumbnailPath() { return thumbnailPath; }
}