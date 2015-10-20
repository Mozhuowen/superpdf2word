package monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import javax.servlet.ServletContext;

import tools.LogUtil;
import tools.FileUtil;
import tools.SpringContextUtil;

import com.opensymphony.xwork2.ActionContext;

import dao.taskDao;
import dao.userDao;
import dao.Impl.taskDaoImpl;
import domain.Task;
import domain.User;

public class FinishedRunnable implements Runnable
{
	private String filecode;
	private String resultdir;
	private String downdir;
	ServletContext servletcontext;
	
	public FinishedRunnable(ServletContext s,String filecode) {
		this.filecode = filecode;
		this.servletcontext = s;
	}

	@Override
	public void run() {
		LogUtil.v(this, "begin to finish file: "+filecode);
		init();
		taskDao taskdao = (taskDao) SpringContextUtil.getBean("taskDao");
		Task task = taskdao.findByFilecode(filecode);
		if (task == null)
			return;
		File file = new File(resultdir+filecode+".docx");
		if (!file.exists()) {	//转换出错
			LogUtil.v(this, "file not exist! error!");
			task.setStat(6);   //转换出错
		} else  {	//将文件复制到下载目录并删除，然后持久化数据
			try{
				FileOutputStream fos = new FileOutputStream(downdir+filecode+".docx");
				FileInputStream fis = new FileInputStream(resultdir+filecode+".docx");
				byte[] buffer = new byte[8*1024];
				int hasRead = 0;
				while((hasRead = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, hasRead);
				}
				fos.close();
				fis.close();
				
				task.setFinishmd5(FileUtil.getMd5ByFile(downdir+filecode+".docx"));
				task.setFinishtime(Calendar.getInstance());
				task.setFinishsize((int)file.length());
				task.setStat(2);   	//转换结束,等待下载
				task.setTargetname(filecode+".docx");
			} catch(Exception e) {
				LogUtil.e(this, e.toString());
				return;
			}			
			
		}
		
		taskdao.update(task);
		//删除
		file.delete();
		taskdao = null;
	}
	
	private void init(){
		resultdir = this.servletcontext.getInitParameter("resultdir");
		downdir = this.servletcontext.getInitParameter("downdir");
	}
}