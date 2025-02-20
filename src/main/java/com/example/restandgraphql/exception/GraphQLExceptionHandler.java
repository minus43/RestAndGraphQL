package com.example.restandgraphql.exception;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import reactor.core.publisher.Mono;

/**
 * GraphQL API에서 발생하는 예외를 처리하는 핸들러 클래스입니다.
 * DataFetcherExceptionResolver를 구현하여 GraphQL 쿼리 실행 중 발생하는 예외를 처리합니다.
 */
@Component // 스프링 컴포넌트로 등록
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {
    
    /**
     * GraphQL 쿼리 실행 중 발생한 예외를 처리하는 메서드입니다.
     * 
     * @param ex 발생한 예외 객체
     * @param env GraphQL 데이터 페칭 환경 정보
     * @return 처리된 GraphQL 에러 목록을 포함하는 Mono 객체
     */
    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable ex, DataFetchingEnvironment env) {
        // NoSuchElementException(데이터를 찾을 수 없는 경우)의 처리
        if (ex instanceof NoSuchElementException) {
            return Mono.just(Collections.singletonList(
                GraphQLError.newError()
                    .message(ex.getMessage())                           // 원본 예외 메시지 사용
                    .path(env.getExecutionStepInfo().getPath())        // 에러 발생 경로 설정
                    .location(env.getField().getSourceLocation())      // 에러 발생 위치 설정
                    .build()
            ));
        }
        
        // 그 외 모든 예외에 대한 기본 에러 처리
        return Mono.just(Collections.singletonList(
            GraphQLError.newError()
                .message("내부 서버 오류가 발생했습니다")                  // 일반적인 에러 메시지
                .path(env.getExecutionStepInfo().getPath())           // 에러 발생 경로 설정
                .location(env.getField().getSourceLocation())         // 에러 발생 위치 설정
                .build()
        ));
    }
} 