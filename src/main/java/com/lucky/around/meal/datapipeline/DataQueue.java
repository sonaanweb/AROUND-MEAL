package com.lucky.around.meal.datapipeline;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class DataQueue {

  private final BlockingDeque<RawData> collectQueue = new LinkedBlockingDeque<>();
  private final BlockingDeque<ParsedData> processQueue = new LinkedBlockingDeque<>();
}
