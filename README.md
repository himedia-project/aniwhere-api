# Aniwhere API

Aniwhere API는 애니메이션 관련 상품을 관리하고 사용자 상호작용을 처리하는 Spring Boot 기반의 백엔드 서비스입니다.


![aniwhere_detail](https://github.com/user-attachments/assets/dc3561fa-56b1-401e-9e68-2eaa1b19a339)


## 주요 기능

- **사용자 인증**

  - JWT 기반 인증
  - 소셜 로그인 지원 (카카오, 구글)
  - 역할 기반 접근 제어

- **상품 관리**

  - 애니메이션 상품 CRUD 작업
  - 카테고리 관리
  - 태그 시스템
  - 성인 콘텐츠 필터링
  - MD 추천 기능

- **파일 관리**

  - AWS S3 연동 파일 저장
  - 이미지 업로드 및 관리
  - 엑셀 파일을 통한 대량 상품 등록

- **보안**
  - Spring Security 구현
  - CORS 설정
  - 커스텀 예외 처리

## 기술 스택

- **프레임워크**: Spring Boot
- **보안**: Spring Security with JWT
- **데이터베이스**: JPA/Hibernate
- **파일 저장소**: AWS S3
- **엑셀 처리**: Apache POI
- **빌드 도구**: Gradle

### 주요 패키지 설명

- **config/**: 애플리케이션의 각종 설정을 담당하는 패키지입니다. AWS S3, 보안, JPA 등의 설정이 포함됩니다.

- **domain/**: 핵심 비즈니스 로직을 담당하는 패키지입니다. 회원과 상품 관련 기능이 도메인별로 구분되어 있습니다.

  - member/: 회원 가입, 로그인, 소셜 로그인 등 회원 관련 기능
  - product/: 상품 등록, 조회, 수정, 삭제 및 엑셀을 통한 대량 등록 기능

- **security/**: 보안 관련 기능을 담당하는 패키지입니다. JWT 인증, 인가 처리를 담당합니다.

- **exception/**: 애플리케이션에서 발생하는 예외를 처리하는 패키지입니다. 전역 예외 처리와 커스텀 예외가 포함됩니다.

- **util/**: 공통으로 사용되는 유틸리티 기능들을 모아둔 패키지입니다. 파일 처리, 엑셀 처리, JWT 관련 유틸리티가 포함됩니다.

- **props/**: 애플리케이션의 설정 속성들을 관리하는 패키지입니다. JWT, AWS 등의 설정 값을 관리합니다.

- **entity/**: 공통으로 사용되는 엔티티 클래스를 관리하는 패키지입니다. 생성일시, 수정일시 등을 관리하는 BaseEntity가 포함됩니다.

- **health/**: 서버의 상태를 체크하는 헬스 체크 엔드포인트를 관리하는 패키지입니다.


## 실행 및 빌드
0. 로컬 실행
   1. application.yml 내 sql init mode 주석풀기
   2. jpa ddl-auto create 실행한후, data.sql 문이 insert 
   3. 그후 ddl-auto none으로 변경
1. Build : `./gradlew build -x test`
2. Run : `nohup java -jar *.jar --spring.profiles.active=prod  &`

