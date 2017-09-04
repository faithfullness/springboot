package cn.joojee.wxqh.utils;

/**
 * Created by Administrator on 2017/8/31.
 */
public class WebStringUtils extends org.apache.commons.lang3.StringUtils {


    public static boolean isEmpty(CharSequence cs) {
        System.out.println("进来了");
        return cs == null || cs.length() == 0 || cs.equals("null") || cs.equals("undefined");
    }

}
