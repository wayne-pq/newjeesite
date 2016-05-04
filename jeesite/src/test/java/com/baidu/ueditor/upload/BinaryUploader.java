package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.HdfsUtil;

public class BinaryUploader {
	
	
	
	
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("imageUrl");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			/*long maxSize = ((Long) conf.get("maxSize")).longValue();*/

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);
/*
			String physicalPath = (String) conf.get("rootPath") + savePath;*/
			/*InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);*/
			
			
			InputStream is = fileStream.openStream();
			
			savePath = Global.getConfig("Hadoop.url") + savePath;
			
			
			
			
			
			
//			HadoopService hadoopService = (HadoopService) wac.getBean(HadoopService.class);
			
			//hadoopService.add(is, savePath);
			
			
			
			try {
				HdfsUtil.uploadfromInputstream(is,savePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*hadoopService.add(is, savePath);*/
		/*	// 实例化一个Jersey
			Client client = new Client();
			// 另一台服务器的请求地址
			String url = "http://127.0.0.1:8008/imgserver/upload/"+UUID.randomUUID()+suffix;
			// 设置请求路径
						WebResource resource = client.resource(url);
			// 本地路径
			byte[] file = null;
			try {
				 ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
			        byte[] buff = new byte[100];  
			        int rc = 0;  
			        while ((rc = is.read(buff, 0, 100)) > 0) {  
			            swapStream.write(buff, 0, rc);  
			        }  
			        file = swapStream.toByteArray();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 发送开始

			resource.put(String.class, file);
			
			System.out.println("发送成功！");
			*/
			
			
			
			
			StorageManager manager = new StorageManager();
			State storageState = new BaseState();
			//is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url", savePath);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
	
	
	

}
