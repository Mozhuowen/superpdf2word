package action;

import resmodel.ResCheckStat;
import service.TaskGeneral;
import tools.LogUtil;
import tools.NetErrorUtil;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;

public class CheckStat implements Action
{
	private TaskGeneral task;
	private String filecode;
	private String auth;
	
	public String jsonstr;

	@Override
	public String execute() throws Exception {
		LogUtil.v(this, "CheckStat get request info: "+filecode);
		
		if (filecode == null || filecode.length() == 0) {
			jsonstr = new Gson().toJson(new ResCheckStat(false,NetErrorUtil.PARAM_ERROR,0));
			return SUCCESS;
		}
		
		int stat = task.getFileStat(filecode);
		if (stat >0)
			jsonstr = new Gson().toJson(new ResCheckStat(true,0,stat));
		else
			jsonstr = new Gson().toJson(new ResCheckStat(false,NetErrorUtil.FILE_NOT_EXIST,0));
		return SUCCESS;
	}

	public String getFilecode() {
		return filecode;
	}

	public void setFilecode(String filecode) {
		this.filecode = filecode;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getJsonstr() {
		return jsonstr;
	}

	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}

	public TaskGeneral getTask() {
		return task;
	}

	public void setTask(TaskGeneral task) {
		this.task = task;
	}
	
}