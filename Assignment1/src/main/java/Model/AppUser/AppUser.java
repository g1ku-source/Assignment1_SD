package Model.AppUser;

import Model.Vacation.Vacation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    @ToString.Exclude
    private String password;
    @Column
    private final AppUserRole role = AppUserRole.USER_ROLE;


    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
