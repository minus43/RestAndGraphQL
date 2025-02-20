package com.example.restandgraphql.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

/**
 * 정렬 관련 유틸리티 클래스입니다.
 * 정렬 문자열을 Spring Data의 Sort 객체로 변환하는 기능을 제공합니다.
 */
@UtilityClass // 유틸리티 클래스임을 나타내는 롬복 어노테이션   
public class SortUtils {    
    /**
     * 정렬 문자열을 파싱하여 Sort 객체로 변환합니다.
     * 
     * @param sort 정렬 문자열 (예: "name,desc" 또는 "id,asc")
     * @return 변환된 Sort 객체
     */
    public static Sort parseSort(String sort) {
        // 콤마(,)를 기준으로 문자열을 분리
        String[] sortParams = sort.split(",");
        
        // 정렬 속성과 방향이 모두 지정된 경우
        if (sortParams.length == 2) {
            String property = sortParams[0];  // 정렬 속성 (예: name, id 등)
            Sort.Direction direction = Sort.Direction.fromString(sortParams[1]); // 정렬 방향 (asc 또는 desc)
            return Sort.by(new Sort.Order(direction, property));
        }
        
        // 정렬 기준이 없거나 잘못된 경우 기본값으로 ID 기준 오름차순 정렬
        return Sort.by(Sort.Order.asc("id"));
    }
} 