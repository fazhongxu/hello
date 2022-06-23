package com.xxl.kit;

import androidx.collection.SimpleArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xxl.
 * @date 2021/12/01.
 */
public final class RegexUtils {

    public static final int INDEX_NOT_FOUND = -1;

    private final static SimpleArrayMap<String, String> CITY_MAP = new SimpleArrayMap<>();

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // If u want more please visit http://toutiao.com/i6231678548520731137
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return whether input matches regex of simple mobile.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    
    /**
     * Returns the domain part of a given Email address
     *
     * @param email The Email address. E.g Returns "protonmail.com" from the given Email "johnsmith@protonmail.com".
     * @return the domain part of a given Email address.
     */
    public static String extractEmailProvider(String email) {
        return email.substring(email.lastIndexOf("@") + 1);
    }

    /**
     * Returns the username part of a given Email address. E.g. Returns "johnsmith" from the given Email "johnsmith@protonmail.com".
     *
     * @param email The Email address.
     * @return the username part of a given Email address.
     */
    public static String extractEmailUsername(String email) {
        return email.substring(0, email.lastIndexOf("@"));
    }


    /**
     * Return whether a given Email address is on a specified Email provider. E.g. "johnsmith@protonmail.com" and "gmail.com" will return false.
     *
     * @param email The Email address.
     * @param emailProvider The Email provider to testify against.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFromEmailProvider(String email, String emailProvider) {
        return extractEmailProvider(email).equalsIgnoreCase(emailProvider);
    }

    /**
     * Return whether input matches regex of exact mobile.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMobileExact(input, null);
    }

    /**
     * Return whether input matches regex of exact mobile.
     *
     * @param input       The input.
     * @param newSegments The new segments of mobile number.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileExact(final CharSequence input, List<String> newSegments) {
        boolean match = isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
        if (match) return true;
        if (newSegments == null) return false;
        if (input == null || input.length() != 11) return false;
        String content = input.toString();
        for (char c : content.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        for (String newSegment : newSegments) {
            if (content.startsWith(newSegment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether input matches regex of telephone number.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * Return whether input matches regex of id card number which length is 15.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * Return whether input matches regex of id card number which length is 18.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * Return whether input matches regex of exact id card number which length is 18.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18Exact(final CharSequence input) {
        if (isIDCard18(input)) {
            int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] suffix = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            if (CITY_MAP.isEmpty()) {
                CITY_MAP.put("11", "北京");
                CITY_MAP.put("12", "天津");
                CITY_MAP.put("13", "河北");
                CITY_MAP.put("14", "山西");
                CITY_MAP.put("15", "内蒙古");

                CITY_MAP.put("21", "辽宁");
                CITY_MAP.put("22", "吉林");
                CITY_MAP.put("23", "黑龙江");

                CITY_MAP.put("31", "上海");
                CITY_MAP.put("32", "江苏");
                CITY_MAP.put("33", "浙江");
                CITY_MAP.put("34", "安徽");
                CITY_MAP.put("35", "福建");
                CITY_MAP.put("36", "江西");
                CITY_MAP.put("37", "山东");

                CITY_MAP.put("41", "河南");
                CITY_MAP.put("42", "湖北");
                CITY_MAP.put("43", "湖南");
                CITY_MAP.put("44", "广东");
                CITY_MAP.put("45", "广西");
                CITY_MAP.put("46", "海南");

                CITY_MAP.put("50", "重庆");
                CITY_MAP.put("51", "四川");
                CITY_MAP.put("52", "贵州");
                CITY_MAP.put("53", "云南");
                CITY_MAP.put("54", "西藏");

                CITY_MAP.put("61", "陕西");
                CITY_MAP.put("62", "甘肃");
                CITY_MAP.put("63", "青海");
                CITY_MAP.put("64", "宁夏");
                CITY_MAP.put("65", "新疆");

                CITY_MAP.put("71", "台湾老");
                CITY_MAP.put("81", "香港");
                CITY_MAP.put("82", "澳门");
                CITY_MAP.put("83", "台湾新");
                CITY_MAP.put("91", "国外");
            }
            if (CITY_MAP.get(input.subSequence(0, 2).toString()) != null) {
                int weightSum = 0;
                for (int i = 0; i < 17; ++i) {
                    weightSum += (input.charAt(i) - '0') * factor[i];
                }
                int idCardMod = weightSum % 11;
                char idCardLast = input.charAt(17);
                return idCardLast == suffix[idCardMod];
            }
        }
        return false;
    }

    /**
     * Return whether input matches regex of email.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * Return whether input matches regex of url.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isURL(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * Return whether input matches regex of Chinese character.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * Return whether input matches regex of username.
     * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>can't end with "_"</p>
     * <p>length is between 6 to 20</p>.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     * Return whether input matches regex of date which pattern is "yyyy-MM-dd".
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * Return whether input matches regex of ip address.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIP(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * Return whether input matches the regex.
     *
     * @param regex The regex.
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * Return the list of input matches the regex.
     *
     * @param regex The regex.
     * @param input The input.
     * @return the list of input matches the regex
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return Collections.emptyList();
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * Splits input around matches of the regex.
     *
     * @param input The input.
     * @param regex The regex.
     * @return the array of strings computed by splitting input around matches of regex
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null) return new String[0];
        return input.split(regex);
    }

    /**
     * Replace the first subsequence of the input sequence that matches the
     * regex with the given replacement string.
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing the first matching
     * subsequence by the replacement string, substituting captured
     * subsequences as needed
     */
    public static String getReplaceFirst(final String input,
                                         final String regex,
                                         final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * Replace every subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing each matching subsequence
     * by the replacement string, substituting captured subsequences
     * as needed
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    public static final class RegexConstants {

        /**
         * Regex of simple mobile.
         */
        public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
        /**
         * Regex of exact mobile.
         * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 165, 172, 178, 182, 183, 184, 187, 188, 195, 197, 198</p>
         * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 167, 175, 176, 185, 186, 196</p>
         * <p>china telecom: 133, 149, 153, 162, 173, 177, 180, 181, 189, 190, 191, 199</p>
         * <p>china broadcasting: 192</p>
         * <p>global star: 1349</p>
         * <p>virtual operator: 170, 171</p>
         */
        public static final String REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[579])|(15[0-35-9])|(16[2567])|(17[0-35-8])|(18[0-9])|(19[0-35-9]))\\d{8}$";
        /**
         * Regex of telephone number.
         */
        public static final String REGEX_TEL           = "^0\\d{2,3}[- ]?\\d{7,8}$";
        /**
         * Regex of id card number which length is 15.
         */
        public static final String REGEX_ID_CARD15     = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        /**
         * Regex of id card number which length is 18.
         */
        public static final String REGEX_ID_CARD18     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
        /**
         * Regex of email.
         */
        public static final String REGEX_EMAIL         = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        /**
         * Regex of url.
         */
        public static final String REGEX_URL           = "[a-zA-z]+://[^\\s]*";
        /**
         * Regex of Chinese character.
         */
        public static final String REGEX_ZH            = "^[\\u4e00-\\u9fa5]+$";
        /**
         * Regex of username.
         * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
         * <p>can't end with "_"</p>
         * <p>length is between 6 to 20</p>
         */
        public static final String REGEX_USERNAME      = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
        /**
         * Regex of date which pattern is "yyyy-MM-dd".
         */
        public static final String REGEX_DATE          = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
        /**
         * Regex of ip address.
         */
        public static final String REGEX_IP            = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

