package com.becoder.dto;

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
