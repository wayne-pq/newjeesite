package com.thinkgem.jeesite.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.HdfsUtil;
public class HadoopText {
	
	
	
	@Test
	public void testDelete(){
		System.setProperty("hadoop.home.dir", "E:/hadoop/hadoop-2.4.1/data/dfs/hadoop-2.4.1");
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		 String uri = "hdfs://namenode01:9000/";  
	        Configuration config = new Configuration();  
	        FileSystem fs;
			try {
				
				fs = FileSystem.get(URI.create(uri), config);
				//FileStatus[] statuses = fs.listStatus(new Path("/")); 
				Path path = new Path("/imgserver");
				boolean b = fs.delete(path, true);
				
				System.out.println(b);
			} catch (IOException e) {
				e.printStackTrace();
			}  
	   
	        // 列出hdfs上/user/fkong/目录下的所有文件和目录  
	}
	@Test
	public void testAdd(){
		/*System.setProperty("hadoop.home.dir", "E:/hadoop/hadoop-2.4.1/hadoop-2.4.1");
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		String uri = "hdfs://namenode01:9000/";  
		Configuration config = new Configuration();  
		FileSystem fs;
		byte[] buff;
		try {
			InputStream inputStream = new FileInputStream(new File("e:/hadoop/悲惨世界/www.txt8.cn/雨果 悲惨世界 英文版.txt"));
			buff = FileUtils.getBytes(inputStream);
			fs = FileSystem.get(URI.create(uri), config);
			//FileStatus[] statuses = fs.listStatus(new Path("/")); 
			Path path = new Path("/txt/"+"雨果 悲惨世界 英文版.txt");
			 FSDataOutputStream outputStream = fs.create(path);
			outputStream.write(buff, 0, buff.length);
			System.out.println("上传成功!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
		
		
		
		try {
			HdfsUtil.uploadfromLocalPath("e:/hadoop/悲惨世界/www.txt8.cn/雨果 悲惨世界 英文版.txt", "/txt/"+"雨果 悲惨世界 英文版.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
//	@Test
	public static void testAdd1(){
		System.setProperty("hadoop.home.dir", "E:/hadoop/hadoop-2.4.1/data/dfs/hadoop-2.4.1");
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		String uri = "hdfs://namenode01:9000/";  
		Configuration config = new Configuration();
		 config.set("fs.defaultFS", uri); 
		FileSystem fs;
		byte[] buff;
		try {
			InputStream inputStream = new FileInputStream(new File("d:/删元数据.txt"));
			buff = FileUtils.getBytes(inputStream);
			Path path = new Path(uri+"/test2/"+"删元数据1.txt");
			fs = FileSystem.get(URI.create(uri), config);
			// FileStatus[] statuses = fs.listStatus(new Path("/"));
			
			 FSDataOutputStream outputStream = fs.create(path);
				outputStream.write(buff, 0, buff.length);
			System.out.println("上传成功!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
