/*
 * File: PengembalianController.java
 * Package: controller
 * Deskripsi: Controller untuk manajemen pengembalian
 */

package controller;

import dao.PengembalianDao;
import dao.PeminjamanDao;
import dao.DetailPeminjamanDao;
import daoimpl.PengembalianDAOImpl;
import daoimpl.PeminjamanDAOImpl;
import daoimpl.DetailPeminjamanDAOImpl;
import model.PengembalianModel;
import model.PeminjamanModel;
import model.DetailPeminjamanModel;
import javax.swing.*;
import java.util.Date;
import java.util.List;

public class PengembalianController {
    private PengembalianDao pengembalianDAO;
    private PeminjamanDao peminjamanDAO;
    private DetailPeminjamanDao detailDAO;
    
    public PengembalianController() {
        this.pengembalianDAO = new PengembalianDAOImpl();
        this.peminjamanDAO = new PeminjamanDAOImpl();
        this.detailDAO = new DetailPeminjamanDAOImpl();
    }
    
    /**
     * Generate nomor pengembalian otomatis
     * Format: PG + YYYYMM + 0001
     */
    public String generateNoPengembalian() {
        return pengembalianDAO.generateNoPengembalian();
    }
    
    /**
     * Menghitung total denda dari peminjaman
     * Denda Rp 1000 per hari keterlambatan
     */
    public double hitungTotalDenda(Date jatuhTempo, Date tanggalKembali, 
                                   List<DetailPeminjamanModel> listDetail) {
        double total = 0;
        
        // Hitung denda per buku
        for (DetailPeminjamanModel detail : listDetail) {
            if (!detail.isStatusKembali()) {
                // Hitung denda berdasarkan selisih hari
                double denda = pengembalianDAO.hitungDenda(jatuhTempo, tanggalKembali);
                detail.setDenda(denda);
                total += denda;
            }
        }
        
        return total;
    }
    
    /**
     * Proses pengembalian buku
     */
    public boolean prosesPengembalian(PeminjamanModel peminjaman, 
                                      int petugasId, 
                                      double totalDenda) {
        try {
            // Validasi
            if (peminjaman == null) {
                JOptionPane.showMessageDialog(null, 
                    "Data peminjaman tidak ditemukan!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Ambil detail peminjaman
            List<DetailPeminjamanModel> listDetail = detailDAO.getByPeminjaman(peminjaman.getIdPeminjaman());
            
            if (listDetail.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Tidak ada detail peminjaman!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            Date tanggalKembali = new Date();
            
            // Update status setiap detail
            for (DetailPeminjamanModel detail : listDetail) {
                if (!detail.isStatusKembali()) {
                    // Update status menjadi sudah kembali
                    detailDAO.updateStatusKembali(detail.getIdDetail(), true);
                    detailDAO.updateTanggalKembali(detail.getIdDetail(), tanggalKembali);
                    
                    // Hitung denda untuk buku ini
                    double dendaBuku = pengembalianDAO.hitungDenda(
                        peminjaman.getTanggalJatuhTempo(), 
                        tanggalKembali
                    );
                    
                    if (dendaBuku > 0) {
                        detail.setDenda(dendaBuku);
                        detailDAO.update(detail);
                    }
                }
            }
            
            // Buat record pengembalian
            PengembalianModel pengembalian = new PengembalianModel();
            pengembalian.setNoPengembalian(generateNoPengembalian());
            pengembalian.setIdPeminjaman(peminjaman.getIdPeminjaman());
            pengembalian.setIdAnggota(peminjaman.getIdAnggota());
            pengembalian.setTanggalPengembalian(tanggalKembali);
            pengembalian.setTotalDenda(totalDenda);
            pengembalian.setDiterimaOleh(petugasId);
            
            boolean success = pengembalianDAO.insert(pengembalian);
            
            if (success) {
                // Update status peminjaman menjadi "dikembalikan"
                peminjamanDAO.updateStatus(peminjaman.getIdPeminjaman(), "dikembalikan");
                
                JOptionPane.showMessageDialog(null, 
                    "Pengembalian berhasil diproses!\n" +
                    "No. Pengembalian: " + pengembalian.getNoPengembalian() + "\n" +
                    "Total Denda: Rp " + String.format("%,.0f", totalDenda),
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            return success;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat memproses pengembalian: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Mengambil semua data pengembalian
     */
    public List<PengembalianModel> getAllPengembalian() {
        return pengembalianDAO.getAll();
    }
    
    /**
     * Mengambil data pengembalian berdasarkan anggota
     */
    public List<PengembalianModel> getPengembalianByAnggota(int idAnggota) {
        return pengembalianDAO.getByAnggota(idAnggota);
    }
    
    /**
     * Mengambil data pengembalian berdasarkan periode tanggal
     */
    public List<PengembalianModel> getPengembalianByTanggal(Date mulai, Date akhir) {
        return pengembalianDAO.getByTanggal(mulai, akhir);
    }
    
    /**
     * Mengambil data pengembalian berdasarkan ID peminjaman
     */
    public PengembalianModel getPengembalianByPeminjaman(int idPeminjaman) {
        return pengembalianDAO.getByPeminjaman(idPeminjaman);
    }
    
    /**
     * Mengecek apakah peminjaman sudah dikembalikan
     */
    public boolean isSudahDikembalikan(int idPeminjaman) {
        PengembalianModel p = pengembalianDAO.getByPeminjaman(idPeminjaman);
        return p != null;
    }
    
    /**
     * Menghitung denda untuk satu buku
     */
    public double hitungDendaBuku(Date jatuhTempo, Date tanggalKembali) {
        return pengembalianDAO.hitungDenda(jatuhTempo, tanggalKembali);
    }
}