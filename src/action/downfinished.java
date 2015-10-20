package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import service.TaskGeneral;
import tools.LogUtil;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class downfinished implements Action
{
	private TaskGeneral taskgeneral;
	public String downfilename;
	String sourcefilename;
	public String filecode;
	public String downdir;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		downdir = (String)ctx.getApplication().get("downdir");
		this.sourcefilename = downdir + filecode + ".docx";
		File file = new File(sourcefilename);
		if (!file.exists())
			return ERROR;
		
		downfilename = taskgeneral.getDownFileName(filecode)+".docx";
		downfilename = java.net.URLEncoder.encode(downfilename, "UTF-8");
		taskgeneral.updateFileStat(filecode, 4);	//已经下载
		LogUtil.v(this, "begin to download: "+filecode);
		return SUCCESS;
	}
	
	// 文件下载
    public InputStream getDownloadFile() throws FileNotFoundException,
            UnsupportedEncodingException {
//        System.out.println(getFileName());

        // 如果下载文件名为中文，进行字符编码转换
//        ServletActionContext.getResponse().setHeader("Content-Disposition","attachment;fileName="
//                        + java.net.URLEncoder.encode(downfilename, "UTF-8"));
        InputStream inputStream = new FileInputStream(sourcefilename);

        return inputStream;
    }


	public String getFilecode() {
		return filecode;
	}

	public void setFilecode(String filecode) {
		this.filecode = filecode;
	}

	public TaskGeneral getTaskgeneral() {
		return taskgeneral;
	}

	public void setTaskgeneral(TaskGeneral taskgeneral) {
		this.taskgeneral = taskgeneral;
	}
	
}