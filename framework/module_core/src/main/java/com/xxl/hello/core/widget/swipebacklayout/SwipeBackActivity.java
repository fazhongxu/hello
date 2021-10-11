package com.xxl.hello.core.widget.swipebacklayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xxl.hello.core.R;
import com.xxl.hello.core.utils.DisplayUtils;
import com.xxl.hello.core.utils.StatusBarUtil;

/**
 * @author xxl
 * @date 2021./10/08
 */
public class SwipeBackActivity extends InnerBaseActivity  implements SwipeBackLayout.SwipeListener{
    private static final String TAG = "SwipeBackActivity";
    private SwipeBackLayout.ListenerRemover mListenerRemover;
    private SwipeBackgroundView mSwipeBackgroundView;
    private boolean mIsInSwipeBack = false;

    private SwipeBackLayout.Callback mSwipeCallback = () -> SwipeBackActivityManager.getInstance().canSwipeBack() && canDragBack();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(newSwipeBackLayout(view));
    }

    @Override
    public void setContentView(int layoutResID) {
        SwipeBackLayout swipeBackLayout = SwipeBackLayout.wrap(this,
                layoutResID, dragBackEdge(), mSwipeCallback);
        if (translucentFull()) {
            swipeBackLayout.getContentView().setFitsSystemWindows(false);
        } else {
            swipeBackLayout.getContentView().setFitsSystemWindows(true);
        }
        mListenerRemover = swipeBackLayout.addSwipeListener(this);
        super.setContentView(swipeBackLayout);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(newSwipeBackLayout(view), params);
    }

    private View newSwipeBackLayout(View view) {
        if (translucentFull()) {
            view.setFitsSystemWindows(false);
        } else {
            view.setFitsSystemWindows(true);
        }
        final SwipeBackLayout swipeBackLayout = SwipeBackLayout.wrap(view, dragBackEdge(), mSwipeCallback);
        mListenerRemover = swipeBackLayout.addSwipeListener(this);
        return swipeBackLayout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListenerRemover != null) {
            mListenerRemover.remove();
        }
        if (mSwipeBackgroundView != null) {
            mSwipeBackgroundView.unBind();
            mSwipeBackgroundView = null;
        }
    }

    /**
     * final this method, if need override this method, use doOnBackPressed as an alternative
     */
    @Override
    public final void onBackPressed() {
        if (!mIsInSwipeBack) {
            doOnBackPressed();
        }
    }

    protected void doOnBackPressed() {
        super.onBackPressed();
    }

    public boolean isInSwipeBack() {
        return mIsInSwipeBack;
    }

    /**
     * disable or enable drag back
     *
     * @return
     */
    protected boolean canDragBack() {
        return true;
    }

    /**
     * if enable drag back,
     *
     * @return
     */
    protected int backViewInitOffset() {
        return DisplayUtils.dp2px(this,100);
    }

    /**
     * called when drag back started.
     */
    protected void onDragStart() {

    }


    protected int dragBackEdge() {
        return SwipeBackLayout.EDGE_LEFT;
    }

    /**
     * Immersive processing
     *
     * @return if true, the area under status bar belongs to content; otherwise it belongs to padding
     */
    protected boolean translucentFull() {
        return false;
    }

    /**
     * restore sub window(e.g dialog) when drag back to previous activity
     *
     * @return
     */
    protected boolean restoreSubWindowWhenDragBack() {
        return true;
    }


    @Override
    public void onScrollStateChange(int state, float scrollPercent) {
        Log.i(TAG, "SwipeListener:onScrollStateChange: state = " + state + " ;scrollPercent = " + scrollPercent);
        mIsInSwipeBack = state != SwipeBackLayout.STATE_IDLE;
        if (state == SwipeBackLayout.STATE_IDLE) {
            if (mSwipeBackgroundView != null) {
                if (scrollPercent <= 0.0F) {
                    mSwipeBackgroundView.unBind();
                    mSwipeBackgroundView = null;
                } else if (scrollPercent >= 1.0F) {
                    // unBind mSwipeBackgroundView until onDestroy
                    finish();
                    int exitAnim = mSwipeBackgroundView.hasChildWindow() ?
                            R.anim.swipe_back_exit_still : R.anim.swipe_back_exit;
                    overridePendingTransition(R.anim.swipe_back_enter, exitAnim);
                }
            }
        }
    }

    @Override
    public void onScroll(int edgeFlag, float scrollPercent) {
        if (mSwipeBackgroundView != null) {
            scrollPercent = Math.max(0f, Math.min(1f, scrollPercent));
            int targetOffset = (int) (Math.abs(backViewInitOffset()) * (1 - scrollPercent));
            SwipeBackLayout.offsetInScroll(mSwipeBackgroundView, edgeFlag, targetOffset);
        }
    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        Log.i(TAG, "SwipeListener:onEdgeTouch: edgeFlag = " + edgeFlag);
        onDragStart();
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        if (decorView != null) {
            Activity prevActivity = SwipeBackActivityManager.getInstance()
                    .getPenultimateActivity(SwipeBackActivity.this);
            if (decorView.getChildAt(0) instanceof SwipeBackgroundView) {
                mSwipeBackgroundView = (SwipeBackgroundView) decorView.getChildAt(0);
            } else {
                mSwipeBackgroundView = new SwipeBackgroundView(SwipeBackActivity.this);
                decorView.addView(mSwipeBackgroundView, 0, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            mSwipeBackgroundView.bind(prevActivity, SwipeBackActivity.this, restoreSubWindowWhenDragBack());
            SwipeBackLayout.offsetInEdgeTouch(mSwipeBackgroundView, edgeFlag,
                    Math.abs(backViewInitOffset()));
        }
    }

    @Override
    public void onScrollOverThreshold() {
        Log.i(TAG, "SwipeListener:onEdgeTouch:onScrollOverThreshold");
    }
}
