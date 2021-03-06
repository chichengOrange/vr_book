/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>vr_search</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_search.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: SearchDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class SearchDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>KEY_WORDS</tt>.
	 */
	private String keyWords;

	/**
	 * This property corresponds to db column <tt>TYPE</tt>.
	 */
	private String type;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

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
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Setter method for property <tt>userId</tt>.
	 * 
	 * @param userId value to be assigned to property userId
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * Getter method for property <tt>keyWords</tt>.
     *
     * @return property value of keyWords
     */
	public String getKeyWords() {
		return keyWords;
	}
	
	/**
	 * Setter method for property <tt>keyWords</tt>.
	 * 
	 * @param keyWords value to be assigned to property keyWords
     */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type value to be assigned to property type
     */
	public void setType(String type) {
		this.type = type;
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
}
