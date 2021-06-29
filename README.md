# api-server-spring-boot

소프트스퀘어드 스프링부트 템플릿 입니다.

마지막 업데이트 : 20년 12월 11일

# 목차
- [구조](#구조)
- [사용법](#사용법)
- [설명](#설명)
- [라이센스](#라이센스)
- [배포법](#배포법)

## 구조
```text
api-server-spring-boot
  > build
  > gradle
  > logs
    | app.log // warn, error 레벨에 해당하는 로그가 작성 되는 파일
    | app-%d{yyyy-MM-dd}.%i.gz
    | error.log // error 레벨에 해당하는 로그가 작성 되는 파일
    | error-%d{yyyy-MM-dd}.%i.gz
  > src.main.java.com.softsquared.template
    > config
      > secret
        | Secret.java // git에 추적되지 않아야 할 시크릿 키 값들이 작성되어야 하는 곳
      > utils
        | AES128.java
        | JwtService.java
        | ValidationRegex.java
      | BaseEntity.java // createdAt, updatedAt 과 같이 테이블의 공통 컬럼들의 엔티티 클래스
      | BaseException.java // Controller, Service, Provider 에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
      | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
      | BaseResponseStatus.java // Controller, Service, Provider 에서 사용 할 Response Status 관리 클래스 
      | Constant.java // 상수 값들을 관리하는 곳
    > src
      > test
        | TestController.java // logger 어떻게 써야하는지 알아놓으라고 설명용으로 만든 클래스
      > user
        > models //
          | JwtRes.java
          | LoginReq.java
          | UserInfo.java
          | UserInfoDetailRes.java
          | UserInfoReq.java
          | UserInfoRes.java
        | UserInfoController.java
        | UserInfoProvider.java
        | UserInfoRepository.java
        | UserInfoService.java
      | WebSecurityConfig.java // spring-boot-starter-security, jwt 를 사용하기 위한 클래스 
    | TemplateApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | logback-spring.xml // logger 사용시 console, file 설정 값 정의 파일
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳
```

### 다른 언어와의 구조 비교
#### PHP (패키지매니저 = composer)
> Request(시작)-> Router (Index.php) -> Controller -> PDO (DB) -> Response(끝)

#### Node.js (패키지매니저 = npm)
> Request -> Router (*router.js) -> Controller (*Controller.js) -> DAO (DB) -> Response

#### Springboot java (패키지매니저 = Maven (= Spring 선호), Gradle (Springboot 선호))
> Request -> *Controller.java(=Router+Controller) -> Service (CUD) / Provider (R) (=Controller) -> Repository (DB) -> Response


## 사용법
템플릿 프로젝트는 도메인 이름이 `com.example.template` 로 되어있습니다.

그러므로 프로젝트를 그대로 사용하지 말고 위에 작성한 환경과 구조를 이해하고 아래 내용을 참고하여 새로 프로젝트를 만들어서 사용해주세요. 

1) 새로 프로젝트를 생성하시고 build.gradle 파일을 참고해서 값 설정 및 아래 dependencies 부분을 추가해주세요. 
```text
dependencies {
    // Spring Boot Starter
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // DB
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'mysql:mysql-connector-java'

    // Web
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // Security, Authentication
    implementation 'org.springframework.boot:spring-boot-starter-security'
    compile group: 'io.jsonwebtoken', name: 'jjwt', version: '0.7.0'
}
```

## 설명

> 들어가기에 앞서 스프링, 스프링 부트는 `어노테이션`을 다양하게 아는 것이 중요하다. SpringBoot의 시작점을 알리는 `@SpringBootApplication` 어노테이션 뿐만 아니라 `스프링 부트 어노테이션` 등의 키워드로 구글링 해서 **스프링 부트에서 자주 사용되는 다양한 어노테이션을 이해하고 외워두자.**

템플릿은 크게 log폴더와 src 폴더로 나뉜다. log는 통신 시에 발생하는 오류들을 기록하는 곳이다. 실제 코드는 src를 살펴보면 된다.

### src - main - resources

`application.yml` 

에서 **포트 번호를 정의**하고 **DataBase 연동**을 위한 값을 설정한다. 추가로 `show-sql: true` 로 설정하면 JPA를 통해 생성되는 Query문을 앱 실행 시에 보여준다. 자신이 짠 코드가 의도한 Query문을 뱉는지 확인할 때 유용하다.

`logback-spring.xml` 

logs 폴더에 로그 기록을 어떤 형식으로 남길 것인지 설정한다. logs 폴더에 어떻게 기록이 남겨져 있는지 확인해보시라. (커스텀 하지 않아도 된다면`logback-spring.xml` 를 수정할 필요는 없다.)

### src - main - java

`com.softsquared.template` 패키지에는 크게 `config` 폴더, `src` 폴더와 이 프로젝트의 시작점인 `TemplateApplication.java`가 있다.

`TemplateApplication.java` 은 스프링 부트 프로젝트의 시작을 알리는 `@SpringBootApplication` 어노테이션을 사용하고 있다. (구글링 통해 `@SpringBootApplication`의 다른 기능도 살펴보자.)

 `src`폴더에는 실제 **API가 동작하는 프로세스**를 담았고 `config` 폴더에는 `src`에서 필요한 **부가 기능**들을 (Validation, JWT, Secret key, Base 등) 모아놨다.

`src`를 자세하게 살펴보자. `src`는 각 **도메인**별로 패키지를 구분해 놓는다. 현재는 `user` 도메인과 `test` 도메인이 있다. **도메인**이란 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면 된다.

이 도메인들은 API 통신에서 어떤 프로세스로 처리되는가? API 통신의 기본은 Request → Response이다. 스프링 부트에서 **어떻게 Request를 받아서, 어떻게 처리하고, 어떻게 Response 하는지**를 중점적으로 살펴보자. 전반적인 API 통신 프로세스는 다음과 같다.

> **Request** → `XXXController.java`(=Router+Controller) → `Service` (CUD) / `Provider` (R) (=Business Logic) → `Repository` (DB) → **Response**

#### 1. Controller / `UserInfoController.java`  / @RestController

> 1) API 통신의 **Routing** 처리 
> 2) Request를 다른 계층에 넘기고 처리된 결과 값을 Response 해주는 로직
>  + Request의 **형식적 Validation** 처리 (DB를 거치지 않고도 검사할 수 있는)

**1) `@Autowired`**

UserInfoController의 생성자에 `@Autowired` 어노테이션이 붙어있다. 이는 **의존성 주입**을 위한 것으로, `UserInfoController`  뿐만 아니라 다음에 살펴볼 `UserInfoService`, `UserInfoProvider`의 생성자에도 각각 붙어 있는 것을 확인할 수 있다. 간단히 요약하면 객체 생성을 자동으로 해주는 역할이다. 자세한 프로세스는 구글링을 통해 살펴보자.

나머지 어노테이션들은 구글링을 통해 이해하자.

**2) `BaseResponse`**

