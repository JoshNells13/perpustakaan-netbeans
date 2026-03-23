/*
 * File: PeminjamanController.java
 * Package: controller
 * Deskripsi: Controller untuk manajemen peminjaman
 */

package controller;

import dao.PeminjamanDao;
import dao.DetailPeminjamanDao;
import dao.BukuDao;
import daoimpl.PeminjamanDAOImpl;
import daoimpl.DetailPeminjamanDAOImpl;
import daoimpl.BukuDAOImpl;
import model.PeminjamanModel;
import model.DetailPeminjamanModel;
import model.BukuModel;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class PeminjamanController {
    private PeminjamanDao peminjamanDAO;
    private DetailPeminjamanDao detailDAO;
    private BukuDao bukuDAO;
    
    public PeminjamanController() {
        this.peminjamanDAO = new PeminjamanDAOImpl();
        this.detailDAO = new DetailPeminjamanDAOImpl();
        this.bukuDAO = new BukuDAOImpl();
    }
    
    /**
     * Generate nomor peminjaman otomatis
     * Format: PJ + YYYYMM + 0001
     */
    public String generateNoPeminjaman() {
        return peminjamanDAO.generateNoPeminjaman();
    }
    
    /**
     * Menyimpan data peminjaman beserta detailnya
     */
    public boolean insert(PeminjamanModel peminjaman) {
        try {
            // Insert data peminjaman
            boolean success = peminjamanDAO.insert(peminjaman);
            
            if (success) {
                // Insert detail peminjaman untuk setiap buku
                for (DetailPeminjamanModel detail : peminjaman.getDetailPeminjaman()) {
                    detail.setIdPeminjaman(peminjaman.getIdPeminjaman());
                    detailDAO.insert(detail);
                    
                    // Kurangi stok buku
                    bukuDAO.updateStok(detail.getIdBuku(), -1);
                }
                
                JOptionPane.showMessageDialog(null, 
                    "Peminjaman berhasil disimpan!\nNo. Peminjaman: " + peminjaman.getNoPeminjaman(),
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                return true;
            }
            return false;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat menyimpan peminjaman: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Mengambil semua data peminjaman
     */
    public List<PeminjamanModel> getAllPeminjaman() {
        return peminjamanDAO.getAll();
    }
    
    /**
     * Mengambil data peminjaman berdasarkan anggota
     */
    public List<PeminjamanModel> getPeminjamanByAnggota(int idAnggota) {
        return peminjamanDAO.getByAnggota(idAnggota);
    }
    
    /**
     * Mencari peminjaman berdasarkan keyword
     */
    public List<PeminjamanModel> searchPeminjaman(String keyword) {
        return peminjamanDAO.search(keyword);
    }
    
    /**
     * Mencari peminjaman yang masih aktif (status = dipinjam)
     */
    public List<PeminjamanModel> searchPeminjamanAktif(String keyword) {
        List<PeminjamanModel> all = peminjamanDAO.search(keyword);
        List<PeminjamanModel> aktif = new ArrayList<>();
        
        for (PeminjamanModel p : all) {
            if ("dipinjam".equals(p.getStatusPeminjaman())) {
                // Load data anggota
                if (p.getAnggota() == null) {
                    // Anggota akan diisi oleh DAO jika sudah diimplementasikan
                }
                aktif.add(p);
            }
        }
        
        return aktif;
    }
    
    /**
     * Mengambil detail peminjaman berdasarkan ID peminjaman
     */
    public List<DetailPeminjamanModel> getDetailPeminjaman(int idPeminjaman) {
        List<DetailPeminjamanModel> list = detailDAO.getByPeminjaman(idPeminjaman);
        
        // Load data buku untuk setiap detail
        for (DetailPeminjamanModel detail : list) {
            BukuModel buku = bukuDAO.getById(detail.getIdBuku());
            detail.setBuku(buku);
        }
        
        return list;
    }
    
    /**
     * Mengambil data peminjaman berdasarkan ID
     */
    public PeminjamanModel getPeminjamanById(int id) {
        PeminjamanModel p = peminjamanDAO.getById(id);
        
        // Load detail peminjaman
        if (p != null) {
            List<DetailPeminjamanModel> detail = getDetailPeminjaman(id);
            p.setDetailPeminjaman(detail);
        }
        
        return p;
    }
    
    /**
     * Update status peminjaman
     */
    public boolean updateStatus(int idPeminjaman, String status) {
        return peminjamanDAO.updateStatus(idPeminjaman, status);
    }
    
    /**
     * Menghitung jumlah buku yang dipinjam dalam suatu transaksi
     */
    public int getJumlahBukuDipinjam(int idPeminjaman) {
        return detailDAO.getJumlahBukuDipinjam(idPeminjaman);
    }
    
    /**
     * Menghitung jumlah buku yang sudah dikembalikan
     */
    public int getJumlahBukuDikembalikan(int idPeminjaman) {
        return detailDAO.getJumlahBukuDikembalikan(idPeminjaman);
    }
    
    /**
     * Cek apakah semua buku sudah dikembalikan
     */
    public boolean isSemuaBukuKembali(int idPeminjaman) {
        int dipinjam = getJumlahBukuDipinjam(idPeminjaman);
        return dipinjam == 0;
    }
    
    /**
     * Mendapatkan total denda dari suatu peminjaman
     */
    public double getTotalDenda(int idPeminjaman) {
        List<DetailPeminjamanModel> list = detailDAO.getByPeminjaman(idPeminjaman);
        double total = 0;
        
        for (DetailPeminjamanModel d : list) {
            total += d.getDenda();
        }
        
        return total;
    }
}