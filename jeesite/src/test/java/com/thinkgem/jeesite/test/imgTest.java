package com.thinkgem.jeesite.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.thinkgem.jeesite.common.utils.StringUtils;

public class imgTest {
	@Test
	public void test1(){
		String s = StringUtils.replaceLast("http://dsadsaad//dasdas", "//", "/");
		System.out.println();
	}
	@Test
	public void img(){
		String str = "<div class=\"box thumbnail\" id=\"\"> <img id=\"pictrue\" alt=\"\" src=\"http://127.0.0.1:8008/imgserver/upload/51931386-d62e-43b4-8dfb-09f93d150aba.png\"> </div><div class=\"box thumbnail\" id=\"\"> <img id=\"pictrue\" alt=\"\" src=\"http://127.0.0.1:8008/imgserver/upload/bd5ab6af-a61c-48b6-a2c6-0bbfc8bb271e.jpg\"> </div>";
		StringBuffer content = new StringBuffer();
		String[] num = str.split("<img");
		
		
		for(int i=0;i<num.length;i++){
			if(i==0){
				content.append(num[0]);
				continue;
			}
			
			num[i] = "<img" + num[i]+"    ";
			
			
			
			int first = num[i].indexOf(">");
			
			
			content.append(num[i].substring(first+1,num[i].length()-1));
			num[i] = num[i].substring(0,first+1);
			
			
		}
		List<String> newNum = new ArrayList<String>();
		for(int i=1 ; i<num.length ; i++){
			newNum.add(num[i]);
		}
		
		System.out.println();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("newNum", newNum);
		map.put("content", content);
	}
	
	public Map img(String str){
		StringBuffer content = new StringBuffer();
		String[] num = str.split("<img");
		
		
		for(int i=0;i<num.length;i++){
			if(i==0){
				content.append(num[0]);
				continue;
			}
			
			num[i] = "<img" + num[i]+"    ";
			
			
			
			int first = num[i].indexOf("/>");
			
			
			content.append(num[i].substring(first+2,num[i].length()-1));
			num[i] = num[i].substring(0,first+2);
			
			
		}
		List<String> newNum = new ArrayList<String>();
		for(int i=1 ; i<num.length ; i++){
			newNum.add(num[i+1]);
		}
		
		System.out.println();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("newNum", newNum);
		map.put("content", content);
		return map;
	}
}
