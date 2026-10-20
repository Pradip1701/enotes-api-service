package com.becoder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;

public interface NotesService {

	public Boolean saveNotes(String file, MultipartFile file2) throws Exception;
	
	public List<NotesDto> getAllNotes();

	//Boolean saveNotes() throws ResourceNotFoundException;
}
