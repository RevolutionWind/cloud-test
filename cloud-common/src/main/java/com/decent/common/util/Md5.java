package com.decent.common.util;

import java.security.MessageDigest;

/**
 * MD5加密工具
 *
 * @author wangyx
 */
public final class Md5 {
    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * *
     *
     * @param plainText 需要加密的字符串
     * @param md5Format true 32位，false 16 位
     * @param charset   字符集格式
     * @return 密文
     */
    public static String getMd5(String plainText, boolean md5Format, String charset) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.trim().getBytes(charset));
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            if (md5Format) {
                str = buf.toString().toUpperCase();
            } else {
                str = buf.toString().substring(8, 24).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
