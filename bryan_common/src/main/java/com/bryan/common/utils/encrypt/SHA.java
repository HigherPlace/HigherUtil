package com.bryan.common.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bryan on 2018/9/15 0015.
 */
public class SHA {
    /** 传入文本内容，返回SHA-256 串*/
    public static String encry256(final String strText){
        return  SHA(strText,"SHA-256");
    }

    /** 传入文本内容，返回SHA-512 串*/
    public static String encry512(final String strText){
        return SHA(strText,"SHA-512");
    }

    /** 字符串 SHA加密*/
    private  static String SHA(final String strText,final String strType){

        // 返回值
        String strResult=null;

        // 是否是有效的字符串
        if (strText != null && strText.length()>0){

            // 加密开始，创建加密对象，并传入加密类型
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到bytes类型结果
                byte[] byteBuffer = messageDigest.digest();
                //将byte转为string
                StringBuffer strHexString = new StringBuffer();
                for (int i =0;i<byteBuffer.length;i++){
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length()==1){
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回的结果
                strResult = strHexString.toString();


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
        return  strResult;
    }
}
