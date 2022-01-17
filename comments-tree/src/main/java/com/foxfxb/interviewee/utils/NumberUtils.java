package com.foxfxb.interviewee.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
	
	/**
	 * 格式化成整数，如果整数为0，则显示0，当整数长度超过3位时，不用逗号隔开
	 */
	public final static String NUMBER_IN = "##0";
	/**
	 * 有一位小数，且当这个小数为0时不显示，整数部分正常显示，若整数部分为0，则显示0.x，若整数部分长度超过3位，不用逗号分开
	 */
	public final static String NUMBER_IN_1 = "##0.#";
	/**
	 * 有二位小数，且当最后这位小数为0时不显示,整数部分正常显示，若整数部分为0，则显示0.xx，若整数部分长度超过3位，不用逗号分开
	 */
	public final static String NUMBER_IN_2 = "##0.##";
	/**
	 * 有三位小数，且当最后这位小数为0时不显示，整数部分正常显示，若整数部分为0，则显示0.xxx，若整数部分长度超过3位，不用逗号分开
	 */
	public final static String NUMBER_IN_3 = "##0.###";
	/**
	 * 有四位小数，且当最后这位小数为0时不显示，整数部分正常显示，若整数部分为0，则显示0.xxxx，若整数部分长度超过3位，不用逗号分开
	 */
	public final static String NUMBER_IN_4 = "##0.####";
	
	 // 默认除法运算精度
    private static final Integer DEF_DIV_SCALE = 2;
    
	/**
	 *将给定的一个数字按默认操作系统所在的国家的习惯和指定的格式来格式化它，并返回一个该数字对应的字符串
	 * @param number 需要被格式化的数字
	 * @param formatStr 格式化该数字的字符串，参见本类中定义的常量
	 * @return  返回格式化后的数字的对应的字符串
	 */
	public static String formatNumber(double number, String formatStr) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(formatStr);
		return df.format(number);
	}
	

    /**
     * 提供精确的加法运算。
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static Double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1
     *            被减数
     * @param value2
     *            减数
     * @return 两个参数的差
     */
    public static double sub(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1
     *            被乘数
     * @param value2
     *            乘数
     * @return 两个参数的积
     */
    public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param dividend
     *            被除数
     * @param divisor
     *            除数
     * @return 两个参数的商
     */
    public static Double div(Double dividend, Double divisor) {
        return div(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend
     *            被除数
     * @param divisor
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double div(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param value
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(Double value, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean equals(Number num1, Number num2){
        return num1.doubleValue() == num2.doubleValue();
    }
}
