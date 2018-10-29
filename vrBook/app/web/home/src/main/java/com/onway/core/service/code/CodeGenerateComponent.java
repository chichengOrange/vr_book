/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.code;

import com.onway.model.enums.BizTypeEnum;
import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * �������������
 * 
 * @author guangdong.li
 * @version $Id: CodeGenerateComponent.java, v 0.1 2015��11��2�� ����4:50:26 guangdong.li Exp $
 */
public interface CodeGenerateComponent {

    /**
     * �������ͻ�ñ��� 
     * 
     * @param platformCode  ƽ̨code
     * @return
     */
    public String nextCodeByType(PlatformCodeEnum platformCode);
    
    
    /**
     * �������ͻ�ñ��� 
     * 
     * @param bizTypeEnum
     * @return
     */
    public String nextCodeByType(BizTypeEnum bizTypeEnum);

    /**
     * �������к�
     * 
     * @param bizType ҵ������
     * @return
     */
    public int nextCode(final String bizType);

}