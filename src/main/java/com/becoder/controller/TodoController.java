package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.TodoDto;
import com.becoder.entity.Todo;
import com.becoder.service.TodoService;
import com.becoder.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception
	{
		Boolean isSave=todoService.saveTodo(todoDto);
		if(isSave)
		{
			return CommonUtil.createBuildResponeMessage("todo save success",HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponeMessage("todo save failure",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping("/get-todo/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception
	{
		TodoDto todoDto=todoService.getTodoById(id);
		
		return CommonUtil.createBuildRespone(todoDto,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/get-todo-byuser/{id}")
	public ResponseEntity<?> getTodoByIdUser() throws Exception
	{
		Integer id=2;
		List<TodoDto> todoDto=todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(todoDto))
		{
			 return ResponseEntity.noContent().build();
		}
		 return CommonUtil.createBuildRespone(todoDto,HttpStatus.OK);
		
	}
	

}
