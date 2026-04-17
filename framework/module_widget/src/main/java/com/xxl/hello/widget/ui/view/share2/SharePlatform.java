package com.xxl.hello.widget.ui.view.share2;

public enum SharePlatform {
    WEIXIN("微信"),
    WEIXIN_CIRCLE("朋友圈"),
    QQ("QQ"),
    QZONE("QQ空间"),
    WEIBO("微博"),
    SYSTEM("系统分享");
    
    private String name;
    
    SharePlatform(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}