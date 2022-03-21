package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {
	
	
	/**
	 * @Id는 테이블의 Id와 매핑
	 * @GeneratedValue로 자동 생성되도록함, 이떼 제너레이터로 system-uuid를 사용할것임을 표시 
	 *system-uuid는 @GenericGenerator에 정의된 generator의 이름으로 Hibernate가 제공하는 제너레이터 외 커스텀한제너레이터를 이용하고자할때 이용
	 */

	@Id
	@GeneratedValue(generator = "system-uuod")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String userID;
	private String title;
	private boolean isDone;
}


/**
 * Todo annotation을 추가하지 않거나 name을 지정하지 않은경우는 Entity 명으로 매핑
 * Entity도 이름을 지정하지 않은 경우에는 클래스명으로 매핑
 */