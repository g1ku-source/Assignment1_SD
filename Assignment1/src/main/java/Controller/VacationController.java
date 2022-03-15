package Controller;

import Model.AppUser.AppUser;
import Model.AppUserVacation;
import Model.Vacation.Location;
import Model.Vacation.Vacation;
import Model.Vacation.VacationStatus;
import Service.AppUserVacationService;
import Service.LocationService;
import Service.VacationService;
import lombok.NoArgsConstructor;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class VacationController {

    private final VacationService vacationService = new VacationService();
    private final LocationService locationService = new LocationService();
    private final Service.AppUserVacationService appUserVacationService =
            new AppUserVacationService();

    public Location insertLocation(String destination, String description) {
        try {
            return locationService.insertLocation(destination, description);
        } catch (InvalidParameterException | RollbackException exception){
            return null;
        }
    }

    public String insertVacation(Location location,
                                 Double price,
                                 Date start,
                                 Date end,
                                 Integer numberUsers) {
        try {
            vacationService.insertVacation(location, price, start, end, numberUsers);
            return "Vacation created.";
        } catch (InvalidParameterException exception) {
            return "Invalid parameters.";
        }
    }

    public List<Location> findAll(){
        try {
            return locationService.findAll();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public void deleteLocation(Location location) {
        try {
            List<Vacation> vacations = vacationService.findVacationsByLocation(location);
            if(vacations != null)
                vacationService.deleteVacations(vacations);
            locationService.deleteLocation(location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Location findLocation(String destination) {
        try {
            return locationService.findLocation(destination);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Vacation> findVacationsByLocation(Location location) {
        try {
            return vacationService.findVacationsByLocation(location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteVacations(List<Vacation> vacations) {
        try {
            vacationService.deleteVacations(vacations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Vacation updateVacation(Vacation vacation) {
        try {
            return vacationService.updateVacation(vacation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insertAppUserInVacation(Vacation vacation, AppUser appUser) {

        if(vacation.getNumberUsers() == 0) {
            throw new InvalidParameterException();
        }
        try {
            Vacation vacationUpdated = updateVacation(vacation);
            appUserVacationService.insert(vacationUpdated, appUser);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<AppUserVacation> findByVacation(Vacation vacation) {
        try {
            return appUserVacationService.findByVacation(vacation);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public void deleteAppUserVacation(Vacation vacation) {
        try {
            appUserVacationService.deleteAppUserVacation(vacation);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<AppUserVacation> findByAppUser(AppUser appUser) {
        try {
            return appUserVacationService.findByAppUser(appUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
