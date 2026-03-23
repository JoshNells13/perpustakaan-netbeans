/*
 * File: DetailPeminjamanDAOImpl.java
 * Package: dao.impl
 * Deskripsi: Implementasi DAO untuk tabel detail_peminjaman
 */

package daoimpl;

import config.Koneksi;
import dao.DetailPeminjamanDao;
import dao.BukuDao;
import model.DetailPeminjamanModel;
import model.BukuModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DetailPeminjamanDAOImpl implements DetailPeminjamanDao {
    private Connection conn;
    private BukuDao bukuDAO;
    
    public DetailPeminjamanDAOImpl() {
        this.conn = Koneksi.getConnection();
        this.bukuDAO = new BukuDAOImpl();
    }
    
    @Override
    public boolean insert(DetailPeminjamanModel detail) {
        String sql = "INSERT INTO detail_peminjaman (id_peminjaman, id_buku, status_kembali, denda) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detail.getIdPeminjaman());
            ps.setInt(2, detail.getIdBuku());
            ps.setBoolean(3, detail.isStatusKembali());
            ps.setDouble(4, detail.getDenda());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        detail.setIdDetail(rs.getInt(1));
                    }
                }
                
                // Update stok buku
                bukuDAO.updateStok(detail.getIdBuku(), -1);
                
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error insert detail peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(DetailPeminjamanModel detail) {
        String sql = "UPDATE detail_peminjaman SET id_buku=?, status_kembali=?, tanggal_kembali=?, denda=? WHERE id_detail=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detail.getIdBuku());
            ps.setBoolean(2, detail.isStatusKembali());
            
            if (detail.getTanggalKembali() != null) {
                ps.setDate(3, new java.sql.Date(detail.getTanggalKembali().getTime()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            
            ps.setDouble(4, detail.getDenda());
            ps.setInt(5, detail.getIdDetail());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error update detail peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int idDetail) {
        String sql = "DELETE FROM detail_peminjaman WHERE id_detail=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Get id_buku before delete to restore stok
            DetailPeminjamanModel detail = getById(idDetail);
            
            ps.setInt(1, idDetail);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0 && detail != null && !detail.isStatusKembali()) {
                // Restore stok if buku belum dikembalikan
                bukuDAO.updateStok(detail.getIdBuku(), 1);
            }
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error delete detail peminjaman: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public DetailPeminjamanModel getById(int idDetail) {
        String sql = "SELECT * FROM detail_peminjaman WHERE id_detail=?";
        DetailPeminjamanModel detail = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDetail);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detail = mapResultSetToDetail(rs);
                    
                    // Set buku
                    BukuModel buku = bukuDAO.getById(detail.getIdBuku());
                    detail.setBuku(buku);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return detail;
    }
    
    @Override
    public List<DetailPeminjamanModel> getByPeminjaman(int idPeminjaman) {
        String sql = "SELECT * FROM detail_peminjaman WHERE id_peminjaman=?";
        List<DetailPeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailPeminjamanModel detail = mapResultSetToDetail(rs);
                    
                    // Set buku
                    BukuModel buku = bukuDAO.getById(detail.getIdBuku());
                    detail.setBuku(buku);
                    
                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<DetailPeminjamanModel> getByBuku(int idBuku) {
        String sql = "SELECT * FROM detail_peminjaman WHERE id_buku=?";
        List<DetailPeminjamanModel> list = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailPeminjamanModel detail = mapResultSetToDetail(rs);
                    
                    // Set buku
                    BukuModel buku = bukuDAO.getById(detail.getIdBuku());
                    detail.setBuku(buku);
                    
                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public boolean updateStatusKembali(int idDetail, boolean statusKembali) {
        String sql = "UPDATE detail_peminjaman SET status_kembali=? WHERE id_detail=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, statusKembali);
            ps.setInt(2, idDetail);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                // Get detail to update stok
                DetailPeminjamanModel detail = getById(idDetail);
                if (detail != null) {
                    if (statusKembali) {
                        // Buku dikembalikan, tambah stok
                        bukuDAO.updateStok(detail.getIdBuku(), 1);
                    } else {
                        // Buku dipinjam lagi, kurangi stok
                        bukuDAO.updateStok(detail.getIdBuku(), -1);
                    }
                }
            }
            
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateTanggalKembali(int idDetail, java.util.Date tanggalKembali) {
        String sql = "UPDATE detail_peminjaman SET tanggal_kembali=? WHERE id_detail=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (tanggalKembali != null) {
                ps.setDate(1, new java.sql.Date(tanggalKembali.getTime()));
            } else {
                ps.setNull(1, Types.DATE);
            }
            ps.setInt(2, idDetail);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public int getJumlahBukuDipinjam(int idPeminjaman) {
        String sql = "SELECT COUNT(*) FROM detail_peminjaman WHERE id_peminjaman=? AND status_kembali=0";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public int getJumlahBukuDikembalikan(int idPeminjaman) {
        String sql = "SELECT COUNT(*) FROM detail_peminjaman WHERE id_peminjaman=? AND status_kembali=1";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    private DetailPeminjamanModel mapResultSetToDetail(ResultSet rs) throws SQLException {
        DetailPeminjamanModel detail = new DetailPeminjamanModel();
        detail.setIdDetail(rs.getInt("id_detail"));
        detail.setIdPeminjaman(rs.getInt("id_peminjaman"));
        detail.setIdBuku(rs.getInt("id_buku"));
        detail.setStatusKembali(rs.getBoolean("status_kembali"));
        detail.setTanggalKembali(rs.getDate("tanggal_kembali"));
        detail.setDenda(rs.getDouble("denda"));
        return detail;
    }
}