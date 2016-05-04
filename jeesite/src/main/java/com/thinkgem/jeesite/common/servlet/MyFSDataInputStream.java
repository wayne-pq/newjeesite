package com.thinkgem.jeesite.common.servlet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.hdfs.client.HdfsDataInputStream;
import org.apache.hadoop.io.Writable;

public class MyFSDataInputStream implements Writable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	
	FSDataInputStream stream;
	
	
	
	
	public FSDataInputStream getStream() {
		return stream;
	}




	public void setStream(FSDataInputStream stream) {
		this.stream = stream;
	}




	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		
	}






}
