package com.example.restandgraphql.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API 문서를 설정하는 클래스입니다.
 * API 문서의 기본 정보와 서버 정보를 구성합니다.
 */
@Configuration // 스프링의 설정 클래스임을 나타냅니다.
public class SwaggerConfig {
    
    /**
     * OpenAPI 사양에 맞는 API 문서를 생성하는 Bean을 정의합니다.
     * API의 제목, 설명, 버전, 연락처 등의 기본 정보를 설정합니다.
     * 
     * @return 구성된 OpenAPI 객체를 반환합니다.
     */
    @Bean // 스프링 컨테이너에서 관리할 Bean으로 등록합니다.
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant API") // API의 대표 제목을 설정합니다.
                        .description("레스토랑 관리를 위한 REST API 문서입니다.") // API에 대한 상세 설명을 제공합니다.
                        .version("1.0") // API의 현재 버전을 명시합니다.
                        .contact(new Contact() // API 관리자의 연락처 정보를 설정합니다.
                                .name("minus43") // 관리자 이름
                                .email("minus425854@gmail.com"))) // 관리자 이메일
                .addServersItem(new Server().url("/")); // API 서버의 기본 URL을 설정합니다.
    }
} 