package dao;

import java.util.List;

import model.BookLog;
import model.BookOrder;

public interface BookLogDao {

	public void delete(BookLog element);
	public BookLog getbyKey(int id) ;
	public List<BookLog> findAll();
	public void save(BookLog element) ;
	public void update(BookOrder elementToAdd) ;
}
