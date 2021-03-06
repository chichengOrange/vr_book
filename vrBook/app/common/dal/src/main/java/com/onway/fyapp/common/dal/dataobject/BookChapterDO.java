/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>vr_book_chapter</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_book_chapter.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: BookChapterDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class BookChapterDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>BOOK_ID</tt>.
	 */
	private String bookId;

	/**
	 * This property corresponds to db column <tt>CHAPTER_ID</tt>.
	 */
	private String chapterId;

	/**
	 * This property corresponds to db column <tt>CHAPTER_NAME</tt>.
	 */
	private String chapterName;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
     */
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Getter method for property <tt>bookId</tt>.
     *
     * @return property value of bookId
     */
	public String getBookId() {
		return bookId;
	}
	
	/**
	 * Setter method for property <tt>bookId</tt>.
	 * 
	 * @param bookId value to be assigned to property bookId
     */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

    /**
     * Getter method for property <tt>chapterId</tt>.
     *
     * @return property value of chapterId
     */
	public String getChapterId() {
		return chapterId;
	}
	
	/**
	 * Setter method for property <tt>chapterId</tt>.
	 * 
	 * @param chapterId value to be assigned to property chapterId
     */
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

    /**
     * Getter method for property <tt>chapterName</tt>.
     *
     * @return property value of chapterName
     */
	public String getChapterName() {
		return chapterName;
	}
	
	/**
	 * Setter method for property <tt>chapterName</tt>.
	 * 
	 * @param chapterName value to be assigned to property chapterName
     */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
	public Date getGmtModified() {
		return gmtModified;
	}
	
	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
