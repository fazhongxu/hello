package com.xxl.kit;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/**
 * 金额工具类
 *
 * @author xxl.
 * @date 2020/11/06.
 */
public final class MoneyUtils {

    /**
     * 分转换为元
     *
     * @param amount 人民币 单位（分）
     * @return 返回 人民币 元 为单位的 String 字符数字
     * BigDecimal(String.valueOf(amount)).divider
     * BigDecimal(double).divide.divider 0.01精度问题(double 的0.01 很长，string的没问题）
     */
    public static String fen2Yuan(final int amount) {
        BigDecimal fenBigDecimal = new BigDecimal(String.valueOf(amount)).divide(new BigDecimal(100));
        return String.format(Locale.getDefault(), "%.2f", Double.parseDouble(fenBigDecimal.toString()));
    }

    /**
     * 分转换为元
     *
     * @param amount 人民币 单位为"分"的钱
     * @return 单位为"元" 的数字类型金额
     */
    public static double fenToYuan(int amount) {
        BigDecimal divideAmount = new BigDecimal(String.valueOf(amount)).divide(new BigDecimal(100));
        return divideAmount.doubleValue();
    }

    /**
     * 分转换为元
     *
     * @param amount 人民币 单位为"分"的钱
     * @return 单位为"元" 的数字类型金额
     */
    public static double fenToYuan(long amount) {
        BigDecimal divideAmount = new BigDecimal(amount).divide(new BigDecimal(100));
        return divideAmount.doubleValue();
    }

    /**
     * 元转换为 分
     *
     * @param amount 单位为"元" 的金额
     * @return
     */
    public static int yuanToFen(double amount) {
        BigDecimal multiplyAmount = new BigDecimal(Double.toString(amount)).multiply(new BigDecimal(100));
        return multiplyAmount.intValue();
    }

    /**
     * 美分转换为美元
     *
     * @param amount
     * @return
     */
    public static String cent2Dollar(int amount) {
        BigDecimal fenBigDecimal = new BigDecimal(String.valueOf(amount)).divide(new BigDecimal(100))
                .setScale(2);
        return String.format(Locale.getDefault(), "%.2f", fenBigDecimal.doubleValue());
    }

    /**
     * 美分转换为美元
     *
     * @param amount
     * @return
     */
    public static double centToDollar(int amount) {
        BigDecimal fenBigDecimal = new BigDecimal(String.valueOf(amount)).divide(new BigDecimal(100))
                .setScale(2);
        return fenBigDecimal.doubleValue();
    }

    /**
     * 美元转换为美分
     *
     * @param amount
     * @return
     */
    public static int dollarToCent(double amount) {
        BigDecimal fenBigDecimal = new BigDecimal(String.valueOf(amount)).multiply(new BigDecimal(100));
        return fenBigDecimal.intValue();
    }

    /**
     * "美元" 转换为 人民币"元"
     *
     * @param amount 美元
     * @param rate   人民币兑美元汇率
     * @return
     */
    public static double dollarToYuan(double amount,
                                      double rate) {
        return dollarToYuan(amount, 2, rate);
    }

    /**
     * "美元" 转换为 人民币"元"
     *
     * @param amount 美元
     * @param scale  保留小数点位数
     * @param rate   人民币兑美元汇率
     * @return
     */
    public static double dollarToYuan(double amount,
                                      int scale,
                                      double rate) {
        final BigDecimal divideAmount = new BigDecimal(Double.toString(amount)).divide(BigDecimal.valueOf(rate), scale, RoundingMode.HALF_UP);
        return divideAmount.doubleValue();
    }

    /**
     * "元" 转换为 "美元"
     *
     * @param amount 元
     * @param rate   人民币兑美元汇率
     * @return
     */
    public static double yuanToDollar(double amount,
                                      double rate) {
        return yuanToDollar(amount, 2, rate, RoundingMode.HALF_UP);
    }

    /**
     * "元" 转换为 "美元"
     *
     * @param amount 元
     * @param scale  保留小数点位数
     * @param rate   人民币兑美元汇率
     * @return
     */
    public static double yuanToDollar(double amount,
                                      int scale,
                                      double rate,
                                      RoundingMode roundingMode) {
        if (rate <= 0) {
            return 0;
        }
        final BigDecimal divideAmount = new BigDecimal(Double.toString(amount))
                .multiply(BigDecimal.valueOf(rate))
                .setScale(scale, roundingMode);
        return divideAmount.doubleValue();
    }

    /**
     * 获取输入框以"元"为单位的金额
     *
     * @param targetEditText 目标输入框
     * @return
     */
    public static double getEditInputYuanAmount(@Nullable final EditText targetEditText) {
        int fenAmount = getEditInputFenAmount(targetEditText);
        return fenToYuan(fenAmount);
    }

