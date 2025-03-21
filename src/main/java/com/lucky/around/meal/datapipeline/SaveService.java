package com.lucky.around.meal.datapipeline;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lucky.around.meal.repository.RestaurantJdbcRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveService {

  private final DataQueue dataQueue;
  private final RestaurantJdbcRepository restaurantJdbcRepository;

  @Async
  public void saveData() {
    try {
      while (true) {
        // 가공된 데이터 가져오기
        ParsedData parsedData = dataQueue.getProcessQueue().take();

        // 종료 신호 수신하기 (빈 배열 받기)
        if (parsedData.getData().isEmpty()) {
          log.info("[저장] 종료 신호를 수신했습니다.");
          break;
        }

        restaurantJdbcRepository.saveAll(parsedData.getData());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Async
  public void updateData() {
    try {
      while (true) {
        // 가공된 데이터 가져오기
        ParsedData parsedData = dataQueue.getProcessQueue().take();

        // 종료 신호 수신하기 (빈 배열 받기)
        if (parsedData.getData().isEmpty()) {
          log.info("[저장] 종료 신호를 수신했습니다.");
          break;
        }

        restaurantJdbcRepository.updateAll(parsedData.getData());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
