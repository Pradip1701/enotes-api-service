package com.becoder.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    
    private Boolean isDeleted;
    
    private LocalDateTime deletedOn; //here insted of Date datatype we used LocalDateTime bcz we perform opertaion for minus 
                                  //days from current date and LocalDateTime have some methods for it
}
