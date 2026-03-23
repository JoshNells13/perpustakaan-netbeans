/*
 * File: MainFrameUser.java
 * Package: view
 * Deskripsi: Main menu untuk user/siswa
 * Komponen: JFrame, JMenuBar, JMenu, JMenuItem, JLabel
 * Layout: BorderLayout
 */
package view;

import controller.AuthController;
import model.AnggotaModel;
import javax.swing.*;
import java.awt.*;

public class MainFrameUser extends JFrame {

    private AuthController authController;
    private AnggotaModel currentUser;

    // Komponen
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu transaksiMenu;
    private JMenu helpMenu;

    private JMenuItem logoutItem;
    private JMenuItem exitItem;
    private JMenuItem pinjamItem;
    private JMenuItem kembaliItem;
    private JMenuItem riwayatItem;
    private JMenuItem cariBukuItem;
    private JMenuItem aboutItem;

    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JLabel infoLabel;
    private JPanel infoPanel;

    public MainFrameUser(AuthController authController, AnggotaModel user) {
        this.authController = authController;
        this.currentUser = user;
        initComponents();
        setupMenu();
    }

    private void initComponents() {
        // Frame setup
        setTitle("Aplikasi Perpustakaan Digital - User Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
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

        // Transaksi Menu
        transaksiMenu = new JMenu("Transaksi");
        pinjamItem = new JMenuItem("Peminjaman Buku");
        kembaliItem = new JMenuItem("Pengembalian Buku");
        riwayatItem = new JMenuItem("Riwayat Peminjaman");
        cariBukuItem = new JMenuItem("Cari Buku");

        transaksiMenu.add(pinjamItem);
        transaksiMenu.add(kembaliItem);
        transaksiMenu.addSeparator();
        transaksiMenu.add(riwayatItem);
        transaksiMenu.add(cariBukuItem);

        // Help Menu
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        // Add menus to bar
        menuBar.add(fileMenu);
        menuBar.add(transaksiMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Info Panel
        infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(new Color(0, 153, 76));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        welcomeLabel = new JLabel("Halo, " + currentUser.getNamaLengkap());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        infoLabel = new JLabel("NIS: " + currentUser.getNis() + " | Kelas: " + currentUser.getKelas());
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel("Anda login sebagai: SISWA");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setForeground(Color.WHITE);

        infoPanel.add(welcomeLabel);
        infoPanel.add(infoLabel);
        infoPanel.add(roleLabel);

        // Welcome message
        JLabel descLabel = new JLabel("Selamat datang di Perpustakaan Digital", JLabel.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel menuLabel = new JLabel("Silakan pilih menu Transaksi untuk memulai", JLabel.CENTER);
        menuLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(descLabel);
        centerPanel.add(menuLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void setupMenu() {
        // File menu
        logoutItem.addActionListener(e -> authController.logout(this));
        exitItem.addActionListener(e -> System.exit(0));

        // Transaksi menu
        pinjamItem.addActionListener(e -> openPeminjamanFrame());
        kembaliItem.addActionListener(e -> openPengembalianFrame());
        riwayatItem.addActionListener(e -> showRiwayat());
        cariBukuItem.addActionListener(e -> cariBuku());

        // Help menu
        aboutItem.addActionListener(e -> showAbout());
    }

    private void openPeminjamanFrame() {
        // Buka PeminjamanUserFrame khusus user (tidak bisa pilih anggota)
        PeminjamanUserFrame peminjamanFrame = new PeminjamanUserFrame(this, currentUser);
        peminjamanFrame.setVisible(true);
    }

    private void openPengembalianFrame() {
        // Buka PengembalianUserFrame khusus user (hanya untuk dirinya sendiri)
        PengembalianUserFrame pengembalianFrame = new PengembalianUserFrame(this, currentUser);
        pengembalianFrame.setVisible(true);
    }

    private void showRiwayat() {
        JOptionPane.showMessageDialog(this,
                "Fitur Riwayat Peminjaman akan segera tersedia!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cariBuku() {
        JOptionPane.showMessageDialog(this,
                "Fitur Pencarian Buku akan segera tersedia!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Aplikasi Perpustakaan Digital\n"
                + "Versi 1.0\n"
                + "© 2024 - Rekayasa Perangkat Lunak",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