Response할 때, 공통 부분은 묶고 다른 부분은 제네릭을 통해 구현함으로써 반복되는 코드를 줄여준다. (`BaseResponse.java` 코드 살펴 볼 것. 여기에 쓰이는`BaseResponseStatus` 는 `enum`을 통해 Status 값을 관리하고 있다.)

**3) 메소드 네이밍룰**

이 템플릿에서는 사용되는 메소드 명명 규칙은 다음과 같다.

> HTTP Method + 핵심 URI

- **GET** `/users` 를 처리하는 메소드명 → getUsers
- **PATCH** `/users` 를 처리하는 메소드명 →patchUsers

항상 이 규칙을 따라야 하는 것은 아니지만, 네이밍은 통일성 있게 해주는 게 좋다.

**4) Res, Req 네이밍룰**

각 메소드에서 사용되는 Res, Req 모델의 명명 규칙도 메소드 명과 비슷하다.

> HTTP Method + 핵심 URI +**Res/Req**

**Patch** `/users/:userId` → PatchUserRes / PatchUserReq

이 Res, Req 모델은 `(도메인명) / models` 폴더에 만들면 된다.

#### 2. Service 와 Provider / `UserInfoService.java` `UserInfoProvider.java` / @Service

> 1) **비즈니스 로직**을 다루는 곳 (DB 접근[CRUD], DB에서 받아온 것 형식화)
>  + Request의 **의미적** **Validation** 처리 (DB를 거쳐야 검사할 수 있는)

