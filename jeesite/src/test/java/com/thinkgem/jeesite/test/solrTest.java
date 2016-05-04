package com.thinkgem.jeesite.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.thinkgem.jeesite.solr.SolrServer;

public class solrTest {

	@Test
	public void test() {
		SolrServer solrServer = new SolrServer();
		try {
			HttpSolrServer httpSolrServer = solrServer.getServer();

			//addFileIndex(httpSolrServer);
			
			
		    SolrQuery query = new SolrQuery();  
		    
	        query.setQuery("*:*");  
	        query.setHighlight(true).setHighlightSnippets(1);                                                     
	  
	        QueryResponse ret = httpSolrServer.query(query);  
	  
	        System.out.println(ret);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	// 删除索引  
    // 据查询结果删除：  
    public void DeleteByQuery(HttpSolrServer httpSolrServer) {  
        try {  
            // 删除所有的索引  
        	httpSolrServer.deleteByQuery("jobsName:高级技术支持");  
        	httpSolrServer.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	public void addindex(HttpSolrServer httpSolrServer){
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", 12);
		doc1.addField("content", "my test is easy,测试solr");
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "solrj简单测试");
		doc2.addField("content", "doc2");
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		docs.add(doc2);
		try {
			httpSolrServer.add(docs);
		
		UpdateRequest req = new UpdateRequest();
		req.setAction(AbstractUpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs);
		req.process(httpSolrServer);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addFileIndex(HttpSolrServer httpSolrServer){
		 //Solr cell can also index MS file (2003 version and 2007 version) types.  
	      String fileName = "E:\\day63lucene&solr\\day63lucene&solr\\第六十三天lucene&solr\\Day63\\资料\\solr.doc";   
	      //this will be unique Id used by Solr to index the file contents.  
	      String solrId = "solr.pdf";   
	        
	      ContentStreamUpdateRequest up   
	      = new ContentStreamUpdateRequest("/update/extract");  
	      
	    try {
			up.addFile(new File(fileName),getFileContentType(fileName));
			up.setParam("literal.id", solrId);  
	        up.setParam("uprefix", "attr_");  
	        up.setParam("fmap.content", "attr_content");  
			
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);  
			
			httpSolrServer.request(up);  
			
			QueryResponse rsp = httpSolrServer.query(new SolrQuery("*:*"));  
			
			System.out.println(rsp);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      
	}
	
	  /** 
     * 根据文件名获取文件的ContentType类型 
     * @param filename 
     * @return 
     */  
    public static String getFileContentType(String filename)  
    {  
        String contentType="";  
         String prefix=filename.substring(filename.lastIndexOf(".")+1);  
        if(prefix.equals("xlsx"))  
        {  
            contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";  
        }else if(prefix.equals("pdf"))  
        {  
            contentType="application/pdf";  
        }else if(prefix.equals("doc"))  
        {  
            contentType="application/msword";  
        }else if(prefix.equals("txt"))  
        {  
            contentType="text/plain";  
        }else if(prefix.equals("xls"))  
        {  
            contentType="application/vnd.ms-excel";  
        }else if(prefix.equals("docx"))  
        {  
            contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document";  
        }else if(prefix.equals("ppt"))  
        {  
            contentType="application/vnd.ms-powerpoint";  
        }else if(prefix.equals("pptx"))  
        {  
            contentType="application/vnd.openxmlformats-officedocument.presentationml.presentation";  
        }  
          
        else  
        {  
            contentType="othertype";  
        }  
          
          
        return contentType;  
    }  
}
