package com.lucky.around.meal.datapipeline;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucky.around.meal.common.util.GeometryUtil;
import com.lucky.around.meal.entity.Restaurant;
import com.lucky.around.meal.entity.enums.Category;
import com.lucky.around.meal.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessService {

  private final RawRestaurantRepository rawRestaurantRepository;
  private final RestaurantRepository restaurantRepository;
  private final ObjectMapper objectMapper;
  private final GeometryUtil geometryUtil;

  public synchronized void executeDataProcess(int pageSize) {
    log.info("[execute] 데이터 가공하기 - size : {}.", pageSize);

    try {
      int page = 0;

      while (true) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<RawRestaurant> rawRestaurantsPage = rawRestaurantRepository.findAll(pageRequest);

        if (rawRestaurantsPage.isEmpty()) {
          break; // 더 이상 처리할 데이터가 없으면 종료
        }

        List<RawRestaurant> rawRestaurants = rawRestaurantsPage.getContent();

        for (RawRestaurant rawRestaurant : rawRestaurants) {
          if (rawRestaurant.isUpdated()) {
            log.info("[dataProcessing] 변경된 맛집 : {}", rawRestaurant.getJsonData());

            try {
              Restaurant processedRestaurant = convertToProcessedRestaurant(rawRestaurant);
              if (processedRestaurant != null) {
                restaurantRepository.save(processedRestaurant);
                rawRestaurant.setUpdated(false);
                rawRestaurantRepository.save(rawRestaurant);
              }
            } catch (Exception e) {
              log.error("[fail] 데이터 가공하기 id: {}", rawRestaurant.getId(), e);
            }
          }
        }
        page++;
      }
    } catch (Exception e) {
      log.error("[fail] 데이터 가공하기", e);
    }
  }

  private Restaurant convertToProcessedRestaurant(RawRestaurant rawRestaurant) {
    try {
      JsonNode rootNode = objectMapper.readTree(rawRestaurant.getJsonData());

      // 좌표 유효하지 않은 음식점은 저장하지 않기
      String xStr = rootNode.path("X").asText();
      String yStr = rootNode.path("Y").asText();

      if (xStr.isEmpty() || yStr.isEmpty()) {
        log.warn("[skip] 유효하지 않은 데이터는 저장하지 않음 id {}", rawRestaurant.getId());
        return null;
      }

      double longitude = Double.parseDouble(xStr);
      double latitude = Double.parseDouble(yStr);

      Point location = geometryUtil.createPoint(longitude, latitude);

      String id = rawRestaurant.getId();
      String restaurantName = rootNode.path("BPLCNM").asText();
      String category = rootNode.path("UPTAENM").asText();
      String restaurantTel = rootNode.path("SITETEL").asText().replace(" ", "");

      String jibunAddress = rootNode.path("SITEWHLADDR").asText();
      String[] jibunAddresses = splitAddress(jibunAddress);
      String dosi = jibunAddresses[0];
      String sigungu = jibunAddresses[1];
      String jibunDetailAddress = jibunAddresses[2];

      String doroAddress = rootNode.path("RDNWHLADDR").asText();
      String[] doroAddresses = splitAddress(doroAddress);
      String doroDetailAddress = doroAddresses[2];

      return Restaurant.builder()
          .id(id)
          .restaurantName(restaurantName)
          .category(Category.of(category))
          .restaurantTel(restaurantTel)
          .jibunDetailAddress(jibunDetailAddress)
          .doroDetailAddress(doroDetailAddress)
          .dosi(dosi)
          .sigungu(sigungu)
          .location(location)
          .build();
    } catch (Exception e) {
      log.error("[fail] 데이터 파싱하기 ", e);
      return null;
    }
  }

  private String[] splitAddress(String address) {
    if (address == null || address.isEmpty()) {
      return new String[] {"", "", ""};
    }
    return address.split(" ", 3);
  }
}