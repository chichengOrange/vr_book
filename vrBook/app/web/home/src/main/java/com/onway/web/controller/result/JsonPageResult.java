package com.onway.web.controller.result;

/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
* <p>Title: JsonPageResult</p>  
* <p>Description: json 数据分页结果集</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:08:46
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonPageResult<E> extends JsonResult {

    public JsonPageResult(boolean bizSucc) {
        super(bizSucc);
    }

    public JsonPageResult(boolean bizSucc, String errCode, String errMsg) {
        super(bizSucc, errCode, errMsg);
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7641724788545434094L;

    // 当前页码
    protected int             pageNum          = 1;

    // 总页数
    protected int             totalPages;

    // 是否有下一页
    protected boolean         next             = false;

    private List<E>           listObject;
    
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<E> getListObject() {
        return listObject;
    }

    public void setListObject(List<E> listObject) {
        this.listObject = listObject;
    }
}
