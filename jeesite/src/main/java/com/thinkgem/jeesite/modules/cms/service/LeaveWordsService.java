/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DelHTMLUtil;
import com.thinkgem.jeesite.modules.cms.dao.LeaveWordsDao;
import com.thinkgem.jeesite.modules.cms.dao.LeaveWordsDataDao;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWords;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWordsData;
import com.thinkgem.jeesite.modules.cms.entity.Reply;

/**
 * 留言Service
 * 
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class LeaveWordsService extends CrudService<LeaveWordsDao, LeaveWords> {

	@Autowired
	private LeaveWordsDataDao leaveWordsDataDao;

	@Override
	@Transactional(readOnly = false)
	public void save(LeaveWords entity) {
		if (entity.getIsNewRecord()) {
			entity.preInsert();
			String id = entity.getId();
			LeaveWordsData leaveWordsData = new LeaveWordsData();
			leaveWordsData.setLeavewordid(id);
			leaveWordsData.setDelFlag("0");
			for (LeaveWordsData s : entity.getData()) {
				s.setLeavewordid(id);
				leaveWordsDataDao.insert(s);
			}
			entity.setContent(entity.getContent().substring(
					entity.getContent().indexOf(",") + 1,
					entity.getContent().length()));
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public void saveReply(Reply entity) {
		dao.insertReply(entity);
	}

	public List<Reply> getReplys(String comment_id, String id, String isfirst) {
		return dao.getReplys(comment_id, id, isfirst);
	}

	public Reply getReply(String id) {
		return dao.getReply(id);
	}

	@Override
	public LeaveWords get(String id) {
		LeaveWords leaveWords = dao.get(id);
		return leaveWords;
	}

	@Override
	public Page<LeaveWords> findPage(Page<LeaveWords> page,
			LeaveWords leaveWords) {
		// DetachedCriteria dc = dao.createDetachedCriteria();
		// if (StringUtils.isNotEmpty(guestbook.getType())){
		// dc.add(Restrictions.eq("type", guestbook.getType()));
		// }
		// if (StringUtils.isNotEmpty(guestbook.getContent())){
		// dc.add(Restrictions.like("content", "%"+guestbook.getContent()+"%"));
		// }
		// dc.add(Restrictions.eq(Guestbook.FIELD_DEL_FLAG,
		// guestbook.getDelFlag()));
		// dc.addOrder(Order.desc("createDate"));
		// return dao.find(page, dc);
		leaveWords.getSqlMap().put("dsf",
				dataScopeFilter(leaveWords.getCurrentUser(), "o", "u"));

		leaveWords.setPage(page);
		List<LeaveWords> list = dao.findList(leaveWords);
		for (LeaveWords words : list) {
			List<LeaveWordsData> data = leaveWordsDataDao
					.getById(words.getId());
			
			List<Reply> list2 = Lists.newArrayList();
			
			List<Reply> replys = dao.getReplys(words.getId(), null, "Y");
			
			for (Reply reply : replys) {
				list2.add(reply);
				for(Reply reply2:dao.getReplys(reply.getID(), null, "N")){
					list2.add(reply2);
					list2.addAll(dao.getReplys(reply2.getID(), null, "N"));
				}
			}
			/*for (Reply reply : replys)
				reply.setCONTENT(reply.getCONTENT());*/
			words.setData(data);
			words.setReplys(list2);
		}
		page.setList(list);
		return page;
	}

	public Map img(String str) {
		/* StringBuffer content = new StringBuffer(); */
		String[] num = str.split("<img");

		for (int i = 0; i < num.length; i++) {
			if (i == 0) {
				/* content.append(num[0]); */
				continue;
			}

			num[i] = "<img" + num[i] + "    ";

			int first = num[i].indexOf(">");

			/* content.append(num[i].substring(first+1,num[i].length()-1)); */
			num[i] = num[i].substring(0, first + 1);

		}
		List<String> newNum = new ArrayList<String>();
		for (int i = 1; i < num.length; i++) {
			newNum.add(num[i]);
		}

		System.out.println();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("newNum", newNum);
		/* map.put("content", content); */
		return map;
	}

	@Transactional(readOnly = false)
	public void delete(LeaveWords guestbook, Boolean isRe) {
		// dao.updateDelFlag(id,
		// isRe!=null&&isRe?Guestbook.DEL_FLAG_AUDIT:Guestbook.DEL_FLAG_DELETE);
		dao.delete(guestbook);
	}

	/**
	 * 更新索引
	 */
	public void createIndex() {
		// dao.createIndex();
	}

	/**
	 * 全文检索
	 */
	// FIXME 暂不提供
	public Page<LeaveWords> search(Page<LeaveWords> page, String q,
			String beginDate, String endDate) {

		// 设置查询条件
		// BooleanQuery query = dao.getFullTextQuery(q,
		// "name","content","reContent");
		//
		// // 设置过滤条件
		// List<BooleanClause> bcList = Lists.newArrayList();
		//
		// bcList.add(new BooleanClause(new TermQuery(new
		// Term(Guestbook.FIELD_DEL_FLAG, Guestbook.DEL_FLAG_NORMAL)),
		// Occur.MUST));
		//
		// if (StringUtils.isNotBlank(beginDate) &&
		// StringUtils.isNotBlank(endDate)) {
		// bcList.add(new BooleanClause(new TermRangeQuery("createDate",
		// beginDate.replaceAll("-", ""),
		// endDate.replaceAll("-", ""), true, true), Occur.MUST));
		// }
		//
		// bcList.add(new BooleanClause(new TermQuery(new Term("type", "1")),
		// Occur.SHOULD));
		// bcList.add(new BooleanClause(new TermQuery(new Term("type", "2")),
		// Occur.SHOULD));
		// bcList.add(new BooleanClause(new TermQuery(new Term("type", "3")),
		// Occur.SHOULD));
		// bcList.add(new BooleanClause(new TermQuery(new Term("type", "4")),
		// Occur.SHOULD));
		//
		// BooleanQuery queryFilter =
		// dao.getFullTextQuery((BooleanClause[])bcList.toArray(new
		// BooleanClause[bcList.size()]));
		//
		// System.out.println(queryFilter);
		//
		// // 设置排序（默认相识度排序）
		// Sort sort = null;//new Sort(new SortField("updateDate",
		// SortField.DOC, true));
		// // 全文检索
		// dao.search(page, query, queryFilter, sort);
		// // 关键字高亮
		// dao.keywordsHighlight(query, page.getList(), 30, "name");
		// dao.keywordsHighlight(query, page.getList(), 1300, "content");
		// dao.keywordsHighlight(query, page.getList(), 1300, "reContent");

		return page;
	}

}
