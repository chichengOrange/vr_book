package com.onway.web.controller.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

public class Test {

		public static void main(String[] args) {
			String a ="{\"��һ����\":\"1\",\"��������\":\"3\",\"��������\":\"6\"}";
			Map<String, String> map = (Map<String, String>) JSONArray.parse(a);
			 for(String key:map.keySet()){
		            System.out.println(key+"/"+map.get(key));
		        }
			System.out.println(a);
		}
		
				
	
	 public static boolean isIDNumber(String IDNumber) {
	        if (IDNumber == null || "".equals(IDNumber)) {
	            return false;
	        }
	        // �����б��û�����֤�ŵ��������ʽ��15λ����18λ�����һλ����Ϊ��ĸ��
	        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
	                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
	        //����18λ����֤����:41000119910101123X  410001 19910101 123X
	        //^��ͷ
	        //[1-9] ��һλ1-9�е�һ��      4
	        //\\d{5} ��λ����           10001��ǰ��λʡ���ص�����
	        //(18|19|20)                19���ֽ׶ο���ȡֵ��Χ18xx-20xx�꣩
	        //\\d{2}                    91����ݣ�
	        //((0[1-9])|(10|11|12))     01���·ݣ�
	        //(([0-2][1-9])|10|20|30|31)01�����ڣ�
	        //\\d{3} ��λ����            123����ʮ��λ���������У�ż������Ů��
	        //[0-9Xx] 0123456789Xx���е�һ�� X����ʮ��λΪУ��ֵ��
	        //$��β

	        //����15λ����֤����:410001910101123  410001 910101 123
	        //^��ͷ
	        //[1-9] ��һλ1-9�е�һ��      4
	        //\\d{5} ��λ����           10001��ǰ��λʡ���ص�����
	        //\\d{2}                    91����ݣ�
	        //((0[1-9])|(10|11|12))     01���·ݣ�
	        //(([0-2][1-9])|10|20|30|31)01�����ڣ�
	        //\\d{3} ��λ����            123����ʮ��λ���������У�ż������Ů����15λ����֤����X
	        //$��β


	        boolean matches = IDNumber.matches(regularExpression);

	        //�жϵ�18λУ��ֵ
	        if (matches) {

	            if (IDNumber.length() == 18) {
	                try {
	                    char[] charArray = IDNumber.toCharArray();
	                    //ǰʮ��λ��Ȩ����
	                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	                    //���ǳ���11�󣬿��ܲ�����11λ������Ӧ����֤��
	                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
	                    int sum = 0;
	                    for (int i = 0; i < idCardWi.length; i++) {
	                        int current = Integer.parseInt(String.valueOf(charArray[i]));
	                        int count = current * idCardWi[i];
	                        sum += count;
	                    }
	                    char idCardLast = charArray[17];
	                    int idCardMod = sum % 11;
	                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
	                        return true;
	                    } else {
	                        System.out.println("����֤���һλ:" + String.valueOf(idCardLast).toUpperCase() + 
	                                "����,��ȷ��Ӧ����:" + idCardY[idCardMod].toUpperCase());
	                        return false;
	                    }

	                } catch (Exception e) {
	                    e.printStackTrace();
	                    System.out.println("�쳣:" + IDNumber);
	                    return false;
	                }
	            }

	        }
	        return matches;
	    }
}