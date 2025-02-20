package com.example.restandgraphql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 레스토랑 정보를 저장하는 엔티티 클래스입니다.
 * JPA를 사용하여 데이터베이스와 매핑됩니다.
 */
@Entity // JPA 엔티티임을 나타냅니다
@Getter // Lombok: 모든 필드의 Getter 메서드를 자동 생성합니다
@Setter // Lombok: 모든 필드의 Setter 메서드를 자동 생성합니다  
@NoArgsConstructor // Lombok: 파라미터가 없는 기본 생성자를 생성합니다
@AllArgsConstructor // Lombok: 모든 필드를 파라미터로 받는 생성자를 생성합니다
public class Restaurant {

    /**
     * 레스토랑의 고유 식별자입니다.
     * 자동 증가하는 기본키로 사용됩니다.
     */
    @Id // 기본키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 전략을 IDENTITY로 설정
    @Schema(description = "레스토랑 ID", example = "1")
    private Long id;

    /**
     * 레스토랑의 이름입니다.
     * null이 허용되지 않는 필수 필드입니다.
     */
    @Column(nullable = false) // NOT NULL 제약조건 설정
    @Schema(description = "레스토랑 이름", example = "맛있는 김밥")
    private String name;

    /**
     * 레스토랑의 주소입니다.
     * null이 허용되지 않는 필수 필드입니다.
     */
    @Column(nullable = false) // NOT NULL 제약조건 설정
    @Schema(description = "레스토랑 주소", example = "서울시 강남구 역삼동 123-45")
    private String address;

    /**
     * 레스토랑의 전화번호입니다.
     * 선택적 필드로 null이 허용됩니다.
     */
    @Schema(description = "레스토랑 전화번호", example = "02-1234-5678")
    private String phoneNumber;
}
