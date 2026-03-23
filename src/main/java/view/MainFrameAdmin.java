/*
 * File: MainFrameAdmin.java
 * Package: view
 * Deskripsi: Main menu untuk admin
 * Komponen: JFrame, JMenuBar, JMenu, JMenuItem, JLabel
 * Layout: BorderLayout
 */

package view;

import controller.AuthController;
import model.AnggotaModel;
import javax.swing.*;
import java.awt.*;

public class MainFrameAdmin extends JFrame {
    private AuthController authController;
    private AnggotaModel currentUser;
    
    // Komponen
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu masterMenu;
    private JMenu transaksiMenu;
    private JMenu laporanMenu;
    private JMenu helpMenu;
    
    private JMenuItem logoutItem;
    private JMenuItem exitItem;
    private JMenuItem bukuItem;
    private JMenuItem anggotaItem;
    private JMenuItem peminjamanItem;
    private JMenuItem pengembalianItem;
    private JMenuItem laporanBukuItem;
    private JMenuItem laporanPeminjamanItem;
    private JMenuItem aboutItem;
    
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JLabel roleLabel;
    private JPanel infoPanel;
    
    public MainFrameAdmin(AuthController authController, AnggotaModel user) {
        this.authController = authController;
        this.currentUser = user;
        initComponents();
        setupMenu();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Aplikasi Perpustakaan Digital - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Menu Bar
        menuBar = new JMenuBar();
        
        // File Menu
        fileMenu = new JMenu("File");
        logoutItem = new JMenuItem("Logout");
        exitItem = new JMenuItem("Exit");
        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Master Menu
        masterMenu = new JMenu("Master Data");
        bukuItem = new JMenuItem("Data Buku");
        anggotaItem = new JMenuItem("Data Anggota");
        masterMenu.add(bukuItem);
        masterMenu.add(anggotaItem);
        
        // Transaksi Menu
        transaksiMenu = new JMenu("Transaksi");
        peminjamanItem = new JMenuItem("Peminjaman Buku");
        pengembalianItem = new JMenuItem("Pengembalian Buku");
        transaksiMenu.add(peminjamanItem);
        transaksiMenu.add(pengembalianItem);
        

        
        // Help Menu
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        
        // Add menus to bar
        menuBar.add(fileMenu);
        menuBar.add(masterMenu);
        menuBar.add(transaksiMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Info Panel
        infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(new Color(0, 102, 204));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        welcomeLabel = new JLabel("Selamat Datang, " + currentUser.getNamaLengkap());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        
        roleLabel = new JLabel("Anda login sebagai: ADMIN");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roleLabel.setForeground(Color.WHITE);
        
        infoPanel.add(welcomeLabel);
        infoPanel.add(roleLabel);
        
        // Add welcome message
        JLabel descLabel = new JLabel("Silakan pilih menu untuk memulai", JLabel.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(descLabel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupMenu() {
        // File menu
        logoutItem.addActionListener(e -> authController.logout(this));
        exitItem.addActionListener(e -> System.exit(0));
        
        // Master menu
        bukuItem.addActionListener(e -> openBukuFrame());
        anggotaItem.addActionListener(e -> openAnggotaFrame());
        
        // Transaksi menu
        peminjamanItem.addActionListener(e -> openPeminjamanFrame());
        pengembalianItem.addActionListener(e -> openPengembalianFrame());
        

        
        // Help menu
        aboutItem.addActionListener(e -> showAbout());
    }
    
    private void openBukuFrame() {
        KelolaBukuFrame bukuFrame = new KelolaBukuFrame(this, currentUser);
        bukuFrame.setVisible(true);
    }
    
    private void openAnggotaFrame() {
        KelolaAnggotaFrame anggotaFrame = new KelolaAnggotaFrame(this, currentUser);
        anggotaFrame.setVisible(true);
    }
    
    private void openPeminjamanFrame() {
        PeminjamanFrame peminjamanFrame = new PeminjamanFrame(this, currentUser);
        peminjamanFrame.setVisible(true);
    }
    
    private void openPengembalianFrame() {
        PengembalianFrame pengembalianFrame = new PengembalianFrame(this, currentUser);
        pengembalianFrame.setVisible(true);
    }
 
    
    private void showAbout() {
        JOptionPane.showMessageDialog(this, 
            "Aplikasi Perpustakaan Digital\n" +
            "Versi 1.0\n" +
            "© 2024 - Rekayasa Perangkat Lunak", 
            "About", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}