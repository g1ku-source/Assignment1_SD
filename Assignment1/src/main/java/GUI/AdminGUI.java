package GUI;

import Controller.VacationController;
import Model.AppUser.AppUser;
import Model.Vacation.Location;
import Model.Vacation.Vacation;

import javax.swing.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdminGUI extends JFrame {

    private final static Integer defaultWidth = 1500;
    private final static Integer defaultHeight = 1000;

    private List<Location> locations;
    private final JComboBox<Location> locationComboBox = new JComboBox<>();
    private final JLabel locationDescriptionLabel = new JLabel("");
    private final JComboBox<Vacation> vacationJComboBox = new JComboBox<>();
    private final JLabel resultLabel = new JLabel("");
    private final JButton deleteVacationButton = new JButton("Delete vacation");
    private final JButton deleteLocationButton = new JButton("Delete location");
    private final JButton addLocation = new JButton("Add location");
    private final JButton addVacation = new JButton("Add vacation");

    private final JLabel startDate = new JLabel("Start date date/month/year");
    private final JTextField yearTextStart = new JTextField();
    private final JTextField monthTextStart = new JTextField();
    private final JTextField dateTextStart = new JTextField();
    private final JLabel checkStartDate = new JLabel("");

    private final JLabel endDate = new JLabel("End date date/month/year");
    private final JTextField yearTextEnd = new JTextField();
    private final JTextField monthTextEnd = new JTextField();
    private final JTextField dateTextEnd = new JTextField();
    private final JLabel checkEndDate = new JLabel("");

    private final JLabel priceLabel = new JLabel("Price");
    private final JTextField priceText = new JTextField();
    private final JLabel numberOfUsersLabel = new JLabel("Number of users");
    private final JTextField numberOfUsersText = new JTextField();

    private final JLabel destinationLabel = new JLabel("Destination");
    private final JTextField destinationText = new JTextField();
    private final JLabel descriptionLabel = new JLabel("Description");
    private final JTextField descriptionText = new JTextField();

    private final AppUser user;
    private final VacationController vacationController = new VacationController();

    public AdminGUI(AppUser user) {
        this.user = user;
        windowInit();
        getLocations();
        initObjects();
        addButton();
    }

    private void windowInit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setSize(defaultWidth, defaultHeight);
        setTitle("Travel Agency " + user.getUsername());
    }

    private void getVacations(Location location) {

        List<Vacation> vacations = vacationController.findVacationsByLocation(location);

        vacationJComboBox.removeAllItems();

        for(Vacation vacation : vacations)
            vacationJComboBox.addItem(vacation);
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
        });
    }

    private void addButton() {
        deleteVacationButton.addActionListener(e -> {
            Vacation vacation = (Vacation) vacationJComboBox.getSelectedItem();
            if(vacation == null) {
                resultLabel.setText("NO VACATION FOUND");
                return;
            }
            try {
                vacationController.deleteAppUserVacation(vacation);
                vacationController.deleteVacations(Collections.singletonList(vacation));
                resultLabel.setText("SUCCESS");
            }
            catch (Exception exception) {
                resultLabel.setText("ERROR");
            }
        });

        deleteLocationButton.addActionListener(e -> {
            Location location = (Location) locationComboBox.getSelectedItem();
            if(location == null) {
                resultLabel.setText("NO LOCATION FOUND");
                return ;
            }
            try {
                List<Vacation> vacations = vacationController.findVacationsByLocation(location);
                for(Vacation vacation : vacations)
                    vacationController.deleteAppUserVacation(vacation);
                vacationController.deleteVacations(vacations);
                vacationController.deleteLocation(location);
                resultLabel.setText("SUCCESS");
            } catch (Exception exception) {
                resultLabel.setText("ERROR");
            }
        });

        addLocation.addActionListener(e -> {
            String destination = destinationText.getText();
            String description = descriptionText.getText();

            if(destination.equals("") || description.equals("")) {
                resultLabel.setText("Invalid destination / description");
                return ;
            }
            try {
                vacationController.insertLocation(destination, description);
                dispose();
                new AdminGUI(user);
            } catch (Exception exception) {
                resultLabel.setText("Destination already exists");
                System.out.println(exception.getClass());
            }
        });

        addVacation.addActionListener(e -> {
            Location location = (Location) locationComboBox.getSelectedItem();
            if(location == null) {
                resultLabel.setText("Invalid location");
                return ;
            }
            int startYear;
            int startMonth;
            int startDate;
            int endYear;
            int endMonth;
            int endDate;
            double price;
            int numberOfUsers;

            try {
                startYear = Integer.parseInt(yearTextStart.getText());
                startMonth = Integer.parseInt(monthTextStart.getText());
                startDate = Integer.parseInt(dateTextStart.getText());

                endYear = Integer.parseInt(yearTextEnd.getText());
                endMonth = Integer.parseInt(monthTextEnd.getText());
                endDate = Integer.parseInt(dateTextEnd.getText());

                if(startMonth > 11 || startMonth < 0) {
                    resultLabel.setText("Month should be < 12 and >= 0!");
                    return;
                }

                if(endMonth > 11 || endMonth < 0) {
                    resultLabel.setText("Month should be < 12 and >= 0!");
                    return;
                }
                price = Double.parseDouble(priceText.getText());
                numberOfUsers = Integer.parseInt(numberOfUsersText.getText());

            } catch (NumberFormatException exception) {
                resultLabel.setText("Invalid date format");
                return ;
            }

            Date start = new Date(startYear - 1900, startMonth, startDate);
            checkStartDate.setText(String.valueOf(start));
            Date end = new Date(endYear - 1900, endMonth, endDate);
            checkEndDate.setText(String.valueOf(end));

            if(start.compareTo(end) > 0) {
                resultLabel.setText("Start should be lower than end!");
                return ;
            }

            if(start.compareTo(new Date(System.currentTimeMillis())) <= 0) {
                resultLabel.setText("Start should be greater than today!");
                return ;
            }

            if(price <= 0) {
                resultLabel.setText("Price should be a natural number! (>0)");
                return ;
            }
            if(numberOfUsers <= 0) {
                resultLabel.setText("Number of Users should be a natural number! (>0)");
                return ;
            }

            vacationController.insertVacation(location, price, start, end, numberOfUsers);
        });
    }

    private void initObjects() {
        locationDescriptionLabel.setBounds(defaultWidth / 2 - 50, 130, defaultWidth / 2, 30);
        this.add(locationDescriptionLabel);

        resultLabel.setBounds(defaultWidth / 2 - 50, 190, defaultWidth / 4, 30);
        this.add(resultLabel);

        vacationJComboBox.setBounds(0, 160, defaultWidth, 30);
        this.add(vacationJComboBox);

        deleteVacationButton.setBounds(0, 220, defaultWidth / 2, 30);
        this.add(deleteVacationButton);

        deleteLocationButton.setBounds(defaultWidth / 2,  220, defaultWidth / 2, 30);
        this.add(deleteLocationButton);

        startDate.setBounds(0, 250, defaultWidth / 4, 30);
        this.add(startDate);

        checkStartDate.setBounds(defaultWidth / 3, 250, defaultWidth / 4, 30);
        this.add(checkStartDate);

        dateTextStart.setBounds(0, 280, defaultWidth / 3, 30);
        this.add(dateTextStart);

        monthTextStart.setBounds(defaultWidth / 3, 280, defaultWidth / 3, 30);
        this.add(monthTextStart);

        yearTextStart.setBounds(defaultWidth * 2 / 3, 280, defaultWidth / 3, 30);
        this.add(yearTextStart);

        endDate.setBounds(0, 310, defaultWidth / 4, 30);
        this.add(endDate);

        checkEndDate.setBounds(defaultWidth / 3, 310, defaultWidth / 4, 30);
        this.add(checkEndDate);

        dateTextEnd.setBounds(0, 340, defaultWidth / 3, 30);
        this.add(dateTextEnd);

        monthTextEnd.setBounds(defaultWidth / 3, 340, defaultWidth / 3, 30);
        this.add(monthTextEnd);

        yearTextEnd.setBounds(defaultWidth * 2 / 3, 340, defaultWidth / 3, 30);
        this.add(yearTextEnd);

        priceLabel.setBounds(0, 370, defaultWidth / 4, 30);
        this.add(priceLabel);

        priceText.setBounds(0, 400, defaultWidth / 3, 30);
        this.add(priceText);

        numberOfUsersLabel.setBounds(0, 430, defaultWidth / 4, 30);
        this.add(numberOfUsersLabel);

        numberOfUsersText.setBounds(0, 460, defaultWidth / 3, 30);
        this.add(numberOfUsersText);

        addVacation.setBounds(0, 490, defaultWidth / 3, 30);
        this.add(addVacation);

        destinationLabel.setBounds(0, 520, defaultWidth / 3, 30);
        this.add(destinationLabel);

        destinationText.setBounds(0, 550, defaultWidth / 3, 30);
        this.add(destinationText);

        descriptionLabel.setBounds(0, 580, defaultWidth / 3, 30);
        this.add(descriptionLabel);

        descriptionText.setBounds(0, 610, defaultWidth / 3, 30);
        this.add(descriptionText);

        addLocation.setBounds(0, 640, defaultWidth / 3, 30);
        this.add(addLocation);
    }
}
