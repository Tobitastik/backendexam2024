package dat.dtos;

import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Getter;

import java.util.Set;

@Getter
public class GuideDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
    private Set<Trip> trips;

    public GuideDTO(int id, String firstName, String lastName, String email, int phone, int yearsOfExperience, Set<Trip> trip) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
    }

    public static GuideDTO convertToDTO(Guide guide) {
        return new GuideDTO(
                guide.getId(),
                guide.getFirstName(),
                guide.getLastName(),
                guide.getEmail(),
                guide.getPhone(),
                guide.getYearsOfExperience(),
                guide.getTrips()
        );
    }

    public static Guide convertToEntity(GuideDTO guideDTO) {
        return new Guide(guideDTO);
    }
}
