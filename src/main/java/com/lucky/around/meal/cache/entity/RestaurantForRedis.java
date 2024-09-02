package com.lucky.around.meal.cache.entity;

import java.io.Serializable;

import jakarta.persistence.Id;

import org.locationtech.jts.geom.Point;
import org.springframework.data.redis.core.RedisHash;

import com.lucky.around.meal.entity.Restaurant;
import com.lucky.around.meal.entity.enums.Category;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@RedisHash(value = "Restaurant", timeToLive = 86400)
@ToString
public class RestaurantForRedis implements Serializable {

  @Id private String id;

  private String restaurantName;
  private String dosi;
  private String sigungu;
  private String jibunDetailAddress;
  private String doroDetailAddress;
  private Category category;
  private String restaurantTel;
  private double lon;
  private double lat;
  private double ratingAverage;

  public Restaurant toRestaurant(Point location) {
    Restaurant restaurant =
        Restaurant.builder()
            .id(this.id)
            .restaurantName(this.restaurantName)
            .dosi(this.dosi)
            .sigungu(this.sigungu)
            .jibunDetailAddress(this.jibunDetailAddress)
            .doroDetailAddress(this.doroDetailAddress)
            .category(this.category)
            .restaurantTel(this.restaurantTel)
            .location(location)
            .build();
    return restaurant;
  }

  public static RestaurantForRedis toRestaurantForRedis(Restaurant restaurant) {
    // 좌표 초기화(null 대비)
    double lat = 0.0;
    double lon = 0.0;

    // null이 아닐 경우 제대로 된 값 넣어주기
    if (restaurant.getLocation() != null) {
      lon = restaurant.getLocation().getX();
      lat = restaurant.getLocation().getY();
    }

    return RestaurantForRedis.builder()
        .id(restaurant.getId())
        .restaurantName(restaurant.getRestaurantName())
        .dosi(restaurant.getDosi())
        .sigungu(restaurant.getSigungu())
        .jibunDetailAddress(restaurant.getJibunDetailAddress())
        .doroDetailAddress(restaurant.getDoroDetailAddress())
        .category(restaurant.getCategory())
        .restaurantTel(restaurant.getRestaurantTel())
        .lat(lat)
        .lon(lon)
        .build();
  }
}