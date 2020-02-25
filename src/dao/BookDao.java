package dao;


import java.util.List;

import model.Book;

public interface BookDao {

	public void delete(Book book);
	public List<Book> findAll() ;
	public void save(Book book) ;
	public void update(Book book);
	public Book getbyKey(int id); 

}
