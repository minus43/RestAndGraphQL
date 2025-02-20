# REST와 GraphQL 예제 프로젝트

이 프로젝트는 Spring Boot에서 REST API와 GraphQL API를 함께 사용하는 방법을 보여주는 예제입니다.

## 개요

간단한 레스토랑 정보를 관리하는 API를 통해 다음 내용을 보고 이해할 수 있습니다:

### REST API
- 기본적인 CRUD 엔드포인트 구현
- 페이징과 정렬 처리 방법
- Swagger를 통한 API 문서화

### GraphQL
- 스키마 정의와 리졸버 구현
- 쿼리와 뮤테이션 작성법

## 시작하기

### 환경 설정
- Java 17
- Spring Boot 3.x
- H2 Database

### 실행 방법
1. 프로젝트 클론
```bash
git clone https://github.com/yourusername/restaurant-management.git
cd restaurant-management
```

2. 의존성 설치
```bash
gradle clean build
```

3. 애플리케이션 실행
```bash
gradle bootRun
```

4. API 문서 확인
- REST API: `http://localhost:8080/swagger-ui.html`
- GraphQL API: `http://localhost:8080/graphiql`