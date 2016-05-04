package com.thinkgem.jeesite.solr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWords;
import com.thinkgem.jeesite.modules.cms.service.LeaveWordsService;
import com.thinkgem.jeesite.solr.SolrServer;

@Service
@Transactional(readOnly = true)
public class SolrService {

	@Autowired
	private LeaveWordsService leaveWordsService;

	SolrServer solrServer = new SolrServer();

	public Page search(int pageNo, int pageSize, String q) {
		Page<LeaveWords> page = new Page<LeaveWords>(pageNo, pageSize);
		try {
			HttpSolrServer httpSolrServer = solrServer.getServer();

			SolrQuery query = new SolrQuery();
			if ("".equals(q.trim()) || null == q) {
				query.setQuery("BIGCONTENT:" + q);
			} else {
				query.setQuery("BIGCONTENT:"  + q);
			}
			query.setStart((pageNo - 1) * pageSize);
			query.setRows(pageSize);
			query.addSort("create_date", SolrQuery.ORDER.desc);
			query.setHighlight(true);
			/*
			 * query.setParam("hl", "true");
			 */
			query.addHighlightField("BIGCONTENT");
			query.setHighlightSimplePre("<font color=\'red\'>");
			query.setHighlightSimplePost("</font>");
			QueryResponse ret = httpSolrServer.query(query);
			
			Map<String, Map<String, List<String>>> hightMap = ret
					.getHighlighting();
			//List<String> BIGCONTENTlist = hightMap.get(id).get("BIGCONTENT");
			
			
			
			SolrDocumentList list = ret.getResults();

			page.setCount(list.getNumFound());

			LeaveWords leaveWords = new LeaveWords();

			List<LeaveWords> newsList = new ArrayList<LeaveWords>();
			
			for (SolrDocument solrDocument : list) {
				String id = (String) solrDocument.getFieldValue("id");// 这边是获取solr上有存储的字段，也就是我们在schema.xml文件里配置的field标签的stored属性，=true就会存储在solr服务器上，因为我的schema.xml只配置id的stored=true，所以这边我先通过获取id，在通过id去数据库获取具体的新闻信息
				
				String BIGCONTENT = hightMap.get(id).get("BIGCONTENT").get(0);
				leaveWords = leaveWordsService.get(id);
				if (leaveWords != null) {
					leaveWords.setContent(BIGCONTENT);
					newsList.add(leaveWords);
				}
			}

			page.setList(newsList);

			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;

	}
}
