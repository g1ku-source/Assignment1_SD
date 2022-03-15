package Model;

import Model.AppUser.AppUser;
import Model.Vacation.Vacation;

import java.io.Serializable;

public class CompositeKeys implements Serializable {

    private Vacation vacation;
    private AppUser appUser;
}
