package Controller;

import Model.AppUser.AppUser;
import Service.AppUserService;
import lombok.NoArgsConstructor;

import javax.persistence.RollbackException;
import java.security.InvalidParameterException;

@NoArgsConstructor
public class AppUserController {

    private final AppUserService appUserService = new AppUserService();

    public String insertUser(String username, String password) {
        try {
            appUserService.insertUser(username, password);
            return "User created!";
        } catch (RollbackException exception) {
//            System.out.println("Username taken");
            return "Username taken";
        } catch (InvalidParameterException exception) {
//            System.out.println("Invalid parameters");
            return "Invalid parameters";
        }
    }

    public AppUser findByUsername(String username) {
        try {
            return appUserService.findByUsername(username);
        } catch (InvalidParameterException exception) {
            System.out.println("Invalid parameters");
        }
        return null;
    }

    public AppUser login(String username, String password) {
        try {
            return appUserService.login(username, password);
        } catch (InvalidParameterException exception) {
            System.out.println("Invalid parameters");
        }
        return null;
    }
}
