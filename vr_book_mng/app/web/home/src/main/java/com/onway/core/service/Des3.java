package com.onway.core.service;


import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.onway.common.lang.StringUtils;
import com.onway.utils.Base64;
import com.onway.web.controller.AbstractController;

/**
 * 3DES鍔犲瘑宸ュ叿绫�
 * 
 * @author li.hong
 */
public class Des3 {
    public static final Logger      logger            = Logger.getLogger(AbstractController.class);
    // 瀵嗛挜
    private final static String secretKey = "dxcbbs@apricot888$#365#$";
    // 鍚戦噺
    private final static String iv        = "01234567";
    // 鍔犺В瀵嗙粺涓�娇鐢ㄧ殑缂栫爜鏂瑰紡
    private final static String encoding  = "utf-8";

    /**
     * 3DES鍔犲瘑
     * 
     * @param plainText 鏅�鏂囨湰
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
     * 3DES瑙ｅ瘑
     * 
     * @param encryptText 鍔犲瘑鏂囨湰
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

            System.out.println(encode("鏉庢柇鐗�"));
            System.out.println(encode("淇℃伅涓庤绠楃瀛�"));
            System.out.println(decode("VXx3KkfTc+I="));

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
