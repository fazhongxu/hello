package com.xxl.kit;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脱敏工具类
 * reference https://www.rstk.cn/news/891873.html?action=onClick
 *
 * @author xxl.
 * @date 2023/6/28.
 */
public class SensitiveUtils {

    public static final String REG_PHONE = "(?<!\\d)(?:(?:\\+86|86)?1\\d{2})\\d{4}(\\d{4})(?!\\d)";
    public static final String ID_CARD = "(?<!\\d)\\d{6}(\\d{8})\\d{4}(?!\\d)";

    private SensitiveUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        try {
            if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
                return mobile;
            }
            return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobile;
    }

    /**
     * 身份证号的保留前三后四，中间的数为星号 "*"
     *
     * @param identity
     * @return
     */
    public static String identityEncrypt(String identity) {
        try {
            if (StringUtils.isEmpty(identity) || (identity.length() != 18)) {
                return identity;
            }
            return identity.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return identity;
    }

    /**
     * 获取手机号和身份证号脱敏
     *
     * @param targetText
     * @return
     */
    public static String findPhoneAndIdCardEncrypt(@NonNull final String targetText) {
        String text = targetText;
        try {
            Pattern phonePattern = Pattern.compile(REG_PHONE);
            Matcher phoneMatcher = phonePattern.matcher(text);
            while (phoneMatcher.find()) {
                String phone = phoneMatcher.group();
                String replacement = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                text = text.replace(phone, replacement);
            }

            // 匹配身份证号
            Pattern idCardPattern = Pattern.compile(ID_CARD);
            Matcher idCardMatcher = idCardPattern.matcher(text);
            while (idCardMatcher.find()) {
                String idCard = idCardMatcher.group();
                String replacement = idCard.replaceAll("(\\d{3})\\d{8}(\\d{4})", "$1********$2");
                text = text.replace(idCard, replacement);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return targetText;
        }

        return text;
    }

}