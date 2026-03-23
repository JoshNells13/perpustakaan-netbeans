/*
 * File: PengembalianFrame.java
 * Package: view
 * Deskripsi: Form untuk transaksi pengembalian buku
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

public class PengembalianFrame extends JFrame {
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
    private JTextField cariField;
    private JButton cariButton;
    private JComboBox<PeminjamanModel> peminjamanCombo;
    
    // Data pengembalian
    private JLabel noKembaliLabel;
    private JTextField noKembaliField;
    private JLabel tanggalLabel;
    private JTextField tanggalField;
    private JLabel anggotaLabel;
    private JTextField anggotaField;
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
    
    public PengembalianFrame(JFrame parent, AnggotaModel user) {
        this.parent = parent;
        this.currentUser = user;
        this.pengembalianController = new PengembalianController();
        this.peminjamanController = new PeminjamanController();
        initComponents();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Transaksi Pengembalian Buku");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Cari Peminjaman
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Cari Data Peminjaman"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        cariLabel = new JLabel("Cari No/Nama Anggota:");
        cariField = new JTextField(20);
        cariButton = new JButton("Cari");
        cariButton.setBackground(new Color(0, 102, 204));
        cariButton.setForeground(Color.BLACK);
        
        peminjamanCombo = new JComboBox<>();
        peminjamanCombo.setPreferredSize(new Dimension(300, 25));
        
        gbc.gridx = 0; gbc.gridy = 0; topPanel.add(cariLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; topPanel.add(cariField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; topPanel.add(cariButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; topPanel.add(new JLabel("Pilih Peminjaman:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; topPanel.add(peminjamanCombo, gbc);
        
        // Data Peminjaman Panel
        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBorder(BorderFactory.createTitledBorder("Data Peminjaman"));
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
        
        anggotaLabel = new JLabel("Anggota:");
        anggotaField = new JTextField(20);
        anggotaField.setEditable(false);
        
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
        
        gbc2.gridx = 0; gbc2.gridy = 1; dataPanel.add(anggotaLabel, gbc2);
        gbc2.gridx = 1; gbc2.gridy = 1; gbc2.gridwidth = 3; dataPanel.add(anggotaField, gbc2);
        gbc2.gridwidth = 1;
        
        gbc2.gridx = 0; gbc2.gridy = 2; dataPanel.add(tglPinjamLabel, gbc2);
        gbc2.gridx = 1; gbc2.gridy = 2; dataPanel.add(tglPinjamField, gbc2);
        gbc2.gridx = 2; gbc2.gridy = 2; dataPanel.add(jatuhTempoLabel, gbc2);
        gbc2.gridx = 3; gbc2.gridy = 2; dataPanel.add(jatuhTempoField, gbc2);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3; topPanel.add(dataPanel, gbc);
        
        // Center Panel - Daftar Buku
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Daftar Buku yang Dipinjam"));
        
        String[] columns = {"No", "Kode Buku", "Judul Buku", "Status", "Denda"};
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
        simpanButton = new JButton("Simpan Pengembalian");
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
        cariButton.addActionListener(e -> cariPeminjaman());
        peminjamanCombo.addActionListener(e -> {
            if (peminjamanCombo.getSelectedItem() != null) {
                pilihPeminjaman();
            }
        });
        hitungDendaButton.addActionListener(e -> hitungDenda());
        simpanButton.addActionListener(e -> simpanPengembalian());
        batalButton.addActionListener(e -> dispose());
    }
    
    private void cariPeminjaman() {
        String keyword = cariField.getText();
        peminjamanCombo.removeAllItems();
        
        List<PeminjamanModel> list = peminjamanController.searchPeminjamanAktif(keyword);
        for (PeminjamanModel p : list) {
            peminjamanCombo.addItem(p);
        }
        
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data peminjaman aktif ditemukan!");
        }
    }
    
    private void pilihPeminjaman() {
        peminjamanDipilih = (PeminjamanModel) peminjamanCombo.getSelectedItem();
        if (peminjamanDipilih == null) return;
        
        // Tampilkan data peminjaman
        if (peminjamanDipilih.getAnggota() != null) {
            anggotaField.setText(peminjamanDipilih.getAnggota().getNamaLengkap() + 
                               " (" + peminjamanDipilih.getAnggota().getNis() + ")");
        }
        
        tglPinjamField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(peminjamanDipilih.getTanggalPinjam()));
        jatuhTempoField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(peminjamanDipilih.getTanggalJatuhTempo()));
        
        // Load detail peminjaman
        listDetail = peminjamanController.getDetailPeminjaman(peminjamanDipilih.getIdPeminjaman());
        loadDetailTable();
    }
    
    private void loadDetailTable() {
        bukuModel.setRowCount(0);
        
        int no = 1;
        for (DetailPeminjamanModel d : listDetail) {
            if (!d.isStatusKembali()) { // Hanya tampilkan yang belum dikembalikan
                Object[] row = {
                    no++,
                    d.getBuku().getKodeBuku(),
                    d.getBuku().getJudulBuku(),
                    "Belum Kembali",
                    d.getDenda()
                };
                bukuModel.addRow(row);
            }
        }
    }
    
    private void hitungDenda() {
        if (peminjamanDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!");
            return;
        }
        
        double totalDenda = pengembalianController.hitungTotalDenda(
            peminjamanDipilih.getTanggalJatuhTempo(), 
            new Date(),
            listDetail
        );
        
        dendaField.setText(String.format("%,.0f", totalDenda));
    }
    
    private void simpanPengembalian() {
        if (peminjamanDipilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!");
            return;
        }
        
        // Hitung denda
        double totalDenda = pengembalianController.hitungTotalDenda(
            peminjamanDipilih.getTanggalJatuhTempo(), 
            new Date(),
            listDetail
        );
        
        // Konfirmasi
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Total Denda: Rp " + String.format("%,.0f", totalDenda) + 
            "\n\nYakin akan menyimpan pengembalian ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = pengembalianController.prosesPengembalian(
                peminjamanDipilih,
                currentUser.getIdAnggota(),
                totalDenda
            );
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Pengembalian berhasil disimpan!");
                dispose();
            }
        }
    }
}