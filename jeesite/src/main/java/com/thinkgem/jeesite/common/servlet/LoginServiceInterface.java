package com.thinkgem.jeesite.common.servlet;

import java.io.InputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.hdfs.client.HdfsDataInputStream;
import org.apache.hadoop.io.BytesWritable;

public interface LoginServiceInterface {
	
	public static final long versionID=1L;
	public String login(String username,String password);
	
	public BytesWritable getFileInputstream(String path);

}
