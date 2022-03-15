package Model;

import Model.AppUser.AppUser;
import Model.Vacation.Vacation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@IdClass(CompositeKeys.class)
@Getter
public class AppUserVacation {

    @Id
    @ManyToOne
    private Vacation vacation;

    @Id
    @ManyToOne
    private AppUser appUser;

    private Integer numberOfUsers = 0;

    public AppUserVacation(Vacation vacation, AppUser appUser) {
        this.vacation = vacation;
        this.appUser = appUser;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    @Override
    public String toString() {
        return vacation.toString() + " with " + (numberOfUsers + 1) + " tickets.";
    }
}
