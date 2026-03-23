/*
 * File: PeminjamanFrame.java
 * Package: view
 * Deskripsi: Form untuk transaksi peminjaman buku
 * Komponen: JFrame, JPanel, JLabel, JTextField, JComboBox, 
 *           JTable, JButton, JScrollPane
 * Layout: BorderLayout + GroupLayout
 */

package view;

import controller.PeminjamanController;
import controller.BukuController;
import controller.AnggotaController;
import model.AnggotaModel;
import model.BukuModel;
import model.PeminjamanModel;
import model.DetailPeminjamanModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeminjamanFrame extends JFrame {
    private PeminjamanController peminjamanController;
    private BukuController bukuController;
    private AnggotaController anggotaController;
    private AnggotaModel currentUser;
    private JFrame parent;
    
    // Komponen
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    
    // Data peminjaman
    private JLabel noPinjamLabel;
    private JTextField noPinjamField;
    private JLabel tanggalLabel;
    private JTextField tanggalField;
    private JLabel anggotaLabel;
    private JComboBox<AnggotaModel> anggotaCombo;
    private JLabel jatuhTempoLabel;
    private JTextField jatuhTempoField;
    
    // Input buku
    private JLabel bukuLabel;
    private JComboBox<BukuModel> bukuCombo;
    private JButton tambahButton;
    
    // Table detail
    private JTable detailTable;
    private DefaultTableModel detailModel;
    private JScrollPane tableScroll;
    
    // Buttons
    private JButton simpanButton;
    private JButton batalButton;
    private JButton hapusItemButton;
    
    // List detail sementara
    private List<DetailPeminjamanModel> listDetail;
    private PeminjamanModel peminjamanSaatIni;
    
    public PeminjamanFrame(JFrame parent, AnggotaModel user) {
        this.parent = parent;
        this.currentUser = user;
        this.peminjamanController = new PeminjamanController();
        this.bukuController = new BukuController();
        this.anggotaController = new AnggotaController();
        this.listDetail = new ArrayList<>();
        initComponents();
        initData();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Transaksi Peminjaman Buku");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Data Peminjaman
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Data Peminjaman"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // No Peminjaman
        noPinjamLabel = new JLabel("No. Peminjaman:");
        noPinjamField = new JTextField(15);
        noPinjamField.setEditable(false);
        noPinjamField.setText(peminjamanController.generateNoPeminjaman());
        
        // Tanggal
        tanggalLabel = new JLabel("Tanggal Pinjam:");
        tanggalField = new JTextField(10);
        tanggalField.setEditable(false);
        tanggalField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        
        // Anggota
        anggotaLabel = new JLabel("Anggota *:");
        anggotaCombo = new JComboBox<>();
        anggotaCombo.setPreferredSize(new Dimension(200, 25));
        
        // Jatuh Tempo
        jatuhTempoLabel = new JLabel("Jatuh Tempo:");
        jatuhTempoField = new JTextField(10);
        jatuhTempoField.setEditable(false);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 7);
        jatuhTempoField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()));
        
        // Layout top panel
        gbc.gridx = 0; gbc.gridy = 0; topPanel.add(noPinjamLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; topPanel.add(noPinjamField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; topPanel.add(tanggalLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 0; topPanel.add(tanggalField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; topPanel.add(anggotaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3; topPanel.add(anggotaCombo, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 2; topPanel.add(jatuhTempoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; topPanel.add(jatuhTempoField, gbc);
        
        // Input Buku Panel
        JPanel inputBukuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputBukuPanel.setBorder(BorderFactory.createTitledBorder("Tambah Buku"));
        
        bukuLabel = new JLabel("Pilih Buku:");
        bukuCombo = new JComboBox<>();
        bukuCombo.setPreferredSize(new Dimension(300, 25));
        tambahButton = new JButton("Tambah");
        tambahButton.setBackground(new Color(0, 102, 204));
        tambahButton.setForeground(Color.BLACK);
        
        inputBukuPanel.add(bukuLabel);
        inputBukuPanel.add(bukuCombo);
        inputBukuPanel.add(tambahButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4; topPanel.add(inputBukuPanel, gbc);
        
        // Center Panel - Detail Buku
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Daftar Buku yang Dipinjam"));
        
        String[] columns = {"No", "Kode Buku", "Judul Buku", "Status"};
        detailModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        detailTable = new JTable(detailModel);
        detailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableScroll = new JScrollPane(detailTable);
        
        // Button hapus item
        hapusItemButton = new JButton("Hapus Item");
        hapusItemButton.setBackground(new Color(204, 0, 0));
        hapusItemButton.setForeground(Color.BLACK);
        
        JPanel buttonItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonItemPanel.add(hapusItemButton);
        
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(buttonItemPanel, BorderLayout.SOUTH);
        
        // Bottom Panel
        bottomPanel = new JPanel(new FlowLayout());
        simpanButton = new JButton("Simpan Peminjaman");
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
        tambahButton.addActionListener(e -> tambahBuku());
        hapusItemButton.addActionListener(e -> hapusItem());
        simpanButton.addActionListener(e -> simpanPeminjaman());
        batalButton.addActionListener(e -> dispose());
    }
    
    private void initData() {
        // Load anggota ke combo
        List<AnggotaModel> listAnggota = anggotaController.getAllAnggota();
        for (AnggotaModel a : listAnggota) {
            if (a.isStatusAktif()) {
                anggotaCombo.addItem(a);
            }
        }
        
        // Load buku ke combo
        loadBukuCombo();
    }
    
    private void loadBukuCombo() {
        bukuCombo.removeAllItems();
        List<BukuModel> listBuku = bukuController.getBukuTersedia();
        for (BukuModel b : listBuku) {
            bukuCombo.addItem(b);
        }
    }
    
    private void tambahBuku() {
        BukuModel buku = (BukuModel) bukuCombo.getSelectedItem();
        if (buku == null) {
            JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu!");
            return;
        }
        
        // Cek apakah buku sudah ditambahkan
        for (DetailPeminjamanModel d : listDetail) {
            if (d.getIdBuku() == buku.getIdBuku()) {
                JOptionPane.showMessageDialog(this, "Buku sudah ditambahkan!");
                return;
            }
        }
        
        // Tambah ke list detail
        DetailPeminjamanModel detail = new DetailPeminjamanModel();
        detail.setIdBuku(buku.getIdBuku());
        detail.setBuku(buku);
        detail.setStatusKembali(false);
        
        listDetail.add(detail);
        
        // Update table
        Object[] row = {
            listDetail.size(),
            buku.getKodeBuku(),
            buku.getJudulBuku(),
            "Dipinjam"
        };
        detailModel.addRow(row);
        
        // Hapus buku dari combo
        bukuCombo.removeItem(buku);
    }
    
    private void hapusItem() {
        int row = detailTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih item yang akan dihapus!");
            return;
        }
        
        // Ambil detail yang dihapus
        DetailPeminjamanModel detail = listDetail.get(row);
        
        // Kembalikan buku ke combo
        bukuCombo.addItem(detail.getBuku());
        
        // Hapus dari list
        listDetail.remove(row);
        detailModel.removeRow(row);
        
        // Update nomor urut
        for (int i = 0; i < detailModel.getRowCount(); i++) {
            detailModel.setValueAt(i + 1, i, 0);
        }
    }
    
    private void simpanPeminjaman() {
        // Validasi
        if (anggotaCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih anggota peminjam!");
            return;
        }
        
        if (listDetail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Minimal pinjam 1 buku!");
            return;
        }
        
        // Buat object peminjaman
        PeminjamanModel peminjaman = new PeminjamanModel();
        peminjaman.setNoPeminjaman(noPinjamField.getText());
        peminjaman.setIdAnggota(((AnggotaModel) anggotaCombo.getSelectedItem()).getIdAnggota());
        peminjaman.setTanggalPinjam(new Date());
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 7);
        peminjaman.setTanggalJatuhTempo(cal.getTime());
        
        peminjaman.setStatusPeminjaman("dipinjam");
        peminjaman.setCreatedBy(currentUser.getIdAnggota());
        peminjaman.setDetailPeminjaman(listDetail);
        
        // Simpan
        if (peminjamanController.insert(peminjaman)) {
            JOptionPane.showMessageDialog(this, 
                "Peminjaman berhasil disimpan!\nNo. Peminjaman: " + peminjaman.getNoPeminjaman());
            dispose();
        }
    }
}