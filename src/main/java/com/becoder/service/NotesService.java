package com.becoder.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;

public interface NotesService {

	public Boolean saveNotes(String file, MultipartFile file2) throws Exception;
	
	public List<NotesDto> getAllNotes();

	//Boolean saveNotes() throws ResourceNotFoundException;
	
	public byte[] downloadFile(FileDetails filedtls) throws Exception;
	
	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesPage(Integer id);

	public void restoreNotes(Integer id) throws ResourceNotFoundException;

	public void deleteNotes(Integer id) throws ResourceNotFoundException;
	
	public List<Notes> recycleNotes(Integer id);
}
