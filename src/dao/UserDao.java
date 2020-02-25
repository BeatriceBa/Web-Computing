package dao;

import java.util.List;

import model.User;

public interface UserDao {

	public void delete(User user);
	public List<User> findAll();
	public void save(User user);
	public void update(User user) ;
	public boolean isMoroso(User user);
	public User getbyKey(int id);
	public int getBookCount(User user);
	public List<User> findAllNoAdmin();
}
