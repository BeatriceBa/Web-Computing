package model;

import java.util.LinkedList;
import java.util.List;

public class BookLog {

	private int book_id;
	List<BookOrder> orders = new LinkedList<BookOrder>();

	public  BookLog(int id, List<BookOrder> orders) {
		this.book_id = id;
		this.orders = orders;
	}

	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public List<BookOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<BookOrder> orders) {
		this.orders = orders;
	}

	public boolean removeLoan(BookOrder order) {
		return orders.remove(order);
	}

	@Override
	public String toString() {
		return "BookLog [orders=" + orders + "]";
	}


}
