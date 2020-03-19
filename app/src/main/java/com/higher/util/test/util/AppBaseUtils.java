package com.higher.util.test.util;



import com.bryan.common.utils.encrypt.DecriptTools;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bryan on 2018/2/25 0025.
 */

public class AppBaseUtils {

    /**
     * 生成登录验证的签名
     *
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String generatorSign(String phone, String pwd, String timestamp, String nonce) {
        // appId、timestamp、nonce按字典序排序后进行MD5加密，与key进行比对
        ArrayList<String> list = new ArrayList<String>();
        list.add(phone);
        list.add(pwd);
        list.add(nonce);
        list.add(timestamp);
        Collections.sort(list);

        StringBuilder sbTemp = new StringBuilder();
        for (String str : list) {
            sbTemp.append(str);
        }
        return DecriptTools.MD5(sbTemp.toString());
    }

    /**
     * 生成登录验证的签名
     *
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String generatorSign(String phone, String timestamp, String nonce) {
        // appId、timestamp、nonce按字典序排序后进行MD5加密，与key进行比对
        ArrayList<String> list = new ArrayList<String>();
        list.add(phone);
        list.add(nonce);
        list.add(timestamp);
        Collections.sort(list);

        StringBuilder sbTemp = new StringBuilder();
        for (String str : list) {
            sbTemp.append(str);
        }
        return DecriptTools.MD5(sbTemp.toString());
    }


}
