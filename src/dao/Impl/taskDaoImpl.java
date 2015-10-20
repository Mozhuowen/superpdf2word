package dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.taskDao;
import domain.Task;

public class taskDaoImpl extends HibernateDaoSupport implements taskDao
{

	@Override
	public Task get(Integer id) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Task.class, id);
	}

	@Override
	public Integer save(Task task) {
		// TODO Auto-generated method stub
		return (Integer)this.getHibernateTemplate().save(task);
	}

	@Override
	public void update(Task task) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(task);
	}

	@Override
	public Task findByFilecode(String filecode) {
		List list = this.getHibernateTemplate().find("from Task t where t.filecode=?", filecode);
		if (list.size() > 0)
			return (Task)list.get(0);
		else
			return null;
	}
	
}