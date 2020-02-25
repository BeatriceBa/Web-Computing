package model;

import java.util.LinkedList;
import java.util.List;

public class BookOrder {

	private int bookOrder_id;
	private int user_id ; 
	List<BookLoan> loans = new LinkedList<BookLoan>();

	public BookOrder(List<BookLoan> loans, int user_id) {
		this.loans = loans;
		this.user_id = user_id; 
	}
	
	public BookOrder(int id, List<BookLoan> loans, int user_id) {
		this.bookOrder_id = id;
		this.loans = loans;
		this.user_id = user_id; 
	}

	public List<BookLoan> getLoans() {
		return loans;
	}
	public void setLoans(List<BookLoan> loans) {
		this.loans = loans;
	}

	public boolean removeLoan(BookLoan loan) {
		return loans.remove(loan);
	}

	@Override
	public String toString() {
		return "BookOrder [loans=" + loans + "]";
	}

	public int getBookOrder_id() {
		return bookOrder_id;
	}

	public void setBookOrder_id(int bookOrder_id) {
		this.bookOrder_id = bookOrder_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
