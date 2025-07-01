package com.xxl.hello.widget.ui.view.keyboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xxl.kit.KeyboardUtils;

/**
 * 表情键盘处理
 * https://github.com/KaneShaw/EmotionKeyboard/blob/master/emotionkeyboard/src/main/java/com/xk2318/emotionkeyboard/EmotionKeyboard.java
 */
public class EmotionKeyboard {
    private static final String SHARE_PREFERENCE_NAME = "com.xk2318.EmotionKeyboard";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private View mEmotionLayout;//表情布局
    private View mExtendLayout;//扩展布局（上传图片、拍照、位置、红包等等功能）
    private EditText mEditText;
    private View mContentView;
    private int mSoftSoftInputHeight;
    private OnEmotionKeyboardListener mOnEmotionKeyboardListener;

    private EmotionKeyboard() {
    }

    public static EmotionKeyboard with(Activity activity) {
        EmotionKeyboard EmotionKeyboard = new EmotionKeyboard();
        EmotionKeyboard.mActivity = activity;
        EmotionKeyboard.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        EmotionKeyboard.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return EmotionKeyboard;
    }

    public EmotionKeyboard bindToContent(View contentView) {
        mContentView = contentView;
        return this;
    }

    /* 绑定输入框 */
    public EmotionKeyboard bindToEditText(EditText editText) {
        mEditText = editText;
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    /*
                     * 若表情布局可见，则 锁定内容布局高度、隐藏表情布局、显示软键盘、解锁内容布局高度
                     * 若扩展布局可见，则 锁定内容布局高度、隐藏扩展布局、显示软键盘、解锁内容布局高度
                     * 若表情布局与扩展布局均不可见，则do nothing
                     */
                    if (mEmotionLayout != null && mEmotionLayout.isShown()) {
                        lockContentHeight();
                        hideLayout(mEmotionLayout, true);
                        mEditText.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                unlockContentHeightDelayed();
                                if (mOnEmotionKeyboardListener != null) {
                                    mOnEmotionKeyboardListener.onEmotionKeyboardExpand();
                                }
                            }
                        }, 200L);
                    } else if (mExtendLayout != null && mExtendLayout.isShown()) {
                        lockContentHeight();
                        hideLayout(mExtendLayout, true);
                        mEditText.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                unlockContentHeightDelayed();
                                if (mOnEmotionKeyboardListener != null) {
                                    mOnEmotionKeyboardListener.onEmotionKeyboardExpand();
                                }
                            }
                        }, 200L);
                    }
                }
                return false;
            }
        });
        return this;
    }

    /* 绑定表情按钮 */
    public EmotionKeyboard bindToEmotionButton(View emotionButton) {
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLayout(mExtendLayout, false);
                if (mEmotionLayout != null && mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideLayout(mEmotionLayout, true);
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showLayout(mEmotionLayout);
                        unlockContentHeightDelayed();
                    } else {
                        if (mExtendLayout != null && mExtendLayout.isShown()) {
                            hideLayout(mExtendLayout, false);
                        }
                        showLayout(mEmotionLayout);
                    }
                    if (mOnEmotionKeyboardListener != null) {
                        mOnEmotionKeyboardListener.onEmotionKeyboardExpand();
                    }
                }
            }
        });
        return this;
    }

    /* 绑定扩展按钮(拍照上传、位置、红包等) */
    public EmotionKeyboard bindToExtendButton(View extendButton) {
        extendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideLayout(mEmotionLayout, false);
                if (mExtendLayout != null && mExtendLayout.isShown()) {
                    lockContentHeight();
                    hideLayout(mExtendLayout, true);
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showLayout(mExtendLayout);
                        unlockContentHeightDelayed();
                    } else {
                        if (mEmotionLayout != null && mEmotionLayout.isShown()) {
                            hideLayout(mEmotionLayout, false);
                        }
                        showLayout(mExtendLayout);
                    }
                }
            }
        });
        return this;
    }

    /* 设置需要显示的表情布局 */
    public EmotionKeyboard setEmotionView(View emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }

    /* 设置需要显示的扩展布局 */
    public EmotionKeyboard setExtendView(View extendView) {
        mExtendLayout = extendView;
        return this;
    }

    /**
     * 设置监听事件
     *
     * @param listener
     * @return
     */
    public EmotionKeyboard setOnEmotionKeyboardListener(OnEmotionKeyboardListener listener) {
        mOnEmotionKeyboardListener = listener;
        return this;
    }

    public EmotionKeyboard build() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();

        KeyboardUtils.registerSoftInputChangedListener(mActivity, height -> {
            if (height > 0) {
                mSoftSoftInputHeight = height;
                sp.edit().putInt(SHARE_PREFERENCE_TAG, height).apply();

                if (mOnEmotionKeyboardListener != null) {
                    mOnEmotionKeyboardListener.onEmotionKeyboardExpand();
                }
            }
        });
        return this;
    }

    public boolean interceptBackPress() {
        if (mEmotionLayout.isShown()) {
            hideLayout(mEmotionLayout, false);
            return true;
        }
        return false;
    }

    public void hideExtendLayout() {
        if (mExtendLayout == null) {
            return;
        }
        if (mExtendLayout.getVisibility() == View.VISIBLE) {
            mExtendLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示指定布局
     *
     * @param layout 需要显示的布局
     */
    private void showLayout(View layout) {
        int softInputHeight = getKeyBoardHeight();
        hideSoftInput();
        layout.getLayoutParams().height = softInputHeight;
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * @param layout        需要隐藏的布局视图
     * @param showSoftInput 是否显示软键盘
     */
    private void hideLayout(View layout, boolean showSoftInput) {
        if (layout == null) {
            return;
        }
        if (layout.isShown()) {
            layout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;
        mContentView.setLayoutParams(params);
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
                params.weight = 1.0F;
                mContentView.setLayoutParams(params);
            }
        }, 200L);
    }

    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight + getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionKeyboard", "Warning: value of softInputHeight is below zero!");
        }
        Log.d("EmotionKeyboard", "" + softInputHeight);
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 获取软键盘高度，由于第一次直接弹出表情时会出现小问题，787是一个均值，作为临时解决方案
     *
     * @return
     */
    public int getKeyBoardHeight() {
        return sp.getInt(SHARE_PREFERENCE_TAG, 787);
    }

    public interface OnEmotionKeyboardListener {
        /**
         * 表情键盘展开（包括 表情/软键盘）
         */
        void onEmotionKeyboardExpand();
    }
}
