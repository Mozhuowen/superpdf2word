package service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.opensymphony.xwork2.ActionContext;

import service.NewTaskService; 
import tools.CharacterUtil;
import tools.FileUtil;
import tools.LogUtil;
import dao.taskDao;
import dao.userDao;
import domain.Task;
import domain.User;

public class NewTaskServiceImpl implements NewTaskService
{
	private userDao user;
	private taskDao task;

	@Override
	public String buildTask(String filename, String sourcepath, String serviceid,int servicetype,String ip,String sv,String phonemodel,String brand) {
		LogUtil.v(this, "filename: "+filename+" sourcepath: "+sourcepath+" serviceid: "+serviceid);
		File file = new File(sourcepath);
		int filesize = (int)file.length();
		String filecode = CharacterUtil.getRandomString(16);
		String filemd5 = null;
		try {
			filemd5 = FileUtil.getMd5ByFile(sourcepath);
		} catch (IOException e) {
			LogUtil.e(this, e.toString());
			return null;
		}
		//copy file to work dir
		ActionContext ctx = ActionContext.getContext();
		String workdir = (String)ctx.getApplication().get("workdir");
		try{
			FileOutputStream fos = new FileOutputStream(workdir+filecode+".pdf");
			FileInputStream fis = new FileInputStream(sourcepath);
			byte[] buffer = new byte[8*1024];
			int hasRead = 0;
			while((hasRead = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, hasRead);
			}
			fos.close();
			fis.close();
		} catch(Exception e) {
			LogUtil.e(this, e.toString());
			return null;
		}
		//persis
		User u = user.findByServiceid(serviceid);
		if (u == null ) {
			u = new User();
			u.setServiceid(serviceid);
			u.setServicetype(0);
			u.setRegistip(ip);
			u.setRegistime(Calendar.getInstance());
			u.setSv(sv);
			u.setPhonemodel(phonemodel);
			u.setBrand(brand);
			user.save(u);
		}
		u.setLastip(ip);
		Task newtask = new Task();
		newtask.setFilecode(filecode);
		newtask.setFilename(filename);
		newtask.setFilemd5(filemd5);
		newtask.setFilesize(filesize);
		newtask.setStartime(Calendar.getInstance());
		newtask.setStat(1);
		newtask.setUser(u);
		task.save(newtask);
		
		return filecode;
	}

	public userDao getUser() {
		return user;
	}

	public void setUser(userDao user) {
		this.user = user;
	}

	public taskDao getTask() {
		return task;
	}

	public void setTask(taskDao task) {
		this.task = task;
	}
	
}