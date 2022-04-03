package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

	private String id;
	private String title;
	private boolean isDone;
	
	public TodoDTO(final TodoEntity entity) {
		this();
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.isDone = entity.isDone();
	}

	public  TodoEntity asEntity() {
		return TodoEntity.builder()
				.id(this.id)
				.title(this.title)
				.isDone(this.isDone)
				.build();
	}
}
