/*
 * File: PengembalianUserFrame.java
 * Package: view
 * Deskripsi: Form untuk transaksi pengembalian buku oleh USER (hanya untuk dirinya sendiri)
 * Komponen: JFrame, JPanel, JLabel, JTextField, JComboBox, 
 *           JTable, JButton, JScrollPane
 * Layout: BorderLayout + GroupLayout
 */

package view;

import controller.PengembalianController;
import controller.PeminjamanController;
import model.AnggotaModel;
import model.PeminjamanModel;
import model.DetailPeminjamanModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class PengembalianUserFrame extends JFrame {
    private PengembalianController pengembalianController;
    private PeminjamanController peminjamanController;
    private AnggotaModel currentUser;
    private JFrame parent;
    
    // Komponen
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    
    // Pencarian peminjaman
    private JLabel cariLabel;
    private JComboBox<PeminjamanModel> peminjamanCombo;
    private JButton refreshButton;
    
    // Data pengembalian
    private JLabel noKembaliLabel;
    private JTextField noKembaliField;
    private JLabel tanggalLabel;
    private JTextField tanggalField;
    private JLabel noPinjamLabel;
    private JTextField noPinjamField;
    private JLabel tglPinjamLabel;
    private JTextField tglPinjamField;
    private JLabel jatuhTempoLabel;
    private JTextField jatuhTempoField;
    
    // Table buku
    private JTable bukuTable;
    private DefaultTableModel bukuModel;
    private JScrollPane tableScroll;
    
    // Denda
    private JLabel dendaLabel;
    private JTextField dendaField;
    private JButton hitungDendaButton;
    
    // Buttons
    private JButton simpanButton;
    private JButton batalButton;
    
    // Data
    private PeminjamanModel peminjamanDipilih;
    private List<DetailPeminjamanModel> listDetail;
    
    public PengembalianUserFrame(JFrame parent, AnggotaModel user) {
        this.parent = parent;
        this.currentUser = user;
        this.pengembalianController = new PengembalianController();
        this.peminjamanController = new PeminjamanController();
        initComponents();
        loadPeminjamanUser();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Transaksi Pengembalian Buku - User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Cari Peminjaman
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Pilih Peminjaman Aktif"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        cariLabel = new JLabel("Peminjaman Aktif Anda:");
        peminjamanCombo = new JComboBox<>();
        peminjamanCombo.setPreferredSize(new Dimension(400, 25));
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.BLACK);
        
        gbc.gridx = 0; gbc.gridy = 0; topPanel.add(cariLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; topPanel.add(peminjamanCombo, gbc);
        gbc.gridx = 2; gbc.gridy = 0; topPanel.add(refreshButton, gbc);
        
        // Data Pengembalian Panel
        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBorder(BorderFactory.createTitledBorder("Data Pengembalian"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        
        noKembaliLabel = new JLabel("No. Pengembalian:");
        noKembaliField = new JTextField(15);
        noKembaliField.setEditable(false);
        noKembaliField.setText(pengembalianController.generateNoPengembalian());
        
        tanggalLabel = new JLabel("Tgl. Pengembalian:");
        tanggalField = new JTextField(10);
        tanggalField.setEditable(false);
        tanggalField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        
        noPinjamLabel = new JLabel("No. Peminjaman:");
        noPinjamField = new JTextField(15);
        noPinjamField.setEditable(false);
        
        tglPinjamLabel = new JLabel("Tgl Pinjam:");
        tglPinjamField = new JTextField(10);
        tglPinjamField.setEditable(false);
        
        jatuhTempoLabel = new JLabel("Jatuh Tempo:");
        jatuhTempoField = new JTextField(10);
        jatuhTempoField.setEditable(false);
        
        gbc2.gridx = 0; gbc2.gridy = 0; dataPanel.add(noKembaliLabel, gbc2);
        gbc2.gridx = 1; gbc2.gridy = 0; dataPanel.add(noKembaliField, gbc2);
        gbc2.gridx = 2; gbc2.gridy = 0; dataPanel.add(tanggalLabel, gbc2);
        gbc2.gridx = 3; gbc2.gridy = 0; dataPanel.add(tanggalField, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1; dataPanel.add(noPinjamLabel, gbc2);
        gbc2.gridx = 1; gbc2.gridy = 1; gbc2.gridwidth = 3; dataPanel.add(noPinjamField, gbc2);
        gbc2.gridwidth = 1;
        
        gbc2.gridx = 0; gbc2.gridy = 2; dataPanel.add(tglPinjamLabel, gbc2);
        gbc2.gridx = 1; gbc2.gridy = 2; dataPanel.add(tglPinjamField, gbc2);
        gbc2.gridx = 2; gbc2.gridy = 2; dataPanel.add(jatuhTempoLabel, gbc2);
        gbc2.gridx = 3; gbc2.gridy = 2; dataPanel.add(jatuhTempoField, gbc2);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; topPanel.add(dataPanel, gbc);
        
        // Center Panel - Daftar Buku
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Daftar Buku yang Dipinjam"));
        
        String[] columns = {"No", "Kode Buku", "Judul Buku", "Pengarang", "Status", "Denda"};
        bukuModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bukuTable = new JTable(bukuModel);
        tableScroll = new JScrollPane(bukuTable);
        
        // Denda Panel
        JPanel dendaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dendaLabel = new JLabel("Total Denda: Rp ");
        dendaField = new JTextField(15);
        dendaField.setEditable(false);
        dendaField.setHorizontalAlignment(JTextField.RIGHT);
        dendaField.setFont(new Font("Arial", Font.BOLD, 14));
        dendaField.setForeground(Color.RED);
        
        hitungDendaButton = new JButton("Hitung Denda");
        hitungDendaButton.setBackground(new Color(255, 153, 0));
        hitungDendaButton.setForeground(Color.BLACK);
        
        dendaPanel.add(dendaLabel);
        dendaPanel.add(dendaField);
        dendaPanel.add(hitungDendaButton);
        
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(dendaPanel, BorderLayout.SOUTH);
        
        // Bottom Panel
        bottomPanel = new JPanel(new FlowLayout());
        simpanButton = new JButton("Proses Pengembalian");
        simpanButton.setBackground(new Color(0, 153, 76));
        simpanButton.setForeground(Color.BLACK);
        simpanButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        batalButton = new JButton("Batal");
        batalButton.setBackground(new Color(102, 102, 102));
        batalButton.setForeground(Color.BLACK);
        
        bottomPanel.add(simpanButton);
        bottomPanel.add(batalButton);
        
        // Add to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Add listeners
        peminjamanCombo.addActionListener(e -> {
            if (peminjamanCombo.getSelectedItem() != null) {
                pilihPeminjaman();
            }
        });
        refreshButton.addActionListener(e -> loadPeminjamanUser());
        hitungDendaButton.addActionListener(e -> hitungDenda());
        simpanButton.addActionListener(e -> simpanPengembalian());
        batalButton.addActionListener(e -> dispose());
    }
    
    private void loadPeminjamanUser() {
        peminjamanCombo.removeAllItems();
        
        // Ambil peminjaman yang masih aktif milik user yang login
        List<PeminjamanModel> list = peminjamanController.getPeminjamanByAnggota(currentUser.getIdAnggota());
        
        for (PeminjamanModel p : list) {
            // Hanya tampilkan yang statusnya masih "dipinjam"
            if ("dipinjam".equals(p.getStatusPeminjaman())) {
                // Cek apakah masih ada buku yang belum dikembalikan
                List<DetailPeminjamanModel> detail = peminjamanController.getDetailPeminjaman(p.getIdPeminjaman());
                boolean adaBelumKembali = false;
                for (DetailPeminjamanModel d : detail) {
                    if (!d.isStatusKembali()) {
                        adaBelumKembali = true;
                        break;
                    }
                }
                
                if (adaBelumKembali) {
                    peminjamanCombo.addItem(p);
                }
            }
        }
        
        if (peminjamanCombo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Tidak ada peminjaman aktif yang perlu dikembalikan!", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void pilihPeminjaman() {
        peminjamanDipilih = (PeminjamanModel) peminjamanCombo.getSelectedItem();
        if (peminjamanDipilih == null) return;
        
        // Tampilkan data peminjaman
        noPinjamField.setText(peminjamanDipilih.getNoPeminjaman());
        tglPinjamField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(peminjamanDipilih.getTanggalPinjam()));
        jatuhTempoField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(peminjamanDipilih.getTanggalJatuhTempo()));
        
        // Load detail peminjaman
        listDetail = peminjamanController.getDetailPeminjaman(peminjamanDipilih.getIdPeminjaman());
        loadDetailTable();
        
        // Reset denda
        dendaField.setText("0");
    }
    
    private void loadDetailTable() {
        bukuModel.setRowCount(0);
        
        int no = 1;
        double totalDenda = 0;
        
        for (DetailPeminjamanModel d : listDetail) {
            if (!d.isStatusKembali()) { // Hanya tampilkan yang belum dikembalikan
                Object[] row = {
                    no++,
                    d.getBuku().getKodeBuku(),
                    d.getBuku().getJudulBuku(),
                    d.getBuku().getPengarang(),
                    "Belum Kembali",
                    d.getDenda()
                };
                bukuModel.addRow(row);
                totalDenda += d.getDenda();
            }
        }
        
        if (totalDenda > 0) {
            dendaField.setText(String.format("%,.0f", totalDenda));
        }
    }
    
    private void hitungDenda() {
        if (peminjamanDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!");
            return;
        }
        
        Date sekarang = new Date();
        double totalDenda = 0;
        
        for (DetailPeminjamanModel detail : listDetail) {
            if (!detail.isStatusKembali()) {
                double denda = pengembalianController.hitungDendaBuku(
                    peminjamanDipilih.getTanggalJatuhTempo(), 
                    sekarang
                );
                detail.setDenda(denda);
                totalDenda += denda;
            }
        }
        
        dendaField.setText(String.format("%,.0f", totalDenda));
        
        // Update table
        loadDetailTable();
        
        if (totalDenda > 0) {
            JOptionPane.showMessageDialog(this, 
                "Total denda keterlambatan: Rp " + String.format("%,.0f", totalDenda),
                "Info Denda", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Tidak ada denda (pengembalian tepat waktu)",
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void simpanPengembalian() {
        if (peminjamanDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!");
            return;
        }
        
        // Hitung denda final
        double totalDenda = 0;
        for (DetailPeminjamanModel detail : listDetail) {
            if (!detail.isStatusKembali()) {
                totalDenda += detail.getDenda();
            }
        }
        
        // Konfirmasi
        String message = "Anda akan mengembalikan buku:\n" +
                        "No. Peminjaman: " + peminjamanDipilih.getNoPeminjaman() + "\n" +
                        "Jumlah Buku: " + (bukuModel.getRowCount()) + "\n";
        
        if (totalDenda > 0) {
            message += "Total Denda: Rp " + String.format("%,.0f", totalDenda) + "\n\n";
        } else {
            message += "\n";
        }
        
        message += "Yakin akan memproses pengembalian?";
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            message, 
            "Konfirmasi Pengembalian", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = pengembalianController.prosesPengembalian(
                peminjamanDipilih,
                currentUser.getIdAnggota(), // Petugas adalah user sendiri
                totalDenda
            );
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Pengembalian berhasil diproses!\n" +
                    "No. Pengembalian: " + noKembaliField.getText() + "\n" +
                    "Total Denda: Rp " + String.format("%,.0f", totalDenda),
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }
}