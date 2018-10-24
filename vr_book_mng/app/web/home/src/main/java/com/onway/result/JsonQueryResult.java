package com.onway.result;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
* <p>Title: JsonQueryResult</p>  
* <p>Description: json ��ݲ�ѯ������</p>  
* @author yugang.ni  
* @date 2018��6��26��  ����5:10:29
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
