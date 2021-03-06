/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.SequenceDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>sequence</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sequence.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: SequenceDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface SequenceDAO {
	/**
	 *  Insert one <tt>SequenceDO</tt> object to DB table <tt>sequence</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sequence(biz_name,current_value,increment_value,gmt_create,gmt_modified) values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param sequence
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public String insertSequence(SequenceDO sequence) throws DataAccessException;

	/**
	 *  Query DB table <tt>sequence</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select biz_name, current_value, increment_value, gmt_create, gmt_modified from sequence where (biz_name = ?)</tt>
	 *
	 *	@param bizName
	 *	@return SequenceDO
	 *	@throws DataAccessException
	 */	 
    public SequenceDO currentSequence(String bizName) throws DataAccessException;

	/**
	 *  Update DB table <tt>sequence</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sequence set current_value=(current_value + increment_value), gmt_modified=CURRENT_TIMESTAMP where (biz_name = ?)</tt>
	 *
	 *	@param bizName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int nextSequence(String bizName) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>sequence</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sequence where (biz_name = ?)</tt>
	 *
	 *	@param bizName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteSequence(String bizName) throws DataAccessException;

}