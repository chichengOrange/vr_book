package com.onway.utils.des3;


import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.onway.common.lang.StringUtils;
import com.onway.utils.Base64;

/**
 * 3DES加密工具类
 * 
 * @author li.hong
 */
public class Des3 {
    public static final Logger      logger            = Logger.getLogger(Des3.class);
    // 密钥
    private final static String secretKey = "hqytwechat@apricot888$#365#$";
    // 向量
    private final static String iv        = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding  = "utf-8";

    /**
     * 3DES加密
     * 
     * @param plainText 普通文本
     * @return
     * @throws Exception 
     */
    public static String encode(String plainText) {
        if (StringUtils.isBlank(plainText)) {
            return plainText;
        }
        Key deskey = null;
        byte[] encryptData = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            encryptData = cipher.doFinal(plainText.getBytes(encoding));
        } catch (Exception e) {
            logger.error("", e);
        }
        return Base64.encode(encryptData);
    }

    /**
     * 3DES解密
     * 
     * @param encryptText 加密文本
     * @return
     * @throws UnsupportedEncodingException 
     * @throws Exception
     */
    public static String decode(String encryptText) {
        Key deskey = null;
        byte[] decryptData = null;
        if (StringUtils.isBlank(encryptText) || StringUtils.equals(encryptText, "null")) {
            return "";
        }
        String clearString = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            decryptData = cipher.doFinal(Base64.decode(encryptText));
            clearString = new String(decryptData, encoding);
        } catch (Exception e) {
            logger.error("", e);
        } 

        return clearString;
    }

    public static void main(String[] args) {

        try {
        	System.out.println(encode("信息与计算科学"));
            System.out.println(encode("123456"));
            System.out.println(encode("111111"));
            System.out.println(encode("博士"));
            System.out.println(encode("http://127.0.0.1:8080/music/111.mp3"));
            System.out.println(encode("1510742086312"));
            System.out.println(decode("M70DIhmxrK4ukh4k8ObMMg=="));

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
