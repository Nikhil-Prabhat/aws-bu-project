package com.truyum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
public class UserTruyum {

	@Id
	@Column(name = "userid", length = 20)
	private String userid;
	@Column(name = "upassword", length = 20)
	private String upassword;
	@Column(name = "roles", length = 20)
	private String roles;

	public UserTruyum(String userid, String upassword, String roles) {
		super();
		this.userid = userid;
		this.upassword = upassword;
		this.roles = roles;
	}

	public UserTruyum() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserTruyum [userid=" + userid + ", upassword=" + upassword + ", roles=" + roles + "]";
	}

}
