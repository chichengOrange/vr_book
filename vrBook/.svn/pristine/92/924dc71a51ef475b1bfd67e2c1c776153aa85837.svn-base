package com.onway.web.controller.result;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
* <p>Title: JsonQueryResult</p>  
* <p>Description: json 数据查询对象结果集</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:10:29
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonQueryResult<T> extends JsonResult {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7641724788545434094L;

    public JsonQueryResult(boolean bizSucc) {
        super(bizSucc);
    }

    public JsonQueryResult(boolean bizSucc, String errCode, String errMsg) {
        super(bizSucc, errCode, errMsg);
    }

    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
