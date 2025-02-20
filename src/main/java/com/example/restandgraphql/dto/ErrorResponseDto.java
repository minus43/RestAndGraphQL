package com.example.restandgraphql.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * API 에러 응답을 위한 DTO 클래스
 */
@Getter // Lombok을 사용하여 모든 필드의 getter 메서드 자동 생성
@Builder // Builder 패턴을 적용하여 객체 생성을 용이하게 함
public class ErrorResponseDto {
    private final LocalDateTime timestamp; // 에러 발생 시간
    private final int status;             // HTTP 상태 코드 
    private final String error;           // 에러 종류 (예: "Bad Request", "Not Found" 등)
    private final String message;         // 구체적인 에러 메시지
    private final String path;            // 에러가 발생한 요청 경로
} 