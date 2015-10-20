package service.Impl;

import java.util.Calendar;

import dao.taskDao;
import domain.Task;
import service.TaskGeneral;

public class TaskGeneralImpl implements TaskGeneral
{
	private taskDao task;

	@Override
	public String getDownFileName(String filecode) {
		// TODO Auto-generated method stub
		Task t = task.findByFilecode(filecode);
		String str = t.getFilename();
		return str;
	}

	@Override
	public void updateFileStat(String filecode,int stat) {
		// TODO Auto-generated method stub
		Task t = task.findByFilecode(filecode);
		t.setStat(stat);
		t.setDowntime(Calendar.getInstance());
		task.update(t);
	}

	public taskDao getTask() {
		return task;
	}

	public void setTask(taskDao task) {
		this.task = task;
	}

	@Override
	public int getFileStat(String filecode) {
		Task t = task.findByFilecode(filecode);		
		if (t != null )
			return t.getStat();
		else 
			return -1;
	}
	
}