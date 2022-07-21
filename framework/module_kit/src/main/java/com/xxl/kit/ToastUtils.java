package com.xxl.kit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * @author xxl.
 * @date 2022/07/20.
 */
@SuppressLint("InflateParams")
public class ToastUtils {
    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 14; // in SP

    private static boolean tintIcon = true;
    private static boolean allowQueue = true;
    private static int toastGravity = -1;
    private static int xOffset = -1;
    private static int yOffset = -1;
    private static boolean supportDarkTheme = true;
    private static boolean isRTL = false;

    private static Toast lastToast = null;

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private ToastUtils() {
        // avoiding instantiation
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message) {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, Drawable icon) {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration) {
        return normal(context, context.getString(message), duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon) {
        return normal(context, context.getString(message), duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon, boolean withIcon) {
        return normalWithDarkThemeSupport(context, context.getString(message), icon, duration, withIcon);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon, boolean withIcon) {
        return normalWithDarkThemeSupport(context, message, icon, duration, withIcon);
    }

    @CheckResult
    public static Toast warning(@StringRes int message) {
        return warning(AppUtils.getApplication(), StringUtils.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(CharSequence  message) {
        return warning(AppUtils.getApplication(),message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message) {
        return warning(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration) {
        return warning(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), ResourceUtils.getDrawable(R.drawable.kit_ic_error_outline_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_warning_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ResourceUtils.getDrawable(R.drawable.kit_ic_error_outline_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_warning_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message) {
        return info(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration) {
        return info(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), ResourceUtils.getDrawable(R.drawable.kit_ic_info_outline_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_info_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ResourceUtils.getDrawable(R.drawable.kit_ic_info_outline_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_info_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@StringRes int message) {
        return success(AppUtils.getApplication(), StringUtils.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(CharSequence message) {
        return success(AppUtils.getApplication(), message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message) {
        return success(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration) {
        return success(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), ResourceUtils.getDrawable(R.drawable.kit_ic_check_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_success_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ResourceUtils.getDrawable(R.drawable.kit_ic_check_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_success_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@StringRes int message) {
        return error(AppUtils.getApplication(), StringUtils.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(CharSequence message) {
        return error(AppUtils.getApplication(), message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message) {
        return error(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration) {
        return error(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), ResourceUtils.getDrawable(R.drawable.kit_ic_clear_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_error_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ResourceUtils.getDrawable(R.drawable.kit_ic_clear_white_24dp),
                ColorUtils.getColor(R.color.kit_toast_error_color), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               int duration, boolean withIcon) {
        return custom(context, context.getString(message), icon, -1, ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               int duration, boolean withIcon) {
        return custom(context, message, icon, -1, ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, @DrawableRes int iconRes,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), ResourceUtils.getDrawable(iconRes),
                ColorUtils.getColor(tintColorRes), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, message, ResourceUtils.getDrawable(iconRes),
                ColorUtils.getColor(tintColorRes), ColorUtils.getColor(R.color.kit_toast_default_text_color),
                duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), icon, ColorUtils.getColor(tintColorRes),
                ColorUtils.getColor(R.color.kit_toast_default_text_color), duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               @ColorRes int tintColorRes, @ColorRes int textColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), icon, ColorUtils.getColor(tintColorRes),
                ColorUtils.getColor(textColorRes), duration, withIcon, shouldTint);
    }

    @SuppressLint("ShowToast")
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.kit_toast_layout, null);
        final LinearLayout toastRoot = toastLayout.findViewById(R.id.toast_root);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            drawableFrame = DrawableUtils.tint9PatchDrawableFrame(context, R.drawable.kit_toast_frame, tintColor);
        } else {
            drawableFrame = ResourceUtils.getDrawable(R.drawable.kit_toast_frame);
        }
        DrawableUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            if (isRTL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toastRoot.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            DrawableUtils.setBackground(toastIcon, tintIcon ? DrawableUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);

        if (!allowQueue) {
            if (lastToast != null) {
                lastToast.cancel();
            }
            lastToast = currentToast;
        }

        // Make sure to use default values for non-specified ones.
        currentToast.setGravity(
                toastGravity == -1 ? currentToast.getGravity() : toastGravity,
                xOffset == -1 ? currentToast.getXOffset() : xOffset,
                yOffset == -1 ? currentToast.getYOffset() : yOffset
        );

        return currentToast;
    }

    private static Toast normalWithDarkThemeSupport(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                                                    int duration, boolean withIcon) {
        if (supportDarkTheme && Build.VERSION.SDK_INT >= 29) {
            int uiMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (uiMode == Configuration.UI_MODE_NIGHT_NO) {
                return withLightTheme(context, message, icon, duration, withIcon);
            }
            return withDarkTheme(context, message, icon, duration, withIcon);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                return withLightTheme(context, message, icon, duration, withIcon);
            } else {
                return withDarkTheme(context, message, icon, duration, withIcon);
            }
        }
    }

    private static Toast withLightTheme(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                                        int duration, boolean withIcon) {
        return custom(context, message, icon, ColorUtils.getColor(R.color.kit_toast_default_text_color),
                ColorUtils.getColor(R.color.kit_toast_toast_normal_color), duration, withIcon, true);
    }

    private static Toast withDarkTheme(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                                       int duration, boolean withIcon) {
        return custom(context, message, icon, ColorUtils.getColor(R.color.kit_toast_toast_normal_color),
                ColorUtils.getColor(R.color.kit_toast_default_text_color), duration, withIcon, true);
    }

    public static class Config {
        private Typeface typeface = ToastUtils.currentTypeface;
        private int textSize = ToastUtils.textSize;

        private boolean tintIcon = ToastUtils.tintIcon;
        private boolean allowQueue = true;
        private int toastGravity = ToastUtils.toastGravity;
        private int xOffset = ToastUtils.xOffset;
        private int yOffset = ToastUtils.yOffset;
        private boolean supportDarkTheme = true;
        private boolean isRTL = false;

        private Config() {
            // avoiding instantiation
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            ToastUtils.currentTypeface = LOADED_TOAST_TYPEFACE;
            ToastUtils.textSize = 16;
            ToastUtils.tintIcon = true;
            ToastUtils.allowQueue = true;
            ToastUtils.toastGravity = -1;
            ToastUtils.xOffset = -1;
            ToastUtils.yOffset = -1;
            ToastUtils.supportDarkTheme = true;
            ToastUtils.isRTL = false;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        @CheckResult
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        @CheckResult
        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.toastGravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        @CheckResult
        public Config setGravity(int gravity) {
            this.toastGravity = gravity;
            return this;
        }

        @CheckResult
        public Config supportDarkTheme(boolean supportDarkTheme) {
            this.supportDarkTheme = supportDarkTheme;
            return this;
        }

        public Config setRTL(boolean isRTL) {
            this.isRTL = isRTL;
            return this;
        }

        public void apply() {
            ToastUtils.currentTypeface = typeface;
            ToastUtils.textSize = textSize;
            ToastUtils.tintIcon = tintIcon;
            ToastUtils.allowQueue = allowQueue;
            ToastUtils.toastGravity = toastGravity;
            ToastUtils.xOffset = xOffset;
            ToastUtils.yOffset = yOffset;
            ToastUtils.supportDarkTheme = supportDarkTheme;
            ToastUtils.isRTL = isRTL;
        }
    }
}