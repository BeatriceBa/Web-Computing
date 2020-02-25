package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.BookDescription;

public interface BookDescriptionDao {

	public void delete(BookDescription bookDescription);
	public List<BookDescription> findAll();
	public void save(BookDescription bookDescription) ;
	public boolean update(BookDescription bookDescription);
	public List<String> getTitles(String query);
	public List<String> getAllTitles();
	public LinkedList<BookDescription> getbyTitle(String title);
	public BookDescription getbyKey(String id);
	public HashMap<String,Boolean> isThereABook();
}
