package com.onway.web.controller.result;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
* <p>Title: JsonQueryResult</p>  
* <p>Description: json 数据查询对象结果集</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:10:29
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonListResult<T> extends JsonResult {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7641724788545434094L;

    public JsonListResult(boolean bizSucc) {
        super(bizSucc);
    }

	private List<T>           listObject;
	
    public JsonListResult(boolean bizSucc, String errCode, String errMsg) {
        super(bizSucc, errCode, errMsg);
    }

    public List<T> getListObject() {
		return listObject;
	}

	public void setListObject(List<T> listObject) {
		this.listObject = listObject;
	}



   

}
