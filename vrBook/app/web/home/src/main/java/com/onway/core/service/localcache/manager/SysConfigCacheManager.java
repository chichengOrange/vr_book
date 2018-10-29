/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.localcache.manager;

import com.onway.core.service.localcache.enums.SysConfigCacheKeyEnum;

/**
 * ϵͳ����
 * 
 * @author guangdong.li
 * @version $Id: SysConfigCacheManager.java, 
 */
public interface SysConfigCacheManager {

    /**
     * ��ȡϵͳvalueֵ
     * 
     * @param configKeyEnum
     * 
     * @return
     */
    public String getConfigValue(SysConfigCacheKeyEnum configKeyEnum);

    /**
     * ��ȡϵͳvalueֵ
     * 
     * @param configKey
     * @return
     */
    public String getConfigValue(String configKey);
    
    /**
     * ˢ��
     */
    public void reload();
}
