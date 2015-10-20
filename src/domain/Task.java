package domain;

import java.util.Calendar;

public class Task
{
	private int taskid;
	private String filename;	//原始文件名
	private String filemd5;		//原始文件md5
	private int filesize;		//原始文件大小
	private Calendar startime;//开始工作时间
	private int stat;				//当前状态
	private Calendar finishtime;	//完成时间
	private Calendar downtime;		//下载时间
	private String finishmd5;		//完成文件md5
	private int finishsize;			//完成文件大小
	private String targetname;		//目标下载文件名
	private int errorcode;			//错误码，如果有错误
	private String filecode;		//每次任务为该文件生成一个随机码用于下载认证
	private User user;				//发起任务的用户 N-1双向
	
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int id) {
		this.taskid = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilemd5() {
		return filemd5;
	}
	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public Calendar getStartime() {
		return startime;
	}
	public void setStartime(Calendar startime) {
		this.startime = startime;
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	public Calendar getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Calendar finishtime) {
		this.finishtime = finishtime;
	}
	public String getFinishmd5() {
		return finishmd5;
	}
	public void setFinishmd5(String finishmd5) {
		this.finishmd5 = finishmd5;
	}
	public int getFinishsize() {
		return finishsize;
	}
	public void setFinishsize(int finishsize) {
		this.finishsize = finishsize;
	}
	public String getTargetname() {
		return targetname;
	}
	public void setTargetname(String targetname) {
		this.targetname = targetname;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getFilecode() {
		return filecode;
	}
	public void setFilecode(String filecode) {
		this.filecode = filecode;
	}
	public Calendar getDowntime() {
		return downtime;
	}
	public void setDowntime(Calendar downtime) {
		this.downtime = downtime;
	}
	
}