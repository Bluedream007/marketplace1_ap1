package com.bluedream.cliapp.marketplace1.domain;
// Generated 2019/3/30 �U�� 04:14:45 by Hibernate Tools 5.1.7.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
//@Table(name = "user", catalog = "testDB")
//@Table(name = "userinfo")
public class UserInfo implements java.io.Serializable {

	private String username;

	public UserInfo() {
	}

	public UserInfo(String username) {
		this.username = username;
	}

	@Id

	@Column(name = "username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}