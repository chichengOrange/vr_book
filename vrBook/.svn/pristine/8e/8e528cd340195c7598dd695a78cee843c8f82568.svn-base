/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.localcache.manager;

import com.onway.core.service.localcache.enums.SysConfigCacheKeyEnum;

/**
 * 系统参数
 * 
 * @author guangdong.li
 * @version $Id: SysConfigCacheManager.java, 
 */
public interface SysConfigCacheManager {

    /**
     * 获取系统value值
     * 
     * @param configKeyEnum
     * 
     * @return
     */
    public String getConfigValue(SysConfigCacheKeyEnum configKeyEnum);

    /**
     * 获取系统value值
     * 
     * @param configKey
     * @return
     */
    public String getConfigValue(String configKey);
    
    /**
     * 刷新
     */
    public void reload();
}
