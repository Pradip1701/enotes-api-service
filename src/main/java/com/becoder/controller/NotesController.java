package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.FileRepository;
import com.becoder.service.impl.NotesServiceImpl;
import com.becoder.util.CommonUtil;

@RestController
@RequestMapping("api/v1/notes")
public class NotesController {

	
	@Autowired
	private NotesServiceImpl notesService;
	
	@Autowired
	private FileRepository fileRepo;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveNotes(@RequestParam String notes,
			                           @RequestParam(required=false) MultipartFile file) throws Exception
	{
		
		Boolean saveNote=notesService.saveNotes(notes,file);
		if(saveNote)
		{
			return CommonUtil.createBuildResponeMessage("notes saved",HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponeMessage("notes not saved",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllNotes()
	{
		List<NotesDto> allNotes=notesService.getAllNotes();
		
		if(CollectionUtils.isEmpty(allNotes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes,HttpStatus.OK);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception
	{
		FileDetails filedtls=notesService.getFileDetails(id);
		byte[] data=notesService.downloadFile(filedtls);
		
		HttpHeaders headers=new HttpHeaders();
		String contentType=CommonUtil.getContentType(filedtls.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", filedtls.getOriginalFileName());//Write on the label: “Download this file and name it java-notes.pdf.”
		
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@GetMapping("/get-notes-page")
	public ResponseEntity<?> getAllNotesPage()
	{
		Integer id=11;
		NotesResponse allNotes=notesService.getAllNotesPage(id);
		
		if(ObjectUtils.isEmpty(allNotes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes,HttpStatus.OK);
	}
	
	@GetMapping("/delete-notes/{id}")
	public ResponseEntity<?> softDeleteNotes(@PathVariable Integer id) throws ResourceNotFoundException
	{  
		notesService.deleteNotes(id);
		return CommonUtil.createBuildResponeMessage("notes deleted success",HttpStatus.OK);
	}
	
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException
	{  
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponeMessage("notes resstore success",HttpStatus.OK);
	}
	
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> recycleNotes()
	{  
		Integer id=11;
		List<Notes> recycleNote=notesService.recycleNotes(id);
		if(!CollectionUtils.isEmpty(recycleNote)) 
		{
		return CommonUtil.createBuildRespone(recycleNote,HttpStatus.OK);
		}else
			return CommonUtil.createBuildResponeMessage("notes not available",HttpStatus.OK); 
	}

	
	
}
