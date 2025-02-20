package com.example.restandgraphql.repository;

import com.example.restandgraphql.entity.Restaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 레스토랑 리포지토리 테스트 클래스입니다.
 * 기본적인 CRUD 작업을 테스트합니다.
 */
@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("레스토랑을 생성하고 조회할 수 있다")
    void createAndRead() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("테스트 레스토랑");
        restaurant.setAddress("테스트 주소");

        // when
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        Restaurant foundRestaurant = restaurantRepository.findById(savedRestaurant.getId()).orElse(null);

        // then
        assertThat(foundRestaurant).isNotNull();
        assertThat(foundRestaurant.getName()).isEqualTo("테스트 레스토랑");
        assertThat(foundRestaurant.getAddress()).isEqualTo("테스트 주소");
    }

    @Test
    @DisplayName("레스토랑 정보를 수정할 수 있다")
    void update() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("원래 이름");
        restaurant.setAddress("원래 주소");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // when
        savedRestaurant.setName("변경된 이름");
        savedRestaurant.setAddress("변경된 주소");
        Restaurant updatedRestaurant = restaurantRepository.save(savedRestaurant);

        // then
        assertThat(updatedRestaurant.getName()).isEqualTo("변경된 이름");
        assertThat(updatedRestaurant.getAddress()).isEqualTo("변경된 주소");
    }

    @Test
    @DisplayName("레스토랑을 삭제할 수 있다")
    void delete() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("삭제될 레스토랑");
        restaurant.setAddress("삭제될 주소");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // when
        restaurantRepository.delete(savedRestaurant);

        // then
        assertThat(restaurantRepository.findById(savedRestaurant.getId())).isEmpty();
    }
}