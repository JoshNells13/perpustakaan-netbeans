/*
 * File: BukuDAOImpl.java
 * Package: dao.impl
 * Deskripsi: Implementasi DAO untuk tabel buku
 */

package daoimpl;

import config.Koneksi;
import dao.BukuDao;
import model.BukuModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BukuDAOImpl implements BukuDao {
    private Connection conn;
    
    public BukuDAOImpl() {
        this.conn = Koneksi.getConnection();
    }
    
    @Override
    public boolean insert(BukuModel buku) {
        String sql = "INSERT INTO buku (kode_buku, judul_buku, pengarang, penerbit, tahun_terbit, isbn, kategori, jumlah_tersedia, jumlah_total, letak_rak, deskripsi, tanggal_masuk, status_buku) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getKodeBuku());
            ps.setString(2, buku.getJudulBuku());
            ps.setString(3, buku.getPengarang());
            ps.setString(4, buku.getPenerbit());
            ps.setString(5, buku.getTahunTerbit());
            ps.setString(6, buku.getIsbn());
            ps.setString(7, buku.getKategori());
            ps.setInt(8, buku.getJumlahTersedia());
            ps.setInt(9, buku.getJumlahTotal());
            ps.setString(10, buku.getLetakRak());
            ps.setString(11, buku.getDeskripsi());
            ps.setDate(12, new java.sql.Date(buku.getTanggalMasuk().getTime()));
            ps.setString(13, buku.getStatusBuku());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error insert buku: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(BukuModel buku) {
        String sql = "UPDATE buku SET kode_buku=?, judul_buku=?, pengarang=?, penerbit=?, tahun_terbit=?, isbn=?, kategori=?, jumlah_tersedia=?, jumlah_total=?, letak_rak=?, deskripsi=?, tanggal_masuk=?, status_buku=? WHERE id_buku=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getKodeBuku());
            ps.setString(2, buku.getJudulBuku());
            ps.setString(3, buku.getPengarang());
            ps.setString(4, buku.getPenerbit());
            ps.setString(5, buku.getTahunTerbit());
            ps.setString(6, buku.getIsbn());
            ps.setString(7, buku.getKategori());
            ps.setInt(8, buku.getJumlahTersedia());
            ps.setInt(9, buku.getJumlahTotal());
            ps.setString(10, buku.getLetakRak());
            ps.setString(11, buku.getDeskripsi());
            ps.setDate(12, new java.sql.Date(buku.getTanggalMasuk().getTime()));
            ps.setString(13, buku.getStatusBuku());
            ps.setInt(14, buku.getIdBuku());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error update buku: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int idBuku) {
        String sql = "DELETE FROM buku WHERE id_buku=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error delete buku: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public BukuModel getById(int idBuku) {
        String sql = "SELECT * FROM buku WHERE id_buku=?";
        BukuModel buku = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    buku = mapResultSetToBuku(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return buku;
    }
    
    @Override
    public BukuModel getByKode(String kodeBuku) {
        String sql = "SELECT * FROM buku WHERE kode_buku=?";
        BukuModel buku = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeBuku);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    buku = mapResultSetToBuku(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return buku;
    }
    
    @Override
    public List<BukuModel> getAll() {
        String sql = "SELECT * FROM buku ORDER BY judul_buku";
        List<BukuModel> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToBuku(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<BukuModel> search(String keyword) {
        String sql = "SELECT * FROM buku WHERE kode_buku LIKE ? OR judul_buku LIKE ? OR pengarang LIKE ? OR penerbit LIKE ? OR kategori LIKE ? ORDER BY judul_buku";
        List<BukuModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            ps.setString(5, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBuku(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<BukuModel> getByKategori(String kategori) {
        String sql = "SELECT * FROM buku WHERE kategori=? ORDER BY judul_buku";
        List<BukuModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kategori);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBuku(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<BukuModel> getTersedia() {
        String sql = "SELECT * FROM buku WHERE jumlah_tersedia > 0 AND status_buku='tersedia' ORDER BY judul_buku";
        List<BukuModel> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToBuku(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM buku";
        
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
    public boolean updateStok(int idBuku, int perubahan) {
        String sql = "UPDATE buku SET jumlah_tersedia = jumlah_tersedia + ? WHERE id_buku=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, perubahan);
            ps.setInt(2, idBuku);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private BukuModel mapResultSetToBuku(ResultSet rs) throws SQLException {
        BukuModel buku = new BukuModel();
        buku.setIdBuku(rs.getInt("id_buku"));
        buku.setKodeBuku(rs.getString("kode_buku"));
        buku.setJudulBuku(rs.getString("judul_buku"));
        buku.setPengarang(rs.getString("pengarang"));
        buku.setPenerbit(rs.getString("penerbit"));
        buku.setTahunTerbit(rs.getString("tahun_terbit"));
        buku.setIsbn(rs.getString("isbn"));
        buku.setKategori(rs.getString("kategori"));
        buku.setJumlahTersedia(rs.getInt("jumlah_tersedia"));
        buku.setJumlahTotal(rs.getInt("jumlah_total"));
        buku.setLetakRak(rs.getString("letak_rak"));
        buku.setDeskripsi(rs.getString("deskripsi"));
        buku.setTanggalMasuk(rs.getDate("tanggal_masuk"));
        buku.setStatusBuku(rs.getString("status_buku"));
        return buku;
    }
}