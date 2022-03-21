package com.example.demo.services;

import org.springframework.stereotype.Service;



/**
 * 
 * @Service은 스테레오 타입의 어노테이션, 내부에 @component 어노테이션 존재
 * => spring 어노테이션 이며 단지 서비스 레이어임을 알려주기위한 어노테이
 */


@Service
public class TodoService {
	
	public String todoService() {
		return "this is todo service";
	}
}
