package com.lucky.around.meal.datapipeline;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataPipeline {

  private final CollectService collectService;
  private final ProcessService processService;
  private final SaveService saveService;

  @Bean
  public ApplicationRunner initializer() {
    return args -> {
      runDataPipeline();
      //      repeatDataPipeline();
    };
  }

  @Async("taskExecutor")
  public void runDataPipeline() throws InterruptedException {
    // 각 단계별 비동기 작업 시작
    collectService.collectData();
    processService.processData(false);
    saveService.saveData();
  }

  @Async("taskExecutor")
  @Scheduled(cron = "0 0 1 * * ?")
  public void repeatDataPipeline() throws InterruptedException {
    // 각 단계별 비동기 작업 시작
    collectService.collectData();
    processService.processData(true);
    saveService.updateData();
  }
}
