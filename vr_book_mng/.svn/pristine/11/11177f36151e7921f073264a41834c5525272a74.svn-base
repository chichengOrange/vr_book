/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: BizTypeEnum</p>  
* <p>Description: 编码生成枚举类</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:16:13
 */
public enum BizTypeEnum implements EnumBase {

    USER_ID("USER_ID", "用户编号", 1610),
    
    
    STUDENT_NO("STUDENT_NO", "学号", 1612),
    
    BOOK_ID("BOOK_ID", "书籍ID", 1613),
    
    BOOK_VERSION_ID("BOOK_VERSION_ID", "书籍版本ID", 1614),

    ORDER_ID("ORDER_ID", "账单ID", 1615),
    
    CHAPTER_ID("CHAPTER_ID", "章ID", 1616),
    
    SECTION_ID("SECTION_ID", "节ID", 1617),
    ;

    private String code;

    private String message;

    private int    value;

    BizTypeEnum(String code, String message, int value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public String code() {
        return code;
    }

    /**
     * 获取枚举消息
     *
     * @return
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    @Override
    public Number value() {
        return value;
    }
}
