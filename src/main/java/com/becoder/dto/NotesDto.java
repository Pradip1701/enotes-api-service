package com.becoder.dto;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto 
{
	private Integer Id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
    private Integer createdBy;
	
    private Date createdOn;
		
    private Integer updatedBy;
		
    private Date updatedOn;
    
    private FilesDto fileDetails;

    private Boolean isDeleted;
    
    private LocalDateTime deletedOn;//here insted of Date datatype we used LocalDateTime bcz we perform opertaion for minus 
    //days from current date and LocalDateTime have some methods for it		
    
    
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilesDto
    {
    	private Integer id;
    	private String originalFileName;
    	private String displayFileName;
    }
    
    
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto
    {
		private Integer id;		
		private String name;
    }
}
