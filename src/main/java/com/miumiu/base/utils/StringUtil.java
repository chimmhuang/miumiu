package com.miumiu.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 18:29
 * @description
 */
public class StringUtil {

    public final static String reg_number = "^-?[0-9]+(.[0-9]+)?$";
    public final static String reg_int = "^-?[0-9]+";

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty() || value == "null" ||
                value.replaceAll("\\(","").replaceAll("\\)", "").equals("null");
    }

    public static boolean isEmpty(Object value) {
        return value == null;
    }

    public static boolean isNotEmpty(String value) {
        return value != "null" && value != null && !value.isEmpty() && !value.trim().isEmpty()
                && !value.replaceAll("\\(","").replaceAll("\\)", "").equals("null");
    }
    public static boolean isNotEmpty(Object value) {
        return value != null;
    }

    public static int asint(String value, int def) {
        if (isNotEmpty(value) && value.matches(reg_int)) {
            return Integer.valueOf(value);
        }
        return def;
    }

    public static Integer asint(String value, Integer def) {
        if (isNotEmpty(value) && value.matches(reg_int)) {
            return Integer.valueOf(value);
        }
        return def;
    }

    public static double asDouble(String value, double def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Double.valueOf(value);
        }
        return def;
    }

    public static float asFloat(String value, float def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Float.valueOf(value);
        }
        return def;
    }

    public static long asLong(String value, long def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Long.valueOf(value);
        }
        return def;
    }

    public static String uuid() {
        return (UUID.randomUUID().toString()+DateUtil.nowDate()).replaceAll("-", "");
    }

    /**
     * 替换名字，将其他字符影藏
     * @param name - 要转换的字符串
     * @return
     */
    public static String replaceName(String name){
        if(name.length()<=4){
            return "「"+name.substring(0,2)+"****」";
        }else{
            return "「"+name.substring(0,2)+"****"+name.substring(name.length()-2,name.length())+"」";
        }
    }

    /**
     * 生成默认的名字
     * @return
     */
    public static String createUserName(String tel){
        String name = DateUtil.formatByown(new Date(), "HHmmss")+tel.substring(7,11);
        return name;
    }

    /**
     * 生成默认的名字
     * @return
     */
    public static String createRandName(){
        String name = "探街用户"+DateUtil.formatByown(new Date(), "YYYYmmddSSS");
        return name;
    }

    /**
     * 通过UUID的hashcode来取值
     * @return
     */
    public static String getOrderIdByUUIDHashCode(){
//        int machineId = 1;//最大支持1-9个集群机器部署 - 部署每台机器时请自行修改
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 10 代表长度为10
        // d 代表参数为正数型
        String date = formatOrder(new Date());
        String orderid = date + String.format("%06d", hashCodeV);
        return orderid;
    }

    /**
     * 格式化时期，去掉年的前两位
     * @param date
     * @return
     */
    public static String formatOrder(Date date){
        SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
        String sDate = f.format(date);
        return sDate;
    }

    public static Integer isEmptyDefaultAsInt(String value,int defaultVal) {
        if(value == null || value.isEmpty() || value.trim().isEmpty() || value == "null" ||
                value.replaceAll("\\(","").replaceAll("\\)", "").equals("null"))
            return defaultVal;
        return Integer.parseInt(value);
    }
}
