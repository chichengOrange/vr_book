package com.onway.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.onway.model.ProgressBean;
/**
 * 文件上传进度回显
 * 
 * @author Administrator
 * @version $Id: FileUploadProgressListener.java, v 0.1 2018年8月1日 下午4:32:53 Administrator Exp $
 */
public class FileUploadProgressListener implements ProgressListener {
    
    private HttpSession session;
    
    @Override
    public void update(long bytesRead, long contentLength, int items) {
        //设置上传进度
        ProgressBean progress = new ProgressBean(bytesRead, contentLength, items); 
        //将上传进度保存到session中
        session.setAttribute("progress", progress); 
    }

    public void setSession(HttpSession session){
        this.session = session;
    }
}


