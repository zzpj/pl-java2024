package com.zzpj.TrainTripManagerService;

import com.zzpj.openapi.api.BookingsApi;
import com.zzpj.openapi.api.StationsApi;
import com.zzpj.openapi.api.TripsApi;
import com.zzpj.openapi.model.Booking;
import com.zzpj.openapi.model.BookingPayment;
import com.zzpj.openapi.model.Station;
import com.zzpj.openapi.model.Trip;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EnableDiscoveryClient
@SpringBootApplication
public class TrainTripManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTripManagerServiceApplication.class, args);
	}
}

@RestController
@RequiredArgsConstructor
class StationController implements StationsApi {

	private final StationService stationService;

	@Override
	public ResponseEntity<List<Station>> getStations() {
		return ResponseEntity.ok(stationService.getStations());
	}
}

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


@RestController
@RequiredArgsConstructor
class Trips implements TripsApi {

	private final TripService tripService;

	@Override
	public ResponseEntity<List<Trip>> getTrips(UUID destination, OffsetDateTime date, UUID origin, Boolean bicycles, Boolean dogs) {
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
		return null;
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