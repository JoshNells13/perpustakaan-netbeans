/*
 * File: AnggotaController.java
 * Package: controller
 * Deskripsi: Controller untuk manajemen anggota
 */

package controller;

import dao.AnggotaDao;
import daoimpl.AnggotaDAOImpl;
import model.AnggotaModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

public class AnggotaController {
    private AnggotaDao anggotaDAO;
    
    public AnggotaController() {
        this.anggotaDAO = new AnggotaDAOImpl();
    }
    
    public void loadTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<AnggotaModel> list = anggotaDAO.getAll();
        
        for (AnggotaModel a : list) {
            Object[] row = {
                a.getIdAnggota(),
                a.getNis(),
                a.getNamaLengkap(),
                a.getKelas(),
                a.getJenisKelamin(),
                a.getNoTelepon(),
                a.getUsername(),
                a.getRole(),
                a.isStatusAktif() ? "Aktif" : "Tidak Aktif"
            };
            model.addRow(row);
        }
    }
    
    public void searchTable(JTable table, String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<AnggotaModel> list = anggotaDAO.search(keyword);
        
        for (AnggotaModel a : list) {
            Object[] row = {
                a.getIdAnggota(),
                a.getNis(),
                a.getNamaLengkap(),
                a.getKelas(),
                a.getJenisKelamin(),
                a.getNoTelepon(),
                a.getUsername(),
                a.getRole(),
                a.isStatusAktif() ? "Aktif" : "Tidak Aktif"
            };
            model.addRow(row);
        }
    }
    
    /**
     * Mendapatkan semua data anggota
     */
    public List<AnggotaModel> getAllAnggota() {
        return anggotaDAO.getAll();
    }
    
    /**
     * Mendapatkan semua anggota yang aktif
     */
    public List<AnggotaModel> getAllAnggotaAktif() {
        List<AnggotaModel> all = anggotaDAO.getAll();
        List<AnggotaModel> aktif = new ArrayList<>();
        for (AnggotaModel a : all) {
            if (a.isStatusAktif()) {
                aktif.add(a);
            }
        }
        return aktif;
    }
    
    /**
     * Mendapatkan anggota berdasarkan role
     */
    public List<AnggotaModel> getAnggotaByRole(String role) {
        List<AnggotaModel> all = anggotaDAO.getAll();
        List<AnggotaModel> filtered = new ArrayList<>();
        for (AnggotaModel a : all) {
            if (role.equals(a.getRole())) {
                filtered.add(a);
            }
        }
        return filtered;
    }
    
    public boolean insert(AnggotaModel anggota) {
        // Validasi
        if (anggota.getNis().isEmpty() || anggota.getNamaLengkap().isEmpty() || 
            anggota.getUsername().isEmpty() || anggota.getPassword().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data wajib harus diisi!");
            return false;
        }
        
        // Cek username
        AnggotaModel existing = anggotaDAO.getByUsername(anggota.getUsername());
        if (existing != null) {
            JOptionPane.showMessageDialog(null, "Username sudah digunakan!");
            return false;
        }
        
        anggota.setRole("user");
        anggota.setStatusAktif(true);
        
        boolean success = anggotaDAO.insert(anggota);
        if (success) {
            JOptionPane.showMessageDialog(null, "Data anggota berhasil ditambahkan!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data anggota!");
        }
        
        return success;
    }
    
    public boolean update(AnggotaModel anggota) {
        // Validasi
        if (anggota.getNis().isEmpty() || anggota.getNamaLengkap().isEmpty() || 
            anggota.getUsername().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data wajib harus diisi!");
            return false;
        }
        
        boolean success = anggotaDAO.update(anggota);
        if (success) {
            JOptionPane.showMessageDialog(null, "Data anggota berhasil diupdate!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengupdate data anggota!");
        }
        
        return success;
    }
    
    public boolean delete(int idAnggota) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Yakin akan menghapus data anggota ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = anggotaDAO.delete(idAnggota);
            if (success) {
                JOptionPane.showMessageDialog(null, "Data anggota berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data anggota!");
            }
            return success;
        }
        return false;
    }
    
    public AnggotaModel getById(int idAnggota) {
        return anggotaDAO.getById(idAnggota);
    }
    
    public boolean activate(int idAnggota) {
        boolean success = anggotaDAO.activate(idAnggota);
        if (success) {
            JOptionPane.showMessageDialog(null, "Anggota berhasil diaktifkan!");
        }
        return success;
    }
    
    public boolean deactivate(int idAnggota) {
        boolean success = anggotaDAO.deactivate(idAnggota);
        if (success) {
            JOptionPane.showMessageDialog(null, "Anggota berhasil dinonaktifkan!");
        }
        return success;
    }
}