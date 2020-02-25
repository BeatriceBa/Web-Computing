package model;

import java.util.LinkedList;
import java.util.List;

public class User {


	private int id;
	private String name;
	private String surname;
	private String address;
	private String email;
	private String password;
	//Se studente, docente, addetto
	private UserType user_type;
	//Numero di libri in prestito
	private int book_count;
	private Cart cart;
	private float total_arrears;

	public User(int id, String name, String surname, String address, String email, String password, UserType user_type,
			 float total_arrears, int book_count) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.password = password;
		this.user_type = user_type;
		this.book_count = book_count;
		this.total_arrears = total_arrears;
		this.cart = new Cart();
	}

	public float getTotal_arrears() {
		return total_arrears;
	}

	public void setTotal_arrears(float total_arrears) {
		this.total_arrears = total_arrears;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserType getUser_type() {
		return user_type;
	}
	public void setUser_type(UserType user_type) {
		this.user_type = user_type;
	}
	public int getBook_count() {
		return book_count;
	}
	public void setBook_count(int book_count) {
		this.book_count = book_count;
	}
	public List<Book> getCart() {
		return this.cart.getBooks();
	}

	public void setCart(List<Book> cart) {
		this.cart.setBooks(cart);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", address=" + address + ", email="
				+ email + ", password=" + password + ", user_type=" + user_type + ", book_count=" + book_count
				+ "]";
	}

}
