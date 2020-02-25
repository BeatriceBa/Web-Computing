package model;

import java.util.LinkedList;
import java.util.List;

public class Cart {
	List<Book> books;
	
	public Cart() {
		this.books = new LinkedList<Book>();
	}
	
	public void addBook(Book b) {
		this.books.add(b);
	}
	
	public void removeBook(Book b) {
		this.books.remove(b);
	}
	
	public void deleteAllBooks() {
		this.books.clear();
	}
	
	public boolean isEmpty() {
		if (this.books.size() == 0) {
			return true;
		}
		return false;
	}
	
	public List<Book> getBooks() {
		return this.books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	@Override
	public String toString() {
		return "Cart [books=" + books + "]";
	}
	
}