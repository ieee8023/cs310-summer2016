package com.sshtools.daemon.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CS310AuthenticationProvider extends NativeAuthenticationProvider {

	Log log = LogFactory.getLog(CS310AuthenticationProvider.class);
	
	public CS310AuthenticationProvider() {
		log.error("DummyAuthenticationProvider is in use. This is only for testing.");
	}
	
	@Override
	public boolean changePassword(String username, String oldpassword,
			String newpassword) {
		return false;
	}

	@Override
	public String getHomeDirectory(String username) throws IOException {
		return "/home/" + username;
	}

	@Override
	public void logoffUser() throws IOException {
		
	}

	@Override
	public boolean logonUser(String username, String password)
			throws PasswordChangeException, IOException {
		System.out.println(username + "-" + password);
		
		return Arrays.equals(getSha(password),getSha(file2String("secret")));				
	}

	@Override
	public boolean logonUser(String username) throws IOException {
		System.out.println(username);
		return false;
	}
	
	public static String file2String(String filename){
		
		try {
			File file = new File(filename);
		    FileInputStream fis = new FileInputStream(file);
		    byte[] data = new byte[(int)file.length()];
		    fis.read(data);
		    fis.close();
		    return new String(data);
		} catch (Exception e) {
			return new String("File read error");
		}
		
	}
	
	
	public static byte[] getSha(String str){
		
		try{
			// part of java that gives us sha1
			MessageDigest m = MessageDigest.getInstance("SHA-1");
			
			// get the sha1
			byte[] sha1 = m.digest(str.getBytes("UTF-8"));
			
			// reduce complexity to only three bytes
			byte[] sha = new byte[]{sha1[0], sha1[1], sha1[2]};
			
			return sha;
			
		}catch(Exception e){
			return new byte[]{};
		}
	
	}

}
