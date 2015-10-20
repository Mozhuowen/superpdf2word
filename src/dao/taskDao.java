package dao;

import domain.Task;

public interface taskDao
{
	Task get(Integer id);
	Integer save(Task task);
	void update(Task task);
	Task findByFilecode(String filecode);
}