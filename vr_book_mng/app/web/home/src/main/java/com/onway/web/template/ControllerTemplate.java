package com.onway.web.template;

import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.result.JsonResult;


/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */

/**
 * 服务模板
 * @author Administrator
 *
 */
public interface ControllerTemplate {

	/**
     * <pre> 支持本地事务模板执行业务处理
     * 1. 本地事务封装
     * 2. 异常捕获，及分类处理
     * 3. 业务日志记录
     * </pre>
     * @param baseResult    返回对象
     * @param action        业务操作回调的接口
     */
    public void execute(JsonResult jsonResult, ServiceCheckCallback callBack);

}
