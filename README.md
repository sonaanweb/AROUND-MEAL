# 주변한끼 (Around Meal)
1. [프로젝트 개요](#1-프로젝트-개요)
2. [프로젝트 관리](#2-프로젝트-관리)
3. [기술 문서](#3-기술-문서)
4. [기능 구현](#4-기능-구현)
5. [트러블 슈팅](#5-트러블-슈팅)

## 1. 프로젝트 개요

### ⚙️ 기술 스택
![java](https://img.shields.io/badge/Java-17-blue?logo=java)
![spring-boot](https://img.shields.io/badge/SpringBoot-3.3.3-grren?logo=springboot)
![postgresql](https://img.shields.io/badge/PostgreSQL-16.4-blue?logo=postgresql)
![redis](https://img.shields.io/badge/Redis-7.4-red?logo=redis)

### ✔️ 요구사항
[위치 기반 맛집 추천 서비스](https://bow-hair-db3.notion.site/f2c5e47f67254726ba0ef8d894f7e8b9#f62425d8700b485da11338e3b0eb06c4)

### 👩🏻‍💻 팀 구성

| 이름  | 담당                                    |
|-----|-----------------------------------------|
| 유하진 | 데이터 파이프라인 구축(수집, 전처리, 저장, 자동화) |
| 김성은 | 맛집 목록 조회 API, 점심 추천 알림(Webhook) 전송 스케줄러  |
| 김연희 | 사용자 회원가입 API, 사용자 로그인 API, 사용자 정보 API, 인기있는 맛집만 캐싱|
| 안소나 | 시군구 목록 조회 API, 맛집 평가 생성 API         |
| 유서정 | 사용자 설정(좌표, 서비스 수신 여부) 업데이트 API, 맛집 상세정보 API, 조회수 N 이상만 캐싱|

</br>

## 2. 프로젝트 관리

<details>
<summary><strong>일정</strong></summary>

| 날짜 | 활동 |
| --- | --- |
| 24.08.27 (화) | 역할 분담, 기술 선택  |
| ~ 24.08.30 (금) | 요구사항 기능 개발, 추가 요구사항 분담 |
| ~ 24.09.04 (일)| 추가 요구사항 개발 |
| 24.09.04 (월) | 최종 README.md 작성 및 추가 요구사항 점검|

</details>

<details>
<summary><strong>협업 라이프사이클</strong></summary>

1. 브랜치 생성
2. 코드 작성
3. PR 생성
4. 리뷰 요청
5. dev 브랜치로 Merge
</details>

<details>
<summary><strong>이슈 관리</strong></summary>
<img src=https://github.com/user-attachments/assets/f74c4fb2-15ac-4a68-b040-5241ce8d1e29>

</details>

<details>
<summary><strong>컨벤션</strong></summary>

- **Branch**
    - **전략**

      | Branch Type | Description |
      | --- | --- |
      | `dev` | 주요 개발 branch, `main`으로 merge 전 거치는 branch |
      | `feature` | 각자 개발할 branch, 기능 단위로 생성하기, 할 일 issue 등록 후 branch 생성 및 작업 |

    - **네이밍**
        - `{header}/#{issue number}`
        - 예) `feat/#1`

- **커밋 메시지 규칙**
    ```bash
    > [HEADER] : 기능 요약
    
    - [CHORE]: 내부 파일 수정
    - [FEAT] : 새로운 기능 구현
    - [ADD] : FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시
    - [FIX] : 코드 수정, 버그, 오류 해결
    - [DEL] : 쓸모없는 코드 삭제
    - [DOCS] : README나 WIKI 등의 문서 개정
    - [MOVE] : 프로젝트 내 파일이나 코드의 이동
    - [RENAME] : 파일 이름의 변경
    - [MERGE]: 다른 브렌치를 merge하는 경우
    - [STYLE] : 코드가 아닌 스타일 변경을 하는 경우
    - [INIT] : Initial commit을 하는 경우
    - [REFACTOR] : 로직은 변경 없는 클린 코드를 위한 코드 수정
    
    ex) [FEAT] 게시글 목록 조회 API 구현
    ex) [FIX] 내가 작성하지 않은 리뷰 볼 수 있는 버그 해결
    ```

- **Issue**
    ```bash
    🍚 Description
    <!-- 진행할 작업을 설명해주세요 -->
    
    🍚 To-do
    <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
    [ ] todo1
    
    🍚 ETC
    <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
    ```

- **PR**
  - **규칙**
    - branch 작업 완료 후 PR 보내기
    - 항상 local에서 충돌 해결 후 remote에 올리기
    - PR 후 디스코드에 공유하기
    - 당일 PR은 당일에 리뷰하기
    - 최소 2명 이상의 동의를 받으면 merge하기
    - review 반영 후, 본인이 merge
    ```bash
        > [MERGE] {브랜치이름}/{#이슈번호}
        ex) [MERGE] setting/#1
    ```
  - **Template**
    ```bash
    🍚 Description
    <!-- 진행할 작업을 설명해주세요 -->
    
    🍚 To-do
    <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
    [ ] todo1
    
    🍚 ETC
    <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
    ```
</details>

<br>

## 3. 기술 문서

### 🛠️ 아키텍처
<img width="707" alt="architecture" src="https://github.com/user-attachments/assets/bea07fac-77ae-4fee-8e85-9f0e20905882">


### 📄 API 명세서

▶️ [API 명세서 자세히보기](https://www.notion.so/API-eedd570467c7427782b8b71a10ae4001?pvs=4)

| API 명칭 | HTTP 메서드 | 엔드포인트 | 설명 |
| --- | --- | --- | --- |
| **사용자 회원가입** | POST | `/api/members` | 새로운 사용자를 등록합니다. |
| **사용자 로그인** | POST | `/api/members/login` | 사용자를 로그인시킵니다. |
| **사용자 로그아웃** | POST | `/api/members/logout`  | 사용자를 로그아웃시킵니다. |
| **사용자 정보 조회** | GET | `/api/members` | 사용자 정보를 조회합니다. |
| **사용자 토큰 재발급** | POST | `/api/members/refresh_token`  | 리프레시 토큰을 재발급합니다. |
| **사용자 설정 업데이트** | POST | `/api/locations/real-time`  | 사용자의 위치를 업데이트 합니다. |
| **맛집 목록 조회** | GET | `/api/restaurants` | 맛집 목록을 조회합니다. |
| **맛집 평가 생성** | POST | `/api/rating` | 맛집에 대한 평가를 생성합니다. |
| **맛집 상세정보 조회** | GET | `/api/restaurants/{restaurantId}` | 맛집 상세정보를 조회합니다. |
| **시군구 목록 조회** | GET | `/api/region/allRegionList` | 시군구 목록을 조회합니다. |

</br>

<details>
<summary><strong>ERD</strong></summary>
<img src=https://github.com/user-attachments/assets/4fc9a113-aee1-4cd9-9f6f-8ec9dab376a8>
</details>

<details>
<summary><strong>디렉토리 구조 - 도메인이 많지 않아 계층별로 패키지를 나누었습니다.</strong></summary>


```bash
├── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── lucky
    │   │           └── around
    │   │               └── meal
    │   │                   ├── Application.java
    │   │                   ├── cache
    │   │                   │   ├── entity
    │   │                   │   │   └── RestaurantForRedis.java
    │   │                   │   ├── repository
    │   │                   │   │   └── RestaurantForRedisRepository.java
    │   │                   │   └── service
    │   │                   │       ├── RatingCountService.java
    │   │                   │       └── ViewCountService.java
    │   │                   ├── common
    │   │                   │   ├── WebClientConfig.java
    │   │                   │   ├── config
    │   │                   │   │   ├── CacheConfig.java
    │   │                   │   │   ├── GeometryConfig.java
    │   │                   │   │   ├── RedisConfig.java
    │   │                   │   │   └── SpringSecurityConfig.java
    │   │                   │   ├── redis
    │   │                   │   │   └── RedisRepository.java
    │   │                   │   ├── security
    │   │                   │   │   ├── details
    │   │                   │   │   │   ├── PrincipalDetails.java
    │   │                   │   │   │   └── PrincipalDetailsService.java
    │   │                   │   │   ├── filter
    │   │                   │   │   │   ├── JwtAuthenticationFilter.java
    │   │                   │   │   │   └── JwtAuthorizationFilter.java
    │   │                   │   │   ├── handler
    │   │                   │   │   │   ├── CustomAccessDeniedHandler.java
    │   │                   │   │   │   ├── CustomAuthenticationEntryPoint.java
    │   │                   │   │   │   ├── CustomLogoutHandler.java
    │   │                   │   │   │   └── CustomLogoutSuccessHandler.java
    │   │                   │   │   ├── record
    │   │                   │   │   │   └── JwtRecord.java
    │   │                   │   │   ├── redis
    │   │                   │   │   │   ├── RefreshToken.java
    │   │                   │   │   │   └── RefreshTokenRepository.java
    │   │                   │   │   └── util
    │   │                   │   │       ├── CookieProvider.java
    │   │                   │   │       └── JwtProvider.java
    │   │                   │   └── util
    │   │                   │       └── GeometryUtil.java
    │   │                   ├── controller
    │   │                   │   ├── LocationController.java
    │   │                   │   ├── MemberController.java
    │   │                   │   ├── RatingController.java
    │   │                   │   ├── RegionController.java
    │   │                   │   ├── RestaurantController.java
    │   │                   │   ├── dto
    │   │                   │   │   ├── GetRestaurantsDto.java
    │   │                   │   │   └── MemberDto.java
    │   │                   │   ├── record
    │   │                   │   │   ├── LoginRecord.java
    │   │                   │   │   ├── RegionRecord.java
    │   │                   │   │   └── RegisterRecord.java
    │   │                   │   ├── request
    │   │                   │   │   ├── MemberLocationRequestDto.java
    │   │                   │   │   ├── RatingRequestDto.java
    │   │                   │   │   ├── RestaurantDetailsRequestDto.java
    │   │                   │   │   └── StaticLocationRequestDto.java
    │   │                   │   └── response
    │   │                   │       ├── LocationResponseDto.java
    │   │                   │       ├── RatingResponseDto.java
    │   │                   │       ├── RestaurantDetailResponseDto.java
    │   │                   │       └── StaticLocationResponseDto.java
    │   │                   ├── datapipeline
    │   │                   │   ├── DataPipeLineService.java
    │   │                   │   ├── DataProcessService.java
    │   │                   │   ├── HashUtil.java
    │   │                   │   ├── RawDataLoadService.java
    │   │                   │   ├── RawRestaurant.java
    │   │                   │   └── RawRestaurantRepository.java
    │   │                   ├── entity
    │   │                   │   ├── Member.java
    │   │                   │   ├── Rating.java
    │   │                   │   ├── Region.java
    │   │                   │   ├── Restaurant.java
    │   │                   │   └── enums
    │   │                   │       ├── Category.java
    │   │                   │       └── MemberRole.java
    │   │                   ├── exception
    │   │                   │   ├── CustomException.java
    │   │                   │   ├── CustomExceptionHandler.java
    │   │                   │   └── exceptionType
    │   │                   │       ├── CommonExceptionType.java
    │   │                   │       ├── CsvRegionExceptionType.java
    │   │                   │       ├── DataExceptionType.java
    │   │                   │       ├── ExceptionType.java
    │   │                   │       ├── MemberExceptionType.java
    │   │                   │       ├── RegionExceptionType.java
    │   │                   │       ├── RegisterExceptionType.java
    │   │                   │       ├── RestaurantExceptionType.java
    │   │                   │       └── SecurityExceptionType.java
    │   │                   ├── repository
    │   │                   │   ├── MemberRepository.java
    │   │                   │   ├── RatingRepository.java
    │   │                   │   ├── RegionRepository.java
    │   │                   │   └── RestaurantRepository.java
    │   │                   └── service
    │   │                       ├── CsvRegionService.java
    │   │                       ├── JwtService.java
    │   │                       ├── LocationService.java
    │   │                       ├── MemberService.java
    │   │                       ├── RatingService.java
    │   │                       ├── RegionService.java
    │   │                       └── RestaurantService.java
    │   └── resources
    │       ├── application.properties
    │       ├── application.yml
    │       └── region
    │           └── sgg_lat_lon.csv
    └── test
        └── java
            └── com
                └── lucky
                    └── around
                        └── meal
                            ├── ApplicationTests.java
                            ├── cache
                            │   ├── RatingCountServiceTest.java
                            │   └── RestaurantRepositoryTest.java
                            ├── common
                            │   └── security
                            │       └── util
                            │           ├── CookieProviderTest.java
                            │           └── JwtProviderTest.java
                            └── service
                                ├── LocationServiceTest.java
                                ├── MemberServiceTest.java
                                └── RestaurantServiceTest.java
```
</details>

<br>

## 4. 기능 구현

### ⭐ 사용자 인증 시스템

#### ✨ 사용자 회원가입(담당: 김연희)
- 계정명 중복 검증 기능 구현
- 비밀번호 BCrypt 암호화 저장 기능 구현
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>계정명 중복 검증</strong></div>
        <div>이번 프로젝트는 사용자의 위치를 활용한 서비스가 주 목적이기 때문에 회원가입 정보 검증 과정은 간단하게 중복검증 기능만 구현했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/MemberController.java" target="_blank">MemberController</a></br>
    </div>
</details>

#### ✨ 사용자 로그인(담당: 김연희)
- 스프링 시큐리티 기능 구현
- JWT 방식 차용하여 로그인 시 AccessToken, RefreshToken 발급 기능 구현
- 핸들러를 사용한 필터 체인 내 예외처리 기능 구현
- RefreshToken으로 AccessToken, RefreshToken 재발급 기능 구현
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>스프링 시큐리티 구현</strong></div>
        <div>요청의 인증, 인가 처리를 간편하게 처리할 수 있기 때문에 스프링 시큐리티를 도입하였습니다. 로그인 처리를 전담하는 필터와, JWT 검증을 전담하는 필터를 커스텀하였고 각 필터에서 발생하는 예외는 예외의 종류에 따라 메세지를 달리하여 클라이언트에게 전달되도록 구현했습니다.</div>
        <div><strong>RefreshToken 발급 구현</strong></div>
        <div>AccessToken만으로는 보안상 취약하다고 판단되어 RefreshToken도 함께 발급하는 방식으로 구현했습니다. RefreshToken은 Redis에 저장하여 빠른 접근이 가능하도록 하였습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/tree/dev/src/main/java/com/lucky/around/meal/common/security" target="_blank">security</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/MemberController.java" target="_blank">MemberController</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/MemberService.java" target="_blank">MemberService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/JwtService.java" target="_blank">JwtService</a></br>
    </div>
</details>

#### ✨ 사용자 정보(담당: 김연희)
- 사용자 상세 정보 반환 기능 구현

<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/MemberController.java" target="_blank">MemberController</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/MemberService.java" target="_blank">MemberService</a></br>
    </div>
</details>

#### ✨ 사용자 설정 업데이트(담당: 유서정)
- 정적 위치 정보 저장 (DB)
- 실시간 위치 정보 저장 (Redis)
- 맛집 추천 서비스 사용 여부 업데이트
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>정적 위치 정보의 일관된 관리</strong></div>
        <div>장기적인 데이터로 데이터베이스에 보관하여, 안전하고 일관된 위치 데이터를 제공하도록 구현하였습니다.</div><br>
        <div><strong>실시간 위치 정보 처리의 효율성</strong></div>
        <div>실시간으로 자주 업데이트되는 위치 정보는 Redis를 사용하여 빠르게 읽고 쓸 수 있도록 하여, 사용자가 실시간으로 자신의 위치 정보를 즉시 반영할 수 있도록 하였습니다.</div><br>
	<div><strong>맛집 추천 서비스 활성화/비활성화 기능</strong></div>
        <div>사용자가 해당 설정을 변경하면, 이 값이 데이터베이스에 저장되어 서비스의 상태를 반영하도록 구현하였습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/LocationService.java" target="_blank">LocationService</a></br>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/LocationController.java" target="_blank">LocationController</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/common/redis/RedisRepository.java" target="_blank">StringRedisTemplate</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/MemberService.java" target="_blank">MemberService</a></br>
    </div>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/MemberController.java" target="_blank">MemberController</a></br>
    </div>
</details>

---
### ⭐ RESTful API

#### ✨ 시군구 목록(담당: 안소나)
- DB 내 존재하는 시군구 목록을 전체 조회하는 페이지
- 시군구 정보가 담긴 csv 파일을 프로젝트 실행 시 DB에 바로 업로드 하는 기능 구현
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>CSV 파일을 통한 데이터 업로드 자동화</strong></div>
        <div>프로젝트 실행 시 별도의 작업 없이 DB내 CSV 파일 데이터가 자동 업로드되도록 구현했습니다. 약 300개 정도의 고정된 데이터이므로 파일을 읽어들이는 while문 내에서 직접 저장하지 않고, 유효성 검사를 마친 데이터를 List에 담아 한번에 저장하는 방식을 선택했습니다. </div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/CsvRegionService.java" target="_blank">CsvRegionService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RegionService.java" target="_blank">RegionService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RegionController.java" target="_blank">RegionController</a></br>
    </div>
</details>

#### ✨ 맛집 목록(담당: 김성은)
- PostGIS를 활용하여 사용자의 현재 위치를 기반으로 범위 내의 맛집을 조회하는 기능
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>1. 정렬 기능</strong></div>
        <div>주변 맛집을 가까운 거리 순, 평점이 높은 순으로 조회하는 정렬 옵션을 제공합니다</div>
        <div><strong>2. 위치 범위 선택</strong></div>
        <div>위치 제공에 동의한 사용자의 현재 위치를 바탕으로 주변 맛집을 검색하며, 0.5Km, 1km 두가지 옵션을 선택하여 검색할 수 있습니다</div>
        <div><strong>3. PostGIS 플러그인 사용</strong></div>
        <div>Postgresql 플러그인 PostGIS를 사용하여 사용자 위치과 가게 위치를 Point로 표현하고, ST_DWithin 함수를 활용하여
효율적으로 특정 반경 내의 객체를 검색합니다 </div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RestaurantController.java#L43-L51" target="_blank">RestaurantController</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/215c43d72be822dc5ab1f1983c056f30c562930f/src/main/java/com/lucky/around/meal/service/RestaurantService.java#L81-L100" target="_blank">RestaurantService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/215c43d72be822dc5ab1f1983c056f30c562930f/src/main/java/com/lucky/around/meal/repository/RestaurantRepository.java#L15-L27" target="_blank">RestaurantRepository</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/entity/Restaurant.java" target="_blank">Restaurant(Entity)</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/common/util/GeometryUtil.java" target="_blank">GeometryUtil(support for gis)</a></br>
    </div>
</details>

#### ✨ 맛집 상세정보(담당: 유서정)
- 캐시를 활용한 맛집 상세 정보 조회
- 조회수 기준으로 맛집 목록 정렬
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>효율적인 데이터 접근 및 응답 속도 개선</strong></div>
        <div>캐시를 활용하여 자주 조회되는 맛집의 상세 정보를 빠르게 제공할 수 있도록 구현하였습니다. 
		캐시가 없는 경우에는 데이터베이스에서 해당 정보를 조회하여 캐시에 저장하고, 이후 요청 시 캐시된 데이터를 반환합니다.
		이를 통해 부하 테스트 결과, 데이터 처리량이 4.92배 향상되었고 응답 시간은 1.2배 단축되었습니다.
		이로 인해 사용자에게는 빠르고 일관된 정보를 제공할 수 있었으며, 데이터베이스의 부하도 크게 줄일 수 있었습니다.<br>
<p>
    <img src="https://github.com/user-attachments/assets/cede1456-2f76-4827-b386-df2c2bebc672" alt="Performance Test Results">
</p>
</div>
        <div><strong>조회수 기준 정렬</strong></div>
        <div>맛집 목록을 조회수 기준으로 정렬하여 사용자에게 인기 있는 맛집을 우선적으로 보여줍니다. 
		이를 통해 사용자 경험을 향상시키고, 자주 조회되는 맛집이 상위에 노출되도록 하여 사용자에게 유용한 정보를 제공합니다. 
		정렬 방향을 지원하여 다양한 요구 사항에 맞게 데이터를 제공할 수 있도록 구현하였습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/repository/RestaurantRepository.java" target="_blank">RestaurantRepository</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RestaurantService.java" target="_blank">RestaurantService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RestaurantController.java" target="_blank">RestaurantController</a></br>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/cache/service/ViewCountService.java" target="_blank">ViewCountService</a>
    </div>
</details>

#### ✨ 맛집 평가 생성(담당: 안소나)
- 로그인한 유저가 특정 식당에 대한 평가를 작성하는 페이지
- 평가가 생성 되면 해당 맛집의 평점이 업데이트 되고, 전체 평점 평균을 계산하여 업데이트하는 로직 구현
- 동일한 유저가 한 식당에 대해 중복 평가가 불가능하도록 예외처리

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>평가 중복 방지 및 데이터 무결성 유지</strong></div>
        <div>한 회원이 동일한 음식점에 중복 평가를 하지 않도록 중복 검사 로직을 추가하여 데이터의 무결성을 보장했습니다. 또한, 평가 생성 시 평가 요청 DTO, 컨트롤러 내에 `@Valid`를 적용하여 잘못된 데이터 입력을 방지하도록 구현했습니다.</div>
        <br>
        <div><strong>음식점 평점 업데이트</strong></div>
        <div>음식점에 새로운 평가가 등록될 때 해당 음식점의 평균 평점을 갱신하는 로직을 추가해 최신 데이터를 유지하도록 구현했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RatingService.java" target="_blank">RatingService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RatingController.java" target="_blank">RatingController</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/request/RatingRequestDto.java" target="_blank">RatingRequestDto</a></br>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/repository/RatingRepository.java" target="_blank">RatingRepository</a></br>
   </div>
</details>

---
### ⭐ 데이터 파이프라인 (담당: 유하진)
- 공공데이터 OpenAPI 활용
- 데이터 파이프라인 (수집,가공, 저장) 구현
- 스케줄링을 통한 주기적인 데이터 파이프라인 실행
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>읽기/가공 책임분리</strong></div>
        <div>데이터 읽어오기 (API 호출 -> 원본 저장)과 데이터 가공하기 (원본 테이블 -> 가공 데이터 저장)로 책임을 분리했습니다. 데이터를 가공하는 도중 오류가 발생하더라도, 처음인 데이터 읽어오기부터 실행하지 않고 데이터 가공하기 과정만 재시도 됩니다. 또한 원본 테이블과 가공 테이블도 따로 관리되어, 원본 데이터를 그대로 보존하고 있으므로, 데이터를 다양하게 가공할 수 있습니다.</div>
        </br>
        <div><strong>해시와 플래그를 활용한 데이터 변경 감지</strong></div>
        <div>해시값을 비교하여 변경된 데이터를 식별합니다. 변경된 데이터는 변경 플래그로 명시합니다. 가공 테이블에는 변경 플래그로 표시된 데이터만 저장하도록 합니다. 데이터 저장 및 가공하는 처리 비용을 절감할 수 있습니다.</div>
        </br>
        <div><strong>데이터 파이프라인 비동기화</strong></div>
        <div>@Async 어노테이션을 사용하여 데이터 파이프라인 비동기처리할 수 있도록 하였습니다. 데이터 파이프라인이 진행되더라도 메인 스레드는 기다리지 않고 작업을 진행합니다.</div>
        </br>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/tree/dev/src/main/java/com/lucky/around/meal/datapipeline" target="_blank">데이터 파이프라인 패키지</a>
    </div>
</details>

---
### ⭐ Webhook
#### ✨ Discord Webhook 을 활용한 점심 추천 서비스(담당: 김성은)
- 점심시간에 사용자 위치를 기반으로 맛집을 추천하는 스케줄러 구현
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>1. 점심 시간에 자동으로 사용자 주변 위치의 맛집을 webhook으로 추천하도록하여 서비스 고도화</strong></div>
        <div>사용자 위치를 기반으로 반경 1km 이내면서, 1km 반경 이내 맛집들의 평균 평점보다 높은 곳을 랜덤하게 discord webhook을 통해 추천하는 기능을 개발하였습니다.</div>
        <div><strong>2. 맛집 추천 알림 전송 로직 비동기화</strong></div>
        <div>맛집 추천 알림 전송 로직을 비동기로 처리하여 회원들에게 알림을 전송하는 로직이 병렬처리 될 수 있도록 개선하였습니다</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RecommendRestaurantService.java" target="_blank">RecommendRestaurantService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/common/discord/service/DiscordService.java" target="_blank">DiscordService</a></br>
    </div>
</details>

---
### ⭐ 대규모 트래픽 대비 캐싱
#### ✨ 시군구 데이터 캐싱(담당: 안소나)
- 긴시간 변동 없는 성격을 지닌 데이터이기에 Redis와 연동한 캐싱을 진행하여 전체 조회 속도 개선
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>캐싱을 통한 조회 속도 개선</strong></div>
        <div>시군구 정보는 변경되지 않기 때문에 Redis와 같은 캐싱처리를 도입하여 데이터베이스에 불필요한 접근을 줄였습니다. 이를 통해 조회 성능을 약 3배 향상시켰습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/common/config/CacheConfig.java" target="_blank">CacheConfig</a>
    </div>
</details>

#### ✨ 평가수 N개 이상 맛집 캐싱(담당: 김연희)
- Redis 와 스케줄러를 이용해서 하루 한 번 새벽 1시에 자동 업로드 기능 구현
- 데이터 동기화를 위해 필드 값 수정이 용이하도록 해시맵으로 변환 후 저장 기능 구현
- 평가가 N개 이상 존재하는 맛집 목록 조회 요청 시 캐싱된 데이터 반환 기능 구현
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>새벽 1시 자동 업로드</strong></div>
        <div>평가수가 몇 시간 단위로 크게 변하지 않을 것 같아 갱신 주기를 하루로 설정하고 서비스 사용량이 비교적 적을 것이라고 예상되는 새벽 1시에 교체작업을 진행하기로 결정했습니다. 목록이 교체되는 시간은 매우 짧을 것이라고 예상되어 Read Through 패턴을 적용하여 DB에서 데이터를 조회해오지는 않게 하였으며 만일 교체되는 시간에 요청이 있을 시 양해 메세지를 반환하도록 구현하였습니다.</div>
        <div><strong>해시맵 저장</strong></div>
        <div>DB와 Redis의 정보 동기화를 위해서는 평가 생성 등의 요청이 생길 때 양쪽 모두 정보를 업데이트해줘야합니다.(Write Through 패턴 적용) 이때 Redis에서는 한 객체의 한 필드만 업데이트하면 되기 때문에 객체가 아닌 해시맵 형태로 저장하는 것이 효율적이라고 판단했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/cache/service/RatingCountService.java" target="_blank">RatingCountService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RestaurantController.java" target="_blank">RestaurantController: getRestaurantsByRaitinCount</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RestaurantService.java" target="_blank">RestaurantService: RestaurantService</a></br>
    </div>
</details>

#### ✨ 조회수 N회 이상 맛집 캐싱(담당: 유서정)
- 스케줄러를 사용하여 매시 정각, 일정 조회수 이상인 맛집의 상세 정보를 Redis에 자동으로 캐싱
- 상세 정보 조회 시 조회수 증가 기능
- 스케줄러를 사용하여 1시간 단위로 조회수를 초기화
- 조회수 데이터 조회
<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>효율적인 데이터 관리</strong></div>
        <div>조회수 기준으로 맛집 정보를 캐싱하여 자주 조회되는 맛집의 정보를 빠르게 제공할 수 있습니다. 
		또한, 매시 정각마다 캐시 데이터를 정기적으로 업데이트하여 최신 정보를 유지합니다. 
		이를 통해 시스템의 성능을 안정적으로 관리하고, 캐시된 데이터를 활용하여 응답 속도를 개선하며 사용자 경험을 향상시키도록 구현하였습니다.</div><br>
	<div><strong>상세 조회 시 조회수 증가</strong></div>
	<div>사용자가 맛집의 상세 정보를 조회할 때마다 해당 맛집의 조회수를 증가시킵니다. 
		이를 통해 인기 있는 맛집의 정보를 보다 효과적으로 캐싱하고, 사용자와의 상호작용에 따라 데이터를 동적으로 관리할 수 있도록 구현하였습니다.</div><br>
        <div><strong>1시간 기준 조회수와 영구적 조회수의 분리 관리</strong></div>
        <div>1시간 기준 조회수는 단기적인 조회 패턴을 반영하며, 1시간마다 초기화되어 최신 상태를 유지합니다. 
		반면, 영구적인 조회수는 장기적인 조회 패턴을 추적하고, 전체 조회수를 기반으로 맛집 정보를 캐시합니다. 
		이러한 분리를 통해 데이터의 정확성을 유지하고, 실시간 및 장기적인 분석 모두를 지원하도록 구현하였습니다.</div><br>
	<div><strong>유연한 데이터 관리</strong></div>
        <div>Redis를 활용하여 캐시된 데이터를 효율적으로 관리하고 분석할 수 있는 기능을 제공합니다. 
		이를 통해 1시간 기준 조회수와 같은 단기 데이터뿐만 아니라 영구적인 데이터의 일관성을 유지하며, 시스템의 데이터 관리 및 분석을 용이하게 합니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/common/config/CacheConfig.java" target="_blank">CacheConfig: 직렬화/역직렬화 커스터마이징</a></br>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/service/RestaurantService.java" target="_blank">RestaurantService</a></br>
        <a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/cache/service/ViewCountService.java" target="_blank">ViewCountService</a></br>
	<a href="https://github.com/wanted-pre-onboarding-backend-team-7/AROUND-MEAL/blob/dev/src/main/java/com/lucky/around/meal/controller/RestaurantController.java" target="_blank">RestaurantController</a></br>
    </div>
</details>


<br>

## 5. 트러블 슈팅
👉[캐싱 전략]</br>
👉[데이터 파이프라인 전략]</br>
👉[postgis]</br>
👉[스프링 시큐리티 필터체인 예외처리](https://www.notion.so/b599de75f9594552890cdd68bd0d0841) </br>
👉[JWT 버전차이로 인한 파싱 오류](https://www.notion.so/JWT-200c685081cf4697a50ea810c1b0c614) </br>
