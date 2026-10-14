package com.becoder.dto;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;
    
// we are not going to use predefind validation for now weare going to user custom validation
//    @NotBlank      
//    @Min(value=10)
//    @Max(value=100)
	private String name;
	
//    @NotBlank
//    @Min(value=10)
//    @Max(value=100)
	private String description;
	
//   @NotNull
    private Boolean isActive;
		
    private Integer createdBy;
		
    private Date createdOn;
		
    private Integer updatedBy;
		
    private Date updatedOn;

}
