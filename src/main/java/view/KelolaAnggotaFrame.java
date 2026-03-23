/*
 * File: KelolaAnggotaFrame.java
 * Package: view
 * Deskripsi: Form untuk CRUD data anggota (Admin only)
 * Komponen: JFrame, JPanel, JLabel, JTextField, JTextArea, JButton,
 *           JTable, JScrollPane, JRadioButton, JCheckBox
 * Layout: BorderLayout + GroupLayout
 */

package view;

import controller.AnggotaController;
import model.AnggotaModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KelolaAnggotaFrame extends JFrame {
    private AnggotaController anggotaController;
    private AnggotaModel currentUser;
    private JFrame parent;
    
    // Komponen
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    
    // Form fields
    private JLabel idLabel;
    private JTextField idField;
    private JLabel nisLabel;
    private JTextField nisField;
    private JLabel namaLabel;
    private JTextField namaField;
    private JLabel kelasLabel;
    private JTextField kelasField;
    private JLabel jkLabel;
    private JRadioButton jkLButton;
    private JRadioButton jkPButton;
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
    private JLabel roleLabel;
    private JComboBox<String> roleCombo;
    private JCheckBox aktifCheck;
    
    // Buttons
    private JButton simpanButton;
    private JButton updateButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton aktifButton;
    private JButton nonaktifButton;
    
    // Search
    private JTextField searchField;
    private JButton searchButton;
    
    // Table
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;
    
    public KelolaAnggotaFrame(JFrame parent, AnggotaModel user) {
        this.parent = parent;
        this.currentUser = user;
        this.anggotaController = new AnggotaController();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Frame setup
        setTitle("Kelola Data Anggota - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Form
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Form Data Anggota"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Initialize components
        idLabel = new JLabel("ID:");
        idField = new JTextField(5);
        idField.setEditable(false);
        
        nisLabel = new JLabel("NIS *:");
        nisField = new JTextField(15);
        
        namaLabel = new JLabel("Nama Lengkap *:");
        namaField = new JTextField(20);
        
        kelasLabel = new JLabel("Kelas:");
        kelasField = new JTextField(10);
        
        jkLabel = new JLabel("Jenis Kelamin:");
        jkLButton = new JRadioButton("Laki-laki");
        jkPButton = new JRadioButton("Perempuan");
        jkGroup = new ButtonGroup();
        jkGroup.add(jkLButton);
        jkGroup.add(jkPButton);
        
        alamatLabel = new JLabel("Alamat:");
        alamatArea = new JTextArea(3, 20);
        alamatArea.setLineWrap(true);
        alamatScroll = new JScrollPane(alamatArea);
        
        telpLabel = new JLabel("No. Telepon:");
        telpField = new JTextField(15);
        
        usernameLabel = new JLabel("Username *:");
        usernameField = new JTextField(15);
        
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        
        roleLabel = new JLabel("Role:");
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        
        aktifCheck = new JCheckBox("Aktif");
        aktifCheck.setSelected(true);
        
        // Layout
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; topPanel.add(idField, gbc);
        gbc.gridx = 2; gbc.gridy = row; topPanel.add(nisLabel, gbc);
        gbc.gridx = 3; gbc.gridy = row; topPanel.add(nisField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(namaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 3; topPanel.add(namaField, gbc);
        gbc.gridwidth = 1;
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(kelasLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; topPanel.add(kelasField, gbc);
        gbc.gridx = 2; gbc.gridy = row; topPanel.add(jkLabel, gbc);
        gbc.gridx = 3; gbc.gridy = row; topPanel.add(jkLButton, gbc);
        gbc.gridx = 4; gbc.gridy = row; topPanel.add(jkPButton, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(alamatLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 3; topPanel.add(alamatScroll, gbc);
        gbc.gridwidth = 1;
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(telpLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; topPanel.add(telpField, gbc);
        gbc.gridx = 2; gbc.gridy = row; topPanel.add(usernameLabel, gbc);
        gbc.gridx = 3; gbc.gridy = row; topPanel.add(usernameField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = row; topPanel.add(passwordField, gbc);
        gbc.gridx = 2; gbc.gridy = row; topPanel.add(roleLabel, gbc);
        gbc.gridx = 3; gbc.gridy = row; topPanel.add(roleCombo, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; topPanel.add(aktifCheck, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        simpanButton = new JButton("Simpan");
        updateButton = new JButton("Update");
        hapusButton = new JButton("Hapus");
        batalButton = new JButton("Batal");
        aktifButton = new JButton("Aktifkan");
        nonaktifButton = new JButton("Nonaktifkan");
        
        simpanButton.setBackground(new Color(0, 153, 76));
        simpanButton.setForeground(Color.BLACK);
        updateButton.setBackground(new Color(255, 153, 0));
        updateButton.setForeground(Color.BLACK);
        hapusButton.setBackground(new Color(204, 0, 0));
        hapusButton.setForeground(Color.BLACK);
        batalButton.setBackground(new Color(102, 102, 102));
        batalButton.setForeground(Color.BLACK);
        aktifButton.setBackground(new Color(0, 102, 204));
        aktifButton.setForeground(Color.BLACK);
        nonaktifButton.setBackground(new Color(102, 102, 102));
        nonaktifButton.setForeground(Color.BLACK);
        
        buttonPanel.add(simpanButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(hapusButton);
        buttonPanel.add(aktifButton);
        buttonPanel.add(nonaktifButton);
        buttonPanel.add(batalButton);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4; topPanel.add(buttonPanel, gbc);
        
        // Center Panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Daftar Anggota"));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(30);
        searchButton = new JButton("Cari");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchPanel.add(new JLabel("Pencarian:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Table
        String[] columns = {"ID", "NIS", "Nama", "Kelas", "JK", "Telepon", "Username", "Role", "Status"};
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
        aktifButton.addActionListener(e -> aktifkan());
        nonaktifButton.addActionListener(e -> nonaktifkan());
        searchButton.addActionListener(e -> cari());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tableSelected();
            }
        });
    }
    
    private void loadData() {
        anggotaController.loadTable(table);
    }
    
    private void simpan() {
        AnggotaModel anggota = new AnggotaModel();
        anggota.setNis(nisField.getText());
        anggota.setNamaLengkap(namaField.getText());
        anggota.setKelas(kelasField.getText());
        anggota.setJenisKelamin(jkLButton.isSelected() ? "L" : jkPButton.isSelected() ? "P" : "");
        anggota.setAlamat(alamatArea.getText());
        anggota.setNoTelepon(telpField.getText());
        anggota.setUsername(usernameField.getText());
        anggota.setPassword(new String(passwordField.getPassword()));
        anggota.setRole(roleCombo.getSelectedItem().toString());
        anggota.setStatusAktif(aktifCheck.isSelected());
        
        if (anggotaController.insert(anggota)) {
            loadData();
            resetForm();
        }
    }
    
    private void update() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!");
            return;
        }
        
        AnggotaModel anggota = new AnggotaModel();
        anggota.setIdAnggota(Integer.parseInt(idField.getText()));
        anggota.setNis(nisField.getText());
        anggota.setNamaLengkap(namaField.getText());
        anggota.setKelas(kelasField.getText());
        anggota.setJenisKelamin(jkLButton.isSelected() ? "L" : jkPButton.isSelected() ? "P" : "");
        anggota.setAlamat(alamatArea.getText());
        anggota.setNoTelepon(telpField.getText());
        anggota.setUsername(usernameField.getText());
        
        String password = new String(passwordField.getPassword());
        if (!password.isEmpty()) {
            anggota.setPassword(password);
        }
        
        anggota.setRole(roleCombo.getSelectedItem().toString());
        anggota.setStatusAktif(aktifCheck.isSelected());
        
        if (anggotaController.update(anggota)) {
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
        if (anggotaController.delete(id)) {
            loadData();
            resetForm();
        }
    }
    
    private void aktifkan() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang akan diaktifkan!");
            return;
        }
        
        int id = Integer.parseInt(idField.getText());
        if (anggotaController.activate(id)) {
            loadData();
        }
    }
    
    private void nonaktifkan() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih anggota yang akan dinonaktifkan!");
            return;
        }
        
        int id = Integer.parseInt(idField.getText());
        if (anggotaController.deactivate(id)) {
            loadData();
        }
    }
    
    private void resetForm() {
        idField.setText("");
        nisField.setText("");
        namaField.setText("");
        kelasField.setText("");
        jkGroup.clearSelection();
        alamatArea.setText("");
        telpField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        roleCombo.setSelectedIndex(0);
        aktifCheck.setSelected(true);
    }
    
    private void cari() {
        String keyword = searchField.getText();
        if (keyword.isEmpty()) {
            loadData();
        } else {
            anggotaController.searchTable(table, keyword);
        }
    }
    
    private void tableSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            idField.setText(table.getValueAt(row, 0).toString());
            nisField.setText(table.getValueAt(row, 1).toString());
            namaField.setText(table.getValueAt(row, 2).toString());
            kelasField.setText(table.getValueAt(row, 3) != null ? table.getValueAt(row, 3).toString() : "");
            
            String jk = table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "";
            if ("L".equals(jk)) {
                jkLButton.setSelected(true);
            } else if ("P".equals(jk)) {
                jkPButton.setSelected(true);
            }
            
            telpField.setText(table.getValueAt(row, 5) != null ? table.getValueAt(row, 5).toString() : "");
            usernameField.setText(table.getValueAt(row, 6).toString());
            roleCombo.setSelectedItem(table.getValueAt(row, 7).toString());
            aktifCheck.setSelected("Aktif".equals(table.getValueAt(row, 8).toString()));
            
            // Get full data for alamat
            AnggotaModel anggota = anggotaController.getById(Integer.parseInt(idField.getText()));
            if (anggota != null) {
                alamatArea.setText(anggota.getAlamat());
            }
        }
    }
}