    /**
     * 获取输入框以"分"为单位的金额
     *
     * @param targetEditText 目标输入框
     * @return
     */
    public static int getEditInputFenAmount(@Nullable final EditText targetEditText) {
        if (targetEditText == null || targetEditText.getText() == null) {
            return 0;
        }
        final Editable text = targetEditText.getText();
        String amountString = "";
        if (text != null) {
            amountString = text.toString();
        }
        int amount = 0;
        try {
            if (!TextUtils.isEmpty(amountString)) {
                if (amountString.startsWith(".")) {
                    amountString = "0" + amountString;
                } else if (amountString.endsWith(".")) {
                    amountString = amountString + "0";
                }
                amount = MoneyUtils.yuanToFen(Double.parseDouble(amountString));
            }
        } catch (Exception e) {
            amount = 0;
        }
        return amount;
    }

    /**
     * 获取字符以"分"为单位的金额
     *
     * @param targetText 目标文本
     * @return
     */
    public static int getInputFenAmount(@Nullable final CharSequence targetText) {
        if (TextUtils.isEmpty(targetText)) {
            return 0;
        }
        String amountString = targetText.toString();
        int amount = 0;
        try {
            if (!TextUtils.isEmpty(amountString)) {
                if (amountString.startsWith(".")) {
                    amountString = "0" + amountString;
                } else if (amountString.endsWith(".")) {
                    amountString = amountString + "0";
                }
                amount = MoneyUtils.yuanToFen(Double.parseDouble(amountString));
            }
        } catch (Exception e) {
            amount = 0;
        }
        return amount;
    }

    /**
     * 获取输入框的金额 没有单位
     *
     * @param targetEditText 目标输入框
     * @return
     */
    public static double getEditInputAmount(@Nullable final EditText targetEditText) {
        if (targetEditText == null || targetEditText.getText() == null) {
            return 0;
        }
        final Editable text = targetEditText.getText();
        String amountString = "";
        if (text != null) {
            amountString = text.toString();
        }
        int amount = 0;
        try {
            if (!TextUtils.isEmpty(amountString)) {
                if (amountString.startsWith(".")) {
                    amountString = "0" + amountString;
                } else if (amountString.endsWith(".")) {
                    amountString = amountString + "0";
                }
                return Double.parseDouble(amountString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * 获取输入框的值 没有单位
     *
     * @param targetEditText 目标输入框
     * @return
     */
    public static double getEditInputValue(@Nullable final EditText targetEditText) {
        if (targetEditText == null || targetEditText.getText() == null) {
            return 0;
        }
        final Editable text = targetEditText.getText();
        String valueString = "";
        if (text != null) {
            valueString = text.toString();
        }
        int value = 0;
        try {
            if (!TextUtils.isEmpty(valueString)) {
                if (valueString.startsWith(".")) {
                    valueString = "0" + valueString;
                } else if (valueString.endsWith(".")) {
                    valueString = valueString + "0";
                }
                return Double.parseDouble(valueString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 转换金额（元）
     *
     * @param amount （分）
     * @return 如果后面是两个0，则只取小数点前面的整数，如果最后一个是0，则去掉最后一个0
     */
    public static String fen2YuanTrim(final int amount) {
        String amountString = MoneyUtils.fen2Yuan(amount);
        try {
            if (!TextUtils.isEmpty(amountString) && amountString.indexOf(".") > 0) {
                if (amountString.indexOf(".00") > 0) {
                    amountString = amountString.substring(0, amountString.indexOf(".00"));
                } else if (amountString.endsWith("0")) {
                    amountString = amountString.substring(0, amountString.length() - 1);
                }
                return amountString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amountString;
    }

    /**
     * 获取随机红包金额
     * refer https://www.jianshu.com/p/f14221fef78e
     *
     * @param amount
     * @param person
     * @return
     */
    public static List<Double> getRandomMoney(double amount, Integer person) {

        List<Double> moneys = new ArrayList<>();
        BigDecimal amount1 = new BigDecimal(Double.valueOf(amount));

        //计算出随机数分布值
        amount1 = amount1.multiply(BigDecimal.valueOf(100));

        Set set = new HashSet();
        ArrayList<Integer> list = new ArrayList();
        list.add(0, 0);
        Random r = new Random();
        for (int i = 0; i < person - 1; i++) {
            //防止重复点
            while (true) {
                Integer money = r.nextInt(amount1.intValue());
                boolean isContain = set.contains(money);
                if (!isContain) {
                    set.add(money);
                    list.add(money);
                    break;
                }
            }
        }
        list.add(person, amount1.intValue());

        //排序
        Collections.sort(list, Collections.reverseOrder());

        //根据比例计算金额
        BigDecimal count = new BigDecimal(0);
        for (int i = 0; i < list.size() - 1; i++) {
            if (i == list.size() - 2) {
                BigDecimal a = amount1.divide(BigDecimal.valueOf(100)).subtract(count);
                moneys.add(a.doubleValue());
                count = count.add(a);
                return moneys;
            }
            double gap = list.get(i) - list.get(i + 1);
            DecimalFormat df = new DecimalFormat("0.00");

            String mon = df.format(new BigDecimal(gap / amount * (amount / 100)));

            BigDecimal a = BigDecimal.valueOf(Double.parseDouble(mon));
            count = count.add(a);
            moneys.add(a.doubleValue());
        }
        return moneys;
    }

}
