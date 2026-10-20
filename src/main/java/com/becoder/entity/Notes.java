package com.becoder.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.becoder.dto.CategoryDto;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel
{
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	private Category category;
	
    @OneToOne
    private FileDetails fileDetails;
		
}
