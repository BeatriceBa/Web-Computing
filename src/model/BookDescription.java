package model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BookDescription {

	private String author;
	private String title;
	private String ISBN;
	private String publishingHouse;
	private Date year;
	private Category category;
	private String description;
	private String imageUrl;

	public BookDescription(String iSBN, String author, String title, String publishingHouse, Date year,
			Category category, String description, String imageUrl) {
		super();
		this.author = author;
		this.title = title;
		this.ISBN = iSBN;
		this.publishingHouse = publishingHouse;
		this.year = year;
		this.category = category;
		this.description = description;
		this.imageUrl = imageUrl;
	}
public BookDescription() {
	SimpleDateFormat format = new SimpleDateFormat("yyyy");
	
	this.author = "Non disponibile";
	this.title = "Non disponibile";
	this.ISBN = "Non disponibile";
	this.publishingHouse = "Non disponibile";
	try {
		this.year = format.parse("1900");
	} catch (ParseException e1) {
		e1.printStackTrace();
	}
	this.category = new Category ("Categoria non disponibile");
	this.description = "Non disponibile";
	this.imageUrl = "/noimage.jpg";
	
}
	
	public ArrayList<BookDescription> wantToAdd(String term){
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		boolean isbn_search = false;
		URL url;
		System.out.println(term);
		if(term.matches("-?\\d+(\\.\\d+)?")) {
			isbn_search = true;
		}
		ArrayList<BookDescription> cose = new ArrayList<BookDescription>(); 
		try {
			if(isbn_search) {
			url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + term + "&key=AIzaSyDGBd3sgZ2CZGzgzeI_gDhBJPQRdMGY1vU");
			} else {
				
				url = new URL ("https://www.googleapis.com/books/v1/volumes?q=intitle:" + URLEncoder.encode(term) + "&key=AIzaSyDGBd3sgZ2CZGzgzeI_gDhBJPQRdMGY1vU");
			}
			String response = new String();
			URLConnection yc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	        String inputLine;
	        JSONObject firstItem = null;
	        int etag = 0; 
	        while ((inputLine = in.readLine()) != null) {
	        	response += inputLine;
	        	if (inputLine.contains("etag"))
	        	{
	        		etag++; 
	        	}
	        }
	        JSONObject obj = new JSONObject(response);
	        for (int i=0; i<etag; i++ )
	        {	
	        	BookDescription book = new BookDescription(); 
	        	if(obj.has("items")) {
	        		firstItem = obj.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo");
	        		//System.out.println(firstItem);
		        }
	        	if (firstItem.has("industryIdentifiers")) {
	        		
	        		boolean isbn_13 =false; 
	        		JSONArray id = firstItem.getJSONArray("industryIdentifiers"); 
	        		for (int j=0; j<id.length(); j++) {
	        			String type = id.getJSONObject(j).getString("type"); 
	        			if (type.equals("ISBN_13")) {
	        				isbn_13= true; 
	        				book.setISBN(firstItem.getJSONArray("industryIdentifiers").getJSONObject(j).getString("identifier"));
	        				break; 
	        			}
	        		}
	        		if (!isbn_13) {
	        			book.setISBN(firstItem.getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier"));
	        		}
	        	}
	        	if(firstItem.has("title")) {
	    	        book.setTitle(firstItem.getString("title"));
    	        }
    	        if(firstItem.has("authors")) {
    	        	book.setAuthor(firstItem.getJSONArray("authors").getString(0));;
    	        }
    	        if(firstItem.has("publisher")) {
    	        	book.setPublishingHouse(firstItem.getString("publisher"));
    	        }
    	        if(firstItem.has("publishedDate")) {
    	        	book.setYear(format.parse(firstItem.getString("publishedDate")));
    	        }
    	        if(firstItem.has("categories")) {
    	        	//System.out.println(firstItem.getJSONArray("categories").getString(0));
    	        	Category c= new Category(firstItem.getJSONArray("categories").getString(0));
    	        	//System.out.println(c);
    	        	book.setCategory(c);
    	        //	System.out.println(book.getCategory());
    	        }
    	        if(firstItem.has("description")) {
    	        	book.setDescription(firstItem.getString("description"));
    	        }
    	        if(firstItem.has("imageLinks")) {
    	        	book.setImageUrl(firstItem.getJSONObject("imageLinks").getString("thumbnail"));
    	        }
    	        cose.add(book); 
	        }
	        
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLEXception: " + e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println("IOException: " +e.getLocalizedMessage());
		} catch (JSONException e) {
			System.out.println("JSONException: " +e.getLocalizedMessage());
		} catch (ParseException e) {
			System.out.println("ParseException: " +e.getLocalizedMessage());
		} catch (NullPointerException e) {
			System.out.println("NullPointerException: " + e.getMessage());
		}
		return cose;
	        	 	
	}
	public String getImageUrl() {
	return imageUrl;
	}

	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getAuthor() {
		return author;
	}

	public Category getCategory() {
		return category;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public String getTitle() {
		return title;
	}

	public Date getYear() {
		return year;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(Date year) {
		this.year = year;
	}
	
	 @Override
	 public boolean equals(Object obj) {
		 if (this == obj)
			 return true;
		 if (obj == null)
			 return false;
		 if (getClass() != obj.getClass())
			 return false;
		 BookDescription other = (BookDescription) obj;
		 if (ISBN != other.ISBN)
			 return false;
		 return true;
	 }

	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (ISBN != null ? ISBN.hashCode() : 0);
		return result;
	}
	 
	@Override
	public String toString() {
		return "BookDescription [author=" + author + ", title=" + title + ", ISBN=" + ISBN + ", publishingHouse="
				+ publishingHouse + ", year=" + year + ", category=" + category + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}	
}
