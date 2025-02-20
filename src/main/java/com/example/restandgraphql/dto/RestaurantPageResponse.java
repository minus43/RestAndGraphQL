package com.example.restandgraphql.dto;

import com.example.restandgraphql.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 레스토랑 페이징 응답을 위한 DTO 클래스입니다.
 * Spring Data의 Page 객체를 REST API 응답에 적합한 형태로 변환합니다.
 */
@Getter // Lombok을 사용하여 모든 필드의 getter 메서드 자동 생성
@Builder // Builder 패턴을 적용하여 객체 생성을 용이하게 함
public class RestaurantPageResponse {
    private List<Restaurant> content;      // 현재 페이지의 레스토랑 목록
    private int totalPages;               // 전체 페이지 수
    private long totalElements;           // 전체 레스토랑 수
    private int size;                     // 페이지당 레스토랑 수
    private int number;                   // 현재 페이지 번호 (0부터 시작)

    /**
     * Spring Data의 Page 객체를 RestaurantPageResponse DTO로 변환하는 정적 팩토리 메서드
     * 
     * @param page 변환할 Page<Restaurant> 객체
     * @return 생성된 RestaurantPageResponse 객체
     */
    public static RestaurantPageResponse from(Page<Restaurant> page) {
        return RestaurantPageResponse.builder()
                .content(page.getContent())           // 현재 페이지의 레스토랑 목록 설정
                .totalPages(page.getTotalPages())     // 전체 페이지 수 설정
                .totalElements(page.getTotalElements()) // 전체 레스토랑 수 설정
                .size(page.getSize())                // 페이지 크기 설정
                .number(page.getNumber())            // 현재 페이지 번호 설정
                .build();
    }
} 