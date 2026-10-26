package com.becoder.dto;

import com.becoder.entity.Notes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteNotesDto 
{
	
    private Integer id;

	private Notes notes;
	
	private Integer userId;
}
