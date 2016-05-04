package com.thinkgem.jeesite.modules.cms.entity;

import java.util.Date;
import java.util.UUID;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

public class Reply extends DataEntity<Comment> {
	private static final long serialVersionUID = 1L;
	private String ID;// 编号
	private String COMMENT_ID; // 留言编号
	private String CONTENT; // 回复内容
	private String REPLY_ID; // 回复人ID
	private String IP; // 回复人IP
	private Date createDate;// 回复时间
	private String ISFIRST; // 是否一级回复
	private String BYREPLY_ID;// 被回复人姓名
	private User auditUser; // 审核人
	private Date auditDate; // 审核时间
	private String delFlag; // 删除标记删除标记（0：正常；1：删除；2：审核）

	public Reply() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
		ID = UUID.randomUUID().toString();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCOMMENT_ID() {
		return COMMENT_ID;
	}

	public void setCOMMENT_ID(String cOMMENT_ID) {
		COMMENT_ID = cOMMENT_ID;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getREPLY_ID() {
		return REPLY_ID;
	}

	public void setREPLY_ID(String rEPLY_ID) {
		REPLY_ID = rEPLY_ID;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getISFIRST() {
		return ISFIRST;
	}

	public void setISFIRST(String iSFIRST) {
		ISFIRST = iSFIRST;
	}

	public String getBYREPLY_ID() {
		return BYREPLY_ID;
	}

	public void setBYREPLY_ID(String bYREPLY_ID) {
		BYREPLY_ID = bYREPLY_ID;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