`Service`와 `Provider`는 비즈니스 로직을 다루는 곳이다. **CRUD** 중 **R(Read)** 에 해당하는 코드가 긴 경우가 많기 때문에 **R(Read)** 만 따로 분리해 `Service`는 **CUD(Create, Update, Delete)** 를, `Provider`는 **R(Read)** 를 다루도록 했다. 유지 보수가 용이해진다.

`Provider`
> **R(Read)** 와 관련된 곳이다. DB에서 select 해서 얻어온 값을 가공해서 뱉어준다.

`Service`
> **CUD(Create, Update, Delete)** 와 관련된 곳이다. **CUD**에서 **R**이 필요한 경우가 있는데, 그럴 때는 `Provider`에 구성되어 있는 것을 `Service`에서 사용하면 된다.

**1) 메소드명**

메소드의 prefix로 다음 규칙을 따르고 있다.

C → createXXX `createUserInfo`

R → retrieveXXX `retrieveUserInfoList`

U → updateXXX `updateUserInfo`

D → deleteXXX `deleteUserInfo`

**2) BaseException**

`BaseException`을 통해 `Service`나 `Provider`에서 `Controller`에 Exception을 던진다. 마찬가지로 Status 값은 `BaseResponseStatus` 의 `enum`을 통해 관리한다. 

#### 3. Repository / `UserInfoRepository.java` / @Repository

DB를 객체지향적으로 접근하게 해주는 곳

Repository는 JPA(Java Persistent API)이다. 이 템플릿 에서는 JPA를 구현한 Hibernate를 사용하고 있는데 Hibernate는 ORM, 즉 Database 객체지향으로 접근하게 해주는 프레임워크다. 쉽게 표현하면 객체지향 코드를 통해 DB Query를 생성해준다.

```java
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
}
```

자세하게 살펴보자. `UserInfoRepository`는 `CrudRepository`를 상속받고 있고(자세한 건 `CrudRepository`를 직접 살펴보자.) 제너릭 인자값으로 UserInfo(DB Table명), Integer(그 Table의 PK type)을 넘겨주고 있다. 

**1) DB 테이블 클래스, `UserInfo.java`**

`@Entity`, `@Id`, `@GeneratedValue`, `@Column` 등은 JPA에서 제공하는 어노테이션이다. 각 어노테이션은 반드시 알고 있어야 하며 자세한 내용은 주석과 구글링을 통해 익혀두자.

`@Data`, `@NoArgsConstructor` 등은 lombok에서 제공하는 어노테이션이다. lombok 어노테이션은 코드를 단순화시켜 주지만 필수 어노테이션은 아니다. 각각에 대한 의미와 활용 역시 구글링을 통해 익혀두자.

**2) BaseEntity**

`UserInfo` Table 말고도 수없이 많은 Table이 존재한다. 이 Table 들의 공통된 컬럼들을 모아놓은 Entity를 `BaseEntity`로 구현했다. 가령, `createdAt`, `updatedAt`과 같은 컬럼이다.

**3) 테이블 관계 1:1, 1:N, N:M**

현재 `UserInfo`는 1:1 구조에서의 동작만 구현되어 있다. 1:N, N:M 구조를 사용하고 싶을 때는 더 많은 설계 로직이 필요하다. 

**4) 메소드**

메소드가 실제 Query로 어떻게 매칭되는지 `CrudRepository` 와 로그를 통해서 살펴보자. 다음 findByStatus는 다음과 같이 매칭될 것이다.

```java
List<UserInfo> findByStatus(String status);
```

```sql
Select *
From UserInfo
Where Status = status
```

## 라이센스
소프트스퀘어드 내부용 템플릿이므로 함부로 배포하지 말아야 합니다. 

## 배포법
```text
1. 프로젝트 Build 시작
=> ./gradlew build

2. Jar 파일 실행
=>(포그라운드 실행의 경우)  java -jar ./build/libs/~~~~.jar
=> (백그라운드 실행의 경우) nohup java -jar ./build/libs/~~~~.jar
```