        ///////////////////////////////////////////////////////////////////////////
        // The following come from http://tool.oschina.net/regex
        ///////////////////////////////////////////////////////////////////////////

        /**
         * Regex of double-byte characters.
         */
        public static final String REGEX_DOUBLE_BYTE_CHAR     = "[^\\x00-\\xff]";
        /**
         * Regex of blank line.
         */
        public static final String REGEX_BLANK_LINE           = "\\n\\s*\\r";
        /**
         * Regex of QQ number.
         */
        public static final String REGEX_QQ_NUM               = "[1-9][0-9]{4,}";
        /**
         * Regex of postal code in China.
         */
        public static final String REGEX_CHINA_POSTAL_CODE    = "[1-9]\\d{5}(?!\\d)";
        /**
         * Regex of integer.
         */
        public static final String REGEX_INTEGER              = "^(-?[1-9]\\d*)|0$";
        /**
         * Regex of positive integer.
         */
        public static final String REGEX_POSITIVE_INTEGER     = "^[1-9]\\d*$";
        /**
         * Regex of negative integer.
         */
        public static final String REGEX_NEGATIVE_INTEGER     = "^-[1-9]\\d*$";
        /**
         * Regex of non-negative integer.
         */
        public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
        /**
         * Regex of non-positive integer.
         */
        public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
        /**
         * Regex of positive float.
         */
        public static final String REGEX_FLOAT                = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
        /**
         * Regex of positive float.
         */
        public static final String REGEX_POSITIVE_FLOAT       = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
        /**
         * Regex of negative float.
         */
        public static final String REGEX_NEGATIVE_FLOAT       = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
        /**
         * Regex of positive float.
         */
        public static final String REGEX_NOT_NEGATIVE_FLOAT   = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
        /**
         * Regex of negative float.
         */
        public static final String REGEX_NOT_POSITIVE_FLOAT   = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";

        ///////////////////////////////////////////////////////////////////////////
        // If u want more please visit http://toutiao.com/i6231678548520731137
        ///////////////////////////////////////////////////////////////////////////
    }

}
