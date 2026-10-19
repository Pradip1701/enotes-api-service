package com.becoder.service;

import java.util.List;

import com.becoder.dto.NotesDto;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;

public interface NotesService {

	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;
	
	public List<NotesDto> getAllNotes();
}
