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
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException
	{
		Boolean saveNote=notesService.saveNotes(notesDto);
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
