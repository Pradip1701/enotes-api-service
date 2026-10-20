package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.service.impl.NotesServiceImpl;
import com.becoder.util.CommonUtil;

@RestController
@RequestMapping("api/v1/notes")
public class NotesController {

	
	@Autowired
	private NotesServiceImpl notesService;
	
	
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
}
