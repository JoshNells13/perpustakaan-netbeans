/*
 * File: BukuController.java
 * Package: controller
 * Deskripsi: Controller untuk manajemen buku
 */

package controller;

import dao.BukuDao;
import daoimpl.BukuDAOImpl;
import model.BukuModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class BukuController {
    private BukuDao bukuDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public BukuController() {
        this.bukuDAO = new BukuDAOImpl();
    }
    
    public void loadTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<BukuModel> list = bukuDAO.getAll();
        
        for (BukuModel b : list) {
            Object[] row = {
                b.getIdBuku(),
                b.getKodeBuku(),
                b.getJudulBuku(),
                b.getPengarang(),
                b.getPenerbit(),
                b.getTahunTerbit(),
                b.getKategori(),
                b.getJumlahTersedia(),
                b.getJumlahTotal(),
                b.getLetakRak(),
                b.getStatusBuku()
            };
            model.addRow(row);
        }
    }
    
    public void searchTable(JTable table, String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<BukuModel> list = bukuDAO.search(keyword);
        
        for (BukuModel b : list) {
            Object[] row = {
                b.getIdBuku(),
                b.getKodeBuku(),
                b.getJudulBuku(),
                b.getPengarang(),
                b.getPenerbit(),
                b.getTahunTerbit(),
                b.getKategori(),
                b.getJumlahTersedia(),
                b.getJumlahTotal(),
                b.getLetakRak(),
                b.getStatusBuku()
            };
            model.addRow(row);
        }
    }
    
    /**
     * Mendapatkan semua data buku
     */
    public List<BukuModel> getAllBuku() {
        return bukuDAO.getAll();
    }
    
    /**
     * Mendapatkan buku yang tersedia untuk dipinjam
     * (jumlah_tersedia > 0 dan status_buku = 'tersedia')
     */
    public List<BukuModel> getBukuTersedia() {
        return bukuDAO.getTersedia();
    }
    
    /**
     * Mendapatkan buku berdasarkan kategori
     */
    public List<BukuModel> getBukuByKategori(String kategori) {
        return bukuDAO.getByKategori(kategori);
    }
    
    public boolean insert(BukuModel buku) {
        // Validasi
        if (buku.getKodeBuku().isEmpty() || buku.getJudulBuku().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kode Buku dan Judul Buku wajib diisi!");
            return false;
        }
        
        // Cek kode buku
        BukuModel existing = bukuDAO.getByKode(buku.getKodeBuku());
        if (existing != null) {
            JOptionPane.showMessageDialog(null, "Kode Buku sudah digunakan!");
            return false;
        }
        
        buku.setJumlahTersedia(buku.getJumlahTotal()); // Set tersedia = total
        buku.setStatusBuku(buku.getJumlahTotal() > 0 ? "tersedia" : "kosong");
        
        boolean success = bukuDAO.insert(buku);
        if (success) {
            JOptionPane.showMessageDialog(null, "Data buku berhasil ditambahkan!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data buku!");
        }
        
        return success;
    }
    
    public boolean update(BukuModel buku) {
        // Validasi
        if (buku.getKodeBuku().isEmpty() || buku.getJudulBuku().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kode Buku dan Judul Buku wajib diisi!");
            return false;
        }
        
        boolean success = bukuDAO.update(buku);
        if (success) {
            JOptionPane.showMessageDialog(null, "Data buku berhasil diupdate!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengupdate data buku!");
        }
        
        return success;
    }
    
    public boolean delete(int idBuku) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Yakin akan menghapus data buku ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bukuDAO.delete(idBuku);
            if (success) {
                JOptionPane.showMessageDialog(null, "Data buku berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data buku! Mungkin buku sedang dipinjam.");
            }
            return success;
        }
        return false;
    }
    
    public BukuModel getById(int idBuku) {
        return bukuDAO.getById(idBuku);
    }
    
    public BukuModel getByKode(String kodeBuku) {
        return bukuDAO.getByKode(kodeBuku);
    }
    
    public void loadTersediaToComboBox(JComboBox<BukuModel> comboBox) {
        comboBox.removeAllItems();
        List<BukuModel> list = bukuDAO.getTersedia();
        for (BukuModel b : list) {
            comboBox.addItem(b);
        }
    }
}