package model;

public enum UserType {
	Admin,
	Student,
	Professor,
	Anonymous;
	
	public static UserType fromString(String type) {
		switch(type) {
			case "Admin":
				return Admin;
			case "Student":
				return Student;
			case "Professor":
				return Professor;
			case "Anonymous":
				return Anonymous;
			default:
				return null;
		}
	}
}


