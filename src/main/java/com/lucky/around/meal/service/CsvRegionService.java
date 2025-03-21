package com.lucky.around.meal.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucky.around.meal.entity.Region;
import com.lucky.around.meal.exception.CustomException;
import com.lucky.around.meal.exception.exceptionType.CsvRegionExceptionType;
import com.lucky.around.meal.repository.RegionRepository;

@Service
public class CsvRegionService {

  @Autowired private RegionRepository regionRepository;

  @PostConstruct
  @Transactional
  public void importCsvToDatabase() {

    /*
    저장 경로 resources/region/sgg_lat_lon.csv
    통일성을 위해 Region 엔티티에 맞춰 csv 파일 데이터 헤더 이름을 dosi, sigungu로 변경해두었습니다.
     */
    ClassPathResource resource = new ClassPathResource("region/sgg_lat_lon.csv");

    Set<String> existingRegions =
        regionRepository.findAll().stream()
            .map(region -> region.getDosi() + "," + region.getSigungu())
            .collect(Collectors.toSet());

    List<Region> regionList = new ArrayList<>();

    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
      String line;
      reader.readLine(); // 헤더 스킵

      while ((line = reader.readLine()) != null) {

        /*
        쉼표로 구분된 CSV 파일의 각 줄을 columns 배열로 나누어 각 열의 데이터를 추출
        String line = "서울시,마포구,111.11,123.1234";
        String[] columns = line.split(",");
        String[] columns = {"서울시", "마포구", "111.11", "123.1234"};
         */
        String[] columns = line.split(",");

        String dosi = columns[0].trim();
        String sigungu = columns[1].trim();
        double lon = Double.parseDouble(columns[2].trim());
        double lat = Double.parseDouble(columns[3].trim());

        // 데이터 중복 체크
        String regionKey = dosi + "," + sigungu;
        if (!existingRegions.contains(regionKey)) {
          Region region = Region.builder().dosi(dosi).sigungu(sigungu).lon(lon).lat(lat).build();
          regionList.add(region);
          existingRegions.add(regionKey);
        }
      }

      // 데이터베이스에 저장
      if (!regionList.isEmpty()) {
        regionRepository.saveAll(regionList);
      }

    } catch (FileNotFoundException e) {
      throw new CustomException(CsvRegionExceptionType.FILE_NOT_FOUNDED);
    } catch (IOException e) {
      throw new CustomException(CsvRegionExceptionType.FILE_READ_ERROR);
    }
  }
}
