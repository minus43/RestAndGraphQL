package com.example.restandgraphql.repository;

import com.example.restandgraphql.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 레스토랑 데이터에 접근하기 위한 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공합니다.
 */
@Repository // 스프링의 데이터 접근 계층임을 나타내는 어노테이션
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    /**
     * 레스토랑 이름에 특정 문자열이 포함된 레스토랑들을 페이징하여 검색합니다.
     * 
     * @param name 검색할 레스토랑 이름 (부분 문자열)
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬 등)
     * @return 검색된 레스토랑들의 페이지 객체
     */
    Page<Restaurant> findByNameContaining(String name, Pageable pageable);
}
