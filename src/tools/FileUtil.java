package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class FileUtil
{
	public static String getMd5ByFile(String filepath) throws IOException {  
		  FileInputStream fis= new FileInputStream(filepath);    
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));    
        IOUtils.closeQuietly(fis); 
        return md5;
    }  
	public static String getfileMD5(File file) throws IOException {
		FileInputStream fis= new FileInputStream(file);    
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));    
        IOUtils.closeQuietly(fis); 
        return md5.toUpperCase();
	}
	
	public static boolean checkPDF(File file) {
		byte[] buff = new byte[10];
		String str = null;
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);				
			fis.read(buff);		
			str = new String(buff).toUpperCase();	
			fis.close();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		if (str.contains("PDF"))
			return true;
		else
			return false;
	}
}