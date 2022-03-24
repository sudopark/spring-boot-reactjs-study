package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TodoEntity;
import com.example.demo.persistence.TodoRepository;



/**
 * 
 * @Service은 스테레오 타입의 어노테이션, 내부에 @component 어노테이션 존재
 * => spring 어노테이션 이며 단지 서비스 레이어임을 알려주기위한 어노테이
 */


@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String todoService() {
		
		// make entity
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		
		// save entity
		repository.save(entity);
		
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}
}
