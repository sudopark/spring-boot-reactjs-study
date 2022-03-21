package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ResponseDTO;
import com.example.demo.services.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	/**
	 * @Autowired가 TodoService에 해당하는 bean을 검색해서 알아서 연결해줌
	 */
	@Autowired
	private TodoService todoService;
	
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodos() {
		
		String str = todoService.todoService();
		List<String> list = new ArrayList<>();
		list.add(str);
		
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
}
