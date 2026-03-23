/*
 * File: RegistrasiFrame.java
 * Package: view
 * Deskripsi: Form untuk registrasi anggota baru
 * Komponen: JFrame, JPanel, JLabel, JTextField, JPasswordField, 
 *           JRadioButton, JTextArea, JButton
 * Layout: GroupLayout
 */

package view;

import controller.AuthController;
import model.AnggotaModel;
import javax.swing.*;
import java.awt.*;

public class RegistrasiFrame extends JFrame {
    private AuthController authController;
    
    // Komponen GUI
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel nisLabel;
    private JTextField nisField;
    private JLabel namaLabel;
    private JTextField namaField;
    private JLabel kelasLabel;
    private JTextField kelasField;
    private JLabel jkLabel;
    private JRadioButton jkLakiButton;
    private JRadioButton jkPerempuanButton;
    private ButtonGroup jkGroup;
    private JLabel alamatLabel;
    private JTextArea alamatArea;
    private JScrollPane alamatScroll;
    private JLabel telpLabel;
    private JTextField telpField;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel confirmLabel;
    private JPasswordField confirmField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel requiredLabel;
    
    public RegistrasiFrame(AuthController authController) {
        this.authController = authController;
        initComponents();
    }
    
    private void initComponents() {
        // Setup frame
        setTitle("Registrasi Anggota Baru");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(450, 550);
        setLocationRelativeTo(null);
        
        // Main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        titleLabel = new JLabel("FORM REGISTRASI ANGGOTA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        
        // NIS
        nisLabel = new JLabel("NIS *:");
        nisField = new JTextField(20);
        
        // Nama
        namaLabel = new JLabel("Nama Lengkap *:");
        namaField = new JTextField(20);
        
        // Kelas
        kelasLabel = new JLabel("Kelas:");
        kelasField = new JTextField(10);
        
        // Jenis Kelamin
        jkLabel = new JLabel("Jenis Kelamin:");
        jkLakiButton = new JRadioButton("Laki-laki");
        jkPerempuanButton = new JRadioButton("Perempuan");
        jkGroup = new ButtonGroup();
        jkGroup.add(jkLakiButton);
        jkGroup.add(jkPerempuanButton);
        
        // Alamat
        alamatLabel = new JLabel("Alamat:");
        alamatArea = new JTextArea(3, 20);
        alamatArea.setLineWrap(true);
        alamatScroll = new JScrollPane(alamatArea);
        
        // Telepon
        telpLabel = new JLabel("No. Telepon:");
        telpField = new JTextField(15);
        
        // Username
        usernameLabel = new JLabel("Username *:");
        usernameField = new JTextField(15);
        
        // Password
        passwordLabel = new JLabel("Password *:");
        passwordField = new JPasswordField(15);
        
        confirmLabel = new JLabel("Confirm *:");
        confirmField = new JPasswordField(15);
        
        // Buttons
        registerButton = new JButton("Daftar");
        registerButton.setBackground(new Color(0, 153, 76));
        registerButton.setForeground(Color.BLACK);
        
        cancelButton = new JButton("Batal");
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.BLACK);
        
        requiredLabel = new JLabel("* Wajib diisi");
        requiredLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        requiredLabel.setForeground(Color.RED);
        
        // Action listeners
        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> {
            authController.showLoginFrame();
            this.dispose();
        });
        
        // Layout
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nisLabel)
                        .addComponent(namaLabel)
                        .addComponent(kelasLabel)
                        .addComponent(jkLabel)
                        .addComponent(alamatLabel)
                        .addComponent(telpLabel)
                        .addComponent(usernameLabel)
                        .addComponent(passwordLabel)
                        .addComponent(confirmLabel))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nisField)
                        .addComponent(namaField)
                        .addComponent(kelasField)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jkLakiButton)
                            .addComponent(jkPerempuanButton))
                        .addComponent(alamatScroll)
                        .addComponent(telpField)
                        .addComponent(usernameField)
                        .addComponent(passwordField)
                        .addComponent(confirmField)))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(registerButton)
                    .addComponent(cancelButton))
                .addComponent(requiredLabel)
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nisLabel)
                    .addComponent(nisField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(namaLabel)
                    .addComponent(namaField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(kelasLabel)
                    .addComponent(kelasField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jkLabel)
                    .addComponent(jkLakiButton)
                    .addComponent(jkPerempuanButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alamatLabel)
                    .addComponent(alamatScroll))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(telpLabel)
                    .addComponent(telpField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmLabel)
                    .addComponent(confirmField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(registerButton)
                    .addComponent(cancelButton))
                .addComponent(requiredLabel)
        );
        
        add(mainPanel);
    }
    
    private void register() {
        // Validasi password
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());
        
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, 
                "Password dan Konfirmasi tidak cocok!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Buat object anggota
        AnggotaModel anggota = new AnggotaModel();
        anggota.setNis(nisField.getText());
        anggota.setNamaLengkap(namaField.getText());
        anggota.setKelas(kelasField.getText());
        anggota.setJenisKelamin(jkLakiButton.isSelected() ? "L" : 
                                jkPerempuanButton.isSelected() ? "P" : "");
        anggota.setAlamat(alamatArea.getText());
        anggota.setNoTelepon(telpField.getText());
        anggota.setUsername(usernameField.getText());
        anggota.setPassword(password);
        
        authController.register(anggota);
    }
}