package com.lucky.around.meal.datapipeline;

import java.util.List;

import com.lucky.around.meal.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParsedData {
  List<Restaurant> data;
}
