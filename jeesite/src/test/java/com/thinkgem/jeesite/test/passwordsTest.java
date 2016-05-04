package com.thinkgem.jeesite.test;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

public class passwordsTest {

	public static void main(String[] args) {
		
	}
	
	@org.junit.Test
	public void passwordTest(){
		//密码校验
		String passwords = "566b3da26708cebf3198702f53542d6b65186be46098b10f01dfea0a";
		byte[] salt = Encodes.decodeHex(passwords.substring(0,16));
		String hashedCredentials = passwords.substring(16);
		
		SimpleHash hash = new SimpleHash("SHA-1", "admin", ByteSource.Util.bytes(salt), 1024);
		boolean b = hash.toString().equals(hashedCredentials);
		
		//明文加密
		String pa = SystemService.entryptPassword("admin");
		
		System.out.println();
	}
}
