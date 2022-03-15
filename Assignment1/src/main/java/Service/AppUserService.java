package Service;

import Model.AppUser.AppUser;
import Repository.AppUserRepository;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;

@NoArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository = new AppUserRepository();

    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    public void insertUser(String username, String password) {
        if(username != null && password != null && !username.isEmpty() && !password.isEmpty())
            appUserRepository.insertUser(username, password);
        else {
            throw new InvalidParameterException();
        }
    }

    public AppUser findByUsername(String username) {
        if(!username.isEmpty())
            return appUserRepository.findByUsername(username);
        else {
            throw new InvalidParameterException();
        }
    }

    public AppUser login(String username, String password) {
        if(!username.isEmpty() && !password.isEmpty())
            return appUserRepository.login(username, password);
        else {
            throw new InvalidParameterException();
        }
    }
}
