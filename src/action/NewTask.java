package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import resmodel.resnewtask;
import service.NewTaskService;
import tools.LogUtil;
import tools.FileUtil;




import tools.NetErrorUtil;
import tools.encrypt.AesException;
import tools.encrypt.MsgCrypt;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
//import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class NewTask extends ActionSupport
{
	private NewTaskService task;
	private String serviceid;
	private int servicetype;
	private String ip;
	private String sv;
	private String phonemodel;
	private String brand;
	private String filemd5;
	private String auth;
	private long timestamp;
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private String savePath;	
	private HttpServletRequest request;
	private int version;
	
	public String jsonstr;

	@Override
	public String execute() throws Exception {
		request = ServletActionContext.getRequest();
		resnewtask res = new resnewtask();
		this.ip = this.getIpAddr();
		//check param
		if (!this.checkParam()) {
			res.setStat(false);
			res.setErrcode(NetErrorUtil.PARAM_ERROR);
			this.jsonstr = new Gson().toJson(res);
			return SUCCESS;
		}
		//check minversion
		if ( !checkMinVersion()) {
			res.setStat(false);
			res.setErrcode(NetErrorUtil.VERSION_NOTALLOW);
			this.jsonstr = new Gson().toJson(res);
			return SUCCESS;
		}
		//check file md5
		if (!this.checkfilemd5()) {
			res.setStat(false);
			res.setErrcode(NetErrorUtil.MD5_ERROR);
			this.jsonstr = new Gson().toJson(res);
			return SUCCESS;
		}	
		//check if the file is a validata pdf file
		if (!FileUtil.checkPDF(this.getUpload())) {
			res.setStat(false);
			res.setErrcode(NetErrorUtil.NOT_PDF);
			this.jsonstr = new Gson().toJson(res);
			return SUCCESS;		
		}
		
		String filesavedpath = getSavePath()+"/"+getUploadFileName();
		LogUtil.v(this, "save path: "+filesavedpath);
		
		FileOutputStream fos = new FileOutputStream(filesavedpath);
		FileInputStream fis = new FileInputStream(this.getUpload());
		byte[] buffer = new byte[8*1024];
		int len = 0;
		while((len = fis.read(buffer)) > 0) {
			fos.write(buffer,0,len);
		}
		if (fos != null )
			fos.close();
		if (fis != null )
			fis.close();
		
		//file upload finished,start to createTask
		String filecode = task.buildTask(getUploadFileName().substring(0, getUploadFileName().length()-4), filesavedpath, serviceid
				,servicetype,ip,sv,phonemodel,brand);
		if (filecode == null)
			res.setStat(false);
		else {
			res.setStat(true);
			res.setFilecode(filecode);
		}

		LogUtil.v(this, "file name: "+upload.getName());
		LogUtil.v(this, "file path: "+upload.getPath());
		LogUtil.v(this, "upload filename: "+this.getUploadFileName());
		LogUtil.v(this, "upload filecontenttype: "+this.getUploadContentType());
		
		this.jsonstr = new Gson().toJson(res);		
		LogUtil.v(this, "response str: "+jsonstr);
		return SUCCESS;
	}
	
	//过滤不合法请求
	private boolean checkParam() {
		if (this.filemd5 == null || this.auth == null || this.serviceid ==null || this.version==0)
			return false;
		//检测加密是否正确
		String str = String.valueOf(timestamp) + this.serviceid + this.filemd5 + this.version;
		String sinature = null;
		try {
			sinature = MsgCrypt.encryptMsg(str);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if (sinature.equals(this.getAuth()))
			return true;
		else
			return false;
	}
	//检测最低服务版本
	private boolean checkMinVersion(){
		ActionContext ctx = ActionContext.getContext();
		String minversion = (String)ctx.getApplication().get("minversion");
		if (this.version < Integer.parseInt(minversion))
			return false;
		else
			return true;
	}
	//检查文件md5
	private boolean checkfilemd5(){
		if (this.getFilemd5() == null )
			return false;
		String filemd5 = null;
		try {
			filemd5 = FileUtil.getfileMD5(getUpload());
		} catch (IOException e) {
			LogUtil.e(this, e.toString());
			return false;
		}
		if (filemd5.equals(this.getFilemd5()))
			return true;
		else
			return false;
	}
	public String getIpAddr() {     
	      String ip = request.getHeader("x-forwarded-for");     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("Proxy-Client-IP");     
	     }     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("WL-Proxy-Client-IP");     
	      }     
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	          ip = request.getRemoteAddr();     
	     }     
	     //如果有多级代理获取原始IP
	     if(ip.contains(",")){
	    	 ip = ip.split(",")[0].trim();
	     }
	     return ip;     
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath("/WEB-INF/"+savePath);
	}


	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}


	public NewTaskService getTask() {
		return task;
	}


	public void setTask(NewTaskService task) {
		this.task = task;
	}


	public String getServiceid() {
		return serviceid;
	}


	public void setServiceid(String seviceid) {
		this.serviceid = seviceid;
	}


	public String getFilemd5() {
		return filemd5;
	}


	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}


	public String getAuth() {
		return auth;
	}


	public void setAuth(String auth) {
		this.auth = auth;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSv() {
		return sv;
	}

	public void setSv(String sv) {
		this.sv = sv;
	}

	public String getPhonemodel() {
		return phonemodel;
	}

	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getServicetype() {
		return servicetype;
	}

	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}