/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.BookChapterDAO;

import com.onway.fyapp.common.dal.dataobject.BookChapterDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.BookChapterDAO</tt>.
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
 * @version $Id: IbatisBookChapterDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisBookChapterDAO extends SqlMapClientDaoSupport implements BookChapterDAO {
	/**
	 *  Query DB table <tt>vr_book_chapter</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, BOOK_ID, CHAPTER_ID, CHAPTER_NAME, GMT_CREATE, GMT_MODIFIED from vr_book_chapter</tt>
	 *
	 *	@return List<BookChapterDO>
	 *	@throws DataAccessException
	 */	 
    public  List<BookChapterDO>   loadAllchapter() throws DataAccessException {


        return getSqlMapClientTemplate().queryForList("MS-BOOK-CHAPTER-LOAD-ALLCHAPTER", null);

    }

	/**
	 *  Query DB table <tt>vr_book_chapter</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_book_chapter</tt>
	 *
	 *	@param bookId
	 *	@param offset
	 *	@param limit
	 *	@return List<BookChapterDO>
	 *	@throws DataAccessException
	 */	 
    public  List<BookChapterDO>   selectChapterByBookId(String bookId, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("bookId", bookId);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-BOOK-CHAPTER-SELECT-CHAPTER-BY-BOOK-ID", param);

    }

	/**
	 *  Query DB table <tt>vr_book_chapter</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from vr_book_chapter</tt>
	 *
	 *	@param bookId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectCountChapterByBookId(String bookId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("bookId", bookId);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-BOOK-CHAPTER-SELECT-COUNT-CHAPTER-BY-BOOK-ID", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Update DB table <tt>vr_book_chapter</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_book_chapter set CHAPTER_NAME=?, GMT_MODIFIED=? where (ID = ?)</tt>
	 *
	 *	@param chapterName
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateChapterById(String chapterName, Date gmtModified, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("chapterName", chapterName);
        param.put("gmtModified", gmtModified);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-BOOK-CHAPTER-UPDATE-CHAPTER-BY-ID", param);
    }

	/**
	 *  Insert one <tt>BookChapterDO</tt> object to DB table <tt>vr_book_chapter</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_book_chapter(BOOK_ID,CHAPTER_ID,CHAPTER_NAME,GMT_CREATE) values (?, ?, ?, ?)</tt>
	 *
	 *	@param bookChapter
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertChapter(BookChapterDO bookChapter) throws DataAccessException {
    	if (bookChapter == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-BOOK-CHAPTER-INSERT-CHAPTER", bookChapter);

        return bookChapter.getId();
    }

	/**
	 *  Delete records from DB table <tt>vr_book_chapter</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_book_chapter where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteById(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-BOOK-CHAPTER-DELETE-BY-ID", param);
    }

}