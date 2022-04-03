package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.models.TodoDTO;
import com.example.demo.models.TodoEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	private final String tempUserId = "temp-user";
	@PostMapping
	public  ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {

		try {

			TodoEntity entity = dto.asEntity();
			entity.setId(null);

			// 일단은 임시 유저아이디로 세팅
			String userId = this.tempUserId;
			entity.setUserId(userId);

			List<TodoEntity> entities = todoService.create(entity);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {

		// 임시로 temp user id로 세팅
		String userId = this.tempUserId;

		List<TodoEntity> entities = this.todoService.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {

		TodoEntity entity = dto.asEntity();

		// 임시로 temp user id로 세팅
		String userId = this.tempUserId;
		entity.setUserId(userId);

		List<TodoEntity> entities = todoService.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {

		try {

			TodoEntity entity = dto.asEntity();
			// 임시로 temp user id로 세팅
			String userId = this.tempUserId;
			entity.setUserId(userId);

			List<TodoEntity> entities = todoService.delete(entity);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> errorResponse = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}
}
