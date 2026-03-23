/*
 * File: KelolaBukuFrame.java
 * Package: view
 * Deskripsi: Form untuk CRUD data buku (Admin only)
 * Komponen: JFrame, JPanel, JLabel, JTextField, JTextArea, JButton,
 *           JTable, JScrollPane, JComboBox, JOptionPane
 * Layout: BorderLayout + GroupLayout
 */

package view;

import controller.BukuController;
import model.AnggotaModel;
import model.BukuModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KelolaBukuFrame extends JFrame {
    private BukuController bukuController;
    private AnggotaModel currentUser;
    private JFrame parent;
    
    // Komponen
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    
    // Form fields
    private JLabel idLabel;
    private JTextField idField;
    private JLabel kodeLabel;
    private JTextField kodeField;
    private JLabel judulLabel;
    private JTextField judulField;
    private JLabel pengarangLabel;
    private JTextField pengarangField;
    private JLabel penerbitLabel;
    private JTextField penerbitField;
    private JLabel tahunLabel;
    private JTextField tahunField;
    private JLabel isbnLabel;
    private JTextField isbnField;
    private JLabel kategoriLabel;
    private JTextField kategoriField;
    private JLabel jumlahLabel;
    private JSpinner jumlahSpinner;
    private JLabel letakLabel;
    private JTextField letakField;
    private JLabel deskripsiLabel;
    private JTextArea deskripsiArea;
    private JScrollPane deskripsiScroll;
    
    // Buttons
    private JButton simpanButton;
    private JButton updateButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton cariButton;
    
    // Search
    private JTextField searchField;
    private JButton searchButton;
    
    // Table
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;
    
    public KelolaBukuFrame(JFrame parent, AnggotaModel user) {
        this.parent = parent;
        this.currentUser = user;
        this.bukuController = new BukuController();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Kelola Data Buku - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Form
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Form Data Buku"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Initialize components
        idLabel = new JLabel("ID Buku:");
        idField = new JTextField(10);
        idField.setEditable(false);
        
        kodeLabel = new JLabel("Kode Buku *:");
        kodeField = new JTextField(15);
        
        judulLabel = new JLabel("Judul Buku *:");
        judulField = new JTextField(20);
        
        pengarangLabel = new JLabel("Pengarang:");
        pengarangField = new JTextField(20);
        
        penerbitLabel = new JLabel("Penerbit:");
        penerbitField = new JTextField(20);
        
        tahunLabel = new JLabel("Tahun Terbit:");
        tahunField = new JTextField(4);
        
        isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(15);
        
        kategoriLabel = new JLabel("Kategori:");
        kategoriField = new JTextField(15);
        
        jumlahLabel = new JLabel("Jumlah Total:");
        jumlahSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        
        letakLabel = new JLabel("Letak Rak:");
        letakField = new JTextField(10);
        
        deskripsiLabel = new JLabel("Deskripsi:");
        deskripsiArea = new JTextArea(3, 20);
        deskripsiArea.setLineWrap(true);
        deskripsiScroll = new JScrollPane(deskripsiArea);
        
        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0; topPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; topPanel.add(idField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; topPanel.add(kodeLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 0; topPanel.add(kodeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; topPanel.add(judulLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3; topPanel.add(judulField, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 2; topPanel.add(pengarangLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; topPanel.add(pengarangField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2; topPanel.add(penerbitLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 2; topPanel.add(penerbitField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; topPanel.add(tahunLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; topPanel.add(tahunField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 3; topPanel.add(isbnLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 3; topPanel.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; topPanel.add(kategoriLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; topPanel.add(kategoriField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 4; topPanel.add(jumlahLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 4; topPanel.add(jumlahSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; topPanel.add(letakLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; topPanel.add(letakField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; topPanel.add(deskripsiLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 3; topPanel.add(deskripsiScroll, gbc);
        gbc.gridwidth = 1;
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        simpanButton = new JButton("Simpan");
        updateButton = new JButton("Update");
        hapusButton = new JButton("Hapus");
        batalButton = new JButton("Batal");
        
        simpanButton.setBackground(new Color(0, 153, 76));
        simpanButton.setForeground(Color.BLACK);
        updateButton.setBackground(new Color(255, 153, 0));
        updateButton.setForeground(Color.BLACK);
        hapusButton.setBackground(new Color(204, 0, 0));
        hapusButton.setForeground(Color.BLACK);
        batalButton.setBackground(new Color(102, 102, 102));
        batalButton.setForeground(Color.BLACK);
        
        buttonPanel.add(simpanButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(hapusButton);
        buttonPanel.add(batalButton);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 4; topPanel.add(buttonPanel, gbc);
        
        // Center Panel - Search and Table
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Daftar Buku"));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(30);
        searchButton = new JButton("Cari");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.BLACK);
        searchPanel.add(new JLabel("Pencarian:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Table
        String[] columns = {"ID", "Kode", "Judul", "Pengarang", "Penerbit", 
                            "Tahun", "Kategori", "Tersedia", "Total", "Letak", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        tableScroll = new JScrollPane(table);
        
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        
        // Add to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Add listeners
        simpanButton.addActionListener(e -> simpan());
        updateButton.addActionListener(e -> update());
        hapusButton.addActionListener(e -> hapus());
        batalButton.addActionListener(e -> resetForm());
        searchButton.addActionListener(e -> cari());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tableSelected();
            }
        });
    }
    
    private void loadData() {
        bukuController.loadTable(table);
    }
    
    private void simpan() {
        BukuModel buku = new BukuModel();
        buku.setKodeBuku(kodeField.getText());
        buku.setJudulBuku(judulField.getText());
        buku.setPengarang(pengarangField.getText());
        buku.setPenerbit(penerbitField.getText());
        buku.setTahunTerbit(tahunField.getText());
        buku.setIsbn(isbnField.getText());
        buku.setKategori(kategoriField.getText());
        buku.setJumlahTotal((Integer) jumlahSpinner.getValue());
        buku.setLetakRak(letakField.getText());
        buku.setDeskripsi(deskripsiArea.getText());
        buku.setTanggalMasuk(new Date());
        buku.setStatusBuku("tersedia");
        
        if (bukuController.insert(buku)) {
            loadData();
            resetForm();
        }
    }
    
    private void update() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!");
            return;
        }
        
        BukuModel buku = new BukuModel();
        buku.setIdBuku(Integer.parseInt(idField.getText()));
        buku.setKodeBuku(kodeField.getText());
        buku.setJudulBuku(judulField.getText());
        buku.setPengarang(pengarangField.getText());
        buku.setPenerbit(penerbitField.getText());
        buku.setTahunTerbit(tahunField.getText());
        buku.setIsbn(isbnField.getText());
        buku.setKategori(kategoriField.getText());
        buku.setJumlahTotal((Integer) jumlahSpinner.getValue());
        buku.setLetakRak(letakField.getText());
        buku.setDeskripsi(deskripsiArea.getText());
        buku.setStatusBuku("tersedia");
        
        if (bukuController.update(buku)) {
            loadData();
            resetForm();
        }
    }
    
    private void hapus() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int id = Integer.parseInt(idField.getText());
        if (bukuController.delete(id)) {
            loadData();
            resetForm();
        }
    }
    
    private void resetForm() {
        idField.setText("");
        kodeField.setText("");
        judulField.setText("");
        pengarangField.setText("");
        penerbitField.setText("");
        tahunField.setText("");
        isbnField.setText("");
        kategoriField.setText("");
        jumlahSpinner.setValue(0);
        letakField.setText("");
        deskripsiArea.setText("");
    }
    
    private void cari() {
        String keyword = searchField.getText();
        if (keyword.isEmpty()) {
            loadData();
        } else {
            bukuController.searchTable(table, keyword);
        }
    }
    
    private void tableSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            idField.setText(table.getValueAt(row, 0).toString());
            kodeField.setText(table.getValueAt(row, 1).toString());
            judulField.setText(table.getValueAt(row, 2).toString());
            pengarangField.setText(table.getValueAt(row, 3) != null ? table.getValueAt(row, 3).toString() : "");
            penerbitField.setText(table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "");
            tahunField.setText(table.getValueAt(row, 5) != null ? table.getValueAt(row, 5).toString() : "");
            kategoriField.setText(table.getValueAt(row, 6) != null ? table.getValueAt(row, 6).toString() : "");
            jumlahSpinner.setValue(Integer.parseInt(table.getValueAt(row, 8).toString()));
            letakField.setText(table.getValueAt(row, 9) != null ? table.getValueAt(row, 9).toString() : "");
        }
    }
}