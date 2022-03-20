package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TodoEntity {

	private String id;
	private String userID;
	private String title;
	private boolean isDone;
}
