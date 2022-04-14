package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.models.TodoDTO;
import com.example.demo.persistence.entities.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

	@PostMapping
	public  ResponseEntity<?> createTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto
	) {

		try {

			TodoEntity entity = dto.asEntity();
			entity.setId(null);

			// AuthenticationPrincipal에서 넘어온 유저아이디 사용(spring.security에서 제공?)
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
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {

		List<TodoEntity> entities = this.todoService.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto
	) {

		TodoEntity entity = dto.asEntity();
		entity.setUserId(userId);

		List<TodoEntity> entities = todoService.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto
	) {

		try {

			TodoEntity entity = dto.asEntity();
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
