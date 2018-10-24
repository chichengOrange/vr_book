/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.BookVersionDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_book_version</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_book_version.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: BookVersionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface BookVersionDAO {
	/**
	 *  Query DB table <tt>vr_book_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, BOOK_VERSION_ID, BOOK_ID, REVISE_NUM, CHANGE_LOG, STATUS, MEMO, GMT_CREATE, GMT_MODIFIED from vr_book_version</tt>
	 *
	 *	@return List<BookVersionDO>
	 *	@throws DataAccessException
	 */	 
    public  List<BookVersionDO>   loadAllVersion() throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_book_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_book_version</tt>
	 *
	 *	@param bookId
	 *	@return BookVersionDO
	 *	@throws DataAccessException
	 */	 
    public  BookVersionDO   selectBookVersionByBookId(String bookId) throws DataAccessException;

	/**
	 *  Update DB table <tt>vr_book_version</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_book_version set REVISE_NUM=?, CHANGE_LOG=?, STATUS=?, MEMO=?, GMT_MODIFIED=? where (ID = ?)</tt>
	 *
	 *	@param reviseNum
	 *	@param changeLog
	 *	@param status
	 *	@param memo
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateBookVersionByBookId(String reviseNum, String changeLog, String status, String memo, Date gmtModified, int id) throws DataAccessException;

	/**
	 *  Insert one <tt>BookVersionDO</tt> object to DB table <tt>vr_book_version</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_book_version(BOOK_VERSION_ID,BOOK_ID,REVISE_NUM,CHANGE_LOG,STATUS,MEMO,GMT_CREATE) values (?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param bookVersion
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertBookVersion(BookVersionDO bookVersion) throws DataAccessException;

}