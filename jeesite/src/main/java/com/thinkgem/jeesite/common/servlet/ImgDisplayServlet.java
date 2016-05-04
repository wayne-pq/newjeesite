/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.hdfs.client.HdfsDataInputStream;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.ipc.RPC;

import com.thinkgem.jeesite.common.utils.HdfsUtil;

/**
 * 图片显示
 * 
 * @author ThinkGem
 * @version 2015-12-29
 */
@SuppressWarnings("serial")
public class ImgDisplayServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		LoginServiceInterface proxy = RPC.getProxy(LoginServiceInterface.class,
				1L, new InetSocketAddress("namenode01", 10000),
				new Configuration());

		String path = req.getParameter("path");

		// PrintWriter out = res.getWriter();
		res.setContentType("image/jpeg");
		/* res.setDateHeader("Expires", date.getTime() + 1000 * 60 * 60 * 24); */

		OutputStream os = res.getOutputStream();

		/*
		 * try {
		 * org.apache.hadoop.io.IOUtils.copyBytes(HdfsUtil.getFileInputstream
		 * (path),os,4096); } catch (Exception e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		BytesWritable bytesWritable = proxy.getFileInputstream(path);

		org.apache.hadoop.io.IOUtils.copyBytes(new ByteArrayInputStream(
				bytesWritable.getBytes()), os, 4096);

		os.close();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
