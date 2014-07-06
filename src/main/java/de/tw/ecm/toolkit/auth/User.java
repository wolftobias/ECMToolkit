package main.java.de.tw.ecm.toolkit.auth;

public class User {

	String userid;

	String password;

	public User() {
	}
	
	public User(String user, String password) {
		this.userid = user;
		this.password = password;
	}

	public String getUserId() {
		return userid;
	}

	public void setUserId(String user) {
		this.userid = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
