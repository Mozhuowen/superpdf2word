package monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import tools.LogUtil;

import com.opensymphony.xwork2.ActionContext;

public class StatMonitorRunnable implements Runnable
{
	private List<String> workingtask = new ArrayList<String>();
	private String workdir;
	private boolean isInit = false;
	ServletContext servletcontext;
	
	public StatMonitorRunnable(ServletContext s){
		this.servletcontext = s;
	}

	@Override
	public void run() {
		if (!isInit)
			init();
		
		while(true) {
			go();
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				LogUtil.e(this, e.toString());
				break;
			}
		}
	}
	
	private void init() {
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		workdir = this.servletcontext.getInitParameter("workdir");
		LogUtil.v(this, "workdir is "+workdir);
		isInit = true;
	}
	
	private void go() {
		if (workdir == null ){
			LogUtil.v(this, "workdir is not set yet!");
			return;
		}
		
		File file = new File(workdir);
//		LogUtil.v(this, "file list: "+ file);
		String[] workfile = file.list();
		addNew(workfile);
		removeFinished(workfile);
		
	}
	
	private void removeFinished(String[] filelist) {
		Iterator<String> it = workingtask.iterator();
		while(it.hasNext()) {
			String name = (String)it.next();
			boolean flag = false;
			for (String str:filelist) {
				if (name.equals(str)) {
					flag = true;
					break;
				}
			}
			if (!flag) {		//有任务状态改变，先删除队列中的任务，然后再启动完成任务线程
				LogUtil.v(this, "to be remove file: "+name);
				it.remove();
				//启动完成任务线程
				new Thread(new FinishedRunnable(this.servletcontext,name.substring(0, name.length()-4))).start();
			}
		}
	}
	
	private void addNew(String[] filelist) {
		int worktasksize = workingtask.size();
		if (worktasksize == 0)
		{
			if (filelist.length > 0)
				LogUtil.v(this, "worktasksize is 0,begin to add files ");
			for (String str:filelist)
				workingtask.add(str);
		} else {
			for (int i=0;i<filelist.length;i++) {
				String name = filelist[i];
				boolean flag = false;
				for (int j=0;j<worktasksize;j++) {
					String str = workingtask.get(j);
					if (str.equals(name)) {
						
						flag = true;
						break;
					}
				}
				
				if (!flag) {
					LogUtil.v(this, "to be add file: "+name);
					workingtask.add(name);
				}
		}}
	}
	
}