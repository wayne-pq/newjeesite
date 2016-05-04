package com.thinkgem.jeesite.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Test;

import com.thinkgem.jeesite.common.config.Global;

public class HdfsUtil {

	static Configuration conf = new Configuration();
	static FileSystem fs = HdfsUtil.init();

	public static FileSystem init() {
		try {
			System.setProperty("hadoop.home.dir",
					Global.getConfig("hadoop.home.dir"));
			fs = FileSystem.get(new URI(conf.get("fs.defaultFS")), conf,
					Global.getConfig("HADOOP_USER_NAME"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fs;
	}

	/**
	 * 上传文件，封装好的写法
	 * 
	 * @param savepath
	 *            这种形式 ： hdfs://namenode01:9000/test2/1.JPG
	 * @throws Exception
	 * @throws IOException
	 */
	public static void uploadfromLocalPath(String path, String savepath)
			throws Exception, IOException {
		fs.copyFromLocalFile(new Path(path), new Path(savepath));
	}

	/**
	 * 上传输入流
	 * 
	 * @param savePath
	 *            这种形式 ： hdfs://namenode01:9000/test2/1.JPG
	 * @throws Exception
	 * @throws IOException
	 */
	public static void uploadfromInputstream(InputStream is, String savePath)
			throws Exception, IOException {

		Path dst = new Path(savePath);

		FSDataOutputStream os = fs.create(dst);

		org.apache.hadoop.io.IOUtils.copyBytes(is, os, conf, true);
	}

	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	public static FSDataInputStream getFileInputstream(String path) throws Exception {
		return fs.open(new Path(path));
	}

	/**
	 * 查看文件信息
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 * 
	 */
	public void listFiles() throws FileNotFoundException,
			IllegalArgumentException, IOException {

		// listFiles列出的是文件信息，而且提供递归遍历
		RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"),
				true);

		while (files.hasNext()) {

			LocatedFileStatus file = files.next();
			Path filePath = file.getPath();
			String fileName = filePath.getName();
			System.out.println(fileName);

		}

		System.out.println("---------------------------------");

		// listStatus 可以列出文件和文件夹的信息，但是不提供自带的递归遍历
		FileStatus[] listStatus = fs.listStatus(new Path("/"));
		for (FileStatus status : listStatus) {

			String name = status.getPath().getName();
			System.out.println(name
					+ (status.isDirectory() ? " is dir" : " is file"));

		}

	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            这种形式：/aaa/bbb/ccc
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	public void mkdir(String path) throws IllegalArgumentException, Exception {
		fs.mkdirs(new Path(path));
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void rm(String path) throws IllegalArgumentException, IOException {
		fs.delete(new Path(path), true);
	}

}
