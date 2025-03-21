package com.lucky.around.meal.datapipeline;

import java.util.concurrent.BlockingDeque;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectService {

  private final DataQueue dataQueue;
  private final WebClient.Builder webClientBuilder;

  @Value("${API_BASE_URL}")
  private String BASE_URL;

  @Value("${API_SERVICE_NAME}")
  private String SERVICE_NAME;

  @Value("${API_KEY}")
  private String KEY;

  @Value("${API_FORMAT_TYPE}")
  private String FORMAT_TYPE;

  @Value("${API_MAX_INDEX}")
  private int MAX_INDEX;

  @Value("${API_PAGE_SIZE}")
  private int PAGE_SIZE;

  @Async
  public void collectData() {
    try {
      // 1~100, 101~200, 201~300 ...
      for (int startIndex = 1; startIndex <= MAX_INDEX; startIndex += PAGE_SIZE) {
        // API 호출
        int endIndex = startIndex + (PAGE_SIZE - 1);
        String responseData = fetchRawData(startIndex, endIndex).block();
        log.info("[수집] API 호출: " + startIndex + "~" + endIndex);

        // 수집한 결과를 큐에 담음
        BlockingDeque<RawData> collectQueue = dataQueue.getCollectQueue();
        RawData rawData = new RawData(responseData);
        collectQueue.put(rawData);
      }

      // 종료 신호 전달
      dataQueue.getCollectQueue().put(new RawData("FIN"));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public Mono<String> fetchRawData(int startIndex, int endIndex) {
    String uri =
        String.format("/%s/%s/%s/%s/%s", KEY, FORMAT_TYPE, SERVICE_NAME, startIndex, endIndex);
    return webClientBuilder
        .baseUrl(BASE_URL)
        .build()
        .get()
        .uri(uri)
        .retrieve()
        .bodyToMono(String.class);
  }
}
