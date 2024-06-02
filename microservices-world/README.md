# Microservices

## OpenAPI - Server API

* Create small service with use of start.spring.io
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.3.0
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsManager
    * Name: TrainTripsManager
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsManager
    * Packaging: Jar
    * Java: 21
    * Dependencies: Web, Actuator, Lombok
* [OpenAPI ready examples](https://nordicapis.com/3-example-openapi-definitions/)
* [OpenAPI file (train-api)](train-api-example.yml)

* Dependencies-config:
    * dependencies:
  ```xml
    <dependency>
        <groupId>org.openapitools</groupId>
        <artifactId>jackson-databind-nullable</artifactId>
        <version>0.2.6</version>
    </dependency>
  
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
  ```
    * plugin:
  ```xml
    <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>7.6.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>generate</goal>
                </goals>
                <configuration>
                    <inputSpec>${project.basedir}/api/train-api.yml</inputSpec>
                    <generatorName>spring</generatorName>
                    <apiPackage>com.zzpj.openapi.api</apiPackage>
                    <modelPackage>com.zzpj.openapi.model</modelPackage>
                    <configOptions>
                        <interfaceOnly>true</interfaceOnly>
                        <useJakartaEe>true</useJakartaEe>
                        <sourceFolder>src/gen/java/main</sourceFolder>
                        <library>spring-cloud</library>
                    </configOptions>
                </configuration>
            </execution>
        </executions>
    </plugin>
  ```
* run `mvn clean install`
* implement generated api

```java

@RestController
@RequiredArgsConstructor
class StationController implements StationsApi {

    private final StationService stationService;

    @Override
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }
}
```

```java

@Service
class StationService {
    public List<Station> getStations() {
        Station plStation = new Station().id(UUIDConstant.LODZ_STATION).name("Łódź Fabryczna").countryCode("PL").timezone("Europe/Warsaw");
        Station deStation = new Station().id(UUIDConstant.BERLIN_STATION).name("Berlin Hauptbahnhof").countryCode("DE").timezone("Europe/Berlin");
        Station frStation = new Station().id(UUIDConstant.PARIS_STATION).name("Paris Gare du Nord").countryCode("FR").timezone("Europe/Paris");
        Station itStation = new Station().id(UUIDConstant.ROME_STATION).name("Roma Termini").countryCode("IT").timezone("Europe/Rome");
        return List.of(plStation, deStation, frStation, itStation);
    }
}

@UtilityClass
class UUIDConstant {
    public static final UUID LODZ_STATION = UUID.fromString("b2cc2fe2-be4b-4733-9e21-9419711d0e04");
    public static final UUID PARIS_STATION = UUID.fromString("083d3f87-a738-4567-9472-2cf0c325c115");
    public static final UUID BERLIN_STATION = UUID.fromString("23c20c5f-d257-46f6-ace3-9074dad470a2");
    public static final UUID ROME_STATION = UUID.fromString("139d47ee-4724-4028-b261-e003fe5fcc40");
    public static final UUID BOOKING_UUID = UUID.fromString("29e1ca47-7989-4e4c-8c93-525e8f68ae71");
    public static final UUID TRIP_LODZ_BERLIN_UUID = UUID.fromString("ec60282f-24b7-4b60-b209-78c57f475112");
    public static final UUID TRIP_BERLIN_PARIS_UUID = UUID.fromString("5fddbeae-f1ba-497d-927c-49c9b9e6abd0");
    public static final UUID TRIP_PARIS_ROME_UUID = UUID.fromString("23f2c16b-8e35-4960-b8f4-bc18e80fe943");
    public static final UUID TRIP_ROME_LODZ_UUID = UUID.fromString("1231765e-360a-4eff-96e6-bfba39606c34");
}
```

```java

@RestController
@RequiredArgsConstructor
class Trips implements TripsApi {

    private final TripService tripService;

    @Override
    public ResponseEntity<List<Trip>> getTrips(UUID origin, UUID destination, OffsetDateTime date, Boolean bicycles, Boolean dogs) {
        return ResponseEntity.ok(tripService.getTrips(origin, destination, date, bicycles, dogs));
    }
}

@Service
class TripService {
    public List<Trip> getTrips(UUID origin, UUID destination, OffsetDateTime date, Boolean bicycles, Boolean dogs) {
        List<Trip> trips = List.of(
                new Trip().id(UUIDConstant.TRIP_LODZ_BERLIN_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(30))
                        .bicyclesAllowed(true)
                        .dogsAllowed(true)
                        .operator("PKP")
                        .origin(UUIDConstant.LODZ_STATION.toString())
                        .destination(UUIDConstant.BOOKING_UUID.toString()),
                new Trip().id(UUIDConstant.TRIP_BERLIN_PARIS_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(60))
                        .bicyclesAllowed(true)
                        .dogsAllowed(false)
                        .operator("DB")
                        .origin(UUIDConstant.BERLIN_STATION.toString())
                        .destination(UUIDConstant.PARIS_STATION.toString()),
                new Trip().id(UUIDConstant.TRIP_PARIS_ROME_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(90))
                        .bicyclesAllowed(false)
                        .dogsAllowed(false)
                        .operator("SCNF")
                        .origin(UUIDConstant.PARIS_STATION.toString())
                        .destination(UUIDConstant.ROME_STATION.toString())
        );
        return trips.stream()
                .filter(e -> origin.toString().equals(e.getOrigin()) && destination.toString().equals(e.getDestination()))
                .filter(e -> Objects.nonNull(date) && (date.equals(e.getArrivalTime()) || date.equals(e.getDepartureTime())))
                .filter(e -> bicycles == e.getBicyclesAllowed())
                .filter(e -> dogs == e.getDogsAllowed())
                .toList();
    }
}
```

```java

@RestController
@RequiredArgsConstructor
class BookingController implements BookingsApi {

    private final BookingService bookingService;

    @Override
    public ResponseEntity<Booking> createBooking(Booking booking) {
        bookingService.createBooking(booking);
        return ResponseEntity.ok(booking);
    }

    @Override
    public ResponseEntity<BookingPayment> createBookingPayment(UUID bookingId, BookingPayment bookingPayment) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> deleteBooking(UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Booking> getBooking(UUID bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @Override
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

}

@RestController
class BookingService {

    private List<Booking> bookings;

    public BookingService() {
        bookings = new ArrayList<>();
        bookings.add(new Booking()
                .id(UUIDConstant.BOOKING_UUID)
                .hasBicycle(true)
                .hasDog(true)
                .passengerName("Franek")
                .tripId(UUIDConstant.TRIP_ROME_LODZ_UUID)
        );
    }

    public void createBooking(Booking booking) {
        booking.setId(UUID.randomUUID());
        bookings.add(booking);
    }

    public Booking getBooking(UUID bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void deleteBooking(UUID bookingId) {
        bookings.remove(getBooking(bookingId));
    }
}
```

* run app (set `server.port=8020` in _application.properties_)
* Reminder: generated code is not commited to repo

## OpenAPI - Consumer API

* Create second service with use of start.spring.io
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.3.0
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsOrganizerService
    * Name: TrainTripsOrganizerService
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsOrganizerService
    * Packaging: Jar
    * Java: 21
    * Dependencies: Web, Actuator, Lombok
* complete `pom.xml` with openAPI dependencies:

```xml

<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>jackson-databind-nullable</artifactId>
    <version>0.2.6</version>
</dependency>

<dependency>
<groupId>org.springdoc</groupId>
<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
<version>2.5.0</version>
</dependency>
```

```xml

<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.6.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/api/train-api.yml</inputSpec>
                <generatorName>java</generatorName>
                <apiPackage>com.zzpj.openapi.api</apiPackage>
                <modelPackage>com.zzpj.openapi.model</modelPackage>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <configOptions>
                    <useJakartaEe>true</useJakartaEe>
                    <library>resttemplate</library>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

* complete `train-api.yml` with server URL

```yaml
servers:
  - url: https://api.example.com
    description: Production
  - url: http://localhost:8020/
    description: local dev instance
```

* remove spring test units if needed
* complete code

```java

@SpringBootApplication
public class TrainTripsOrganizerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTripsOrganizerServiceApplication.class, args);
    }

    @Bean
    public StationsApi stationsApi() {
        return new StationsApi();
    }

    @Bean
    public CommandLineRunner commandLineRunner(StationsApi stationsApi) {
        return args -> {
            stationsApi.getStations().forEach(System.out::println);
        };
    }
}
```

* run and test
* live example: [ING Open Banking](https://developer.ing.com/openbanking/products)

## Read & watch about microservices (theoretical part)

### How to start

* [https://microservices.io/](https://microservices.io/)
* [12factor.net](https://12factor.net/)

### Online courses

* https://app.pluralsight.com/library/courses/getting-started-microservices
* https://app.pluralsight.com/library/courses/microservices-fundamentals
* https://app.pluralsight.com/library/courses/building-reactive-microservices (Java demo app)
* https://app.pluralsight.com/library/courses/microservices-security-fundamentals
* https://app.pluralsight.com/library/courses/java-microservices-spring-cloud-developing-services
* https://app.pluralsight.com/library/courses/java-microservices-spring-cloud-coordinating-services

### Youtube

* https://youtu.be/P4iomsHmOW0
* https://youtu.be/GBTdnfD6s5Q

### Spring Cloud documentation

* https://spring.io/projects/spring-cloud

## Eureka Server

* Create second service with use of start.spring.io
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.3.0
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsEcoSystem
    * Name: TrainTripsEcoSystem
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsEcoSystem
    * Packaging: Jar
    * Java: 21
    * Dependencies: Web, Eureka Server
* Open main class with `@SpringBootApplication` annotation
* Use Spring Cloud’s `@EnableEurekaServer` to stand up a registry with which other applications can communicate. This is
  a regular Spring Boot application with one annotation added to enable the service registry.
* By default, the registry also tries to register itself, so you need to disable that behavior as well
  in  `application.properties` file.
   ```properties
   eureka.client.register-with-eureka=false
   eureka.client.fetch-registry=false
   ```
* Select the port which will be used by Eureka Server
   ```properties
   server.port=8761
   ```
* Enter URL: `http://localhost:8761/`

