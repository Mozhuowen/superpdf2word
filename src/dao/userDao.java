package dao;

import domain.User;

public interface userDao
{
	User get(Integer id);
	Integer save(User u);
	void update(User u);
	User findByServiceid(String serviceid);
}