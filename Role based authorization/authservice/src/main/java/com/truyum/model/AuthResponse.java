package com.truyum.model;

public class AuthResponse {
	private String Uid;
	private String Name;
	private boolean isValid;
	private String roles;
	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public AuthResponse(String uid, String name, boolean isValid) {
		super();
		Uid = uid;
		Name = name;
		this.isValid = isValid;
	}
	public AuthResponse() {
		super();
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	

}
