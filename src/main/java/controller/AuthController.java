/*
 * File: AuthController.java
 * Package: controller
 * Deskripsi: Controller untuk autentikasi (login, register, logout)
 */

package controller;

import dao.AnggotaDao;
import daoimpl.AnggotaDAOImpl;
import model.AnggotaModel;
import view.LoginFrame;
import view.MainFrameAdmin;
import view.MainFrameUser;
import view.RegistrasiFrame;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class AuthController {
    private AnggotaDao anggotaDAO;
    private LoginFrame loginFrame;
    private RegistrasiFrame registrasiFrame;
    private AnggotaModel currentUser;
    
    public AuthController() {
        this.anggotaDAO = new AnggotaDAOImpl();
    }
    
    public void showLoginFrame() {
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }
    
    public void showRegistrasiFrame() {
        registrasiFrame = new RegistrasiFrame(this);
        registrasiFrame.setVisible(true);
    }
    
    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Username dan Password tidak boleh kosong!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        AnggotaModel user = anggotaDAO.login(username, password);
        
        if (user != null) {
            this.currentUser = user;
            
            if (loginFrame != null) {
                loginFrame.dispose();
            }
            
            // Buka main frame sesuai role
            if ("admin".equals(user.getRole())) {
                MainFrameAdmin adminFrame = new MainFrameAdmin(this, user);
                adminFrame.setVisible(true);
            } else {
                MainFrameUser userFrame = new MainFrameUser(this, user);
                userFrame.setVisible(true);
            }
            
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "Username atau Password salah!", 
                "Login Gagal", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean register(AnggotaModel anggota) {
        // Validasi data
        if (anggota.getNis().isEmpty() || anggota.getNamaLengkap().isEmpty() || 
            anggota.getUsername().isEmpty() || anggota.getPassword().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Data yang ditandai * wajib diisi!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Cek username sudah digunakan
        AnggotaModel existingUser = anggotaDAO.getByUsername(anggota.getUsername());
        if (existingUser != null) {
            JOptionPane.showMessageDialog(null, 
                "Username sudah digunakan, silakan gunakan username lain!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Set default values
        anggota.setRole("user");
        anggota.setStatusAktif(true);
        
        boolean success = anggotaDAO.insert(anggota);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "Registrasi berhasil! Silakan login.", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            if (registrasiFrame != null) {
                registrasiFrame.dispose();
            }
            
            showLoginFrame();
        } else {
            JOptionPane.showMessageDialog(null, 
                "Registrasi gagal! Silakan coba lagi.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
    
    public void logout(JFrame currentFrame) {
        int confirm = JOptionPane.showConfirmDialog(currentFrame, 
            "Apakah Anda yakin ingin logout?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            currentFrame.dispose();
            this.currentUser = null;
            showLoginFrame();
        }
    }
    
    public AnggotaModel getCurrentUser() {
        return currentUser;
    }
}