### Register both, newly created services

* Complete `pom.xml`:
  ```xml
  <spring-cloud.version>2023.0.1</spring-cloud.version>
  ```
  ```xml
  <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```
  ```xml
  <dependencyManagement>
     <dependencies>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-dependencies</artifactId>
             <version>${spring-cloud.version}</version>
             <type>pom</type>
             <scope>import</scope>
         </dependency>
     </dependencies>
  </dependencyManagement>
  ```
* Add annotation `@EnableDiscoveryClient` to main class
* Add some properties into `application.properties`
   ```properties
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
   eureka.client.fetch-registry=true
   eureka.client.register-with-eureka=true
   ```
* Run all services and determine if service has been registered in Eureka Discovery Server, either by
  entering `http://localhost:8761/` or using logs.

### Spring Cloud Client Load balancer

1. Stop running `TrainTripsManager` and comment `server.port` properties
1. Add `TestController`:
   ```java
    @RestController
    class TestController {
    
        @Value("${spring.application.name}")
        private String applicationName;
    
        @Value("${server.port}")
        private String appPort;
    
        @GetMapping("/hello/{name}")
        public String getServiceName(@PathVariable("name") String name) {
            return "Hello " + name + ", you are using " + applicationName + " on port: " + appPort;
        }
    }
   ```
