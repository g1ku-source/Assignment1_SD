package Service;

import Model.Vacation.Location;
import Model.Vacation.Vacation;
import Repository.AppUserRepository;
import Repository.VacationRepository;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository =
            new VacationRepository();

    public VacationRepository getVacationRepository() {
        return vacationRepository;
    }

    public void insertVacation(Location location,
                               Double price,
                               Date start,
                               Date end,
                               Integer numberUsers) {
        if(location != null
                && price > 0 && start != null && end != null && numberUsers > 0)
            vacationRepository.insertVacation
                    (new Vacation(location,
                            price, start, end, numberUsers));
        else
            throw new InvalidParameterException();
    }

    public List<Vacation> findVacationsByLocation(Location location) {
        if(location != null)
            return vacationRepository.findVacationsByLocation(location);
        else
            throw new InvalidParameterException();
    }

    public void deleteVacations(List<Vacation> vacations) {
        if(vacations != null)
            vacationRepository.deleteVacations(vacations);
        else
            throw new InvalidParameterException();
    }

    public Vacation updateVacation(Vacation vacation) {
        if(vacation != null)
            return vacationRepository.updateVacation(vacation);
        else
            throw new InvalidParameterException();
    }
}
