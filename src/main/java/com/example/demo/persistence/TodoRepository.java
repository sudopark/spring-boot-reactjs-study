package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.persistence.entities.TodoEntity;

/**
 * 
 * JpaRepository는 인터페이스
 * generic의 첫번째 파라미터는 테이블에 매핑될 엔티티 클래스를, ID는 기본키의 타입
 *
 */

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

//	
//	/**
//	 * 
//	 * 메소드명을 파싱해서 spring 데이터 JPA가 자동으로 쿼리를 작성해 실행
//	 */
//	
	List<TodoEntity> findByUserId(String userId);
//	
	/**
	 * 
	 * @Query annotation을 이용해서 쿼리 지정 가능
	 * 자세한 내용은 공식 레퍼런스 참고: spring-data/jpa, jpa.query
	 */
	@Query("select t from TodoEntity t where t.userId = ?1")
	List<TodoEntity> findByTitle(String title);
}