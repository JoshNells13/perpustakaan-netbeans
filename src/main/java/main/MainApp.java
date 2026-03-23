/*
 * File: MainApp.java
 * Package: main
 * Deskripsi: Class utama untuk menjalankan aplikasi
 */

package main;

import controller.AuthController;
import config.Koneksi;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainApp {
    
    public static void main(String[] args) {
        // Set look and feel sesuai sistem
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Jalankan aplikasi di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Test koneksi database
            Koneksi.testConnection();
            
            // Tampilkan form login
            AuthController authController = new AuthController();
            authController.showLoginFrame();
        });
    }
}