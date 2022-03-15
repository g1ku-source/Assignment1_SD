package GUI;

import Controller.VacationController;
import Model.AppUser.AppUser;
import Model.AppUserVacation;
import Model.Vacation.*;

import javax.swing.*;
import java.util.List;

public class UserGUI extends JFrame {

    private final AppUser user;
    private final VacationController vacationController = new VacationController();

    private List<Location> locations;
    private final JComboBox<Location> locationComboBox = new JComboBox<>();
    private final JLabel locationDescriptionLabel = new JLabel("");
    private final JComboBox<Vacation> vacationJComboBox = new JComboBox<>();
    private final JButton reserveButton = new JButton("RESERVE");
    private final JLabel resultLabel = new JLabel("");
    private final JLabel appUserVacationsLabel = new JLabel("All your reservations.");
    private final JComboBox<AppUserVacation> appUserVacationJComboBox = new JComboBox<>();
    private final JButton sortByPrice = new JButton("Sort by price");
    private final JButton sortByDate = new JButton("Sort by date");

    private final static Integer defaultWidth = 1500;
    private final static Integer defaultHeight = 600;

    public UserGUI(AppUser appUser) {
        this.user = appUser;
        windowInit();
        getLocations();
        addButton();
        initObjects();
        getAppUserVacations();
    }

    private void addButton() {
        reserveButton.addActionListener(e -> {
            Vacation vacation = (Vacation) vacationJComboBox.getSelectedItem();
            if(vacation == null) {
                resultLabel.setText("No vacation found!!");
                return ;
            }
            try {
                vacationController.insertAppUserInVacation(vacation, user);
                resultLabel.setText("SUCCESS!");
            }
            catch (Exception exception) {
                resultLabel.setText("INVALID!!!");
            }
        });

        sortByPrice.addActionListener(e -> {
            getVacations((Location) this.locationComboBox.getSelectedItem(), false);
        });

        sortByDate.addActionListener(e -> {
            getVacations((Location) this.locationComboBox.getSelectedItem(), true);
        });
    }

    private void getVacations(Location location) {

        List<Vacation> vacations = vacationController.findVacationsByLocation(location);

        vacationJComboBox.removeAllItems();

        for(Vacation vacation : vacations) {
            if(vacation.getVacationStatus() != VacationStatus.BOOKED)
                vacationJComboBox.addItem(vacation);
        }
    }

    private void getVacations(Location location, boolean strategy) {

        List<Vacation> vacations = vacationController.findVacationsByLocation(location);
        if(strategy)
            vacations.sort(new VacationComparatorDate());
        else
            vacations.sort(new VacationComparatorPrice());
        vacationJComboBox.removeAllItems();

        for(Vacation vacation : vacations)
            vacationJComboBox.addItem(vacation);
    }

    private void getAppUserVacations() {
        List<AppUserVacation> appUserVacations = vacationController.findByAppUser(this.user);

        appUserVacationJComboBox.removeAllItems();

        for(AppUserVacation appUserVacation : appUserVacations)
            appUserVacationJComboBox.addItem(appUserVacation);
    }

    private void initObjects() {
        locationDescriptionLabel.setBounds(defaultWidth / 2 - 50, 130, defaultWidth / 2, 30);
        this.add(locationDescriptionLabel);

        resultLabel.setBounds(defaultWidth / 2 - 50, 220, defaultWidth / 4, 30);
        this.add(resultLabel);

        vacationJComboBox.setBounds(0, 160, defaultWidth, 30);
        this.add(vacationJComboBox);

        reserveButton.setBounds(defaultWidth / 2 - 50, 190, defaultWidth / 4, 30);
        this.add(reserveButton);

        this.appUserVacationsLabel.setBounds(defaultWidth / 2 - 50, 250, defaultWidth / 4, 30);
        this.add(appUserVacationsLabel);

        this.appUserVacationJComboBox.setBounds(0, 280, defaultWidth, 30);
        this.add(appUserVacationJComboBox);

        this.sortByDate.setBounds(defaultWidth / 2 - 50, 310, defaultWidth / 4, 30);
        this.add(sortByDate);

        this.sortByPrice.setBounds(defaultWidth / 2 - 50, 340, defaultWidth / 4, 30);
        this.add(sortByPrice);
    }

    private void getLocations() {
        locations = vacationController.findAll();
        locationDescriptionLabel.setText(locations.get(0).getDescription());

        for(Location location : locations)
            locationComboBox.addItem(location);

        locationComboBox.setBounds(defaultWidth / 2 - 50, 100, defaultWidth / 4, 30);
        this.add(locationComboBox);

        locationComboBox.addActionListener(e -> {
            Location location = locations.get(locationComboBox.getSelectedIndex());
            locationDescriptionLabel.setText(location.getDescription());
            getVacations((Location) this.locationComboBox.getSelectedItem());
            getAppUserVacations();
        });
    }

    private void windowInit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setSize(defaultWidth, defaultHeight);
        setTitle("Travel Agency " + user.getUsername());
    }
}
