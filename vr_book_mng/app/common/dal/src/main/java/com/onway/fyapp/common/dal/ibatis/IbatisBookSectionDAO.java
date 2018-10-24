/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.BookSectionDAO;

import com.onway.fyapp.common.dal.dataobject.BookSectionDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.BookSectionDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_book_sections.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisBookSectionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisBookSectionDAO extends SqlMapClientDaoSupport implements BookSectionDAO {
	/**
	 *  Query DB table <tt>vr_book_sections</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, MODEL_ID, CHAPTER_ID, SECTION_ID, SECTION_NAMEGMT_CREATE, GMT_MODIFIED from vr_book_sections</tt>
	 *
	 *	@return List<BookSectionDO>
	 *	@throws DataAccessException
	 */	 
    public  List<BookSectionDO>   loadAllsections() throws DataAccessException {


        return getSqlMapClientTemplate().queryForList("MS-BOOK-SECTION-LOAD-ALLSECTIONS", null);

    }

	/**
	 *  Query DB table <tt>vr_book_sections</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_book_sections</tt>
	 *
	 *	@param chapterId
	 *	@param offset
	 *	@param limit
	 *	@return List<BookSectionDO>
	 *	@throws DataAccessException
	 */	 
    public  List<BookSectionDO>   selectSectionsByChapterId(String chapterId, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("chapterId", chapterId);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-BOOK-SECTION-SELECT-SECTIONS-BY-CHAPTER-ID", param);

    }

	/**
	 *  Query DB table <tt>vr_book_sections</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from vr_book_sections</tt>
	 *
	 *	@param chapterId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectCountsectionsByBookId(String chapterId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("chapterId", chapterId);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-BOOK-SECTION-SELECT-COUNTSECTIONS-BY-BOOK-ID", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Update DB table <tt>vr_book_sections</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_book_sections set SECTION_NAME=?, MODEL_ID=?, GMT_MODIFIED=? where (ID = ?)</tt>
	 *
	 *	@param sectionName
	 *	@param modelId
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateSectionsById(String sectionName, String modelId, Date gmtModified, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("sectionName", sectionName);
        param.put("modelId", modelId);
        param.put("gmtModified", gmtModified);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-BOOK-SECTION-UPDATE-SECTIONS-BY-ID", param);
    }

	/**
	 *  Insert one <tt>BookSectionDO</tt> object to DB table <tt>vr_book_sections</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_book_sections(CHAPTER_ID,SECTION_ID,SECTION_NAME,MODEL_ID,GMT_CREATE) values (?, ?, ?, ?, ?)</tt>
	 *
	 *	@param bookSection
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertSections(BookSectionDO bookSection) throws DataAccessException {
    	if (bookSection == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-BOOK-SECTION-INSERT-SECTIONS", bookSection);

        return bookSection.getId();
    }

	/**
	 *  Delete records from DB table <tt>vr_book_sections</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_book_sections where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteById(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-BOOK-SECTION-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>vr_book_sections</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_book_sections where (CHAPTER_ID = ?)</tt>
	 *
	 *	@param chapterId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteByChapterId(String chapterId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-BOOK-SECTION-DELETE-BY-CHAPTER-ID", chapterId);
    }

}