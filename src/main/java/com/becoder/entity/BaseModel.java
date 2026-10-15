package com.becoder.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

//It tells JPA: “this class is not a table itself, but its fields should become columns in every child entity’s 
//table.”
//It’s a way to write common fields once and reuse them across many entities.

public abstract class BaseModel {
	
	
	@CreatedBy
	@Column(updatable=false)
	private Integer createdBy;
	
	@CreatedDate
	@Column(updatable=false)
	private Date createdOn;
	
	@LastModifiedBy
	@Column(insertable=false)
	private Integer updatedBy;
	
	@LastModifiedDate
	@Column(insertable=false)
	private Date updatedOn;

}
