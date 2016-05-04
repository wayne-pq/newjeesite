/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWords;
import com.thinkgem.jeesite.modules.cms.entity.Reply;

/**
 * 留言DAO接口
 * 
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface LeaveWordsDao extends CrudDao<LeaveWords> {
	public void insertReply(Reply reply);

	public List<Reply> getReplys(@Param("comment_id")String comment_id,@Param("id")String id,@Param("isfirst")String isfirst);
	public Reply getReply(String id);
}
