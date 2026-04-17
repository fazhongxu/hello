package com.xxl.hello.widget.ui.view.share2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xxl.hello.widget.R;

public class SharePlatformAdapter extends BaseQuickAdapter<SharePlatformItem, SharePlatformAdapter.ViewHolder> {
    
    public SharePlatformAdapter() {
        super(R.layout.widget_recycle_item_share_platform);
    }
    
    @Override
    protected void convert(@NonNull ViewHolder holder, SharePlatformItem item) {
        SharePlatform platform = item.getPlatform();
        
        ImageView ivIcon = holder.getView(R.id.platform_icon);
        TextView tvName = holder.getView(R.id.platform_name);
        
        setPlatformUI(platform, ivIcon, tvName);
    }
    
    private void setPlatformUI(SharePlatform platform, ImageView icon, TextView name) {
        name.setText(platform.getName());
        
        if (platform == SharePlatform.WEIXIN) {
            icon.setImageResource(R.drawable.resources_ic_we_chat);
        } else if (platform == SharePlatform.WEIXIN_CIRCLE) {
            icon.setImageResource(R.drawable.resources_ic_we_chat_circle);
        } else if (platform == SharePlatform.QQ) {
            icon.setImageResource(R.drawable.resources_ic_we_chat);
        } else if (platform == SharePlatform.QZONE) {
            icon.setImageResource(R.drawable.resources_ic_we_chat);
        } else if (platform == SharePlatform.WEIBO) {
            icon.setImageResource(R.drawable.resources_ic_we_chat);
        } else if (platform == SharePlatform.SYSTEM) {
            icon.setImageResource(R.drawable.resources_ic_we_chat);
        }
    }
    
    static class ViewHolder extends BaseViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}