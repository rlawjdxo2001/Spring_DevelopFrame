package com.raisin.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(length = 64, nullable = false, unique = true)
	private String userId;
	@Column(length = 60, nullable = false)
	private String password;
	@Column(length = 2048, nullable = false)
	private String username;
	@Column(length = 64, nullable = false)
	private String createUser;
	@Column(nullable = false)
	private Date createDt;
	@Column(length = 64, nullable = false)
	private String modiUser;
	@Column(nullable = false)
	private Date modiDt;

	@PrePersist
    public void onPrePersist() {
        setCreatedDatetime(new Date());
        setUpdatedDatetime(new Date());
    }
	@PreUpdate
    public void onPreUpdate() {
        setUpdatedDatetime(new Date());
    }

	private void setCreatedDatetime(Date dt) {
		createDt = dt;
	}
	private void setUpdatedDatetime(Date dt) {
		modiDt = dt;
	}
}
