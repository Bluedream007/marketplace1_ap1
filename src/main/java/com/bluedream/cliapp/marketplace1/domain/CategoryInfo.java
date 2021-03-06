package com.bluedream.cliapp.marketplace1.domain;
// Generated 2019/3/30 �U�� 04:14:45 by Hibernate Tools 5.1.7.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Category generated by hbm2java
 */
@Entity
//@Table(name = "category", catalog = "testDB")
public class CategoryInfo implements java.io.Serializable {

	private String categoryName;

	public CategoryInfo() {
	}

	public CategoryInfo(String categoryName) {
		this.categoryName = categoryName;
	}

	@Id

	@Column(name = "category_name", unique = true, nullable = false, length = 50)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
