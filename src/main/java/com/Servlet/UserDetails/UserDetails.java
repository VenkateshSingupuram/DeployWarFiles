package com.Servlet.UserDetails;

public class UserDetails {

	private int userId;
	private String userName;
	private String location;
	private String professional;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	
	
	// To String Method
	
	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", userName=" + userName + ", location=" + location + ", professional="
				+ professional + "]";
	}
	
	
	
	
	
	
	
}
