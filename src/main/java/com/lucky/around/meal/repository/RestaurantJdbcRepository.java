package com.lucky.around.meal.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.locationtech.jts.io.WKTWriter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lucky.around.meal.entity.Restaurant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RestaurantJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  public void saveAll(List<Restaurant> restaurantList) {
    String sql =
        "INSERT INTO restaurant "
            + "(id, restaurant_name, dosi, sigungu, category, rating_average, restaurant_tel, jibun_detail_address, doro_detail_address, \"location\") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ST_GeomFromText(?, 4326)) "
            + "ON CONFLICT (id) DO NOTHING";

    WKTWriter wktWriter = new WKTWriter();

    jdbcTemplate.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Restaurant restaurant = restaurantList.get(i);
            ps.setString(1, restaurant.getId());
            ps.setString(2, restaurant.getRestaurantName());
            ps.setString(3, restaurant.getDosi());
            ps.setString(4, restaurant.getSigungu());
            ps.setString(5, String.valueOf(restaurant.getCategory()));
            ps.setDouble(6, 0);
            ps.setString(7, restaurant.getRestaurantTel());
            ps.setString(8, restaurant.getJibunDetailAddress());
            ps.setString(9, restaurant.getDoroDetailAddress());
            ps.setString(10, wktWriter.write(restaurant.getLocation()));
          }

          @Override
          public int getBatchSize() {
            return restaurantList.size();
          }
        });
  }

  public void updateAll(List<Restaurant> restaurantList) {
    String sql =
        "UPDATE restaurant SET "
            + "restaurant_name = ?, "
            + "dosi = ?, "
            + "sigungu = ?, "
            + "category = ?, "
            + "restaurant_tel = ?, "
            + "jibun_detail_address = ?, "
            + "doro_detail_address = ?, "
            + "\"location\" = ST_GeomFromText(?, 4326) "
            + "WHERE id = ?";

    WKTWriter wktWriter = new WKTWriter();

    jdbcTemplate.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Restaurant restaurant = restaurantList.get(i);
            ps.setString(1, restaurant.getRestaurantName());
            ps.setString(2, restaurant.getDosi());
            ps.setString(3, restaurant.getSigungu());
            ps.setString(4, String.valueOf(restaurant.getCategory()));
            ps.setString(5, restaurant.getRestaurantTel());
            ps.setString(6, restaurant.getJibunDetailAddress());
            ps.setString(7, restaurant.getDoroDetailAddress());
            ps.setString(8, wktWriter.write(restaurant.getLocation()));
            ps.setString(9, restaurant.getId()); // ID로 업데이트 대상 레코드 선택
          }

          @Override
          public int getBatchSize() {
            return restaurantList.size();
          }
        });
  }
}
