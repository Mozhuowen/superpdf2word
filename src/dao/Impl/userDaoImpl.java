package dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.userDao;
import domain.User;

public class userDaoImpl extends HibernateDaoSupport implements userDao
{

	@Override
	public User get(Integer id) {
		return this.getHibernateTemplate().get(User.class, id);
	}

	@Override
	public Integer save(User u) {
		return (Integer)this.getHibernateTemplate().save(u);
	}

	@Override
	public void update(User u) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(u);
	}

	@Override
	public User findByServiceid(String serviceid) {
		// TODO Auto-generated method stub
		List list = this.getHibernateTemplate().find("from User u where u.serviceid=?", serviceid);
		if (list.size() > 0)
			return (User)list.get(0);
		else
			return null;
	}
	
}