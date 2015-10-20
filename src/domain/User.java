package domain;

import java.util.Calendar;
import java.util.Set;

public class User
{
	private int userid;
	private String serviceid;
	private int servicetype;	//0-android 1-iphone
	private Calendar registime;
	private String registip;
	private String lastip;
	private Calendar logintime;
	private String sv;	//software version
	private String phonemodel;
	private String brand;
	private Set<Task> tasks;	//1-N双向
	public int getUserid() {
		return userid;
	}
	public void setUserid(int id) {
		this.userid = id;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public int getServicetype() {
		return servicetype;
	}
	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}
	public Calendar getRegistime() {
		return registime;
	}
	public void setRegistime(Calendar registime) {
		this.registime = registime;
	}
	public String getRegistip() {
		return registip;
	}
	public void setRegistip(String registip) {
		this.registip = registip;
	}
	public String getLastip() {
		return lastip;
	}
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	public Calendar getLogintime() {
		return logintime;
	}
	public void setLogintime(Calendar logintime) {
		this.logintime = logintime;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
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
	
}