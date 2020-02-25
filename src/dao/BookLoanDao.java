package dao;

import java.util.List;

import javafx.util.Pair;
import model.BookLoan;
import model.User;

public interface BookLoanDao {

	public void delete(BookLoan book_loan);
	public List<BookLoan> findAll();
	public void save(BookLoan book_loan);
	public void update(BookLoan book_loan);
	public BookLoan getbyKey(int id);
	public List<BookLoan> findCurrentByUser(int id) ;
	public float getTotalArrearsbyUser(User u);
	public int getLastBookLoan() ;
	public void returnBook(int id);
	public void payArrear(int id) ;
	public void extend(int id);
	public void onlineArrearExceeded(int id);
	float calculateDays(java.util.Date begin, java.util.Date end) ;
	Double adjustArrear(java.util.Date restitutionDate);
	public void onlineArrearPayed(int id);
	public List<Pair<String,Integer>> getNumberLoan();
}