1. Run two (or more) instances using Spring Boot Run Configuration, use Environment > VM Options for setting ports:
    * `-Dserver.port=8021`
    * `-Dserver.port=8022`
    * `-Dserver.port=8023`
1. Refresh Eureka Discovery page and determine if both instances of the same service are available
1. Add load balancer dependency in `TrainTripsOrganizerService` project
   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-loadbalancer</artifactId>
   </dependency>
   ```
1. `TrainTripsManagerSupplier` implementation:
   ```java
    class TrainTripsManagerSupplier implements ServiceInstanceListSupplier {
    
        private final String serviceId;
    
        TrainTripsManagerSupplier(String serviceId) {
            this.serviceId = serviceId;
        }
    
        @Override
        public String getServiceId() {
            return serviceId;
        }
    
        @Override
        public Flux<List<ServiceInstance>> get() {
            ServiceInstance int1 = new DefaultServiceInstance("001", serviceId, "localhost", 8021, false);
            ServiceInstance int2 = new DefaultServiceInstance("002", serviceId, "localhost", 8022, false);
            ServiceInstance int3 = new DefaultServiceInstance("003", serviceId, "localhost", 8023, false);
            return Flux.just(List.of(int1, int2, int3));
        }
    }
   ```   
1. Create `restTemplate` and `ServiceInstanceListSupplier` beans:
   ```java
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TrainTripsManagerSupplier("train-trips-service");
    }
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
   ```

1. `TrainTripsOrganizerController` implementation:
   ```java
    @RestController
    class TrainTripsOrganizerController {
        private final RestTemplate restTemplate;
    
        @Value("${spring.application.name}")
        private String applicationName;
    
        TrainTripsOrganizerController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }
    
        @GetMapping("/info")
        public String getHello() {
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://train-trips-service/hello/" + applicationName, String.class);
            return forEntity.getBody();
        }
    }
   ```
1. Verify URL: `http://localhost:8030/info`
1. OpenAPI case by modifying `api.yaml`:
    ```yaml
    servers:
      - url: http://localhost:{port}/
        description: local dev instance
        variables:
        port:
        default: '8021'
        enum:
          - '8021'
          - '8022'
          - '8023'   
    ```
