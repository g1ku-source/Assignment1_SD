package GUI;

import Controller.AppUserController;
import Model.AppUser.AppUser;
import Model.AppUser.AppUserRole;

import javax.swing.*;

public class LoginGUI extends JFrame {

    private final static Integer defaultWidth = 700;
    private final static Integer defaultHeight = 600;

    //controllers
    private final AppUserController appUserController = new AppUserController();

    //login & register
    private final static JLabel usernameLabel = new JLabel("Username");
    private final static JTextField usernameField = new JTextField();
    private final static JLabel passwordLabel = new JLabel("Password");
    private final static JPasswordField passwordField = new JPasswordField();
    private final static JButton loginButton = new JButton("Login");
    private final static JButton registerButton = new JButton("Register");
    private final static JLabel resultLabel = new JLabel();

    private void windowInit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setSize(defaultWidth, defaultHeight);
        setTitle("Travel Agency");
    }

    private void loginScreenInit() {
        usernameLabel.setBounds(defaultWidth / 2 - 50, 100, defaultWidth / 4, 30);
        this.add(usernameLabel);
        usernameField.setBounds(defaultWidth / 2 - 50, 130, defaultWidth / 4, 30);
        this.add(usernameField);
        passwordLabel.setBounds(defaultWidth / 2 - 50, 160, defaultWidth / 4, 30);
        this.add(passwordLabel);
        passwordField.setBounds(defaultWidth / 2 - 50, 190, defaultWidth / 4, 30);
        this.add(passwordField);
        loginButton.setBounds(defaultWidth / 2 - 50, 220, defaultWidth / 4, 30);
        this.add(loginButton);
        registerButton.setBounds(defaultWidth / 2 - 50, 250, defaultWidth / 4, 30);
        this.add(registerButton);
        resultLabel.setBounds(defaultWidth / 2 - 50, 280, defaultWidth / 4, 30);
        this.add(resultLabel);
    }

    private void addButton() {

        loginButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();
            AppUser appUser = appUserController.login(username, password);

            if(appUser != null) {
                resultLabel.setText("Hello " + username);
                System.out.println(appUser);
                dispose();
                if(appUser.getRole() == AppUserRole.USER_ROLE)
                    new UserGUI(appUser);
                else
                    new AdminGUI(appUser);
            }
            else
                resultLabel.setText("Invalid credentials");
        });

        registerButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            String result = appUserController.insertUser(username, password);
            resultLabel.setText(result);
        });
    }

    public LoginGUI() {
        windowInit();
        loginScreenInit();
        addButton();
    }
}
