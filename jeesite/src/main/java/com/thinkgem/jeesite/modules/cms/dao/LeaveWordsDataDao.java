/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWordsData;

/**
 * 留言板数据DAO接口
 * @author pan
 * @version 2015-12-21
 */
@MyBatisDao
public interface LeaveWordsDataDao extends CrudDao<LeaveWordsData> {
	public List<LeaveWordsData> getById(String id);
}
