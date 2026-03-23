/*
 * File: Koneksi.java
 * Package: config
 * Deskripsi: Class untuk mengelola koneksi database MySQL
 */

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/perpustakaan_java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load driver MySQL
                Class.forName(DRIVER);
                
                // Buat koneksi
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                
                System.out.println("Koneksi database berhasil!");
                
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                    "Driver MySQL tidak ditemukan!\n" + e.getMessage(), 
                    "Error Database", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Koneksi database gagal!\n" + e.getMessage(), 
                    "Error Database", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Koneksi database ditutup!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                JOptionPane.showMessageDialog(null, 
                    "Koneksi database berhasil terhubung!", 
                    "Info", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}