package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;



/**
 * 
 * @Service은 스테레오 타입의 어노테이션, 내부에 @component 어노테이션 존재
 * => spring 어노테이션 이며 단지 서비스 레이어임을 알려주기위한 어노테이
 */


@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public List<TodoEntity> create(final TodoEntity entity) {
		// valiate
		validate(entity);
		
		repository.save(entity);
		log.debug("entity id: {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}

	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}

	public List<TodoEntity> update(final TodoEntity newEntity) {

		validate(newEntity);

		final Optional<TodoEntity> original = repository.findById(newEntity.getId());

		original.ifPresent(todo -> {
			todo.setTitle(newEntity.getTitle());
			todo.setDone(newEntity.isDone());

			repository.save(todo);
		});

		return retrieve(newEntity.getUserId());
	}

	public List<TodoEntity> delete(final TodoEntity entity) {

		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("error at delete todo => ", e);

			throw new RuntimeException("error deleting entity, id => " + entity.getId());
		}

		return retrieve(entity.getUserId());
	}

	private void validate(final TodoEntity entity) {
		if (entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("entity can not be null");
		}

		if (entity.getUserId() == null) {
			log.warn("unknown user");
			throw new RuntimeException("unknown user");
		}
	}
}
