package com.onway.web.controller.wechatpay;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service("MD5Util")
public class MD5Util {
	   private  static String byteArrayToHexString(byte b[]) {  
	        StringBuffer resultSb = new StringBuffer();  
	        for (int i = 0; i < b.length; i++)  
	            resultSb.append(byteToHexString(b[i]));  
	        return resultSb.toString();  
	    }  

	    private static String byteToHexString(byte b) {  
	        int n = b;  
	        if (n < 0)  
	            n += 256;  
	        int d1 = n / 16;  
	        int d2 = n % 16;  
	        return hexDigits[d1] + hexDigits[d2];  
	    }  

	    public static  String MD5Encode(String origin, String charsetname) {  
	        String resultString = null;  
	        try {  
	            resultString = new String(origin);  
	            MessageDigest md = MessageDigest.getInstance("MD5");  
	            if (charsetname == null || "".equals(charsetname))  
	                resultString = byteArrayToHexString(md.digest(resultString  
	                        .getBytes()));  
	            else  
	                resultString = byteArrayToHexString(md.digest(resultString  
	                        .getBytes(charsetname)));  
	        } catch (Exception exception) {  
	        }  
	        return resultString;  
	    }  

	    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
	        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	 
	    public static String getout_trade_no()  
	    {  
	        String out_trade_no="";  
	        Date now = new Date();   
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
	        String time=String.valueOf(System.currentTimeMillis());  
	        long d=System.currentTimeMillis();  
	        String r=MD5Encode(time,"UTF-8");  
	        System.out.println(r);  
	        System.out.println(d);  
	        String hehe = dateFormat.format( now );   
	        System.out.println(hehe+"-"+r);   
	        out_trade_no=hehe+"-"+r;  
	        return out_trade_no.substring(0, 30);  
	    }  
}
