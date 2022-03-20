package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ResponseDTO;
import com.example.demo.models.TestRequestBodyDTO;


/**
 * 
 * @RestController는 @Controller, @ResponseBody로 구성
 * @Controller: @Component로 스프링이 알아서 오브젝트를 생성하고 의존을 연결함
 * @ResponseBody: 이 클래스의 메소드가 리턴하는것은 웹 서비스의 response body임을 뜻함 
 * 		=> 리턴시에 오브젝트를 JSON으로 바꾸고(Serialization) 이를 HTTP response body에 담 
 *
 */

@RestController
@RequestMapping("test")				
// endpoint가 test 인것 처리
public class TestController {

	
	@GetMapping
	public String testController() {
		return "requested => localhost:8080/test";
	}
	
	@GetMapping("/testGetMapping")
	public String testGetMapping() {
		return "requested => localhost:8080/test/testGetMapping";
	}
	
	@GetMapping("/{id}")
	public String testWithPathVariable(@PathVariable(required = false) int id) {
		return "requested => localhost:8080/test/{variable} and id: " + id;
	}
	
	@GetMapping("/withQuery")
	public String testwithQuery(@RequestParam(required = true) int id) {
		return "requested => localhost:8080/test/withQuery?id={variable} and id: " + id;
	}
	
	@PostMapping("/withReqBody")
	public String testWithReqBody(@RequestBody(required = true) TestRequestBodyDTO dto) {
		return "requested => localhost:8080/test/withReqBody?id={id}&message={message} id: " + dto.getId() + "and message: " + dto.getMessage();
	}
	
	@GetMapping("/list")
	public ResponseDTO<String> testReponseAsList() {
		List<String> list = new ArrayList<>();
		list.add("hello world");
		ResponseDTO<String> sender = ResponseDTO.<String>builder().data(list).build();
		return sender;
	}
	
	// ResponseEntity를 이용하여 응답의 헤더 + status code 조작 가능
	@GetMapping("/response/error")
	public ResponseEntity<?> testBadResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("this is error..");
		ResponseDTO<String> sender = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(sender);
	}
}



