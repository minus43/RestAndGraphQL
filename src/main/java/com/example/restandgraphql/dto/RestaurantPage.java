package com.example.restandgraphql.dto;

import com.example.restandgraphql.entity.Restaurant;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 레스토랑 페이징 정보를 담는 DTO 클래스입니다.
 * Spring Data의 Page 객체를 GraphQL API에서 사용하기 위해 변환합니다.
 */
@Getter // Lombok을 사용하여 모든 필드의 getter 메서드 자동 생성
public class RestaurantPage {
    private List<Restaurant> content;      // 현재 페이지의 레스토랑 목록
    private int totalPages;               // 전체 페이지 수
    private long totalElements;           // 전체 레스토랑 수
    private int size;                     // 페이지당 레스토랑 수
    private int number;                   // 현재 페이지 번호 (0부터 시작)

    /**
     * Spring Data의 Page 객체를 RestaurantPage DTO로 변환하는 생성자
     * 
     * @param restaurantPage 변환할 Page<Restaurant> 객체
     */
    public RestaurantPage(Page<Restaurant> restaurantPage) {
        this.content = restaurantPage.getContent();           // 현재 페이지의 레스토랑 목록 설정
        this.totalPages = restaurantPage.getTotalPages();     // 전체 페이지 수 설정
        this.totalElements = restaurantPage.getTotalElements(); // 전체 레스토랑 수 설정
        this.size = restaurantPage.getSize();                // 페이지 크기 설정
        this.number = restaurantPage.getNumber();            // 현재 페이지 번호 설정
    }
}
