package com.example.restandgraphql.service;

import com.example.restandgraphql.entity.Restaurant;
import com.example.restandgraphql.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 레스토랑 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service // 스프링의 서비스 계층 컴포넌트임을 나타냅니다
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다
public class RestaurantService {

    private final RestaurantRepository restaurantRepository; // 레스토랑 데이터 접근을 위한 리포지토리

    /**
     * 모든 레스토랑 정보를 페이징하여 조회합니다.
     * @param pageable 페이징 정보
     * @return 페이징된 레스토랑 목록
     */
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    /**
     * 이름으로 레스토랑을 검색하고 페이징하여 반환합니다.
     * @param name 검색할 레스토랑 이름
     * @param pageable 페이징 정보
     * @return 검색된 레스토랑 목록
     */
    public Page<Restaurant> searchRestaurants(String name, Pageable pageable) {
        return restaurantRepository.findByNameContaining(name, pageable);
    }

    /**
     * ID로 특정 레스토랑을 조회합니다.
     * @param id 레스토랑 ID
     * @return 조회된 레스토랑 정보
     * @throws EntityNotFoundException 레스토랑을 찾을 수 없는 경우
     */
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("레스토랑을 찾을 수 없습니다."));
    }

    /**
     * 새로운 레스토랑을 생성합니다.
     * @param restaurant 생성할 레스토랑 정보
     * @return 생성된 레스토랑 정보
     */
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * 기존 레스토랑 정보를 수정합니다.
     * @param id 수정할 레스토랑 ID
     * @param restaurantDetails 수정할 레스토랑 정보
     * @return 수정된 레스토랑 정보
     */
    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        return restaurantRepository.save(restaurant);
    }

    /**
     * 레스토랑을 삭제합니다.
     * @param id 삭제할 레스토랑 ID
     */
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
