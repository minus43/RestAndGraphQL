package com.example.restandgraphql.controller;

import com.example.restandgraphql.dto.RestaurantPage;
import com.example.restandgraphql.entity.Restaurant;
import com.example.restandgraphql.service.RestaurantService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import com.example.restandgraphql.dto.ErrorResponseDto;
import com.example.restandgraphql.dto.RestaurantPageResponse;

import static com.example.restandgraphql.util.SortUtils.parseSort;

/**
 * REST API를 통해 레스토랑 관련 요청을 처리하는 컨트롤러입니다.
 * 레스토랑의 조회, 생성, 수정, 삭제 등의 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor // 생성자 주입을 위한 롬복 어노테이션
public class RestaurantController {

    private final RestaurantService restaurantService; // 레스토랑 서비스 의존성 주입

    /**
     * 전체 레스토랑 목록을 페이징하여 조회합니다.
     * 
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지당 보여줄 항목 수
     * @param sort 정렬 기준 (예: "name,desc")
     * @return 페이징된 레스토랑 목록
     */
    @Operation(summary = "레스토랑 목록 조회", description = "모든 레스토랑을 페이징하여 조회합니다.", responses = {
        @ApiResponse(responseCode = "200", description = "레스토랑 목록 조회 성공", 
                content = @Content(schema = @Schema(implementation = RestaurantPageResponse.class))),
        @ApiResponse(responseCode = "400", description = "유효성 검증 실패", 
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<RestaurantPageResponse> getAllRestaurants(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "정렬 기준 (필드명,정렬방향)", example = "name,desc")
            @RequestParam(defaultValue = "id,asc") String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Restaurant> restaurantPage = restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.ok(RestaurantPageResponse.from(restaurantPage));
    }

    /**
     * 이름으로 레스토랑을 검색하고 페이징된 결과를 반환합니다.
     * 
     * @param name 검색할 레스토랑 이름
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sort 정렬 기준
     * @return 검색된 레스토랑 목록
     */
    @Operation(summary = "레스토랑 검색", description = "이름에 따라 레스토랑을 검색합니다.", responses = {
        @ApiResponse(responseCode = "200", description = "레스토랑 검색 성공", content = @Content(schema = @Schema(implementation = RestaurantPage.class))),
        @ApiResponse(responseCode = "400", description = "유효성 검증 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @GetMapping("/search")
    public ResponseEntity<RestaurantPageResponse> searchRestaurants(
            @Parameter(description = "검색할 레스토랑 이름", example = "맛있는")
            @RequestParam String name,
            
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "정렬 기준 (필드명,정렬방향)", example = "name,desc")
            @RequestParam(defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Restaurant> restaurantPage = restaurantService.searchRestaurants(name, pageable);
        return ResponseEntity.ok(RestaurantPageResponse.from(restaurantPage));
    }

    /**
     * ID를 기준으로 특정 레스토랑의 상세 정보를 조회합니다.
     * 존재하지 않는 ID인 경우 404 응답을 반환합니다.
     * 
     * @param id 조회할 레스토랑의 고유 ID
     * @return 조회된 레스토랑 정보
     */
    @Operation(summary = "레스토랑 상세 조회", description = "ID에 따라 레스토랑을 조회합니다.", responses = {
        @ApiResponse(responseCode = "200", description = "레스토랑 상세 조회 성공", content = @Content(schema = @Schema(implementation = Restaurant.class))),
        @ApiResponse(responseCode = "404", description = "레스토랑 조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(
        @Parameter(description = "레스토랑 ID", example = "1")
        @PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    /**
     * 새로운 레스토랑을 데이터베이스에 생성합니다.
     * 요청 본문의 유효성을 검증하며, 필수 필드가 누락된 경우 400 응답을 반환합니다.
     * 
     * @param restaurant 생성할 레스토랑 정보 (이름, 주소, 전화번호 필수)
     * @return 생성된 레스토랑 정보와 201 상태 코드
     */
    @Operation(summary = "레스토랑 생성", description = "새로운 레스토랑을 생성합니다.", responses = {
        @ApiResponse(responseCode = "201", description = "레스토랑 생성 성공", content = @Content(schema = @Schema(implementation = Restaurant.class))),
        @ApiResponse(responseCode = "400", description = "유효성 검증 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "생성할 레스토랑 정보", required = true,
                    content = @Content(examples = @ExampleObject(value = 
                            """
                            {
                              "name": "맛있는 김밥",
                              "address": "서울시 강남구 역삼동 123-45",
                              "phoneNumber": "02-1234-5678"
                            }
                            """)))
            @RequestBody @Valid Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant));
    }

    /**
     * 기존 레스토랑의 정보를 수정합니다.
     * 존재하지 않는 ID인 경우 404 응답을 반환하며,
     * 요청 본문의 유효성 검증에 실패하면 400 응답을 반환합니다.
     * 
     * @param id 수정할 레스토랑의 ID
     * @param restaurantDetails 수정할 레스토랑 정보
     * @return 수정된 레스토랑 정보
     */
    @Operation(summary = "레스토랑 수정", description = "ID에 따라 레스토랑을 수정합니다.", responses = {
        @ApiResponse(responseCode = "200", description = "레스토랑 수정 성공", content = @Content(schema = @Schema(implementation = Restaurant.class))),
        @ApiResponse(responseCode = "400", description = "유효성 검증 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @Parameter(description = "레스토랑 ID", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 레스토랑 정보", required = true,
                    content = @Content(examples = @ExampleObject(value = 
                            """
                            {
                              "name": "맛있는 김밥",
                              "address": "서울시 강남구 역삼동 123-45",
                              "phoneNumber": "02-1234-5678"
                            }
                            """)))
            @RequestBody @Valid Restaurant restaurantDetails) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDetails));
    }

    /**
     * ID를 기준으로 레스토랑을 삭제합니다.
     * 삭제 성공 시 204 응답을 반환하며,
     * 존재하지 않는 ID인 경우 404 응답을 반환합니다.
     * 
     * @param id 삭제할 레스토랑의 ID
     * @return 응답 본문 없이 상태 코드만 반환
     */
    @Operation(summary = "레스토랑 삭제", description = "ID에 따라 레스토랑을 삭제합니다.", responses = {
        @ApiResponse(responseCode = "204", description = "레스토랑 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "레스토랑 삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
        @Parameter(description = "레스토랑 ID", example = "1")
        @PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}
