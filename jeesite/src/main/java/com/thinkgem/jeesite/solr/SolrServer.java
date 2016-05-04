package com.thinkgem.jeesite.solr; 
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.thinkgem.jeesite.common.config.Global;

/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @createDateTime：2012-2-27 下午03:49:04 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class SolrServer {
    private static SolrServer solrServer = null;
    private static HttpSolrServer server=null;
    private static String url = Global.getConfig("Solr.url");
    
    public static synchronized SolrServer getInstance() {
        if (solrServer==null){
           solrServer=new SolrServer();
        }
        return solrServer;
    }
    public HttpSolrServer getServer() throws MalformedURLException{
         if(server==null){
		  server = new HttpSolrServer(url);
		  server.setSoTimeout(10000);  // socket read timeout
		  server.setConnectionTimeout(10000);
		  server.setDefaultMaxConnectionsPerHost(100);
		  server.setMaxTotalConnections(100);
		  server.setFollowRedirects(false);  // defaults to false
		  //allowCompression defaults to false.
		  //Server side must support gzip or deflate for this to have any effect.
		  server.setAllowCompression(true);
		  server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
		}
        return server;
    }
}