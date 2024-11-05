package dat.entities;

import dat.dtos.TripDTO;
import dat.enums.TripCategories;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    private LocalDate startTime;
    private LocalDate endTime;
    private double longitude;
    private double latitude;
    private String name;
    private double price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripCategories categories;
    @ManyToOne
    private Guide guide;

    public Trip(TripDTO tripDTO) {
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.longitude = tripDTO.getLongitude();
        this.latitude = tripDTO.getLatitude();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.id = tripDTO.getId();
        this.categories = tripDTO.getCategories();
        this.guide = tripDTO.getGuide();
    }

}
