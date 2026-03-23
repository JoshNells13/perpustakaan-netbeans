/*
 * File: LoginFrame.java
 * Package: view
 * Deskripsi: Form untuk login user dan admin
 * Komponen: JFrame, JPanel, JLabel, JTextField, JPasswordField, JButton
 * Layout: GroupLayout
 */
package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private AuthController authController;

    // Komponen GUI
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel footerLabel;

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        initComponents();
    }

    private void initComponents() {
        // Setup frame
        setTitle("Login - Perpustakaan Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));

        // Title
        titleLabel = new JLabel("PERPUSTAKAAN DIGITAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12));

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));

        // Buttons
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.setBackground(new Color(0, 102, 204));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);

        // Footer
        footerLabel = new JLabel("© 2024 Perpustakaan Sekolah");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        footerLabel.setForeground(Color.GRAY);

        // Add action listeners
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> openRegister());

        // Setup layout using GroupLayout
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(titleLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(usernameLabel)
                                                        .addComponent(passwordLabel))
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(usernameField)
                                                        .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(footerLabel))
                                .addGap(50, 50, 50))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(titleLabel)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(usernameLabel)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordLabel)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(loginButton)
                                .addComponent(registerButton))
                        .addGap(30, 30, 30)
                        .addComponent(footerLabel)
                        .addGap(20, 20, 20)
        );

        add(mainPanel);

        // Enter key action
        passwordField.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        authController.login(username, password);
    }

    private void openRegister() {
        authController.showRegistrasiFrame();
        this.dispose();
    }
}
