/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 留言板数据Entity
 * 
 * @author ThinkGem
 * @version 2013-01-15
 */
public class LeaveWordsData extends DataEntity<LeaveWordsData> {

	private static final long serialVersionUID = 1L;
	private String leavewordid; // 留言板ID
	private String imgs; // 留言板图片

	public String getLeavewordid() {
		return leavewordid;
	}

	public void setLeavewordid(String leavewordid) {
		this.leavewordid = leavewordid;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

}