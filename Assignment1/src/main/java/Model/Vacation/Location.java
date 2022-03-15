package Model.Vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String destination;
    @Column
    private String description;
//    @ToString.Exclude
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Vacation> vacations = new ArrayList<>();

    public Location(String destination, String description) {
        this.destination = destination;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getDestination();
    }
}
