package Model.Vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Location location;
    @Column
    private Double price;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private Integer numberUsers;
    @Column
    private VacationStatus vacationStatus = VacationStatus.NOT_BOOKED;

    public Vacation(Location location,
                    Double price,
                    Date start,
                    Date end,
                    Integer numberUsers) {
        this.location = location;
        this.price = price;
        this.start = start;
        this.end = end;
        this.numberUsers = numberUsers;
    }

    public void setNumberUsers(Integer numberUsers) {
        this.numberUsers = numberUsers;
    }

    public void setVacationStatus(VacationStatus vacationStatus) {
        this.vacationStatus = vacationStatus;
    }

    @Override
    public String toString() {
        return "Vacation between " + this.getStart() + " and " + this.getEnd() + " at the price "
                + this.getPrice() + " and remaining tickets: " + this.getNumberUsers() + ". Status: "
                + this.getVacationStatus() + " in " + this.getLocation();
    }
}
