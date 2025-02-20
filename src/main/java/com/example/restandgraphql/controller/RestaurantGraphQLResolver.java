package com.example.restandgraphql.controller;

import com.example.restandgraphql.dto.RestaurantPage;
import com.example.restandgraphql.entity.Restaurant;
import com.example.restandgraphql.service.RestaurantService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.example.restandgraphql.util.SortUtils.parseSort;


/**
 * GraphQL API를 통해 레스토랑 관련 요청을 처리하는 컨트롤러입니다.
 * 레스토랑의 조회, 생성, 수정, 삭제 등의 기능을 제공합니다.
 */
@Controller
@RequiredArgsConstructor // 생성자 주입을 위한 롬복 어노테이션
public class RestaurantGraphQLResolver {

    private final RestaurantService restaurantService; // 레스토랑 서비스 의존성 주입

    /**
     * 전체 레스토랑 목록을 페이징하여 조회합니다.
     * 
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지당 보여줄 항목 수
     * @param sort 정렬 기준 (예: "name,desc")
     * @return 페이징된 레스토랑 목록
     */
    @SchemaMapping(typeName = "Query") // GraphQL Query 타입에 매핑
    public RestaurantPage getRestaurants(
            @Argument int page,
            @Argument int size,
            @Argument String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Restaurant> restaurantPage = restaurantService.getAllRestaurants(pageable);
        return new RestaurantPage(restaurantPage);
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
    @QueryMapping // GraphQL Query에 매핑
    public RestaurantPage searchRestaurants(
            @Argument String name,
            @Argument int page,
            @Argument int size,
            @Argument String sort) {

        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Restaurant> restaurantPage = restaurantService.searchRestaurants(name, pageable);
        return new RestaurantPage(restaurantPage);
    }

    /**
     * 새로운 레스토랑을 데이터베이스에 생성합니다.
     * 
     * @param name 레스토랑 이름 (필수)
     * @param address 레스토랑 주소 (필수)
     * @return 생성된 레스토랑 정보
     */
    @SchemaMapping(typeName = "Mutation") // GraphQL Mutation 타입에 매핑
    public Restaurant createRestaurant(
            @Argument @NotBlank(message = "이름은 필수입니다") String name,
            @Argument @NotBlank(message = "위치는 필수입니다") String address) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        return restaurantService.createRestaurant(restaurant);
    }

    /**
     * 기존 레스토랑 정보를 수정합니다.
     * 
     * @param id 수정할 레스토랑의 ID
     * @param name 새로운 레스토랑 이름
     * @param address 새로운 레스토랑 주소
     * @return 수정된 레스토랑 정보
     */
    @MutationMapping // GraphQL Mutation에 매핑
    public Restaurant updateRestaurant(
            @Argument Long id,
            @Argument String name,
            @Argument String address) {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setAddress(address);

        return restaurantService.updateRestaurant(id, restaurant);
    }

    /**
     * ID로 레스토랑을 삭제합니다.
     * 
     * @param id 삭제할 레스토랑의 ID
     * @return 삭제 성공 여부
     */
    @MutationMapping // GraphQL Mutation에 매핑
    public boolean deleteRestaurant(@Argument Long id) {
        restaurantService.deleteRestaurant(id);
        return true;  // 삭제가 성공적으로 완료되면 true 반환
    }
}
