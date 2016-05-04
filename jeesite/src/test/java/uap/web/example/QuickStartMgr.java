package uap.web.example;

import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * 使用Jetty运行调试Web应用
 */
public class QuickStartMgr {

	public static final int PORT = 10087;
	public static final String CONTEXT = "jeesite";

	public static void main(String[] args) throws Exception {
		 Server server = new Server();  
		  
	        Connector connector = new SelectChannelConnector();  
	        connector.setPort(PORT);  
	  
	        server.setConnectors(new Connector[] { connector });  
	  
	        WebAppContext webAppContext = new WebAppContext("WebContent","/"+CONTEXT);  
	  
	        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
	        URLClassLoader subClassLoader = new URLClassLoader(new URL[]{}, currentClassLoader);
	        //webAppContext.setContextPath("/");  
	        webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");  
	        webAppContext.setResourceBase("src/main/webapp");  
	        webAppContext.setDisplayName(CONTEXT);  
	        webAppContext.setClassLoader(subClassLoader);  
	        webAppContext.setConfigurationDiscovered(true);  
	        webAppContext.setParentLoaderPriority(true);  
	        server.setHandler(webAppContext);  
	        System.out.println(webAppContext.getContextPath());  
	        System.out.println(webAppContext.getDescriptor());  
	        System.out.println(webAppContext.getResourceBase());  
	        System.out.println(webAppContext.getBaseResource());  
	  
	        try {  
	            server.start();
	            //如果server没有起来，这里面join（）函数骑到的作用就是使线程阻塞， 这里join()函数实质上调用的jetty的线程池。(这里和Thread中的join函数相似)
	            server.join();
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        System.out.println("server is  start");  
	    }  
}
