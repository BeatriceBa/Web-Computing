package model;

public class Book {
	
	private int id;
	private BookDescription bookDescription;
	private boolean available;
	
	
	public Book(BookDescription bookDescription, boolean available) {
		super();
		this.bookDescription = bookDescription;
		this.available = available;
	}
	public Book(int id, BookDescription bookDescription, boolean available) {
		super();
		this.id = id;
		this.bookDescription = bookDescription;
		this.available = available;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BookDescription getBookDescription() {
		return bookDescription;
	}
	public void setBookDescription(BookDescription bookDescription) {
		this.bookDescription = bookDescription;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookDescription=" + bookDescription + ", available=" + available + "]";
	}

}
