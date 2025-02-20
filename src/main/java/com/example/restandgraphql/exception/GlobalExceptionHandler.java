package com.example.restandgraphql.exception;

import java.util.NoSuchElementException;

import com.example.restandgraphql.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * 전역 예외 처리를 담당하는 핸들러 클래스입니다.
 * 애플리케이션에서 발생하는 모든 예외를 일관된 형식으로 처리합니다.
 */
@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리하기 위한 어노테이션
public class GlobalExceptionHandler {

    /**
     * 모든 예외를 처리하는 기본 핸들러 메서드입니다.
     * 처리되지 않은 예외가 발생했을 때 500 Internal Server Error를 반환합니다.
     * 
     * @param ex 발생한 예외 객체
     * @param request 웹 요청 정보
     * @return 에러 응답 DTO를 포함한 ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex, WebRequest request) {
        // 에러 응답 DTO 생성
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())         // 현재 시간 기록
                .status(500)                           // HTTP 상태 코드 500 설정
                .error("Internal Server Error")        // 에러 종류 설정
                .message(ex.getMessage())              // 구체적인 에러 메시지
                .path(request.getDescription(false))   // 에러 발생 요청 경로
                .build();
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
} 