package dat.entities;


import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<Trip> trips;

    public Guide(int id, String firstName, String lastName, String email, int phone, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Guide(GuideDTO guideDTO) {
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
    }
}
