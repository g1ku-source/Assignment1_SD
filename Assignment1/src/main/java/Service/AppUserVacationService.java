package Service;

import Model.AppUser.AppUser;
import Model.AppUserVacation;
import Model.Vacation.Vacation;
import Repository.AppUserVacationRepository;

import java.security.InvalidParameterException;
import java.util.List;

public class AppUserVacationService {

    private final AppUserVacationRepository appUserVacationRepository =
            new AppUserVacationRepository();

    public void insert(Vacation vacation, AppUser appUser) {
        if(vacation != null && appUser != null)
            appUserVacationRepository.insert(vacation, appUser);
        else
            throw new InvalidParameterException();
    }

    public List<AppUserVacation> findByVacation(Vacation vacation) {
        if(vacation != null)
            return appUserVacationRepository.findByVacation(vacation);
        throw new InvalidParameterException();
    }

    public void deleteAppUserVacation(Vacation vacation) {
        if(vacation != null)
            appUserVacationRepository.delete(vacation);
        else
            throw new InvalidParameterException();
    }

    public List<AppUserVacation> findByAppUser(AppUser appUser) {
        if(appUser != null)
            return appUserVacationRepository.findByUser(appUser);
        else
            throw new InvalidParameterException();
    }
}
