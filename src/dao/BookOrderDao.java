package dao;

import java.util.List;

import model.BookOrder;

public interface BookOrderDao {

	public void delete(BookOrder element) ;
	public BookOrder getbyKey(int id) ;
	public List<BookOrder> findAll() ;
	public void save(BookOrder element);
	public void update(BookOrder element);
	public int getLastBookLoan();
}
