package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BookLoan {

	private int id;
	private Date loan_date;
	private Date return_date;
	private boolean extendable;
	private float arrears;
	private Book book ; 
	private boolean returned;
	private int user_id; 
	private boolean arrear_payed;

	public BookLoan(Date loan_date, Date return_date, boolean extendable, float arrears,Book book,boolean returned, int user_id) {
		super();
		this.loan_date = loan_date;
		this.return_date = return_date;
		this.extendable = extendable;
		this.arrears = arrears;
		this.book= book; 
		this.returned = returned; 
		this.user_id= user_id; 
		this.arrear_payed = false;

	}
	
	public BookLoan(int id, Date loan_date, Date return_date, boolean extendable, float arrears,Book book,boolean returned, int user_id) {
		super();
		this.id = id;
		this.loan_date = loan_date;
		this.return_date = return_date;
		this.extendable = extendable;
		this.arrears = arrears;
		this.book= book; 
		this.returned = returned; 
		this.user_id= user_id; 
		this.arrear_payed = false;
	}
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLoan_date() {
		return loan_date;
	}
	public void setLoan_date(Date loan_date) {
		this.loan_date = loan_date;
	}
	public Date getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}
	public boolean isExtendable() {
		return extendable;
	}
	public void setExtendable(boolean extendable) {
		this.extendable = extendable;
	}
	public float getArrears() {
		return arrears;
	}
	public void setArrears(float arrears) {
		this.arrears = arrears;
	}

	@Override
	public String toString() {
		return  "BookLoan [id=" + id + ", loan_date=" + loan_date + ", return_date=" + return_date + ", extendable="
				+ extendable + ", arrears=" + arrears + ", Book= " + book+ " , Returned=" + returned + ", user_id= "+ user_id + "]"; 
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public boolean isArrear_payed() {
		return arrear_payed;
	}

	public void setArrear_payed(boolean arrear_payed) {
		this.arrear_payed = arrear_payed;
	}


}