1. Run mvn:  `mvn clean install`
1. Modify `CommandLineRunner` bean:
    ```java
    @Bean
    @LoadBalanced
    public CommandLineRunner commandLineRunner(StationsApi stationsApi) {
        return args -> {
            System.out.println(stationsApi.getApiClient().getBasePath());
            stationsApi.getStations().forEach(System.out::println);
        };
    }    
    ```
1. Rerun for OpenAPI

## Config Server

1. Open [Spring Initializr website](https://start.spring.io/)
1. Complete Metadata section: set Artifact name as `TrainTripsConfigServer`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.3.0
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsConfigServer
    * Name: TrainTripsConfigServer
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsConfigServer
    * Packaging: Jar
    * Java: 21
    * Dependencies: Spring Web, Eureka Discovery Client, Config Server
1. Click Generate button, download and unzip package
1. Copy unzipped `TrainTripsConfigServer` folder into your project folder
1. Add following annotations: `@EnableDiscoveryClient` & `@EnableConfigServer` into main class
1. Add some properties into `application.properties`
   ```properties
    spring.application.name=TrainTripsConfigServer
    server.port=8040
    eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
    
    spring.cloud.config.server.git.uri=https://github.com/zzpj/demo-config-server.git
    spring.cloud.config.server.git.default-label=main
    spring.cloud.config.server.git.clone-on-start=true
   ```
1. Show Github repo in IntelliJ or browser: https://github.com/zzpj/demo-config-server.git
1. Complete `pom.xml` of `TrainTripsOrganizerService`
   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   ```
1. Go to `application.properties` files and add:
   ```properties
    spring.config.import=optional:configserver:
    spring.cloud.config.discovery.enabled=true
    spring.cloud.config.discovery.service-id=TrainTripsConfigServer
    #spring.cloud.config.name=${spring.application.name}
    spring.cloud.config.name=train-trips-organizer-service
    spring.cloud.config.profile=dev
    spring.cloud.config.label=main
    spring.cloud.config.fail-fast=true
   ```
1. Add properties check with use of `@Value` annotation
   ```java
    @Value("${config.server.demo}")
    private String message;
    
    @GetMapping("/getMessage")
    public String getMessage() {
        return "Property value: " + message;
    }
   ```
1. Remember about following properties naming rules
   ```
   /{application}/{profile}[/{label}]
   /{application}-{profile}.yml
   /{label}/{application}-{profile}.yml
   /{application}-{profile}.properties
   /{label}/{application}-{profile}.properties
   ```

------

## Keycloak as authorization server

* zip/jar [Instalacja do pobrania](https://www.keycloak.org/downloads)
* lub [obraz dockerowy](https://quay.io/repository/keycloak/keycloak),
* [niezbędna dokumentacja](https://www.keycloak.org/guides#getting-started).
* [krok po kroku jak zacząć](https://www.keycloak.org/getting-started/getting-started-zip)

### Podstawowe pojęcia:

- Realm
- OAuth
- User/Role/Client/Federation Provider

### Konfiguracja keycloak'a

1. Pobierz keycloak ze strony i rozpakuj
1. Otwórz conf/keycloak.conf i dodaj: `http-port=8999`
1. Uruchom keycloak w powershell: `bin\kc.bat start-dev`
1. Ustaw kredki dla root admin'a
1. Przedstaw poszczególne linki
1. Stwórz:
    - nowy realm
        - zakładka _General_
            - ustaw: Display name
        - zakładka _Login_
            - User registration: On
            - Forgot password: On
            - Remember me: On
            - Verify email: Off
        - zakładka _themes_
            - wszystko prefiksowane keycloak-
        - zakładka _Localization_
            - dodać obsługę języka polskiego
    - nowego klienta
        - zakładka _Credentials_
            - Client Authenticator: client id and secret
            - wygeneruj `Client secret`
        - zakładka _Settings_
            - root url: `http://localhost:8090` (port aplikacji będącej adapterem)
            - valid redirect urls: `/*`
            - Client authentication: on
            - Authorization: on
            - Login theme: keycloak
    - nowego użytkownika (pole 'Required user actions' puste) + ew jakieś role
1. Pobierz token
    - ogólny url: 'http://localhost:8999/realms/master/.well-known/openid-configuration'
    ```http request
    POST http://localhost:8999/realms/master/protocol/openid-connect/token
    Content-Type: application/x-www-form-urlencoded
    
    client_id = train-trip-users-adapter &
    username = user &
    password = pass &
    grant_type = password &
    client_secret = fDS9jQQXmKPvqcBTplBFQUe3ua2ou44q
    ```

### Aplikacja adaptera do keycloaka w Springu

1. start.spring.io:
    - wersja 3.3.0,
    - zależności: web, oauth2-client, security
    - Group: com.zzpj
    - Artifact: TrainTripUsersAdapter

2. plik `application.properties`:
    ```yaml
    spring.application.name=TrainTripUsersAdapter
    server.port=8090
    
    spring.security.oauth2.client.registration.keycloak.client-id=<client_id>
    spring.security.oauth2.client.registration.keycloak.client-secret=<client_secret>
    spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
    spring.security.oauth2.client.registration.keycloak.scope=openid
    
    spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:<port>/realms/<realm_name>
    ```
3. klasa `SecurityConfig` - TODO: refactor bo deprecated:
    ```java
    @EnableWebSecurity
    class SecurityConfig {
    
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth ->
                    auth.requestMatchers("/external")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            );
            http.oauth2Login(Customizer.withDefaults());
            return http.build();
        }
    }
    ```
4. klasa `UserController`:
    ```java
    @RestController
    class UserController {
    
        @GetMapping("/internal")
        public String getSecretInfo() {
            return "secret info, visible after login...";
        }
    
        @GetMapping("/external")
        public String getExternalInfo() {
            return "content visible for all, no login required...";
        }
    }
    ```
5. Sprawdzenie w przeglądarce:
    - logowanie+rejestracja: `http://localhost:8090/internal`

### _Github_ Identity Provider

1. W Keycloaku, dodać nowego providera poprzez wybranie z lewego menu "Identity Providers" i wybraniu kafelka z Githubem
1. Jednocześnie na stronie Githuba, dodać OAuth app [link](https://github.com/settings/developers)
1. Na stronie Github'a, jako "Authorization callback URL" ustaw URL skopiowany ze strony Keycloaka _Redirect URI_
1. W keycloaku, uzupełnij _Client ID_ & _Client Secret_ (skopiowany ze strony Github'a)
1. Zaawansowane ustawienia na _off_, _First login flow_: first broker login
1. Zapisz i sprawdź: `http://localhost:8090/internal` poprzez logowanie się z kredkami z github'a


### Obsługa przy wylogowaniu i usunięcia sesji

1. Stwórz nową klasę `KeycloakLogoutHandler`:
    ```java
    @Component
    public class KeycloakLogoutHandler implements LogoutHandler {
    
        private RestTemplate restTemplate;
    
        public KeycloakLogoutHandler() {
            this.restTemplate = new RestTemplate();
        }
    
        @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
        private String issuerUrl;
    
        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    
            String logoutEndpoint = issuerUrl + "/protocol/openid-connect/logout";
    
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(logoutEndpoint)
                    .queryParam("id_token_hint", ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue());
    
            ResponseEntity<String> logoutResponse = new RestTemplateBuilder().build().getForEntity(builder.toUriString(), String.class);
            if (logoutResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println("ok");
            } else {
                System.out.println("not ok");
            }
    
        }
    }
    ```
2. Uzupełnij `SecurityConfig`:
    ```java
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    
    public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
        http.authorizeHttpRequests()
                .requestMatchers("/external").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and()
                .logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/external")
        ;
        return http.build();
    }
    ```

3. Uzupełnij `UserController`:
    ```java
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
     }
    ```
4. Login i logout dla user'ów: `http://localhost:8090/logout`

### Keycloak API

1. Zarządzenie użytkownikami
    - z poziomu panelu admina
    - indywidualne UI dla każdego user'a
    ```java
     @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
     private String issuerUrl;

     @GetMapping("/profile")
     public void profile(HttpServletResponse response)  {
        response.setHeader("Location", issuerUrl + "/account");
        response.setStatus(302);
     }
    ```
    - poprzez Keycloak Rest API
        ```xml
        <dependency>
            <groupId>org.keycloak</groupId>
            <artifactId>keycloak-admin-client</artifactId>
            <version>21.1.1</version>
        </dependency>
        ```

   ```java
       @Configuration
       public class KeycloakUserConfig {
   
       @Bean
       Keycloak keycloak() {
           return KeycloakBuilder.builder()
                   .serverUrl("http://localhost:8999")
                   .realm("master")
                   .clientId("admin-cli")
                   .grantType(OAuth2Constants.PASSWORD)
                   .username("admin")
                   .password("admin")
                   .build();
         }
       }
   // ----
   @Component
   public class KeycloakUserService {

       private static final String EVENT_APP_REALM = "event_app";
       private final Keycloak keycloak;

       public KeycloakUserService(Keycloak keycloak) {
           this.keycloak = keycloak;
       }

       public List<UserRepresentation> findByUsername(String name, boolean exact) {
           return keycloak.realm(EVENT_APP_REALM)
                   .users()
                   .searchByUsername(name, exact);
       }
   }
   ```
   ```java
   @RestController
   public class KeycloakUserController {

       private final KeycloakUserService keycloakUserService;

       public KeycloakUserController(KeycloakUserService keycloakUserService) {
           this.keycloakUserService = keycloakUserService;
       }

       @GetMapping("/findUsers/{name}")
       public List<UserRepresentation> findUsers(@PathVariable("name") String name, @QueryParam("exact") Boolean exact) {
           return keycloakUserService.findByUsername(name, exact);
       }
   }
   ```
    - weryfikacja: `http://localhost:8090/findUsers/z?exact=false`