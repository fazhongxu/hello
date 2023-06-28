package com.xxl.kit;

/**
 * 脱敏工具类
 * reference https://www.rstk.cn/news/891873.html?action=onClick
 *
 * @author xxl.
 * @date 2023/6/28.
 */
public class SensitiveUtils {

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

}