package dat.dtos;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategories;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class TripDTO {

    private LocalDate startTime;
    private LocalDate endTime;
    private double longitude;
    private double latitude;
    private String name;
    private double price;
    private Guide guide;
    private int id;
    private TripCategories categories;

    public TripDTO(LocalDate startTime, LocalDate endTime, double longitude, double latitude, String name, double price, int id, TripCategories categories) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.id = id;
        this.categories = categories;
    }

    public static Trip convertToEntity(TripDTO tripDTO) {
        return new Trip(tripDTO);
    }

    public static TripDTO convertToDTO(Trip trip) {
        return new TripDTO(trip.getStartTime(),
                trip.getEndTime(),
                trip.getLongitude(),
                trip.getLatitude(),
                trip.getName(),
                trip.getPrice(),
                trip.getId(),
                trip.getCategories());
    }
}
