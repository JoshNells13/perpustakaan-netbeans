/*
 * File: AnggotaDAOImpl.java
 * Package: dao.impl
 * Deskripsi: Implementasi DAO untuk tabel anggota
 */

package daoimpl;

import config.Koneksi;
import dao.AnggotaDao;
import model.AnggotaModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AnggotaDAOImpl implements AnggotaDao {
    private Connection conn;
    
    public AnggotaDAOImpl() {
        this.conn = Koneksi.getConnection();
    }
    
    @Override
    public boolean insert(AnggotaModel anggota) {
        String sql = "INSERT INTO anggota (nis, nama_lengkap, kelas, jenis_kelamin, alamat, no_telepon, username, password, role, status_aktif) VALUES (?, ?, ?, ?, ?, ?, ?, MD5(?), ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, anggota.getNis());
            ps.setString(2, anggota.getNamaLengkap());
            ps.setString(3, anggota.getKelas());
            ps.setString(4, anggota.getJenisKelamin());
            ps.setString(5, anggota.getAlamat());
            ps.setString(6, anggota.getNoTelepon());
            ps.setString(7, anggota.getUsername());
            ps.setString(8, anggota.getPassword());
            ps.setString(9, anggota.getRole() != null ? anggota.getRole() : "user");
            ps.setBoolean(10, anggota.isStatusAktif());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error insert anggota: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(AnggotaModel anggota) {
        String sql = "UPDATE anggota SET nis=?, nama_lengkap=?, kelas=?, jenis_kelamin=?, alamat=?, no_telepon=?, username=?, role=?, status_aktif=? WHERE id_anggota=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, anggota.getNis());
            ps.setString(2, anggota.getNamaLengkap());
            ps.setString(3, anggota.getKelas());
            ps.setString(4, anggota.getJenisKelamin());
            ps.setString(5, anggota.getAlamat());
            ps.setString(6, anggota.getNoTelepon());
            ps.setString(7, anggota.getUsername());
            ps.setString(8, anggota.getRole());
            ps.setBoolean(9, anggota.isStatusAktif());
            ps.setInt(10, anggota.getIdAnggota());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error update anggota: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int idAnggota) {
        String sql = "DELETE FROM anggota WHERE id_anggota=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error delete anggota: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public AnggotaModel getById(int idAnggota) {
        String sql = "SELECT * FROM anggota WHERE id_anggota=?";
        AnggotaModel anggota = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    anggota = mapResultSetToAnggota(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return anggota;
    }
    
    @Override
    public AnggotaModel getByUsername(String username) {
        String sql = "SELECT * FROM anggota WHERE username=?";
        AnggotaModel anggota = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    anggota = mapResultSetToAnggota(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return anggota;
    }
    
    @Override
    public AnggotaModel login(String username, String password) {
        String sql = "SELECT * FROM anggota WHERE username=? AND password=MD5(?) AND status_aktif=1";
        AnggotaModel anggota = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    anggota = mapResultSetToAnggota(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return anggota;
    }
    
    @Override
    public List<AnggotaModel> getAll() {
        String sql = "SELECT * FROM anggota ORDER BY nama_lengkap";
        List<AnggotaModel> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToAnggota(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<AnggotaModel> search(String keyword) {
        String sql = "SELECT * FROM anggota WHERE nis LIKE ? OR nama_lengkap LIKE ? OR kelas LIKE ? OR username LIKE ? ORDER BY nama_lengkap";
        List<AnggotaModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAnggota(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM anggota";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public boolean activate(int idAnggota) {
        String sql = "UPDATE anggota SET status_aktif=1 WHERE id_anggota=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deactivate(int idAnggota) {
        String sql = "UPDATE anggota SET status_aktif=0 WHERE id_anggota=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private AnggotaModel mapResultSetToAnggota(ResultSet rs) throws SQLException {
        AnggotaModel anggota = new AnggotaModel();
        anggota.setIdAnggota(rs.getInt("id_anggota"));
        anggota.setNis(rs.getString("nis"));
        anggota.setNamaLengkap(rs.getString("nama_lengkap"));
        anggota.setKelas(rs.getString("kelas"));
        anggota.setJenisKelamin(rs.getString("jenis_kelamin"));
        anggota.setAlamat(rs.getString("alamat"));
        anggota.setNoTelepon(rs.getString("no_telepon"));
        anggota.setUsername(rs.getString("username"));
        anggota.setPassword(rs.getString("password"));
        anggota.setRole(rs.getString("role"));
        anggota.setTanggalDaftar(rs.getTimestamp("tanggal_daftar"));
        anggota.setStatusAktif(rs.getBoolean("status_aktif"));
        return anggota;
    }
}